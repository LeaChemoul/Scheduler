package fr.jlc.polytech.scheduler.ai.Console;

import fr.jlc.polytech.scheduler.ai.Gamma;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

public class GammaTest extends SchedulingTest{

    private Gamma gamma;

    @Test
    void test_manage() {
        gamma = new Gamma();

        //box = Generator.generateBox();
        System.out.println(FileGenerator.generateContent(box));

        long debut = System.currentTimeMillis();
        gamma.manage(box);
        System.out.println("Execution Time Gamma : " + (System.currentTimeMillis()-debut) + " ms");
    }
}
