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
	 *  mail gönderme iþlemleri için ayar verilerinin set edilmesi gerkemektedir.
	 * @param auth : mail.smtp.auth deðerini set eder ve true/false deðeri alýr.
	 * @param starttls : mail.smtp.starttls.enable deðerini set eder true/false deðeri alýr.
	 * @param host : mail.smtp.host deðerini set eder, mail atýlacak adresin smtp adresini ister
	 * @param port : mail.smtp.port deðerini set eder, mail sunucusunun port adresini ister.
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
	 * @param username : Mail Gönderilecek olan yönetici adresin kullanýcý adý. örn: "muhammetalikaya"
	 * @param password : Yönetici mailin giriþ þifresi
	 * @param email : mail gönderilecek e-mai adresi
	 * @param baslik : mail gönderilecek içeriðin baþlýðý
	 * @param icerik : maile ait konu içeriði
	 * @return : mail iþlemleri yapýldýktan sonra sonucunu dönderir vaha ver ise "HATA...: hata nedeni" þeklinde uyarý verir.
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
			return "Mail Gönderildi.";
		} catch (MessagingException e) {
			return "HATA......: "+e.toString();
			
		}
		
	}
	
	
}
