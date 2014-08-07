package org.totalbeginner.tutorial;

import junit.framework.TestCase;

public class BookTest extends TestCase {
	public void testBook() {
		Book b1 = new Book("Great Expectations");
		assertEquals("Great Expectations", b1.title);
		assertEquals("unknown", b1.author);
	}
	
	public void testGetPersion() {
		Book b2 = new Book("War And Peace");
		Person p2 = new Person("Elvis", 5);
		b2.setPerson(p2);
		
//		Person testPerson = b2.getPerson();
//		String testName = testPerson.getName();
		
		assertEquals("Elvis", b2.getPerson().getName());
	}
	
	public void testToString() {
		Book b2 = new Book("War And Peace");
		Person p2 = new Person("Elvis", 5);

		assertEquals("War And Peace by unknown; Available", b2.toString());
		b2.setPerson(p2);
		assertEquals("War And Peace by unknown; Checked out to Elvis", b2.toString());
	}
}
