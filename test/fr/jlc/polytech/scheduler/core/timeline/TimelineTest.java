package fr.jlc.polytech.scheduler.core.timeline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimelineTest {
	
	Timeline tm;
	
	@BeforeEach
	void setup() {
		tm = new Timeline();
		
		tm.addTimeline();
		tm.addTimeline();
		tm.addTimeline();
		
		tm.addEvent(0, new EventBuilder<String>()
				.setStart(0)
				.setEnd(10)
				.createEvent());
		tm.addEvent(0, new EventBuilder<String>()
				.setStart(15)
				.setEnd(17)
				.createEvent());
		tm.addEvent(0, new EventBuilder<String>()
				.setStart(19)
				.setEnd(19)
				.createEvent());
		tm.addEvent(0, new EventBuilder<String>()
				.setStart(32)
				.setEnd(36)
				.createEvent());
		
		tm.addEvent(1, new EventBuilder<String>()
				.setStart(5)
				.setEnd(9)
				.createEvent());
		tm.addEvent(1, new EventBuilder<String>()
				.setStart(15)
				.setEnd(18)
				.createEvent());
	}
	
	@Test
	void test_toString() {
		System.out.println(tm.toString("TimelineTest.test_toString"));
	}
	
	@Test
	void sort() {
		tm.sort();
		System.out.println(tm.toString());
	}
}