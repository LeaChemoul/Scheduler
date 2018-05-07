package fr.jlc.polytech.scheduler.core;

import jdk.nashorn.internal.scripts.JO;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generate a Box randomly.
 * @see Cluster
 * @see Box
 * @see Machine
 * @see Job
 * @see Task
 * @see Type
 * @see Capacity
 */
public class Generator {
	
	public static final int MAX_MACHINE = 100;
	public static final int MAX_JOB = 100;
    public static final int MAX_TASK = 100;
    
    public static final long MIN_CAPACITY = 0L;
    public static final long MAX_CAPACITY = 500L;
    
	private static Random rand = new Random();
	
	/**
	 * Generate a random cluster with a number of machine between [1 ; MAX_MACHINE]
	 * @return Cluster
	 */
	@NotNull
	public static Cluster generateCluster(){
	    return new Cluster(generateMachines());
    }
	
    @NotNull
    public static ArrayList<Machine> generateMachines() {
        ArrayList<Machine> list = new ArrayList<>();
        
        for (Type type : Type.values()) {
        	// Generate a number of machine for the given type, between [1 ; MAX_MACHINE]
            int nbMachine = random(1, MAX_MACHINE);
            
            for (int i = 0; i < nbMachine ; i++) {
            	// Instantiate the randomized machine
                Machine machine = new Machine(type, generateCapacity(type));
                // Add the machine to the list
	            list.add(machine);
            }
        }
        
        return list;
    }
	
	/**
	 * Generate a random list of jobs
	 * @return ArrayList of Jobs
	 */
	@NotNull
	public static ArrayList<Job> generateJobs() {
		ArrayList<Job> list = new ArrayList<>();
		
		// Generate a number of jobs for the given type, between [1 ; MAX_JOB]
		int nbJob = random(1, MAX_JOB);
		
		for (int i = 0; i < nbJob ; i++) {
			// Instantiate the randomized task
			Job job = new Job(generateTasks());
			
			// Add the machine to the list
			list.add(job);
		}
		
		return list;
	}
	
	/**
	 * Generate a random list of tasks
	 * @return ArrayList of Task
	 */
	@NotNull
	public static ArrayList<Task> generateTasks() {
		ArrayList<Task> list = new ArrayList<>();
		
		// Generate a number of tasks for the given type, between [1 ; MAX_TASK]
		int nbTask = random(1, MAX_TASK);
		
		for (int i = 0; i < nbTask ; i++) {
			// Instantiate the randomized task
			Task task = generateTask();
			
			// There is a chance to attach to this task the last tasks as a dependence (probability = 1/2)
			if (list.size() > 0) {
				for (int j = 0; j < list.size() && random(); j++) {
					task.getDependencies().add(list.get(list.size() - j - 1));
				}
			}
			
			// Add the machine to the list
			list.add(task);
		}
		
		return list;
	}
	/**
	 * Generate a task
	 * @return A Task
	 */
	@NotNull
	public static Task generateTask() {
		// Get random type
		Type type = Type.values()[random(Type.values().length - 1)];
		return new Task(type, generateCapacity(type));
	}
	
	/**
	 * Generate a box
	 * @return A Box
	 */
    @NotNull
    public static Box generateBox(){
        //For now we generate only one cluster in a box
        Cluster cluster = generateCluster();
        ArrayList<Cluster> list_cluster = new ArrayList<>();
        list_cluster.add(cluster);

        //Jobs
        ArrayList<Job> list_job = generateJobs();

        return new Box(list_cluster, list_job);
    }
	
	/**
	 * Generate a capacity with a scale <c>scale</c>
	 * @param scale The scale of the capacity to generate
	 * @return A random capacity
	 * @throws IllegalArgumentException Throws if scale is not valid
	 * @see Capacity
	 */
	@NotNull
	public static Capacity generateCapacity(char scale) {
    	if (!Capacity.isScaleValid(scale))
    		throw new IllegalArgumentException();
    	
    	return new Capacity(random(MIN_CAPACITY, MAX_CAPACITY), scale);
    }
	/**
	 * Generate a capacity with a scale generated from <c>type</c>
	 * @param type The type of the machine/task
	 * @return A random capacity
	 * @throws NullPointerException Throws if <c>type</c> is null
	 * @see Capacity
	 */
	@NotNull
	public static Capacity generateCapacity(@NotNull Type type) {
    	if (type == null)
    		throw new NullPointerException();
    	
    	long min = type.getCapacityMin().convertIntoTrueValue();
		long max = type.getCapacityMax().convertIntoTrueValue();
	    
	    return Capacity.convertIntoCapacity(random(min, max));
	}
	/**
	 * Generate a capacity
	 * @return A random capacity
	 * @see Capacity
	 */
	@NotNull
	public static Capacity generateCapacity() {
    	// Get a random scale
		final char[] scales = Capacity.getAllScales();
		char scale = scales[random(scales.length - 1)];
		
		return generateCapacity(scale);
	}
	
	/**
	 * Generate a random number between [min ; max]
	 * @param min Minimum value of the random number (included)
	 * @param max Maximum value of the random number (included)
	 * @return Random value in [min ; max]
	 */
	public static int random(int min, int max) {
		if (min > max)
			throw new IllegalArgumentException();
		
		if (min == max)
			return min;
		
		return Math.abs(rand.nextInt()) % (max + 1 - min) + min;
	}
	/**
	 * Generate a random number between [0 ; max]
	 * @param max Maximum value of the random number (included)
	 * @return Random value in [0 ; max]
	 */
	public static int random(int max) {
		return random(0, max);
	}
	/**
	 * Generate a random number between [min ; max]
	 * @param min Minimum value of the random number (included)
	 * @param max Maximum value of the random number (included)
	 * @return Random value in [min ; max]
	 */
	public static long random(long min, long max) {
		if (min > max)
			throw new IllegalArgumentException();
		
		if (min == max)
			return min;
		
		return Math.abs(rand.nextLong()) % (max + 1 - min) + min;
	}
	/**
	 * Generate a random boolean value
	 * @return Random value in [0 ; 1]
	 */
	public static boolean random() {
		return rand.nextBoolean();
	}
}
