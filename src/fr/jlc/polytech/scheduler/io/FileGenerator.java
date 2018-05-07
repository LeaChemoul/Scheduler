package fr.jlc.polytech.scheduler.io;

import fr.jlc.polytech.scheduler.core.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Objects;

public class FileGenerator {
	
	public static final String path = "input_scheduler.txt";
	
	private FileGenerator() { }
	
	@SuppressWarnings("Duplicates")
	public static boolean generateFile(@NotNull Box box) {
		//noinspection ConstantConditions
		if (box == null)
			throw new NullPointerException();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		boolean problem = false;
		
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			
			bw.write(generateContent(box));
		} catch (IOException ex) {
			ex.printStackTrace();
			problem = true;
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				problem = true;
			}
			
			try {
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				problem = true;
			}
		}
		
		return !problem;
	}
	
	/**
	 * Generate the string content of the box.
	 * @param box The box to transform into string.
	 * @return The string representation of the box
	 */
	@SuppressWarnings("DanglingJavadoc")
	@NotNull
	public static String generateContent(@NotNull Box box) {
		//noinspection ConstantConditions
		if (box == null)
			throw new NullPointerException();
		
		// At least one of the two lists must contain something
		if (!((box.getClusters().isEmpty() && !box.getJobs().isEmpty()) ||
			 (!box.getClusters().isEmpty() && box.getJobs().isEmpty()) ||
			 (!box.getClusters().isEmpty() && !box.getJobs().isEmpty())))
			throw new IllegalArgumentException("There is no cluster nor jobs in the box.");
		
		/**
		 * The string builder
		 */
		StringBuilder build = new StringBuilder();
		
		// Begin with servers
		build.append("Servers\n");
		
		// For each types, list the servers available in "box" with this type
		for (Type type : Type.values()) {
			build.append("\t")
				 .append(type.toString())
				 .append(" = [");
			
			for (Cluster cluster : box.getClusters()) {
				ArrayList<Machine> machineType = cluster.getAll(type);
				
				for (Machine machine : machineType) {
					build.append(machine.getCapacity().toString())
						 .append(", ");
				}
			}
			
			// Delete last ", " (last 2 characters)
			if (box.getClusters().size() > 0) {
				build.deleteCharAt(build.length() - 1);
				build.deleteCharAt(build.length() - 1);
			}
			
			build.append("]\n");
		}
		
		int jobNumber = 1;
		int taskNumber;
		int nbrOfTasks =0;
		
		// For each job in the box
		for (Job job : box.getJobs()) {
			build.append("Job ")
				 .append(jobNumber)
				 .append(" = [");
			
			
			// Each task will be affected to a value, such as 'T1', 't2', ...
			HashMap<Task, String> tasks = new HashMap<>();
			
			taskNumber = 1;
			for (Task task : job) {
				tasks.put(task, "T" + taskNumber);
				taskNumber++;
			}
			
			for (String key : tasks.values()) {
				build.append(key)
					 .append(", ");
			}
			
			// Delete last ", "
			if (tasks.values().size() > 0) {
				build.deleteCharAt(build.length() - 1);
				build.deleteCharAt(build.length() - 1);
			}
			
			build.append("]\n");
			
			taskNumber = 1;
			
			for (Task task : job) {
				build.append("\tT")
					 .append(taskNumber)
					 .append(" = ")
					 .append(task.getType().toString())
					 .append(", ")
					 .append(task.getCapacity().toString())
					 .append(", [");
				
				
				// Build the dependencies according to "tasks" HashMap.
				for (Task predecessor : task.getDependencies()) {
					build.append(tasks.get(predecessor))
						 .append(", ");
				}
				
				// Delete last ", " if there were at least one dependencies written
				if (task.getDependencies().size() > 0) {
					build.deleteCharAt(build.length() - 1);
					build.deleteCharAt(build.length() - 1);
				}
				
				build.append("]\n");
				
				taskNumber++;
			}
			
			jobNumber++;
			nbrOfTasks += taskNumber - 1;
		}
		/*build.append("Number total of tasks = ")
				.append(nbrOfTasks)
				.append("\n");*/ //Incompatible with file generator

		return build.toString();
	}
	
	
	public static @NotNull Box readBox() {
		String content = readContent();
		Box box = new Box();
		
		final String err_format = "The file format is not valid.";
		final String err_job = err_format + " The job number \"%s\" is not valid";
		final String err_found = err_format + " Found \"%s\" instead.";
		
		if (content.isEmpty())
			return box;
		
		/**
		 * Contains the lines of the content
		 */
		String[] lines = content.split("\n");
		
		/**
		 * mode indicates if the method parses servers or job
		 * If mode == 0: It parses servers
		 * If mode > 0: It parses the job n°"mode"
		 */
		int mode = 0;
		// TODO: Skip #-lines
		
		// The current job to process (null if mode == 0)
		Job job = null;
		
		// This ArrayList contains all the tasks for a job. for each new job, the scope is reset. It is used for the dependencies
		HashMap<String, Task> scope = new HashMap<>();
		int nextTaskNumber = 1;
		
		// For each line in lines
		for (String line : lines) {
			
			// If the line is "Servers" or "Job #", change the mode
			if (line.toLowerCase().equals("servers") || line.toLowerCase().startsWith("job"))
			{
				// If the line is "Servers", change the mode to 0
				if (line.toLowerCase().equals("servers"))
					mode = 0;
				// Otherwise, change the mode to the number of the job
				else {
					// Replace all possible alphabetic-characters and whitespaces by nothing to have the digit at the end
					line = line.replaceAll(" ", "");
					line = line.replaceAll("\t", "");
					line = line.replaceAll(":", "");
					
					line = line.substring(3, line.indexOf("="));
					
					// Get the number of the job
					int jobNumber = -1;
					
					try {
						jobNumber = Integer.valueOf(line);
					} catch (NumberFormatException ex) {
						throw new UnsupportedOperationException(String.format(err_job, line));
					}
					
					if (jobNumber <= 0)
						throw new UnsupportedOperationException(String.format(err_job, jobNumber));
					
					// Change the mode
					mode = jobNumber;
					
					// Save the last job
					if (job != null)
						box.addJob(job);
					
					// Instantiate the current job
					job = new Job();
				}
				
				scope.clear();
				nextTaskNumber = 1;
				continue;
			}
			
			// If the line is not "Servers" or "Job #", it is either a list of machines, or a task, depending on mode value
			
			// If mode is SERVERS
			if (mode == 0) {
				// The line is a list of machine, either CPU, GPU or I/O machines
				
				line = line.replaceAll(" ", "");
				line = line.replaceAll("=", "");
				line = line.replaceAll("\t", "");
				
				Type type;
				
				if (line.startsWith("CPU"))
					type = Type.CPU;
				else if (line.startsWith("GPU"))
					type = Type.GPU;
				else if (line.startsWith("I/O") || line.startsWith("IO"))
					type = Type.IO;
				else
					throw new UnsupportedOperationException(err_format);
				
				// Get all the machines
				line = line.substring(3);
				line = line.replaceAll("\\[", "");
				line = line.replaceAll("]", "");
				String[] s_capacity = line.split(",");
				
				if (s_capacity == null)
					throw new UnsupportedOperationException(err_format);
				
				if (s_capacity.length == 0)
					throw new UnsupportedOperationException(err_format);
				
				ArrayList<Machine> machines = new ArrayList<>(s_capacity.length);
				
				for (String sc : s_capacity) {
					Capacity capacity = Capacity.fromString(sc);
					
					machines.add(new Machine(type, capacity));
				}
				
				box.addMachines(machines);
			}
			// If mode is JOB
			else {
				Type type = null;
				Capacity capacity = null;
				ArrayList<Task> dependencies = new ArrayList<>();
				
				line = line.replaceAll("\t", "");

				if(line.isEmpty())
					continue;

				// Get the tak number
				if (!line.toLowerCase().startsWith("t"))
					throw new UnsupportedOperationException(String.format(err_found, line));


				// Delete the 'T'
				line = line.substring(1);
				
				// Delete whitespaces
				line = line.replaceAll(" ", "");
				
				// Copy the line (it will be useful after fetching the task number)
				String c_line = "" + line;
				
				c_line = c_line.substring(0, c_line.indexOf("="));
				
				int taskNumber = -1;
				
				try {
					taskNumber = Integer.valueOf(c_line);
				} catch (NumberFormatException ex) {
					throw new UnsupportedOperationException(err_format);
				}
				
				// Now, get the type, capacity and dependencies
				c_line = line.substring(line.indexOf("=")+1);
				String[] parameters = c_line.split(",", 3);
				
				if (parameters.length != 3)
					throw new UnsupportedOperationException(err_format);
				
				// Get the type
				for (Type t : Type.values()) {
					if (t.toString().equals(parameters[0]) || (t.toString().equals("I/O") && parameters[0].equals("IO"))) {
						type = t;
						break;
					}
				}
				
				// If type not found
				if (type == null)
					throw new UnsupportedOperationException(err_format);
				
				// Get the capacity
				capacity = Capacity.fromString(parameters[1]);
				
				// Get the dependencies
				parameters[2] = parameters[2].replaceAll("\\[", "");
				parameters[2] = parameters[2].replaceAll("]", "");
				
				if (!parameters[2].isEmpty()) {
					String[] s_dep = parameters[2].split(",");
					
					for (String dep : s_dep) {
						if (scope.get(dep) == null)
							throw new UnsupportedOperationException(String.format(err_found, dep));
						
						dependencies.add(scope.get(dep));
					}
				}
				
				Task task = new Task(type, capacity, dependencies);
				job.add(task);
				
				scope.put("T" + taskNumber, task);
			}
		}
		
		// Save the last job
		if (job != null)
			box.addJob(job);
		
		return box;
	}
	
	public static @NotNull String readContent() {
		StringBuilder content = new StringBuilder();
		
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null)
				content.append(line)
						.append("\n");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			try {
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return content.toString();
	}
}
