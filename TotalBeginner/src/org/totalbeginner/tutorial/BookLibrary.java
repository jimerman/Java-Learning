package org.totalbeginner.tutorial;

import java.util.ArrayList;

public class BookLibrary {

	String name;
	ArrayList<Book> books;
	ArrayList<Person> people;

	public BookLibrary(String name) {
		this.name = name;
		books = new ArrayList<>();
		people = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	public void addBook(Book b1) {
		this.books.add(b1);
	}

	public void removeBook(Book b1) {
		this.books.remove(b1);
	}

	public void addPerson(Person p1) {
		this.people.add(p1);
	}

	public void removePerson(Person p1) {
		this.people.remove(p1);
	}

	public boolean checkOut(Book b1, Person p1) {
		if (b1.getPerson() == null) {
			// Check person's Max Books
			int checkedOutBooks = this.getBooksForPerson(p1).size();
			if (checkedOutBooks < p1.getMaxBooks()) {
				b1.setPerson(p1);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public boolean checkIn(Book b1) {
		if (b1.getPerson() != null) {
			b1.setPerson(null);
			return true;
		}
		else {
			return false;
		}
	}

	public ArrayList<Book> getBooksForPerson(Person p1) {
		ArrayList<Book> retVal = new ArrayList<>();
		
		for (Book book : this.getBooks()) {
			if ((book.getPerson() != null) && (book.getPerson().getName().equals(p1.getName()))) {
				retVal.add(book);
			}
		}
		return retVal;
	}

	public ArrayList<Book> getAvailableBooks() {
		ArrayList<Book> result = new ArrayList<Book>();
		
		for (Book aBook : this.getBooks()) {
			if (aBook.getPerson() == null) {
				result.add(aBook);
			}
		}
		return result;
	}

	public ArrayList<Book> getUnavailableBooks() {
		ArrayList<Book> result = new ArrayList<Book>();
		
		for (Book aBook : this.getBooks()) {
			if (aBook.getPerson() != null) {
				result.add(aBook);
			}
		}
		return result;
	}
	
	public Person getPerson(String name) {
		Person returnVal = null;
		
		for (Person p : this.getPeople()) {
			if (p.getName().equals(name)) {
				returnVal = p;
				break;
			}
		}
		
		return returnVal;
	}

	public String toString() {
		return this.getName() + ": " + this.getBooks().size() + " books, " +
				this.getPeople().size() + " people.";
	}

    public static void main(String[] args) {
		// create a new library
    	BookLibrary testLibrary = new BookLibrary("Test Drive Library");
    	Book b1 = new Book("War And Peace");
    	Book b2 = new Book("Great Expectations");
    	b1.setAuthor("Tolstoy");
    	b2.setAuthor("Dickens");
    	testLibrary.addBook(b1);
    	testLibrary.addBook(b2);
    	Person jim = new Person("Jim");
    	Person sue = new Person("Sue");
    	testLibrary.addPerson(jim);
    	testLibrary.addPerson(sue);

    	System.out.println("Just created new library");
    	
    	testLibrary.printStatus();
    	
    	System.out.println("Check out War And Peace to Sue");
    	testLibrary.checkOut(b1, sue);
    	
    	testLibrary.printStatus();
    	
    	System.out.println("Do some more stuff");
    	testLibrary.checkIn(b1);
    	testLibrary.checkOut(b2, jim);
    	
    	testLibrary.printStatus();
	}

	private void printStatus() {
		System.out.println(this.getStatus());
	}
	
	public String getStatus() {
		String returnValue = "";
		returnValue += "Status Report of MyLibrary " + this.toString() + "\r\n";

		for (Book thisBook : this.getBooks()) {
			returnValue += thisBook.toString() + "\r\n";
		}
		
		for (Person p : this.getPeople()) {
			int bookCount = this.getBooksForPerson(p).size();
			returnValue += p + " (has " + bookCount + " of my books)\r\n";
		}
		
		returnValue += "Books Available: " + this.getAvailableBooks().size() + "\r\n";
		returnValue += "---- END OF STATUS REPORT ----\r\n";
		
		return returnValue;
	}
}
