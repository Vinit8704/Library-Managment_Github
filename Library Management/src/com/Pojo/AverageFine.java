package com.Pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AverageFine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2091175554281333277L;
	
	private float fine;

	@XmlElement
	public float getFine() {
		return fine;
	}

	public void setFine(float fine) {
		this.fine = fine;
	}

}
