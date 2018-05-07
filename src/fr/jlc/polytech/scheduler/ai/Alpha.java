package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Job;
import fr.jlc.polytech.scheduler.core.Machine;
import fr.jlc.polytech.scheduler.core.Task;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.Timeline;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Alpha is a basic managers for a box.
 */
public class Alpha extends Scheduling implements Method {
	
	public Alpha() {

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

		box.initAccumulateTime();
		initMachineTimeline(box); // associates machines with timeline lines

		Random random = new Random();

		while(!box.getAccumulateTime().isEmpty()){

			List<Task> keys      = new ArrayList<Task>(box.getAccumulateTime().keySet());
			Task taskToTreat = keys.get( random.nextInt(keys.size()) );
            //Alpha : We must treat its dependencies if they have not been processed yet.
            treatDependencies(taskToTreat,box); //treat predecessors + the task
        }


        //afficher la timeline
        //System.out.println(timeline.toString("Alpha Version : "));
        //System.out.println(timeline.toStringWithTasks());
        System.out.println("Total time Alpha = " + getTime());
		
		return getTime();
	}

	/**
	 * Treat the dependencies of the task chosen
	 * @param task Task chosen
	 */
	private void treatDependencies(Task task,Box box){
		for (Task taskPred: task.getDependencies()) {
			boolean trouve = false;
			for (int i = 0; i < this.timeline.getEvents().size() ; i++) {
				for (int j = 0; j < this.timeline.getEvents().get(i).size(); j++) {
					if(box.getAccumulateTime().containsKey(taskPred))
						treatDependencies(taskPred,box);
				}
			}
		}
		treatTask(box,task);
	}
}
