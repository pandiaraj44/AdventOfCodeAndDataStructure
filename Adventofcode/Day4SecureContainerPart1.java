import java.util.ArrayList;
import java.util.List;


/*
--- Day 4: Secure Container ---
You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the password on a sticky note, but someone threw it out.

However, they do remember a few key facts about the password:

It is a six-digit number.
The value is within the range given in your puzzle input.
Two adjacent digits are the same (like 22 in 122345).
Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
Other than the range rule, the following are true:

111111 meets these criteria (double 11, never decreases).
223450 does not meet these criteria (decreasing pair of digits 50).
123789 does not meet these criteria (no double).
How many different passwords within the range given in your puzzle input meet these criteria?

Your puzzle input is 264793-803935.

Your puzzle answer was 966.


*/
public class Day4SecureContainerPart1 {
	
	public static void main(String args[]) throws Exception {
		Day4SecureContainerPart1 day4SecureContainerPart1 = new Day4SecureContainerPart1();
		int inputStartRange = 264793;
		int inputEndRange = 803935;
		int matchedCount = 0;
		for (int start = inputStartRange; start <= inputEndRange; start++ ) {
			boolean isMatched = day4SecureContainerPart1.isPasswordMatched(start);
			if (isMatched) {
				System.out.println("Matched Numbers---" + start);
				matchedCount ++;
			}
		}
		System.out.println("Matched Count---" + matchedCount);
		
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
