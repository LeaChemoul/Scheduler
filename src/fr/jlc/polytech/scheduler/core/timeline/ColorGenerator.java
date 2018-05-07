package fr.jlc.polytech.scheduler.core.timeline;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

/**
 * Color Generator
 */
public class ColorGenerator {
	
	private static Random random = null;
	
	@NotNull
	public static Color generateRandomBrightColor() {
		int r = getRandom().nextInt(255);
		int g = getRandom().nextInt(255);
		int b = getRandom().nextInt(255);
		
		return Color.rgb(r, g, b).brighter();
	}
	
	@NotNull
	public static ArrayList<Color> generateRandomBrightColors(int number) {
		ArrayList<Color> colors = new ArrayList<>(number);
		
		for (int i = 0; i < number; i++)
			colors.add(generateRandomBrightColor());
		
		return colors;
	}
	@NotNull
	public static ArrayList<Color> generateRandomBrightColors() {
		return generateRandomBrightColors(50);
	}
	
	@NotNull
	public static Random getRandom() {
		if (random == null)
			random = new Random(System.currentTimeMillis());
		
		return random;
	}
}
