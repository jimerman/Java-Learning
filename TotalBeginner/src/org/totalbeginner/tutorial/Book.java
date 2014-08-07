package org.totalbeginner.tutorial;

public class Book {

	String title;
	String author;
	Person person;

	public Book(String string) {
		title = string;
		author = "unknown";
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setPerson(Person p2) {
		this.person = p2;
	}

	public Person getPerson() {
		return this.person;
	}

	public String toString() {
		String available;
		available = (this.getPerson() == null ? "Available" : "Checked out to " + this.getPerson().getName());
		return this.getTitle() + " by " + this.getAuthor() + "; " + available;
	}
}
