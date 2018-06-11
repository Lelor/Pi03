package address.model;

import java.sql.SQLException;
import java.util.ArrayList;

import address.services.BD;
import address.services.Dependences;

public class MaintenanceDAO {
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public MaintenanceDAO() {
		bd = new BD();
	}
	
	/**
	 * Conta quantidade de manutenções ativa e fechadas.
	 * @param ativo - Boolean que determina se esta aberta ou fechada.
	 * @return - Quantidade de manutenções.
	 */
	public int countNumMaintenance(boolean ativo, String searchString) {
		
		String sql2 = "SELECT COUNT(m.idInstrumento) AS total FROM manutencao m, instrumento i " + 
				"WHERE m.ativo = ? " + 
				"AND m.idInstrumento = i.idInstrumento " + 
				"AND i.nome LIKE ?";
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
	 * Insere produtos na tabela de manutenção.
	 * @param idsInstruments - ArrayList com ids dos instrumentos.
	 * @return - Retorna boolean para saber se deu certo.
	 */
	public boolean insertMaintenance(ArrayList<Integer> idsInstruments) {
		
		sql = "INSERT INTO manutencao(tipo, dataEntrada, idInstrumento) " + 
				"VALUES(?, getdate(), ?)";
		
		InstrumentDAO inDAO = new InstrumentDAO();
		
		try {
			
			bd.getConnection();
			
			for(int i = 0; i < idsInstruments.size(); i++) {
				bd.st = bd.con.prepareStatement(sql);
			
				bd.st.setString(1,"Periódico");
				bd.st.setInt(2,idsInstruments.get(i));
				bd.st.executeUpdate();
			}
			
			inDAO.changeStatus(3, idsInstruments);
			
			return true;
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println(e.toString());
			
			return false;

		}
		finally {
			bd.close();
		}
	}
		
	
	/**
	 * Lista manuntenções ativas e fechadas.
	 * @param ativo - Boolean que determina se esta aberta ou fechada.
	 * @return - Array do tipo Maintenance.
	 */
	public Maintenance[] listMaintenance(boolean ativo, String searchString) {
		
		sql = "SELECT m.idManutencao AS idM, i.idInstrumento AS idI, i.nome AS instrumento, m.dataEntrada, " + 
				"m.dataSaida, m.tipo, m.valor, m.descricao " + 
				"FROM manutencao m, instrumento i " + 
				"WHERE m.idInstrumento = i.idInstrumento AND m.ativo = ? AND i.nome LIKE ?";
		
		int i = 0;
		int numRow = countNumMaintenance(ativo, searchString);
		Maintenance[] mn = new Maintenance[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setBoolean(1, ativo);
			bd.st.setString(2, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				//idM, idI, instrumento, dataEntrada, dataSaida, tipo, valor, descricao
				mn[i] = new Maintenance();
				mn[i].setIdManutencao(bd.rs.getInt("idM"));
				mn[i].setIdInstrumento(bd.rs.getInt("idI"));
				mn[i].setNomeinstrumento(bd.rs.getString("instrumento"));
				mn[i].setDataEntrada(bd.rs.getString("dataEntrada"));
				mn[i].setDataSaida(bd.rs.getString("dataSaida"));
				mn[i].setTipo(bd.rs.getString("tipo"));
				mn[i].setValor(bd.rs.getBigDecimal("valor"));
				mn[i].setDescricao(bd.rs.getString("descricao"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return mn;
		
	}
	
	/**
	 * Pega valores da manutenção pelo ID.
	 * @param idM - Id da manutenção.
	 * @return - Retroan Objeto Manutenção.
	 */
	public Maintenance getMaintenance(int idM) {
		
		sql = "SELECT * " + 
				"FROM manutencao " + 
				"WHERE idManutencao = ?";
		
		Maintenance mn = new Maintenance();
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, idM);
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {

				//table consult columns -----
				//idManutencao, tipo, dataEntrada, descricao, dataSaida, valor, idInstrumento
				mn.setIdManutencao(bd.rs.getInt("idManutencao"));
				mn.setTipo(bd.rs.getString("tipo"));
				mn.setDataEntrada(bd.rs.getString("dataEntrada"));
				mn.setDataSaida(bd.rs.getString("dataSaida"));
				mn.setDescricao(bd.rs.getString("descricao"));
				mn.setValor(bd.rs.getBigDecimal("valor"));
				mn.setIdInstrumento(bd.rs.getInt("idInstrumento"));
				
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return mn;
		
	}
	
	/**
	 * Atualiza dados da tabela de manutenção.
	 * @param mn - Objeto manutenção.
	 * @return - Retorna mensagem.
	 */
	public String updateMaintenance(Maintenance mn) {
		
		//verifica se é fechamento ou só atualização
		if(mn.isAtivo()) {
			//se verdadeiro é só atualização
			sql = "UPDATE manutencao SET tipo = ?, valor = ?, descricao = ?, ativo = ? WHERE idManutencao = ?";
			
		}else {
			//se falso é fechamento
			sql = "UPDATE manutencao SET tipo = ?, valor = ?, descricao = ?, dataSaida = GETDATE(), ativo = ? WHERE idManutencao = ?";
		}
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, mn.getTipo());
			bd.st.setBigDecimal(2, mn.getValor());
			bd.st.setString(3, mn.getDescricao());
			bd.st.setBoolean(4, mn.isAtivo());
			bd.st.setInt(5, mn.getIdManutencao());
			
			bd.st.executeUpdate();
			msg = "Manutenção atualizada atualizado! =]";
			
			if(!mn.isAtivo()) {
				
				InstrumentDAO in =  new InstrumentDAO();
				in.changeStatusUnico(1, mn.getIdInstrumento());
				
			}
			
		} catch (SQLException  e) {
			
			msg = "na atualização! =[";
			System.out.println("Erro DAO: insersao de dados " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
		
	}

}
