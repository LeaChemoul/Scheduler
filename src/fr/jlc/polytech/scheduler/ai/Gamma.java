package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Task;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gamma manage a box using an advanced method (compared to Alpha) to assign jobs for the machine.
 */
public class Gamma extends Scheduling implements Method {
	
	public Gamma(){
		//
	}

	/**
	 * Manage a box by assigning to the machine a list of job to process.
	 * In this class, only the first cluster is used.
	 * @param box The box to manage. It contains a list of cluster and a list of jobs.
	 * @return Return the time in seconds to compute all the jobs
	 */
	@Override
	public float manage(@NotNull Box box) {
		checkBox(box);

		checkBox(box);

		initMachineTimeline(box); // associates machines with timeline lines
		for (int i = 0; i < box.getJobs().size() ; i++) {
			for (int j = 0; j < box.getJobs().get(i).size() ; j++) {
				Task taskToTreat = box.getJobs().get(i).get(j);
				treatTask(box, taskToTreat);
			}
		}

		//afficher la timeline
		//System.out.println(timeline.toString("Gamma Version : "));
		//System.out.println(timeline.toStringWithTasks());
		System.out.println("Total time Gamma = " + getTime());

		return getTime();

	}
}
