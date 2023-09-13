package com.roca12.apolobot.model;

import java.util.Date;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("rerunapolo")
public class ReRunApolo {

	@Id
	private String id;
	@NotBlank
	private Date reRunDate;
	@NotBlank
	private String reRunKind;

	public ReRunApolo() {
		// TODO Auto-generated constructor stub
	}

	

	public ReRunApolo(String id, Date reRunDate, String reRunKind) {
		super();
		this.id = id;
		this.reRunDate = reRunDate;
		this.reRunKind = reRunKind;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Date getReRunDate() {
		return reRunDate;
	}



	public void setReRunDate(Date reRunDate) {
		this.reRunDate = reRunDate;
	}



	public String getReRunKind() {
		return reRunKind;
	}



	public void setReRunKind(String reRunKind) {
		this.reRunKind = reRunKind;
	}



	@Override
	public String toString() {
		return "ReRunApolo [id=" + id + ", reRunDate=" + reRunDate + ", reRunKind=" + reRunKind + "]";
	}

}
