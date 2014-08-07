package org.totalbeginner.tutorial;

public class Person {
	// fields
	private String name;
	private int maxBooks;

	// Constructors
	public Person() {
		name = "undefined";
		maxBooks = 2;
	}
	
	public Person(String setName) {
		name = setName;
		maxBooks = 2;
	}
	public Person(String setName, int setMax) {
		name = setName;
		maxBooks = setMax;
	}
	
	// Methods
	public String getName() {
		return name;
	}

	public void setName(String anyName) {
		name = anyName;
	}

	public int getMaxBooks() {
		return maxBooks;
	}

	public void setMaxBooks(int maxBooks) {
		this.maxBooks = maxBooks;
	}
		
	@Override
	public String toString() {
		return this.getName() + "," + this.getMaxBooks();
	}
}
