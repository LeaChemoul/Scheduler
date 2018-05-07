package fr.jlc.polytech.scheduler.core.timeline;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EventNotValidException extends RuntimeException {
	
	public EventNotValidException(@Nullable Event<?> event) {
		super("The event \"" + Objects.toString(event) + "\" is not valid.");
	}
	public EventNotValidException(String message) {
		super(message);
	}
	public EventNotValidException() {
		super();
	}
}
