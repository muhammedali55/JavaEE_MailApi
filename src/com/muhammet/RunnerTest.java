package com.muhammet;

public class RunnerTest {

	public static void main(String[] args) {
		 String host = "pop.gmail.com";// change accordingly
	      String mailStoreType = "pop3";
	      String username = 
	         "vektoreljavaali";// change accordingly
	      String password = "vektorel2004";// change accordingly

	      
	      
	      
	      MailIslemleri mail = new MailIslemleri(null, null, null, null);
	      //Call method fetch
	    
	      mail.mailGonder(username, password, "muhammedali55@gmail.com", "Java Mail APÝ Deneme", 
	    		  "Ýçerik olarak java mail api denemesi için oluþturulmuþtur.");
	      
	      //mail.mailAlma(host, mailStoreType, username, password);
	}
}
