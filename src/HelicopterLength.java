
/* Read input from STDIN. Print your output to STDOUT*/
import java.io.*;
import java.util.*;

public class HelicopterLength {
	public static final String SYMBOL_EMPTY_SPACE = "o";
	public static final String SYMBOL_TOWER = "x";
	public static final String DELIMITER = "#";
	public static final int RESULT_INVALID = -1;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int numberOfTestCases = Integer.parseInt(sc.nextLine());
		int maxLength = 0;
		// Iterate each test case
		for (int testCaseCount = 0; testCaseCount < numberOfTestCases; testCaseCount++) {
			final String areas = sc.nextLine();
			int currentMaxLength = HelicopterLength.findMaxLengthOfHelicopter(areas.split(HelicopterLength.DELIMITER));
			// If length is invalid, break the loop and return -1
			if (HelicopterLength.RESULT_INVALID == currentMaxLength) {
				maxLength = HelicopterLength.RESULT_INVALID;
				break;
			}
			if (maxLength < currentMaxLength) {
				maxLength = currentMaxLength;
			}
		}
		sc.close();
		// To print the result
		System.out.print(maxLength);
	}

	/**
	 * * To find the max length of the Helicopter * * @param areas An area of the
	 * city * @return
	 */
	public static int findMaxLengthOfHelicopter(String areas[]) {
		int maxHelicopterLength = 0;
		int currentHelicopterLength = 0;
		int length = areas.length;
		String lastElement = null;
		for (int surfaceIndex = 0; surfaceIndex < length; surfaceIndex++) {
			if (null == lastElement) {
				lastElement = areas[surfaceIndex];
			}
			if (!HelicopterLength.isValidInput(lastElement)) {
				return HelicopterLength.RESULT_INVALID;
			}
			if (HelicopterLength.SYMBOL_EMPTY_SPACE.equals(lastElement)) {
				currentHelicopterLength++;
				if (maxHelicopterLength < currentHelicopterLength) {
					maxHelicopterLength = currentHelicopterLength;
				}
			} else {
				currentHelicopterLength = 0;
			}
			lastElement = areas[surfaceIndex];
		}
		return maxHelicopterLength;
	}

	/**
	 * 
	 * * To check whether the input is correct or not * @param input A input string
	 * * @return
	 */
	public static boolean isValidInput(String input) {
		return (HelicopterLength.SYMBOL_EMPTY_SPACE.equals(input) || HelicopterLength.SYMBOL_TOWER.equals(input));
	}
}