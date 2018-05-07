package fr.jlc.polytech.scheduler;

import fr.jlc.polytech.scheduler.ai.Alpha;
import fr.jlc.polytech.scheduler.ai.Beta;
import fr.jlc.polytech.scheduler.ai.Gamma;
import fr.jlc.polytech.scheduler.core.Box;
import fr.jlc.polytech.scheduler.core.Generator;
import fr.jlc.polytech.scheduler.io.FileGenerator;

import java.util.concurrent.TimeUnit;

public class SchedulerMain {

    private static Box box;
    public SchedulerMain() {

    }

    public static void main(String[] args) {

        // We generate a new box of clusters
        box = Generator.generateBox();
        //FileGenerator.generateFile(box);

        //Or we read one from a file
        //box = FileGenerator.readBox();

        System.out.println(FileGenerator.generateContent(box));

        Beta beta = new Beta();
        Alpha alpha = new Alpha();
        Gamma gamma = new Gamma();

        long debut = System.currentTimeMillis();
        beta.manage(box);
        System.out.println("Execution Time Beta : " + (System.currentTimeMillis()-debut) + " ms");

        debut = System.currentTimeMillis();
        alpha.manage(box);
        System.out.println("Execution Time Alpha : " + (System.currentTimeMillis()-debut) + " ms");

        debut = System.currentTimeMillis();
        gamma.manage(box);
        System.out.println("Execution Time Gamma : " + (System.currentTimeMillis()-debut) + " ms");

    }

    public static String toSeconds(long millis){
        return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
