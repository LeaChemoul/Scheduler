package fr.jlc.polytech.scheduler.core.timeline;

import fr.berger.beyondcode.util.EnhancedObservable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller of the Timeline
 * @see Timeline
 * @see TimelineView
 */
public class TimelineController implements Observer, Serializable {
	
	@NotNull
	private Timeline timeline;
	@NotNull
	private TimelineView view;
	
	public TimelineController(@NotNull Timeline timeline, @NotNull TimelineView view) {
		setTimeline(timeline);
		setView(view);
	}
	public TimelineController() {
		initTimeline();
		initView();
	}
	
	/* OBSERVER OVERRIDES */
	
	@Override
	public void update(Observable observable, Object o) {
		update();
	}
	public void update() {
		getView().update(getTimeline());
	}
	
	/* GETTERS & SETTERS */
	
	@NotNull
	public Timeline getTimeline() {
		if (timeline == null)
			initTimeline();
		
		return timeline;
	}
	
	public void setTimeline(@NotNull Timeline timeline) {
		if (timeline == null)
			throw new NullPointerException();
		
		this.timeline = timeline;
		configureTimeline();
	}
	
	protected void initTimeline() {
		setTimeline(new Timeline());
	}
	
	protected void configureTimeline() {
		getTimeline().addObserver(this);
	}
	
	@NotNull
	public TimelineView getView() {
		if (view == null)
			initView();
		
		return view;
	}
	
	public void setView(@NotNull TimelineView view) {
		if (view == null)
			throw new NullPointerException();
		
		this.view = view;
	}
	
	protected void initView() {
		setView(new TimelineView());
	}
	
	/* SERIALIZATION OVERRIDE */
	
	private void writeObject(@NotNull ObjectOutputStream stream) throws IOException {
		stream.writeObject(getTimeline());
		stream.writeObject(getView());
	}
	
	private void readObject(@NotNull ObjectInputStream stream) throws IOException, ClassNotFoundException {
		setTimeline((Timeline) stream.readObject());
		setView((TimelineView) stream.readObject());
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TimelineController)) return false;
		TimelineController that = (TimelineController) o;
		return Objects.equals(getTimeline(), that.getTimeline()) &&
				Objects.equals(getView(), that.getView());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getTimeline(), getView());
	}
	
	@Override
	public String toString() {
		return "TimelineController{" +
				"timeline=" + timeline +
				", view=" + view +
				'}';
	}
}
