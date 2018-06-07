package address.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {

	private final String LOGIN = "sa";
	private final String SENHA = "qwe123";
	private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String DATABASE = "pi03";
	private final String URL = "jdbc:sqlserver://localhost:1433;databasename=" + DATABASE ;
	public Connection con = null;
	public PreparedStatement st = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	
	/**
	 * Realiza conexão com o banco de dados.
	 * @return - Retorna verdadeiro se conectar.
	 */
	public boolean getConnection() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,LOGIN, SENHA);
			System.out.println("Conectou");
			return true;
		}
		catch(ClassNotFoundException erro) 
		{
			System.out.println("Driver não encontrado");
			return false;
		}
		catch(SQLException erro) 
		{
			System.out.println(erro.toString());
			return false;
		}
		
	}
	
	/**
	 * Fecha conexão com o banco de dados.
	 */
	public void close() {
		try { if(rs!=null) rs.close();} catch(SQLException erro) {}
		try { if(st!=null) st.close();} catch(SQLException erro) {}
		try {
			if(con!=null) 
			{
			con.close();
			System.out.println("Desconectou");
		    }
		}
		catch(SQLException erro) {}
     }
	
	public static void main(String[] args) {
		BD bd = new BD();
		
		bd.getConnection();
		bd.close();
	}
}
	
	
	
	
	
