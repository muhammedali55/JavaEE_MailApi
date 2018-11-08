package com.muhammet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="indexBean")
@SessionScoped
public class indexBean {

	private String AnaMail;
	private String AnaSifre;
	private String MailList;
	private String MailIcerigi;
	
	public void mailGonder() {
		
		
	}
	
	
	public String getAnaMail() {
		return AnaMail;
	}
	public void setAnaMail(String anaMail) {
		AnaMail = anaMail;
	}
	public String getAnaSifre() {
		return AnaSifre;
	}
	public void setAnaSifre(String anaSifre) {
		AnaSifre = anaSifre;
	}
	public String getMailList() {
		return MailList;
	}
	public void setMailList(String mailList) {
		MailList = mailList;
	}
	public String getMailIcerigi() {
		return MailIcerigi;
	}
	public void setMailIcerigi(String mailIcerigi) {
		MailIcerigi = mailIcerigi;
	}
	
	
	
	
}
