/*
--- Part Two ---
"Good, the new computer seems to be working correctly! Keep it nearby during this mission - you'll probably use it again. Real Intcode computers support many more features than your new one, but we'll let you know what they are as you need them."

"However, your current priority should be to complete your gravity assist around the Moon. For this mission to succeed, we should settle on some terminology for the parts you've already built."

Intcode programs are given as a list of integers; these values are used as the initial state for the computer's memory. When you run an Intcode program, make sure to start by initializing memory to the program's values. A position in memory is called an address (for example, the first value in memory is at "address 0").

Opcodes (like 1, 2, or 99) mark the beginning of an instruction. The values used immediately after an opcode, if any, are called the instruction's parameters. For example, in the instruction 1,2,3,4, 1 is the opcode; 2, 3, and 4 are the parameters. The instruction 99 contains only an opcode and has no parameters.

The address of the current instruction is called the instruction pointer; it starts at 0. After an instruction finishes, the instruction pointer increases by the number of values in the instruction; until you add more instructions to the computer, this is always 4 (1 opcode + 3 parameters) for the add and multiply instructions. (The halt instruction would increase the instruction pointer by 1, but it halts the program instead.)

"With terminology out of the way, we're ready to proceed. To complete the gravity assist, you need to determine what pair of inputs produces the output 19690720."

The inputs should still be provided to the program by replacing the values at addresses 1 and 2, just like before. In this program, the value placed in address 1 is called the noun, and the value placed in address 2 is called the verb. Each of the two input values will be between 0 and 99, inclusive.

Once the program has halted, its output is available at address 0, also just like before. Each time you try a pair of inputs, make sure you first reset the computer's memory to the values in the program (your puzzle input) - in other words, don't reuse memory from a previous attempt.

Find the input noun and verb that cause the program to produce the output 19690720. What is 100 * noun + verb? (For example, if noun=12 and verb=2, the answer would be 1202.)

Noun = 77
Verb = 33

Your puzzle answer was 7733.

*/
public class Day2Program1202AlarmPart2 {
	public static int OPT_CODE_1 = 1;
	public static int OPT_CODE_2 = 2;
	public static int OPT_CODE_99 = 99;
	public static int OUTPUT = 19690720;
	
	public static void main(String args[]) throws Exception {
		Day2Program1202AlarmPart2 day2Program1202AlarmPart2 = new Day2Program1202AlarmPart2();
		final int intCodes[] = {1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,6,1,19,1,19,5,23,2,9,23,27,1,5,27,31,1,5,31,35,1,35,13,39,1,39,9,43,1,5,43,47,1,47,6,51,1,51,13,55,1,55,9,59,1,59,13,63,2,63,13,67,1,67,10,71,1,71,6,75,2,10,75,79,2,10,79,83,1,5,83,87,2,6,87,91,1,91,6,95,1,95,13,99,2,99,13,103,1,103,9,107,1,10,107,111,2,111,13,115,1,10,115,119,1,10,119,123,2,13,123,127,2,6,127,131,1,13,131,135,1,135,2,139,1,139,6,0,99,2,0,14,0};
		int result = -1;
		for (int i=0; i<=99; i++) {
			for (int j=0; j<=99; j++) {
				intCodes[1] = i;
				intCodes[2] = j;
				int[] resultCodes = day2Program1202AlarmPart2.extractAndExecuteOptCodes(intCodes.clone(), 0);
				if (OUTPUT == resultCodes[0]) {
					int noun = resultCodes[1];
					int verb = intCodes[2];
					result = (100 * noun) + verb;
					System.out.println("What is 100 * noun + verb--" + result );
					break;
				}
				
			}
			if (result != -1) {
				break;
			}
		}
	}
	
	public int[] extractAndExecuteOptCodes(int intCodes[], int startPosition) {
		if (OPT_CODE_99 == intCodes[startPosition]) {
			return intCodes;
		} else if (OPT_CODE_1 == intCodes[startPosition]) {
			int sum = intCodes[intCodes[startPosition + 1]] + intCodes[intCodes[startPosition + 2]];
			intCodes[intCodes[startPosition + 3]] = sum;
		} else if (OPT_CODE_2 == intCodes[startPosition]) {
			int product = intCodes[intCodes[startPosition + 1]] * intCodes[intCodes[startPosition + 2]];
			intCodes[intCodes[startPosition + 3]] = product;
		}
		return extractAndExecuteOptCodes(intCodes, startPosition + 4);
	}
}
