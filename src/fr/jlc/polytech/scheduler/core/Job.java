package fr.jlc.polytech.scheduler.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * List of task that a machine can process.
 * @see Task
 * @see Machine
 */
public class Job extends ArrayList<Task> implements Serializable, Cloneable {

	public Job(@Nullable Task... tasks) {
		super(tasks != null ? tasks.length : 3);
		
		if (tasks != null)
			for (Task task : tasks)
				if (task != null)
					add(task);
	}
	public Job(@Nullable ArrayList<Task> tasks) {
		super(tasks != null ? tasks.size() : 3);
		
		if (tasks != null)
			for (Task task : tasks)
				if (task != null)
					add(task);
	}
	public Job(int initialCapacity) {
		super(initialCapacity);
	}
	
	public Capacity computeCapacity() {
		long value = 0;
		
		for (Task task : this) {
			value += task.getCapacity().convertIntoTrueValue();
		}
		
		return Capacity.convertIntoCapacity(value);
	}

	@NotNull
	public ArrayList getFirstTask(Job job) {
        ArrayList<Task> listFirstTask = new ArrayList<>();
        for (Task task : job) {
        	// Task.getDependencies() is always different from null
            if (task.getDependencies().size() == 0)
                listFirstTask.add(task);
        }
        return listFirstTask;
    }


	/*public void sort() {
		int min;
		
		for (int i = 0; i < size() - 1; i++) {
			min = i;
			for (int j = i + 1; j < size(); j++) {
				if (get(j).getCapacity().convertIntoTrueValue() < get(min).getCapacity().convertIntoTrueValue())
					min = j;
			}
			
			if (min != i) {
				//
			}
		}
	}*/
	
	/* OVERRIDES */
	
	@Override
	public String toString() {
		return super.toString();
	}
}
