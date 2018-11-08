package com.muhammet;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailIslemleri {

	private Properties props = new Properties();
	
	/**
	 * MailIslemleri Constructor
	 *  mail g�nderme i�lemleri i�in ayar verilerinin set edilmesi gerkemektedir.
	 * @param auth : mail.smtp.auth de�erini set eder ve true/false de�eri al�r.
	 * @param starttls : mail.smtp.starttls.enable de�erini set eder true/false de�eri al�r.
	 * @param host : mail.smtp.host de�erini set eder, mail at�lacak adresin smtp adresini ister
	 * @param port : mail.smtp.port de�erini set eder, mail sunucusunun port adresini ister.
	 */
	public MailIslemleri(String auth, String starttls, String host,String port) {
		auth = auth == null ? "true" : auth;
		starttls = starttls == null ? "true" : starttls;
		host = host == null ? "smtp.gmail.com" : host;
		port = port == null ? "587" : port;		
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);		
	}
	
	/**
	 * 
	 * @param username : Mail G�nderilecek olan y�netici adresin kullan�c� ad�. �rn: "muhammetalikaya"
	 * @param password : Y�netici mailin giri� �ifresi
	 * @param email : mail g�nderilecek e-mai adresi
	 * @param baslik : mail g�nderilecek i�eri�in ba�l���
	 * @param icerik : maile ait konu i�eri�i
	 * @return : mail i�lemleri yap�ld�ktan sonra sonucunu d�nderir vaha ver ise "HATA...: hata nedeni" �eklinde uyar� verir.
	 */
	public String mailGonder(String username, String password, String email,String baslik, String icerik) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username+"@gmail.com"));
			message.setSubject(baslik);
			message.setText(icerik);
			Transport.send(message);
			return "Mail G�nderildi.";
		} catch (MessagingException e) {
			return "HATA......: "+e.toString();
			
		}
		
	}
	
	
}
