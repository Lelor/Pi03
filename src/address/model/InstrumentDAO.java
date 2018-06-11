package address.model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import address.services.BD;
import address.services.Dependences;

public class InstrumentDAO {
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public InstrumentDAO() {
		bd = new BD();
	}
	
	/**
	 * Conta numero de linhas que a tabela instrumento possui.
	 * @return - retorna o total de linhas.
	 */
	public int countNumInstrument(String searchString) {
		
		String sql2 = "SELECT COUNT(idInstrumento) AS total FROM instrumento WHERE ativo = 1 AND nome like ?";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
			bd.st.setString(1, "%" + searchString + "%");
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
	 * Insere instrumento no banco.
	 * @param in - Instrumento a ser inserido.
	 * @return - Mensagem de erro ou sucesso.
	 */
	public String insertInstrument(Instrument in) {
		
		Dependences dp = new Dependences();
		
		// retorna id para salvar no banco ------
		int idCorIn   	 = dp.propInstrument(in.getCor(), "cor");
		int idTipoIn  	 = dp.propInstrument(in.getTipo(), "tipo");
		int idMarcaIn 	 = dp.propInstrument(in.getMarca(), "marca");
		int idFornecedor = dp.idFornecFromName(in.getNomeFornecedor());
		
		sql = "INSERT INTO "
				+ "instrumento(numSerie, nome, valorCompra, valorLocacao, ano, foto, idFornecedor, idCor, idTipo, idMarca)"
				+ " VAlUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			
			bd.st.setInt(1, in.getNumSerie());
			bd.st.setString(2, in.getNome());
			bd.st.setBigDecimal(3, in.getvalorCompra());
			bd.st.setBigDecimal(4, in.getValorLocacao());
			bd.st.setString(5, in.getAno());
			bd.st.setString(6, in.getFoto());
			bd.st.setInt(7, idFornecedor);
			bd.st.setInt(8, idCorIn);
			bd.st.setInt(9, idTipoIn);
			bd.st.setInt(10, idMarcaIn);
			
			bd.st.executeUpdate();
			
			msg = "Instrumento cadastrado com sucesso! =]";
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Retorna uma array contendo todos os intrumentos listados.
	 * @return in - array de instrumentos.
	 */
	public Instrument[] listInstrument(String searchString) {
		
		sql = "SELECT idInstrumento, numSerie, i.nome as nomeI, valorCompra, valorLocacao, ano, foto, statusIn as status,"
				+ " f.nome as nomeF, c.nome AS nomeC, t.nome as nomeT, m.nome as nomeM"
				+ " FROM instrumento i, cor c, tipo t, marca m, fornecedor f"
				+ " WHERE i.idCor = c.id AND i.idTipo = t.id AND i.idMarca = m.id AND i.idFornecedor = f.idFornecedor AND i.ativo = 1 AND i.nome LIKE ?";
		
		int i = 0;
		int numRow = countNumInstrument(searchString);
		Instrument[] in = new Instrument[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				//idInstrumento, numSerie, nomeI, valorCompra, valorLocacao, ano, status, nomeF, nomeC, nomeT, nomeM
				in[i] = new Instrument();
				in[i].setId(bd.rs.getInt("idInstrumento"));
				in[i].setNumSerie(bd.rs.getInt("numSerie"));
				in[i].setNome(bd.rs.getString("nomeI"));
				in[i].setvalorCompra(bd.rs.getBigDecimal("valorCompra"));
				in[i].setValorLocacao(bd.rs.getBigDecimal("valorLocacao"));
				in[i].setAno(bd.rs.getString("ano"));
				in[i].setFoto(bd.rs.getString("foto"));
				in[i].setStatusId(bd.rs.getInt("status"));
				in[i].setNomeFornecedor(bd.rs.getString("nomeF"));
				in[i].setCor(bd.rs.getString("nomeC"));
				in[i].setTipo(bd.rs.getString("nomeT"));
				in[i].setMarca(bd.rs.getString("nomeM"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return in;
		
	}
	
	/**
	 * Retorna um instrumento com um id especifico.
	 * @param idInstrument - id do instrumento
	 * @return - retorna um instrumento.
	 */
	public Instrument getInstrument(int idInstrument) {
		
		sql = "SELECT idInstrumento, numSerie, i.nome as nomeI, valorCompra, valorLocacao, ano, foto, statusIn as status,"
				+ " f.nome as nomeF, c.nome AS nomeC, t.nome as nomeT, m.nome as nomeM"
				+ " FROM instrumento i, cor c, tipo t, marca m, fornecedor f"
				+ " WHERE i.idCor = c.id AND i.idTipo = t.id AND i.idMarca = m.id AND i.idFornecedor = f.idFornecedor AND i.idInstrumento = ?";
		
		Instrument in = new Instrument();
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idInstrument);
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {

				//table consult columns -----
				//idInstrumento, numSerie, nomeI, valorCompra, valorLocacao, ano, status, nomeF, nomeC, nomeT, nomeM
				in.setId(bd.rs.getInt("idInstrumento"));
				in.setNumSerie(bd.rs.getInt("numSerie"));
				in.setNome(bd.rs.getString("nomeI"));
				in.setvalorCompra(bd.rs.getBigDecimal("valorCompra"));
				in.setValorLocacao(bd.rs.getBigDecimal("valorLocacao"));
				in.setAno(bd.rs.getString("ano"));
				in.setFoto(bd.rs.getString("foto"));
				in.setStatusId(bd.rs.getInt("status"));
				in.setNomeFornecedor(bd.rs.getString("nomeF"));
				in.setCor(bd.rs.getString("nomeC"));
				in.setTipo(bd.rs.getString("nomeT"));
				in.setMarca(bd.rs.getString("nomeM"));
				
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return in;
		
	}
	
	/**
	 * Atualiza instrumento no banco de dados.
	 * @param in - Objeto Instruemento para ser atualizado.
	 * @return - Retorna mensagem de erro ou sucesso.
	 */
	public String updateInstrument(Instrument in) {
		
		Dependences dp = new Dependences();
		
		// retorna id para salvar no banco ------
		int idCorIn   	 = dp.propInstrument(in.getCor(), "cor");
		int idTipoIn  	 = dp.propInstrument(in.getTipo(), "tipo");
		int idMarcaIn 	 = dp.propInstrument(in.getMarca(), "marca");
		int idFornecedor = dp.idFornecFromName(in.getNomeFornecedor());
		
		sql = "UPDATE instrumento SET nome = ?, valorCompra = ?, valorLocacao = ?, ano = ?, "
				+ "foto = ?, idFornecedor = ?, idCor = ?, idTipo = ?, idMarca = ? "
				+ "WHERE idInstrumento = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, in.getNome());
			bd.st.setBigDecimal(2, in.getvalorCompra());
			bd.st.setBigDecimal(3, in.getValorLocacao());
			bd.st.setString(4, in.getAno());
			bd.st.setString(5, in.getFoto());
			bd.st.setInt(6, idFornecedor);
			bd.st.setInt(7, idCorIn);
			bd.st.setInt(8, idTipoIn);
			bd.st.setInt(9, idMarcaIn);
			bd.st.setInt(10, in.getId());
			
			bd.st.executeUpdate();
			msg = "Instrumento atualizado! =]";
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println("Erro DAO: insersao de dados " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Exclui instrumento mudando sua flag 'ativo' para false.
	 * @param id - Id do instrumento que será excluído.
	 * @return - retorna a decisão do usuário através de um JOptionPane.showConfirmDialog.
	 */
	public int excludeInstrument(int id) {
		
		sql = "UPDATE instrumento SET ativo = ? WHERE idInstrumento = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setBoolean(1, false);
			bd.st.setInt(2, id);
			
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog (
					null, "Essa ação é irreversível!\nDeseja mesmo excluir este instrumento?","Alerta!", dialogButton
			);
			if(dialogResult == JOptionPane.YES_OPTION){
				decision = 1;
				bd.st.executeUpdate();
			}else {
				decision = 0;
			}
			
		} catch (SQLException  e) {
			
			System.out.println("Erro DAO: exclusao do instrumento " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return decision;
	}
	
	/**
	 * Muda o status de varios instrumentos.
	 * @param status - 1: Disponível | 2: Locado | 3: Manutenção.
	 * @param idsInstruments - ArrayList contendo os Ids dos instrumentos que mudaram o status.
	 * @return retorna true ou false para tratar erro.
	 */
	public boolean changeStatus(int status, ArrayList<Integer> idsInstruments) {
		
		// muda status do instrumento para Locado
		sql = "UPDATE instrumento SET statusIn = ? WHERE idInstrumento = ?";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			
			// roda loop para mudar status do instrumento para locado
			for(int i = 0; i < idsInstruments.size(); i++) {
				
				bd.st.setInt(1, status);
				bd.st.setInt(2, idsInstruments.get(i));
				
				bd.st.executeUpdate();
        	}
			
			return true;
			
		} catch (SQLException e) {
			System.out.println("ERRO3: Falha ao mudar status do instrumento Instrumentos: " + e.toString());
			return false;
		}
	}
	
	/**
	 * Muda o status de um unico instrumento.
	 * @param status - 1: Disponível | 2: Locado | 3: Manutenção.
	 * @param idsInstruments - Id do instrumento que mudara o status.
	 * @return retorna true ou false para tratar erro.
	 */
	public boolean changeStatusUnico(int status, int idInstrument) {
		
		// muda status do instrumento para Locado
		sql = "UPDATE instrumento SET statusIn = ? WHERE idInstrumento = ?";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, status);
			bd.st.setInt(2, idInstrument);
			bd.st.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			System.out.println("ERRO3: Falha ao mudar status do instrumento Instrumentos: " + e.toString());
			return false;
		}
	}
}
