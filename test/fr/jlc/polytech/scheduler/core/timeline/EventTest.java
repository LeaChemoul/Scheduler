package fr.jlc.polytech.scheduler.core.timeline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 class to test Event&lt;T&gt;.
 */
class EventTest {
	
	Event<String> e1;
	Event<String> e2;
	
	/**
	 * Setup "e1" and "e2"
	 */
	@BeforeEach
	void setup() {
		e1 = new EventBuilder<String>()
				.setData("e1")
				.createEvent();
		
		e2 = new EventBuilder<String>()
				.setData("e2")
				.createEvent();
	}
	
	/**
	 * Test overlap
	 */
	@Test
	void areOverlapping() {
		/* OVERLAPPING */
		
		/*
		this  ----
		event ----
		 */
		e1.setStart(0);
		e1.setEnd(4);
		
		e2.setStart(0);
		e2.setEnd(4);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this  ----
		event   ----
		 */
		e1.setStart(0);
		e1.setEnd(4);
		
		e2.setStart(2);
		e2.setEnd(6);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this    ----
		event ----
		 */
		e1.setStart(2);
		e1.setEnd(6);
		
		e2.setStart(0);
		e2.setEnd(4);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this    ----
		event --------
		 */
		e1.setStart(2);
		e1.setEnd(6);
		
		e2.setStart(0);
		e2.setEnd(8);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this  --------
		event   ----
		 */
		e1.setStart(0);
		e1.setEnd(8);
		
		e2.setStart(2);
		e2.setEnd(6);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this
		event
		 */
		e1.setStart(0);
		e1.setEnd(0);
		
		e2.setStart(0);
		e2.setEnd(0);
		
		Assertions.assertTrue(Event.areOverlapping(e1, e2));
		Assertions.assertTrue(e1.areOverlapping(e2));
		Assertions.assertTrue(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/* NO OVERLAPPING */
		
		/*
		this  ----
		event     ----
		 */
		e1.setStart(0);
		e1.setEnd(4);
		
		e2.setStart(4);
		e2.setEnd(8);
		
		Assertions.assertFalse(Event.areOverlapping(e1, e2));
		Assertions.assertFalse(e1.areOverlapping(e2));
		Assertions.assertFalse(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/* NO OVERLAPPING */
		
		/*
		this  ----
		event       ----
		 */
		e1.setStart(0);
		e1.setEnd(4);
		
		e2.setStart(6);
		e2.setEnd(10);
		
		Assertions.assertFalse(Event.areOverlapping(e1, e2));
		Assertions.assertFalse(e1.areOverlapping(e2));
		Assertions.assertFalse(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this      ----
		event ----
		 */
		e1.setStart(4);
		e1.setEnd(8);
		
		e2.setStart(0);
		e2.setEnd(4);
		
		Assertions.assertFalse(Event.areOverlapping(e1, e2));
		Assertions.assertFalse(e1.areOverlapping(e2));
		Assertions.assertFalse(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
		
		/*
		this        ----
		event ----
		 */
		e1.setStart(6);
		e1.setEnd(10);
		
		e2.setStart(0);
		e2.setEnd(4);
		
		Assertions.assertFalse(Event.areOverlapping(e1, e2));
		Assertions.assertFalse(e1.areOverlapping(e2));
		Assertions.assertFalse(e2.areOverlapping(e1));
		Assertions.assertTrue(e1.areOverlapping(e1));
		Assertions.assertTrue(e2.areOverlapping(e2));
	}
}