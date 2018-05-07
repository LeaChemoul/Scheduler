package fr.jlc.polytech.scheduler.ai.Console;

import fr.jlc.polytech.scheduler.ai.Alpha;
import fr.jlc.polytech.scheduler.ai.Beta;
import fr.jlc.polytech.scheduler.ai.Gamma;
import fr.jlc.polytech.scheduler.core.Generator;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

public class AllTest extends SchedulingTest{

    private Alpha alpha;
    private Beta beta;
    private Gamma gamma;

    @Test
    void test_manage() {
        alpha = new Alpha();
        gamma = new Gamma();
        beta = new Beta();

        // We generate a new box of clusters
        //box = Generator.generateBox();
        //FileGenerator.generateFile(box);

        //Or we read one from a file
        //box = FileGenerator.readBox();

        System.out.println(FileGenerator.generateContent(box));

        long debut = System.currentTimeMillis();
        beta.manage(box);
        System.out.println("Execution Time Alpha : " + (System.currentTimeMillis() - debut) + " ms");

        debut = System.currentTimeMillis();
        alpha.manage(box);
        System.out.println("Execution Time Alpha : " + (System.currentTimeMillis()-debut) + " ms");

        debut = System.currentTimeMillis();
        gamma.manage(box);
        System.out.println("Execution Time Gamma : " + (System.currentTimeMillis()-debut) + " ms");

        //Uncomment for the test with the default setup
        /*System.out.println(beta.getTimeline().toStringWithTasks());
        System.out.println(beta.getTimeline().toString("Beta Version : "));
        System.out.println(alpha.getTimeline().toStringWithTasks());
        System.out.println(alpha.getTimeline().toString("Alpha Version : "));
        System.out.println(gamma.getTimeline().toStringWithTasks());
        System.out.println(gamma.getTimeline().toString("Gamma Version : "));*/


    }
}
