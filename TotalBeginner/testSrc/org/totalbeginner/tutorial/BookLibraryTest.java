package org.totalbeginner.tutorial;

import java.util.ArrayList;

import junit.framework.TestCase;

public class BookLibraryTest extends TestCase {
	private Book b1;
	private Book b2;
	private Person p1;
	private Person p2;
	private BookLibrary myLibrary;

	public void testBookLibrary() {
		BookLibrary bl = new BookLibrary("Test");
		
		assertEquals("Test", bl.name);
		
		assertTrue(bl.books instanceof ArrayList);
		assertTrue(bl.people instanceof ArrayList);
	}
	
	public void setup() {
		b1 = new Book("Book1");
		b2 = new Book("Book2");
		p1 = new Person("Fred");
		p2 = new Person("Mark");
		
		myLibrary = new BookLibrary("Test");
	}
	
	public void testAddBook() {
		// create test objects
		setup();
		// Test initial size of books is 0
		assertEquals(0, myLibrary.getBooks().size());
		
		myLibrary.addBook(b1);
		myLibrary.addBook(b2);
		
		assertEquals(2, myLibrary.getBooks().size());
		assertEquals(0, myLibrary.getBooks().indexOf(b1));
		assertEquals(1, myLibrary.getBooks().indexOf(b2));
		
		myLibrary.removeBook(b1);
		assertEquals(1, myLibrary.getBooks().size());
		assertEquals(0, myLibrary.getBooks().indexOf(b2));

		myLibrary.removeBook(b2);
		assertEquals(0, myLibrary.getBooks().size());
	}

	public void testAddPerson() {
		setup();
		
		assertEquals(0, myLibrary.getPeople().size());
		
		myLibrary.addPerson(p1);
		myLibrary.addPerson(p2);
		
		assertEquals(2, myLibrary.getPeople().size());
		assertEquals(0, myLibrary.getPeople().indexOf(p1));
		assertEquals(1, myLibrary.getPeople().indexOf(p2));
		
		myLibrary.removePerson(p1);
		assertEquals(1, myLibrary.getPeople().size());
		assertEquals(0, myLibrary.getPeople().indexOf(p2));
		
		myLibrary.removePerson(p2);;
		assertEquals(0, myLibrary.getPeople().size());
	}
	
	public void testCheckOut() {
		setup();
		
		addItems();
		
		assertTrue("Book did not check out correctly", myLibrary.checkOut(b1,p1));
		assertEquals("Fred", b1.getPerson().getName());
		
		assertFalse("Book was already checked out", myLibrary.checkOut(b1,p2));
		
		assertTrue("Book check in failed", myLibrary.checkIn(b1));
		
		assertFalse("Book was already checked in", myLibrary.checkIn(b1));
		assertFalse("Book was never checked out", myLibrary.checkIn(b2));

		setup();
		p1.setMaxBooks(1);

		addItems();

		assertTrue("First book did not check out", myLibrary.checkOut(b1, p1));
		assertFalse("Second book should not have checked out", myLibrary.checkOut(b2, p1));
	}

	private void addItems() {
		myLibrary.addBook(b1);
		myLibrary.addBook(b2);
		myLibrary.addPerson(p1);
		myLibrary.addPerson(p2);
	}
	
	public void testGetBooksForPerson() {
		setup();
		addItems();
		assertEquals(0, myLibrary.getBooksForPerson(p1).size());
		
		myLibrary.checkOut(b1, p1);
		ArrayList<Book>	testBooks = myLibrary.getBooksForPerson(p1);
		assertEquals(1, testBooks.size());
		assertEquals(0, testBooks.indexOf(b1));
		
		myLibrary.checkOut(b2, p1);
		testBooks = myLibrary.getBooksForPerson(p1);
		assertEquals(2, testBooks.size());
		assertEquals(1, testBooks.indexOf(b2));
		
		// Additional test for maximum books
		setup();
		p1.setMaxBooks(1);
		addItems();
		
		assertTrue("First book did not check out", myLibrary.checkOut(b2, p1));
		
		assertFalse("Second book should not have checked out", myLibrary.checkOut(b1, p1));
	}

	public void testGetAvailableBooks() {
		setup();
		addItems();
		ArrayList<Book> testBooks = myLibrary.getAvailableBooks();
		
		assertEquals(2, testBooks.size());
		assertEquals(1, testBooks.indexOf(b2));

		myLibrary.checkOut(b1, p1);
		testBooks = myLibrary.getAvailableBooks();
		
		assertEquals(1, testBooks.size());
		assertEquals(0, testBooks.indexOf(b2));

		myLibrary.checkOut(b2, p1);
		testBooks = myLibrary.getAvailableBooks();
		
		assertEquals(0, testBooks.size());
	}
	
	public void testGetUnavailableBooks() {
		setup();
		addItems();
		assertEquals(0, myLibrary.getUnavailableBooks().size());
		
		myLibrary.checkOut(b1, p1);
		ArrayList<Book>	testBooks = myLibrary.getUnavailableBooks();
		assertEquals(1, testBooks.size());
		assertEquals(0, testBooks.indexOf(b1));
		
		myLibrary.checkOut(b2, p2);
		testBooks = myLibrary.getUnavailableBooks();
		assertEquals(2, testBooks.size());
		assertEquals(1, testBooks.indexOf(b2));
		
	}

	public void testToString() {
		setup();
		addItems();
		assertEquals("Test: 2 books, 2 people.", myLibrary.toString());
	}

}
