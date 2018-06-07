package address.test;

import address.services.BD;

public class BdTeste {

	public static void main(String[] args) {
		
		BD bd = new BD();
		bd.getConnection();
		bd.close();
	}

}
