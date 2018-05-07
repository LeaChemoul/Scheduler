package fr.jlc.polytech.scheduler.core.timeline;

import fr.berger.beyondcode.util.EnhancedObservable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Timeline to save events in lines.
 * Each line represents a list of events.
 * @see TimelineController
 * @see TimelineView
 * @see Event
 */
public class Timeline extends EnhancedObservable implements Serializable, Cloneable {
	
	/**
	 * ArrayList of ArrayList of Event
	 */
	@NotNull
	private ArrayList<ArrayList<Event<?>>> events;
	
	public Timeline(int initialTimelinesCapacity) {
		setEvents(new ArrayList<>(initialTimelinesCapacity));
	}
	public Timeline() {
		this(10);
	}
	
	protected boolean checkTimelineIndex(int timelineIndex) {
		if (!(0 <= timelineIndex && timelineIndex < getEvents().size()))
			throw new ArrayIndexOutOfBoundsException("There is no timeline with the index " + timelineIndex + ". " +
					(getEvents().isEmpty() ? "The list is empty." : "The list contains " + getEvents().size() + " element" + (getEvents().size() > 1 ? 's' : "") + '.'));
		
		return true;
	}
	
	/**
	 * Sort the list such that the first event in the list is the soonest event, and the last event in the list
	 * the latest, accordingly to Event.getStart() and Event.getEnd()
	 * @param events The list to sort
	 */
	protected void sort(@NotNull ArrayList<Event<?>> events) {
		events.sort(new Comparator<Event<?>>() {
			@Override
			public int compare(Event<?> e1, Event<?> e2) {
				if (e1.getEnd() < e2.getStart())
					return -1;
				else
					return 1;
			}
		});
		snap(this.events);
	}
	public void sort(int timelineIndex) {
		checkTimelineIndex(timelineIndex);
		sort(getEvents().get(timelineIndex));
	}
	public void sort() {
		for (ArrayList<Event<?>> eventArrayList : getEvents())
			sort(eventArrayList);
	}

	public float maxLine(){
		float max = 0;
		for (int i = 0; i < this.getEvents().size() ; i++) {

			int size = this.getEvents().get(i).size();
			float lineTime = (this.getEvents().get(i).isEmpty())? 0: this.getEvents().get(i).get(size-1).getEnd();
			if(lineTime > max)
				max = lineTime;

		}
		return max;
	}
	
	/* GETTERS & SETTERS */
	
	@NotNull
	public ArrayList<ArrayList<Event<?>>> getEvents() {
		if (events == null)
			events = new ArrayList<>();
		
		return events;
	}
	
	protected void setEvents(@NotNull ArrayList<ArrayList<Event<?>>> events) {
		if (events == null)
			throw new NullPointerException();
		
		this.events = events;
		snap(this.events);
	}
	
	public int addTimeline(@NotNull ArrayList<Event<?>> events) {
		if (events == null)
			throw new NullPointerException();
		
		// Delete all null values
		if (!events.isEmpty()) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i) == null) {
					events.remove(i);
					i--;
				}
			}
		}
		
		int index = getEvents().size();
		getEvents().add(events);
		snap(getEvents());
		
		return index;
	}
	public int addTimeline() {
		return addTimeline(new ArrayList<>());
	}
	
	public boolean addEvent(int timelineIndex, @NotNull Event<?> event) {
		if (event == null)
			throw new NullPointerException();
		
		checkTimelineIndex(timelineIndex);
		
		if (!event.isValid())
			throw new EventNotValidException(event);
		
		// If 'event' overlap another event in its timeline, do not add it and return false
		for (Event<?> e : getEvents().get(timelineIndex)) {
			if (Event.areOverlapping(e, event))
				return false;
		}
		
		// Otherwise, add the event
		event.addObserver((observable, o) -> snap(o));
		getEvents().get(timelineIndex).add(event);
		
		// Sort the list
		sort();
		
		return true;
	}
	
	public float getStart() {
		if (getEvents().isEmpty())
			return 0;
		
		if (getEvents().get(0).isEmpty())
			return 0;
		
		if (getEvents().get(0).get(0) == null)
			throw new NullPointerException("A null event has been found at (0 ; 0).");
		
		float min = getEvents().get(0).get(0).getStart();
		
		for (ArrayList<Event<?>> listEvent : events) {
			float result = getStart(listEvent);
			
			if (result < min)
				min = result;
		}
		
		return min;
	}
	protected float getStart(@NotNull ArrayList<Event<?>> events) {
		if (events == null)
			throw new NullPointerException();
		
		if (events.isEmpty())
			return 0;
		
		int min = 0;
		
		for (int i = 0; i < events.size(); i++)
			if (events.get(i).getStart() < events.get(min).getStart())
				min = i;
		
		return events.get(min).getStart();
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Timeline)) return false;
		Timeline timeline = (Timeline) o;
		return Objects.equals(getEvents(), timeline.getEvents());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getEvents());
	}
	
	public String toString(@Nullable String name) {
		StringBuilder builder = new StringBuilder();
		
		if (name != null && !name.isEmpty())
			builder.append(name);
		else
			builder.append("Timeline");
		
		builder.append('\n');
		
		sort();
		
		// Compute the number of minimal character to display the number of the timeline
		int minChar = Integer.toString(getEvents().size()).length();
		
		for (int i = 0; i < getEvents().size(); i++) {
			ArrayList<Event<?>> line = getEvents().get(i);
			
			int numLine = i + 1;
			int nbCharNumLine = Integer.toString(numLine).length();
			
			for (int j = 0; j < minChar - nbCharNumLine; j++)
				builder.append(' ');
			
			builder.append(numLine)
					.append('|');
			
			// The cursor of the line
			int nbChar = 0;
			
			for (int j = 0; j < line.size(); j++) {
				Event<?> event = line.get(j);
				
				// Fill the gap between the last event and the current with whitespaces
				for (; nbChar < event.getStart(); nbChar++)
					builder.append(' ');
				
				// Now, draw the event
				for (int k = Math.round(event.getStart()); k <= event.getEnd(); k++) {
					builder.append('#');
					nbChar++;
				}
			}
			
			builder.append('\n');
		}
		
		return builder.toString();
	}

	public String toStringWithTasks() {
		StringBuilder builder = new StringBuilder();

		builder.append("Timeline");

		builder.append('\n');

		sort();

		// Compute the number of minimal character to display the number of the timeline
		int minChar = Integer.toString(getEvents().size()).length();

		for (int i = 0; i < getEvents().size(); i++) {
			ArrayList<Event<?>> line = getEvents().get(i);

			int numLine = i + 1;
			int nbCharNumLine = Integer.toString(numLine).length();

			for (int j = 0; j < minChar - nbCharNumLine; j++)
				builder.append(' ');

			builder.append(numLine)
					.append('|');

			for (Event<?> event : line) {
				builder.append(event.toStringWithTasks());
			}
			builder.append('\n');
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return toString(null);
	}
}
