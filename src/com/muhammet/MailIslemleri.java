package com.muhammet;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(baslik);
			message.setText(icerik);
			Transport.send(message);
			return "Mail Gönderildi.";
		} catch (MessagingException e) {
			return "HATA......: "+e.toString();
			
		}
		
	}
	
	public void mailAlma(String pop3Host, String storeType, String user,
		      String password) {
		try {
	         // create properties field
	         Properties properties = new Properties();
	         properties.put("mail.store.protocol", "pop3");
	         properties.put("mail.pop3.host", pop3Host);
	         properties.put("mail.pop3.port", "995");
	         properties.put("mail.pop3.starttls.enable", "true");
	         Session emailSession = Session.getDefaultInstance(properties);
	         // emailSession.setDebug(true);

	         // create the POP3 store object and connect with the pop server
	         Store store = emailSession.getStore("pop3s");

	         store.connect(pop3Host, user, password);

	         // create the folder object and open it
	         Folder emailFolder = store.getFolder("INBOX");
	         emailFolder.open(Folder.READ_ONLY);

	         BufferedReader reader = new BufferedReader(new InputStreamReader(
		      System.in));

	         // retrieve the messages from the folder in an array and print it
	         Message[] messages = emailFolder.getMessages();
	         System.out.println("messages.length---" + messages.length);

	         for (int i = 0; i < messages.length; i++) {
	            Message message = messages[i];
	            System.out.println("---------------------------------");
	            writePart(message);
	            System.out.println("Devam Etemek Ýçin YES/NO seçiniz...: ");
	            String line = reader.readLine();
	            if ("YES".equals(line)) {
	               message.writeTo(System.out);
	            } else if ("NO".equals(line)) {
	               break;
	            }
	         }

	         // close the store and folder objects
	         emailFolder.close(false);
	         store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
	}
	
	public void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	             writeEnvelope((Message) p);

	      System.out.println("----------------------------");
	      System.out.println("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	         System.out.println("This is plain text");
	         System.out.println("---------------------------");
	         System.out.println((String) p.getContent());
	      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	         System.out.println("This is a Multipart");
	         System.out.println("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	         System.out.println("This is a Nested Message");
	         System.out.println("---------------------------");
	         writePart((Part) p.getContent());
	      } 
	      //check if the content is an inline image
	      else if (p.isMimeType("image/jpeg")) {
	         System.out.println("--------> image/jpeg");
	         Object o = p.getContent();

	         InputStream x = (InputStream) o;
	         // Construct the required byte array
	         byte[] bArray = new byte[x.available()];
	         System.out.println("x.length = " + x.available());
	         int i=0;
	         while ((i = (int) ((InputStream) x).available()) > 0) {
	            int result = (int) (((InputStream) x).read(bArray));
	            if (result == -1)
	            	          break;
	         }
	         FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
	         f2.write(bArray);
	      } 
	      else if (p.getContentType().contains("image/")) {
	         System.out.println("content type" + p.getContentType());
	         File f = new File("image" + new Date().getTime() + ".jpg");
	         DataOutputStream output = new DataOutputStream(
	            new BufferedOutputStream(new FileOutputStream(f)));
	            com.sun.mail.util.BASE64DecoderStream test = 
	                 (com.sun.mail.util.BASE64DecoderStream) p
	                  .getContent();
	         byte[] buffer = new byte[1024];
	         int bytesRead;
	         while ((bytesRead = test.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	         }
	      } 
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	         } 
	         else if (o instanceof InputStream) {
	            System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(c);
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	         }
	      }

	   }

	public static void writeEnvelope(Message m) throws Exception {
	      System.out.println("This is the message envelope");
	      System.out.println("---------------------------");
	      Address[] a;

	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      if (m.getSubject() != null)
	         System.out.println("SUBJECT: " + m.getSubject());

	   }
	
	
	
}
