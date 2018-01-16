package com.iat.epoints.ingest.service.beans;

import java.util.Date;

public class EmailUpdateTO {
	
	private String email;
	private Date updatedAt;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}