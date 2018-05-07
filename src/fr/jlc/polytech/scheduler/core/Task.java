package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Task of a job, processed by a machine. Can be use with Event (Event&lt;Task&gt;)
 * @see Job
 * @see Type
 * @see Capacity
 */
public class Task implements Serializable {
	
	/**
	 * The type of the task
	 * @see Type
	 */
	@NotNull
    private Type type;
	
	/**
	 * The capacity of the task.
	 * Example: 3GB
	 * @see Capacity
	 */
	@NotNull
    private Capacity capacity;
	
	/**
	 * The dependencies of the task. If {@code dependencies} is empty, it means that the task has no dependencies
	 * @see ArrayList
	 */
    @NotNull
    private ArrayList<Task> dependencies;

    public Task(@NotNull Type type, @NotNull Capacity capacity, @NotNull ArrayList<Task> dependencies) {
    	setType(type);
    	setCapacity(capacity);
    	setDependencies(dependencies);
    }
    public Task(@NotNull Type type, @NotNull Capacity capacity, @NotNull Task... dependencies) {
    	if (dependencies == null)
    		throw new NullPointerException();
    	
	    ArrayList<Task> list = new ArrayList<>(dependencies.length);
	
	    if (dependencies.length > 0)
	    	list.addAll(Arrays.asList(dependencies));
		
	    setType(type);
	    setCapacity(capacity);
	    setDependencies(list);
    }
	public Task(@NotNull Type type, @NotNull Capacity capacity) {
    	this(type, capacity, new ArrayList<>());
	}

    public boolean detectInfiniteLoops(Task t, ArrayList<Task> visited){
        if (visited.contains(t)){
            return true;
        }
        visited.add(t);
        for (Task p : t.dependencies) {
            if (detectInfiniteLoops(p, visited)){
                return true;
            }
        }
        return false;
    }
    
    /* GETTERS & SETTERS */
	
	public @NotNull Type getType() {
		return this.type;
	}
	
	public void setType(@NotNull Type type) {
		if (type == null)
			throw new NullPointerException();
		
		this.type = type;
	}
	
	public @NotNull Capacity getCapacity() {
		return capacity;
	}
	
	public void setCapacity(@NotNull Capacity capacity) {
		if (capacity == null)
			throw new NullPointerException();
		
		this.capacity = capacity;
	}
	
	public @NotNull ArrayList<Task> getDependencies() {
		if (this.dependencies == null)
			this.dependencies = new ArrayList<>();
		
		return this.dependencies;
	}
	
	public void setDependencies(@NotNull ArrayList<Task> dependencies) {
		if (dependencies == null)
			throw new NullPointerException();
		
		this.dependencies = dependencies;
	}
	
	/* OVERRIDES */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Task)) return false;
		Task task = (Task) o;
		return getType() == task.getType() &&
				Objects.equals(getCapacity(), task.getCapacity()) &&
				Objects.equals(getDependencies(), task.getDependencies());
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(getType(), getCapacity(), getDependencies());
	}
	
	@Override
	public String toString() {
		return "Task{" +
				"type=" + type +
				", capacity=" + capacity +
				", dependencies=" + dependencies +
				'}';
	}
}
