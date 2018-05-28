package address.model;

import java.sql.SQLException;

import address.services.BD;
import address.services.Dependences;

public class InstrumentDAO {
	
	private String sql, msg;
	private BD bd;
	
	public InstrumentDAO() {
		bd = new BD();
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
			
			msg = "Instrumento cadastrado! =]";
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	} 
	
	public Instrument listInstruments() {
		
		Instrument in = new Instrument();
		
		
		
		return in;
	}
}
