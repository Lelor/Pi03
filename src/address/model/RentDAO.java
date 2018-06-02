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
				for(int i = 0; i < rt.getIdInstrument().size(); i++) {

					//convert LocalDate to date sql
					Date date = Date.valueOf(rt.getDataDevolucao().get(i));
					
					bd.st.setDate(1, date);
					bd.st.setInt(2, rt.getIdInstrument().get(i));
					bd.st.setInt(3, idLocacao);
					
					bd.st.executeUpdate();
            	}
				
				//muda status dos instrumentos locados
				InstrumentDAO in = new InstrumentDAO();
				
				if(in.changeStatus(2, rt.idInstrument)) {
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
}
