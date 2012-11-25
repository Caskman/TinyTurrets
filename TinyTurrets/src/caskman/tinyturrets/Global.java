package caskman.tinyturrets;

import java.util.Random;

public class Global {
	
	private static Random r = new Random();
	private static int minCompTotalValue = 100;
	
	public static int getRandNotDarkColor() {
		int one = r.nextInt(255);
		int two = r.nextInt(255);
		int three;
		if (one + two > minCompTotalValue)
			three = r.nextInt(255);
		else three = minCompTotalValue - one - two;
		int red = 255;
		int green = 255;
		int blue = 255;
		switch (r.nextInt(3)) {
		case 0:
			red = one;
			blue = two;
			green = three;
			break;
		case 1:
			red = one;
			blue = three;
			green = two;
			break;
		case 2:
			red = three;
			blue = one;
			green = two;
			break;
		}
		
		
//		int red = r.nextInt(255);
//		int green = r.nextInt(255);
//		int blue = r.nextInt(255);
		int color = (0xff000000)+(red << 16) + (green << 8) + blue;
		return color;
	}

	
}
