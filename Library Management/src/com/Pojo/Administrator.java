package com.Pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Administrator")
public class Administrator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2577229142338796573L;
	
	
	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	private String userName;
	
	private String password;
    @XmlElement
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@XmlElement 
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
