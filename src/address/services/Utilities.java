package address.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Utilities {
	
	/**
	 * Gera um nome randomico.
	 * @return - Nome randomico.
	 */
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
	
	/**
	 * Formata data para exibição.
	 * @param date - Data para formatação.
	 * @return - Data formatada.
	 */
	public String dateFormatView(String date) {
		
		LocalDate dateFormat = LocalDate.parse(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		
		return formatter.format(dateFormat);  
		
	}
	
	/**
	 * Converte datas visuais para o formato LocalDate.
	 * @param date - Data a ser convertida.
	 * @return - Data convertida.
	 */
	public LocalDate dateFormatLocalDate(String date) {
		
		Date dateParse = null;
		
		try {
			dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (Exception e) {
			System.out.println("Erro ao converter data!");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = formatter.format(dateParse);
		
		LocalDate dateFormated = LocalDate.parse(newDate);
		
		return dateFormated;  
		
	}
}
