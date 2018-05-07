package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a machine
 * @see Type
 * @see Capacity
 * @see Job
 */
public class Machine implements Serializable {
	
	/**
	 * The type of the machine
	 * @see Type
	 */
	@NotNull
	private Type type;
	
	/**
	 * The capacity the machine can handle
	 */
	@NotNull
	private Capacity capacity;
	
	/**
	 * The job the machine is currently processing
	 */
	@Nullable
	private Job currentJob;
	
	/* CONSTRUCTORS */
	
	public Machine(@NotNull Type type, @NotNull Capacity capacity, @Nullable Job currentJob) {
		setType(type);
		setCapacity(capacity);
		setCurrentJob(currentJob);
	}
	public Machine(@NotNull Type type, @NotNull Capacity capacity) {
		this(type, capacity, null);
	}
	
	public boolean isOccupied() {
		if (currentJob == null)
			return true;
		
		if (currentJob.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean process() {
		if (currentJob != null && !currentJob.isEmpty()) {
			currentJob.clear();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * The time of a given task on our machine.
	 * @param task the task that the machine would compute
	 * @return Return the time in second that the machine would take if it computes <c>task</c>
	 */
	public float computeTimeOnMachine(Task task){
		return ((float) task.getCapacity().getValue()) / ((float) this.getCapacity().getValue());
	}
	
	/* GETTERS & SETTERS */
	
	public @NotNull Type getType() {
		return type;
	}
	
	protected void setType(@NotNull Type type) {
		if (type == null)
			throw new NullPointerException();
		
		this.type = type;
	}
	
	public @NotNull Capacity getCapacity() {
		return capacity;
	}
	
	protected void setCapacity(@NotNull Capacity capacity) {
		if (capacity == null)
			throw new NullPointerException();
		
		this.capacity = capacity;
	}
	
	public @Nullable Job getCurrentJob() {
		return currentJob;
	}
	
	public void setCurrentJob(@Nullable Job currentJob) {
		this.currentJob = currentJob;
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Machine)) return false;
		Machine machine = (Machine) o;
		return getType() == machine.getType() &&
				Objects.equals(getCapacity(), machine.getCapacity()) &&
				Objects.equals(getCurrentJob(), machine.getCurrentJob());
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(getType(), getCapacity(), getCurrentJob());
	}
	
	@Override
	public String toString() {
		return "Machine{" +
				"type=" + type +
				", capacity=" + capacity +
				", currentJob=" + currentJob +
				'}';
	}
}
