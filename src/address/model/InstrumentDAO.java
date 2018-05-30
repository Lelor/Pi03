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
	 * Conta numero de linhas que a tabela instrumento possui.
	 * @return - retorna o total de linhas.
	 */
	public int countNumInstrument() {
		
		String sql2 = "SELECT COUNT(idInstrumento) AS total FROM instrumento WHERE ativo = 1";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
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
	
	/**
	 * Retorna uma array contendo todos os intrumentos listados.
	 * @return in - array de instrumentos.
	 */
	public Instrument[] listInstrument() {
		
		sql = "SELECT idInstrumento, numSerie, i.nome as nomeI, valorCompra, valorLocacao, ano, statusIn as status,"
				+ " f.nome as nomeF, c.nome AS nomeC, t.nome as nomeT, m.nome as nomeM"
				+ " FROM instrumento i, cor c, tipo t, marca m, fornecedor f"
				+ " WHERE i.idCor = c.id AND i.idTipo = t.id AND i.idMarca = m.id AND i.idFornecedor = f.idFornecedor AND i.ativo = 1";
		
		int i = 0;
		int numRow = countNumInstrument();
		Instrument[] in = new Instrument[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
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
				in[i].setStatus(bd.rs.getInt("status"));
				in[i].setNomeFornecedor(bd.rs.getString("nomeF"));
				in[i].setCor(bd.rs.getString("nomeC"));
				in[i].setTipo(bd.rs.getString("nomeT"));
				in[i].setMarca(bd.rs.getString("nomeM"));
				
//				System.out.println(in[inCount].getId());
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

}
