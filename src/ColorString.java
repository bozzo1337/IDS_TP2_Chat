import java.util.Random;
import java.lang.*;

public class ColorString {

public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
private static String[] colors = new String[6];

	public static String drawColor(){
		colors[0] = ANSI_RED;
		colors[1] = ANSI_GREEN;
		colors[2] = ANSI_YELLOW;
		colors[3] = ANSI_BLUE;
		colors[4] = ANSI_PURPLE;
		colors[5] = ANSI_CYAN;
		Random rdm = new Random();
		int idColor = (int) Math.floor((double)(rdm.nextFloat() * 5.99));
		return colors[idColor];
	}

}