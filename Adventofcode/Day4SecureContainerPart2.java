import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
--- Part Two ---
An Elf just remembered one more important detail: the two adjacent matching digits are not part of a larger group of matching digits.

Given this additional criterion, but still ignoring the range rule, the following are now true:

112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).
How many different passwords within the range given in your puzzle input meet all of the criteria?

Your puzzle answer was 628.


*/
public class Day4SecureContainerPart2 {
	
	public static void main(String args[]) throws Exception {
		Day4SecureContainerPart2 day4SecureContainerPart2 = new Day4SecureContainerPart2();
		int inputStartRange = 264793;
		int inputEndRange = 803935;
		int matchedCount = 0;
		for (int start = inputStartRange; start <= inputEndRange; start++ ) {
			boolean isMatched = day4SecureContainerPart2.isPasswordMatched(start);
			if (isMatched) {
				// System.out.println("Matched Numbers---" + start);
				isMatched = day4SecureContainerPart2.isAdditionalCriteriaMatched(start);
				if (isMatched) {
					System.out.println("Matched Numbers---" + start);
					matchedCount ++;
				}
			}
		}
		System.out.println("Matched Count---" + matchedCount);
		
	}
	
	public boolean isAdditionalCriteriaMatched(int number) {
		// Char c = String.valueOf(number).toCharArray();
		List<Integer> digitsList = new ArrayList<>();
		while (number > 0) {
			digitsList.add(number % 10);
			number = number /10;
		}
		Collections.reverse(digitsList);
		int lastCount = -1;
		int size = digitsList.size();
		Map<Integer, Integer> digitsMap = new HashMap<>();
		for (int i =0; i< size - 1; i++) {
			int firstValue = digitsList.get(i);
			int secondValue = digitsList.get(i + 1);
			if (firstValue == secondValue) {
				if (lastCount == -1) {
					lastCount = 1;
				}
				lastCount++;
			} else {
				lastCount = -1;
			}
			digitsMap.put(secondValue, lastCount);
		}
		if (digitsMap.containsValue(2)) {
			return true;
		}
		return false;
	}
	
	public boolean isPasswordMatched(int number) {
		boolean isMatched = false;
		List<Integer> digitsList = new ArrayList<>();
		while (number > 0) {
			digitsList.add(number % 10);
			number = number /10;
		}
		boolean isAdjacentElementsSame = false;
		for (int end = digitsList.size() - 1; end > 0; end-- ) {
			int firstValue = digitsList.get(end);
			int secondValue = digitsList.get(end -1);
			if (firstValue < secondValue) {
				isMatched = true;
			} else if (firstValue == secondValue) {
				isAdjacentElementsSame = true;
				isMatched = true;
			} else {
				isMatched = false;
				break;
			}
		}
		return (isMatched && isAdjacentElementsSame) ? true : false;
	}
}
