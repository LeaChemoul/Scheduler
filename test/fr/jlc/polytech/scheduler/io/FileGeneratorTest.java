package fr.jlc.polytech.scheduler.io;

import fr.jlc.polytech.scheduler.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 class to test the I/O of a box
 * @see FileGenerator
 * @see Box
 */
class FileGeneratorTest {
	
	Box box;
	
	/**
	 * Setup the box according to the situation seen in class (slide)
	 */
	@BeforeEach
	void setup() {
		box = new Box();
		box.getClusters().add(new Cluster(
				new Machine(Type.CPU, new Capacity(40, 'G')),
				new Machine(Type.CPU, new Capacity(10, 'G')),
				new Machine(Type.CPU, new Capacity(8, 'G')),
				
				new Machine(Type.GPU, new Capacity(25, 'T')),
				new Machine(Type.GPU, new Capacity(11, 'T')),
				
				new Machine(Type.IO, new Capacity(2, 'G')),
				new Machine(Type.IO, new Capacity(2, 'G')),
				new Machine(Type.IO, new Capacity(1, 'G'))
		));
		
		Task t11 = new Task(Type.CPU, new Capacity(400, 'G'));
		Task t12 = new Task(Type.CPU, new Capacity(9, 'T'));
		Task t13 = new Task(Type.GPU, new Capacity(500, 'T'), t11, t12);
		Task t14 = new Task(Type.IO, new Capacity(4, 'G'), t13);
		
		Job job1 = new Job(
				t11,
				t12,
				t13,
				t14
		);
		
		Task t21 = new Task(Type.GPU, new Capacity(800, 'G'));
		Task t22 = new Task(Type.CPU, new Capacity(1, 'T'), t21);
		Task t23 = new Task(Type.IO, new Capacity(4, 'G'), t22);
		
		Job job2 = new Job(
				t21,
				t22,
				t23
		);
		
		box.addJobs(job1, job2);
		
		System.out.println("FileGeneratorTest.setup> box: " + box.toString());
	}
	
	/**
	 * Generate the content (String) of "box"
	 */
	@Test
	void test_generateContent() {
		System.out.println("test_generateContent> box:");
		System.out.println(FileGenerator.generateContent(box));
	}
	
	/**
	 * Generate the file "input_scheduler.txt" with "box"
	 */
	@Test
	void test_generateFile() {
		FileGenerator.generateFile(box);
	}
	
	/**
	 * Read only the content of the file "input_scheduler.txt" (without parsing it)
	 */
	@Test
	void test_readContent() {
		System.out.println("test_readContent>\n" + FileGenerator.readContent());
	}
	
	/**
	 * Read the content of the file and parse it to generate a box.
	 */
	@Test
	void test_readBox() {
		Box b = FileGenerator.readBox();
		System.out.println("test_readBox> " + b.toString());
		System.out.println("test_readBox>\n" + FileGenerator.generateContent(b));
	}
}
