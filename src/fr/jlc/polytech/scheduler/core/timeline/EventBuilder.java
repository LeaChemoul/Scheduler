package fr.jlc.polytech.scheduler.core.timeline;

import fr.jlc.polytech.scheduler.core.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder of event
 * @param <T> The type of data for the event to build
 */
public class EventBuilder<T> {
	
	@NotNull
	private Event<T> event;
	
	public EventBuilder() {
		setEvent(new Event<T>());
	}
	
	public EventBuilder<T> setData(T data) {
		getEvent().setData(data);
		return this;
	}
	
	public EventBuilder<T> setStart(float start) {
		getEvent().setStart(start);
		return this;
	}
	
	public EventBuilder<T> setEnd(float end) {
		getEvent().setEnd(end);
		return this;
	}
	
	public EventBuilder<T> setDuration(float duration) {
		getEvent().setDuration(duration);
		return this;
	}

	public Event<T> createEvent() {
		return getEvent();
	}
	
	/* GETTER & SETTER */
	
	@NotNull
	public Event<T> getEvent() {
		if (event == null)
			event = new Event<>();
		
		return event;
	}
	
	@NotNull
	public void setEvent(@NotNull Event<T> event) {
		if (event == null)
			throw new NullPointerException();
		
		this.event = event;
	}
}
