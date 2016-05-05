package com.Pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6309502132204941217L;
	
	private Boolean success;

	@XmlElement
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
 

}
