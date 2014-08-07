package org.totalbeginner.tutorial;

import junit.framework.TestCase;

public class PersonTest extends TestCase {

	public void testPerson() {
		Person p1 = new Person();
		assertEquals("undefined", p1.getName());
		assertEquals(2, p1.getMaxBooks());
	}

	public void testSetName() {
		Person p2 = new Person();
		p2.setName("Fred");
		assertEquals("Fred", p2.getName());
	}

	public void testSetMaxBooks() {
		Person p3 = new Person();
		p3.setMaxBooks(9);
		assertEquals(9, p3.getMaxBooks());
	}
	
	public void testToString() {
		Person p4 = new Person("Fred Flintstone", 7);
		String testString = "Fred Flintstone,7";
		assertEquals(testString, p4.toString());
	}
}
