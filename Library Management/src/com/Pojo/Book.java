package com.Pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2604578579109088831L;

	private String ISBN;

	private String title;

	private int publisherId;

	private Date publicationDate;
	
	private int bookId;
	
	private float fine;
	
	private Date borrowDate;

	@XmlElement
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	@XmlElement
	public List<Author> getAuthor() {
		return author;
	}

	public void setAuthor(List<Author> author) {
		this.author = author;
	}

	private String status;
	
	private int count;
	
	private Publisher publisher;
	
	private List<Author> author;

	@XmlElement
    public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	@XmlElement
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement
	public float getFine() {
		return fine;
	}

	public void setFine(float fine) {
		this.fine = fine;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String iSBN, String title, int publisherId, Date publicationDate) {
		super();
		ISBN = iSBN;
		this.title = title;
		this.publisherId = publisherId;
		this.publicationDate = publicationDate;
	}

	@XmlElement
	public String getISBN() {
		return ISBN;
	}
    
	@XmlElement
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	@XmlElement
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

}
