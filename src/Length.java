
public class Length {
	public static final String SYMBOL_EMPTY_SPACE = "o";
	public static final String SYMBOL_TOWER = "x";
	public static final String DELIMITER = "#";
	public static final int RESULT_INVALID = -1;

	public static void main(String args[]) {
		args = new String[3];
		args[0] = "2";
		args[1] = "x#x#x#x#x#x#x#x#x";
		args[2] = "x#x#x#x#x#x#x#xx#x";
		int numberOfTestCases = Integer.parseInt(args[0]);
		int maxLength = 0;
		// Iterate each test case
		for (int testCaseCount = 1; testCaseCount <= numberOfTestCases; testCaseCount++) {
			int currentMaxLength = findMaxLengthOfHelicopter(args[testCaseCount].split(Length.DELIMITER));
			// If length is invalid, break the loop and return -1
			if (Length.RESULT_INVALID == currentMaxLength)	{
				maxLength = Length.RESULT_INVALID;
				break;
			}
			if (maxLength < currentMaxLength) {
				maxLength = currentMaxLength;
			}
		}
		// To print the result
		System.out.print(maxLength);
	}
	
	/**
	 * To find the max length of the Helicopter
	 * 
	 * @param areas An area of the city
	 * @return
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
			if (!isValidInput(lastElement)) {
				return Length.RESULT_INVALID;
			}
			if (Length.SYMBOL_EMPTY_SPACE.equals(lastElement)) {
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
	 * To check whether the input is correct or not
	 * @param input A input string
	 * @return
	 */
	public static boolean isValidInput(String input) {
		return (Length.SYMBOL_EMPTY_SPACE.equals(input) || Length.SYMBOL_TOWER.equals(input));
	}
}
