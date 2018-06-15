package address.model;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import address.services.BD;

public class RentDAO {
	
	private String sql, msg;
	private int decision, id;
	private BD bd;
	
	public RentDAO() {
		bd = new BD();
	}
	
	/**
	 * Conta numero de locações ativas ou inativas.
	 * @param ativo - Boolean que define qual tipo de locação que será contado.
	 * @return - Retorna numero de locações.
	 */
	public int countNumRent(boolean ativo, String searchString) {
		
		String sql2 = "SELECT COUNT(idLocacao) AS total FROM locacao l, cliente c " + 
				"WHERE l.ativo = ? " + 
				"AND l.idCliente = c.idCliente " + 
				"AND c.nome LIKE ?";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
			bd.st.setBoolean(1, ativo);
			bd.st.setString(2, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()){
				countRow = bd.rs.getInt("total");
			}
			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return countRow;
	}
	
	/**
	 * Conta o numero de instrumentos que tem em determinada locação.
	 * @param idRent - Id da locação.
	 * @return Retorna a quantidade de instrumentos locados
	 */
	public int countNumInstrumentInRent(int idRent) {
		
		String sql2 = "SELECT COUNT(idInstrumentoLocacao) AS total FROM locado WHERE idLocacao = ?";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
			bd.st.setInt(1, idRent);
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()){
				countRow = bd.rs.getInt("total");
			}
			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return countRow;
	}
	
	/**
	 * Realiza locação de instrumentos.
	 * @param rt - Objeto Rent.
	 * @return - Retorna mensagem de alerta.
	 */
	public String insertRent(Rent rt) {
		
		int idLocacao;
		
		sql = "INSERT INTO locacao (desconto, descricao, pago, idFuncionario, idCliente) " + 
				"VALUES (?, ?, ?, ?, ?)";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			bd.st.setBigDecimal(1, rt.getDesconto());
			bd.st.setString(2, rt.getDescricao());
			bd.st.setBoolean(3, rt.isPago());
			bd.st.setInt(4, rt.getIdFuncionario());
			bd.st.setInt(5, rt.getIdCliente());

			bd.st.executeUpdate();
			
			bd.rs = bd.st.getGeneratedKeys();
			bd.rs.next();
			
			//pega ultima id inserida
			idLocacao = bd.rs.getInt(1);
			
			// registra instrumentos
			sql = "INSERT INTO locado (dataDevolucao, idInstrumento, idLocacao) " + 
					"VALUES (?, ?, ?)";
			
			try {
				
				bd.st = bd.con.prepareStatement(sql);
				
				// roda loop para cadastrar os instrumentos com suas respectivas datas
				for(int i = 0; i < rt.getIdInstrumentList().size(); i++) {

					//convert LocalDate to date sql
					Date date = Date.valueOf(rt.getDataDevolucaoList().get(i));
					
					bd.st.setDate(1, date);
					bd.st.setInt(2, rt.getIdInstrumentList().get(i));
					bd.st.setInt(3, idLocacao);
					
					bd.st.executeUpdate();
            	}
				
				//muda status dos instrumentos locados
				InstrumentDAO in = new InstrumentDAO();
				
				if(in.changeStatus(2, rt.idInstrumentList)) {
					msg = "Locação efetuada com sucesso! =]";
				}else{
					msg = "Falha na locação! =[";
				}
				
				
			} catch (SQLException e) {
				msg = "Falha no cadastro! =[";
				System.out.println("ERRO2: Falha ao registrar Instrumentos: " + e.toString());
			}
			
		} catch (SQLException  e) {
			msg = "Falha no cadastro! =[";
			System.out.println("ERRO1: Falha ao registrar dados de locação: " + e.toString());
		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Lista locações em aberto e fechadas.
	 * @param ativo - se a locação está ativa ou nao.
	 * @return - ArrayList de locações.
	 */
	public Rent[] listRent(boolean ativo, String searchString) {
		
		sql = "SELECT l.idLocacao, l.dataRealizacao, l.dataTermino, l.desconto, l.descricao, l.pago, " + 
				"l.idFuncionario, l.idCliente, c.nome as nomeCliente, f.nome as nomeFuncionario " + 
				"FROM locacao l, cliente c, funcionario f " + 
				"WHERE l.idCliente = c.idCliente AND l.idFuncionario = f.idFuncionario AND l.ativo = ? AND c.nome LIKE ? " + 
				"ORDER BY l.dataRealizacao DESC";
		
		int i = 0;
		int numRow = countNumRent(ativo, searchString);
		Rent[] rt = new Rent[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setBoolean(1, ativo);
			bd.st.setString(2, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				// idLocacao, dataRealizacao, dataTermino, desconto, descricao, pago, idFuncionario, idCliente, 
				//nomeCliente, nomeFuncionario
				rt[i] = new Rent();
				rt[i].setIdLocacao(bd.rs.getInt("idLocacao"));
				rt[i].setDataRealizacao(bd.rs.getString("dataRealizacao"));
				rt[i].setDataTermino(bd.rs.getString("dataTermino"));
				rt[i].setDesconto(bd.rs.getBigDecimal("desconto"));
				rt[i].setDescricao(bd.rs.getString("descricao"));
				rt[i].setPago(bd.rs.getBoolean("pago"));
				rt[i].setIdFuncionario(bd.rs.getInt("idFuncionario"));
				rt[i].setIdCliente(bd.rs.getInt("idCliente"));
				rt[i].setNomeCliente(bd.rs.getString("nomeCliente"));
				rt[i].setNomeFuncionario(bd.rs.getString("nomeFuncionario"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return rt;
		
	}
	
	/**
	 * Pega dados de uma locação.
	 * @param idRent - Id da locação.
	 * @return - Objeto Rent.
	 */
	public Rent getRent(int idRent) {
		
		sql = "SELECT l.idLocacao, l.dataRealizacao, l.dataTermino, l.desconto, l.descricao, l.pago, " + 
				"l.idFuncionario, l.idCliente, c.nome as nomeCliente, f.nome as nomeFuncionario " + 
				"FROM locacao l, cliente c, funcionario f " + 
				"WHERE l.idCliente = c.idCliente AND l.idFuncionario = f.idFuncionario AND l.idLocacao = ?";
		
		Rent rt = new Rent();
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idRent);
			bd.rs = bd.st.executeQuery();
			bd.rs.next();
			
			// idLocacao, dataRealizacao, dataTermino, desconto, descricao, pago, idFuncionario, idCliente, 
			//nomeCliente, nomeFuncionario
			rt = new Rent();
			rt.setIdLocacao(bd.rs.getInt("idLocacao"));
			rt.setDataRealizacao(bd.rs.getString("dataRealizacao"));
			rt.setDataTermino(bd.rs.getString("dataTermino"));
			rt.setDesconto(bd.rs.getBigDecimal("desconto"));
			rt.setDescricao(bd.rs.getString("descricao"));
			rt.setPago(bd.rs.getBoolean("pago"));
			rt.setIdFuncionario(bd.rs.getInt("idFuncionario"));
			rt.setIdCliente(bd.rs.getInt("idCliente"));
			rt.setNomeCliente(bd.rs.getString("nomeCliente"));
			rt.setNomeFuncionario(bd.rs.getString("nomeFuncionario"));
			
		} catch (SQLException e) {
			
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return rt;
	}
	
	/**
	 * Lista Instrumentos de determinada locação.
	 * @param idRent - Id da locação.
	 * @return - Retrona um array do objeto Rent.
	 */
	public Rent[] listInstrumentsRent(int idRent) {
		
		//seleciona instrumentos atrelados a locação 
		sql = "SELECT l.idInstrumento, i.nome as instrumento, l.dataDevolucao, i.valorLocacao, l.multa " + 
				"FROM locado l,instrumento i " + 
				"WHERE l.idInstrumento = i.idInstrumento AND l.idLocacao = ?";
		
		int i = 0;
		int numRow = countNumInstrumentInRent(idRent);
		Rent[] rt = new Rent[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idRent);
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				//idInstrumento, instrumento, dataDevolucao, valorLocacao, multa
				rt[i] = new Rent();
				rt[i].setIdInstrument(bd.rs.getInt("idInstrumento"));
				rt[i].setNomeInstrumento(bd.rs.getString("instrumento"));
				rt[i].setDataDevolucao(bd.rs.getString("dataDevolucao"));
				rt[i].setValorLocacao(bd.rs.getBigDecimal("valorLocacao"));
				rt[i].setMulta(bd.rs.getBigDecimal("multa"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			System.out.println("ERRO: SQL: " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return rt;
	}
	
	/**
	 * Pega dados dos instruementos atrelados a uma locação.
	 * @param idRent - Id da locação.
	 * @param idInstrument - Id do instrumento.
	 * @return - Dados do instrumento.
	 */
	public Rent getInstrumentRent(int idRent, int idInstrument) {
		
		sql = "SELECT l.idInstrumento, l.dataDevolucao, l.dataDevolucaoEfetiva, l.observacao, l.multa, i.valorLocacao " + 
				"FROM locado l,instrumento i " + 
				"WHERE l.idInstrumento = i.idInstrumento AND l.idLocacao = ? AND l.idInstrumento = ?";
		
		Rent rt = new Rent();
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idRent);
			bd.st.setInt(2, idInstrument);
			bd.rs = bd.st.executeQuery();
			bd.rs.next();
			
			// idInstrumento, dataDevolucao, dataDevolucaoEfetiva, observacao, multa, valorLocacao
			rt = new Rent();
			rt.setIdInstrument(bd.rs.getInt("idInstrumento"));
			rt.setDataDevolucao(bd.rs.getString("dataDevolucao"));
			rt.setDataDevolucaoEfetiva(bd.rs.getString("dataDevolucaoEfetiva"));
			rt.setObservacao(bd.rs.getString("observacao"));
			rt.setMulta(bd.rs.getBigDecimal("multa"));
			rt.setValorLocacao(bd.rs.getBigDecimal("valorLocacao"));
			
		} catch (SQLException e) {
			
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return rt;
	}
	
	/**
	 * Verifica se ainda há instrumentos para serem devolvidos.
	 * @param idRent - Id da locação.
	 * @return Verdaeiro ou falso.
	 */
	public boolean verifyInstrumentsReturn(int idRent) {
		
		sql = "SELECT dataDevolucaoEfetiva " + 
				"FROM locacao, locado " + 
				"WHERE locacao.idLocacao = locado.idLocacao AND locacao.idLocacao = ? AND locado.dataDevolucaoEfetiva IS NULL";
		
		boolean have = false;
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idRent);
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {
				have = true;
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return have;
	}
	
	/**
	 * Realiza a devolução do instrumento.
	 * @param rt - Objeto locação.
	 * @param manutencao - Se o instrumento irá para manutenção ou não.
	 * @return - Mensaegm.
	 */
	public String updateInstrumentsRent(Rent rt, boolean manutencao) {
		
		InstrumentDAO inDAO = new InstrumentDAO();
		
		sql = "UPDATE locado SET dataDevolucaoEfetiva = getdate(), observacao = ?, multa = ? " + 
				"WHERE idLocacao = ? AND idInstrumento = ?";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, rt.getObservacao());
			bd.st.setBigDecimal(2, rt.getMulta());
			bd.st.setInt(3, rt.getIdLocacao());
			bd.st.setInt(4, rt.getIdInstrument());
			
			bd.st.executeUpdate();

			if(manutencao) {
				
				sql = "INSERT INTO manutencao(tipo, dataEntrada, idInstrumento) " + 
						"VALUES (?, getdate(), ?)";
				
				try {
					
					bd.st = bd.con.prepareStatement(sql);
					
					bd.st.setString(1, "Periódico");
					bd.st.setInt(2, rt.getIdInstrument());
					bd.st.executeUpdate();
					
					inDAO.changeStatusUnico(3, rt.getIdInstrument());
					
					msg = "Instrumento devolvido com sucesso! =]";
					
				} catch (Exception e) {
					msg = "Ocorreu algum erro! =[";
					System.out.println("ERRO2 manutenção" + e.toString());
				}
				
			}else {
				
				inDAO.changeStatusUnico(1, rt.getIdInstrument());
				msg = "Instrumento devolvido com sucesso! =]";
				
			}
			
		} catch (SQLException e) {
			
			msg = "Erro ao devolver o instrumento! =[";
			System.out.println("ERRO1: UPDATE: " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Atualiza / encerra locação.
	 * @param idRent - Id da locação
	 * @return
	 */
	public String updateRent(int idRent) {
		
		sql = "UPDATE locacao set dataTermino = GETDATE(), pago = 1, ativo = 0 " + 
				"WHERE idLocacao = ?";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idRent);
			
			bd.st.executeUpdate();
				
			msg = "Locação fechada com sucesso! =]";
			
		} catch (SQLException e) {
			
			msg = "Erro ao fechar locação o instrumento! =[";
			System.out.println("ERRO1: UPDATE: " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
}
