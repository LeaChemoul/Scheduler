package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import org.jetbrains.annotations.NotNull;

/**
 * Interface that all method must implement.
 */
public interface Method {
	
	/**
	 * Manage a box by assigning to the machine a list of job to process
	 * @param box The box to manage. It contains a list of cluster and a list of jobs.
	 * @return Return the time in seconds to compute all the jobs
	 * @see Box
	 */
	float manage(@NotNull Box box);
	
	/**
	 * Check if the given argument is valid by checking if it null, if one of its components its null, and if its lists
	 * are empty.
	 * @param box The argument to check
	 * @return Return true if the box is valid, other throw an exception
	 * @throws NullPointerException Thrown if box, box.getClusters() or box.getJobs() is null
	 * @throws IllegalArgumentException Thrown if box.getClusters() or box.getJobs() is empty
	 * @see Box
	 */
	default boolean checkBox(@NotNull Box box) {
		if (box == null)
			throw new NullPointerException();
		
		if (box.getClusters() == null || box.getJobs() == null)
			throw new NullPointerException();
		
		if (box.getClusters().size() == 0 || box.getJobs().size() == 0)
			throw new IllegalArgumentException();
		
		return true;
	}
}
