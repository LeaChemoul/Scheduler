package fr.jlc.polytech.scheduler.ai.Console;

import fr.jlc.polytech.scheduler.ai.Alpha;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import org.junit.jupiter.api.Test;

class AlphaTest extends SchedulingTest{

    private Alpha alpha;

    @Test
    void test_manage() {
        alpha = new Alpha();

        //box = Generator.generateBox();

        System.out.println(FileGenerator.generateContent(box));

        long debut = System.currentTimeMillis();
        alpha.manage(box);
        System.out.println("Execution Time Alpha : " + (System.currentTimeMillis() - debut) + " ms");
    }

}