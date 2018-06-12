package com.lx.bookmanage.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the borrow database table.
 * 
 */
@Entity
@NamedQuery(name="Borrow.findAll", query="SELECT b FROM Borrow b")
public class Borrow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int borrowid;

	@Temporal(TemporalType.DATE)
	private Date borrowidbooktime;

	@Temporal(TemporalType.DATE)
	private Date returnbooktime;

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="sno")
	private Student student;

	//bi-directional many-to-one association to Book
	@ManyToOne
	@JoinColumn(name="bookid")
	private Book book;

	public Borrow() {
	}

	public int getBorrowid() {
		return this.borrowid;
	}

	public void setBorrowid(int borrowid) {
		this.borrowid = borrowid;
	}

	public Date getBorrowidbooktime() {
		return this.borrowidbooktime;
	}

	public void setBorrowidbooktime(Date borrowidbooktime) {
		this.borrowidbooktime = borrowidbooktime;
	}

	public Date getReturnbooktime() {
		return this.returnbooktime;
	}

	public void setReturnbooktime(Date returnbooktime) {
		this.returnbooktime = returnbooktime;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}