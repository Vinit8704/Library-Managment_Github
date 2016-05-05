package com.Pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Publisher")
public class Publisher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3946428390593897545L;

	private int publisherId;
	
	private String publisherAddress;
	
	private String publisherName;
   
	@XmlElement
	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	@XmlElement
	public String getPublisherAddress() {
		return publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}
	@XmlElement 
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	
	

}
