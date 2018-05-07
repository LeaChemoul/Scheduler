package fr.jlc.polytech.scheduler.core;

import fr.jlc.polytech.scheduler.ai.Beta;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit5 class to test Generator
 * @see Generator
 * @see Box
 */
class GeneratorTest {
	
	Box box;
	
	@BeforeEach
	void setup() { }
	
	/**
	 * Generate a box
	 */
    @Test
    void test_generateBox() {
	    box = Generator.generateBox();
	    //box.fillAccumulateTime();
		//box.displayComputeTime();
        System.out.println("test_generateBox> box:");
	    System.out.println(FileGenerator.generateContent(box));
	}

}