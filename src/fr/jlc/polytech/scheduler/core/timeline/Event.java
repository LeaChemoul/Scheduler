package fr.jlc.polytech.scheduler.core.timeline;

import fr.berger.beyondcode.util.EnhancedObservable;
import fr.jlc.polytech.scheduler.core.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * A container for any data of parameter {@code T} with start date and end date.
 * @param <T> The type of the data that the event contains.
 */
public class Event<T> extends EnhancedObservable implements Serializable, Cloneable {
	
	/**
	 * The object to contain
	 */
	@Nullable
	private T data;
	
	/**
	 * The beginning of the event
	 */
	private float start;
	
	/**
	 * The end of the event
	 */
	private float end;
	
	public Event(@Nullable T data, float start, float end) {
		setData(data);
		setStart(start);
		setEnd(end);
	}
	public Event(@Nullable T data) {
		this(data, 0, 0);
	}
	public Event(float start, float end) {
		this(null, start, end);
	}
	public Event() {
		this(null, 0, 0);
	}
	
	public static boolean isValid(@NotNull Event<?> event) {
		if (event == null)
			throw new NullPointerException();
		
		return event.getStart() <= event.getEnd();
	}
	public boolean isValid() {
		return isValid(this);
	}
	
	/**
	 * Check if two events overlap
	 * @param e1 The first event
	 * @param e2 The second event
	 * @return Return {@code true} if the events overlap to each other, {@code false} otherwise.
	 * @throws NullPointerException Thrown if e1 or e2 is null.
	 */
	public static boolean areOverlapping(@NotNull Event<?> e1, @NotNull Event<?> e2) {
		if (e1 == null || e2 == null)
			throw new NullPointerException();
		
		if (!e1.isValid())
			throw new EventNotValidException(e1);
		
		if (!e2.isValid())
			throw new EventNotValidException(e2);
		
		/* Check that configuration :
		e1  ----
		e2   ----
		 */
		if (e1.getStart() < e2.getStart() && e2.getStart() < e1.getEnd() && e1.getEnd() < e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1    ----
		e2 ----
		 */
		else if (e1.getStart() > e2.getStart() && e1.getStart() < e2.getEnd() && e1.getEnd() > e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1    ----
		e2 --------
		 */
		else if (e1.getStart() > e2.getStart() && e1.getStart() < e2.getEnd() && e1.getEnd() < e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1  --------
		e2    ----
		 */
		else if (e1.getStart() < e2.getStart() && e2.getStart() < e1.getEnd() && e1.getEnd() > e2.getEnd())
			return true;
		
		/* Check that configuration :
		e1  ----
		e2  ----
		 */
		else if (e1.getStart() == e2.getStart() && e1.getEnd() == e2.getEnd())
			return true;
		
		// Otherwise, the two events do not overlap
		else
			return false;
	}
	public boolean areOverlapping(@NotNull Event<?> event) {
		return areOverlapping(this, event);
	}
	
	/* GETTERS & SETTERS */
	
	public @Nullable T getData() {
		return data;
	}
	
	public void setData(@Nullable T data) {
		this.data = data;
		snap(this.data);
	}
	
	public float getStart() {
		return start;
	}
	
	public void setStart(float start) {
		this.start = start;
		snap(this.start);
	}
	
	public float getEnd() {
		return end;
	}
	
	public void setEnd(float end) {
		this.end = end;
		snap(this.end);
	}
	
	public float getDuration() {
		return getEnd() - getStart();
	}
	
	public void setDuration(float duration) {
		setEnd(getStart() + duration);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Event)) return false;
		Event<?> event = (Event<?>) o;
		return getStart() == event.getStart() &&
				getEnd() == event.getEnd() &&
				Objects.equals(getData(), event.getData());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getData(), getStart(), getEnd());
	}
	
	@Override
	public String toString() {
		return "Event{" +
				"data=" + data +
				", start=" + start +
				", end=" + end +
				'}';
	}
	

	public  String toStringWithTasks(){
	    float time = this.end - this.start;
		Task task = (Task) data;
		return "{" +
                task.getType() + " | " + task.getCapacity() + " | Time : " + time +
                '}';
    }
}
