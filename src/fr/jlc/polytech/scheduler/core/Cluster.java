package fr.jlc.polytech.scheduler.core;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * List of machines
 */
public class Cluster extends ArrayList<Machine> implements Serializable, Cloneable {
	
	public Cluster(@Nullable Machine... machines) {
		super(machines != null ? machines.length : 3);
		
		if (machines != null)
			for (Machine machine : machines)
				if (machine != null)
					add(machine);
	}
	public Cluster(@Nullable ArrayList<Machine> machines) {
		super(machines != null ? machines.size() : 3);
		
		if (machines != null)
			for (Machine machine : machines)
				if (machine != null)
					add(machine);
	}
	public Cluster(int initialCapacity) {
		super(initialCapacity);
	}
	
	public ArrayList<Machine> getAll(@NotNull Type type) {
		if (type == null)
			throw new NullPointerException();
		
		ArrayList<Machine> list = new ArrayList<>(this.size()/3);
		
		for (Machine machine : this)
			if (machine.getType() == type)
				list.add(machine);
		
		return list;
	}
	
	/* OVERRIDES */
	
	@Override
	public String toString() {
		return super.toString();
	}
}
