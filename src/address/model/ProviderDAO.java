package address.model;

import java.sql.SQLException;

import address.services.BD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProviderDAO {

	private String sql, msg;
	private BD bd;
	
	public ProviderDAO() {
		bd = new BD();
	}
}
