package address.services;

import java.util.Random;

public class Utilities {
	
	public String randomName() {
	    Random r = new Random(); // just create one and keep it around
	    String alphabet = "abcdefghijklmnopqrstuvwxyz";

	    final int N = 20;
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < N; i++) {
	        sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
	    }
	    String randomName = sb.toString();

	    return randomName;
	}
}
