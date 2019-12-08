import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*--- Day 7: Amplification Circuit ---
Based on the navigational maps, you're going to need to send more power to your ship's thrusters to reach Santa in time. To do this, you'll need to configure a series of amplifiers already installed on the ship.

There are five amplifiers connected in series; each one receives an input signal and produces an output signal. They are connected such that the first amplifier's output leads to the second amplifier's input, the second amplifier's output leads to the third amplifier's input, and so on. The first amplifier's input value is 0, and the last amplifier's output leads to your ship's thrusters.

    O-------O  O-------O  O-------O  O-------O  O-------O
0 ->| Amp A |->| Amp B |->| Amp C |->| Amp D |->| Amp E |-> (to thrusters)
    O-------O  O-------O  O-------O  O-------O  O-------O
The Elves have sent you some Amplifier Controller Software (your puzzle input), a program that should run on your existing Intcode computer. Each amplifier will need to run a copy of the program.

When a copy of the program starts running on an amplifier, it will first use an input instruction to ask the amplifier for its current phase setting (an integer from 0 to 4). Each phase setting is used exactly once, but the Elves can't remember which amplifier needs which phase setting.

The program will then call another input instruction to get the amplifier's input signal, compute the correct output signal, and supply it back to the amplifier with an output instruction. (If the amplifier has not yet received an input signal, it waits until one arrives.)

Your job is to find the largest output signal that can be sent to the thrusters by trying every possible combination of phase settings on the amplifiers. Make sure that memory is not shared or reused between copies of the program.

For example, suppose you want to try the phase setting sequence 3,1,2,4,0, which would mean setting amplifier A to phase setting 3, amplifier B to setting 1, C to 2, D to 4, and E to 0. Then, you could determine the output signal that gets sent from amplifier E to the thrusters with the following steps:

Start the copy of the amplifier controller software that will run on amplifier A. At its first input instruction, provide it the amplifier's phase setting, 3. At its second input instruction, provide it the input signal, 0. After some calculations, it will use an output instruction to indicate the amplifier's output signal.
Start the software for amplifier B. Provide it the phase setting (1) and then whatever output signal was produced from amplifier A. It will then produce a new output signal destined for amplifier C.
Start the software for amplifier C, provide the phase setting (2) and the value from amplifier B, then collect its output signal.
Run amplifier D's software, provide the phase setting (4) and input value, and collect its output signal.
Run amplifier E's software, provide the phase setting (0) and input value, and collect its output signal.
The final output signal from amplifier E would be sent to the thrusters. However, this phase setting sequence may not have been the best one; another sequence might have sent a higher signal to the thrusters.

Here are some example programs:

Max thruster signal 43210 (from phase setting sequence 4,3,2,1,0):

3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0
Max thruster signal 54321 (from phase setting sequence 0,1,2,3,4):

3,23,3,24,1002,24,10,24,1002,23,-1,23,
101,5,23,23,1,24,23,23,4,23,99,0,0
Max thruster signal 65210 (from phase setting sequence 1,0,4,3,2):

3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0
Try every combination of phase settings on the amplifiers. What is the highest signal that can be sent to the thrusters?

Your puzzle answer was 101490.
*/
public class Day7AmplificationCircuitPart1 {
	public static int OPT_CODE_1 = 1;
	public static int OPT_CODE_2 = 2;
	public static int OPT_CODE_3 = 3;
	public static int OPT_CODE_4 = 4;
	public static int OPT_CODE_5 = 5;
	public static int OPT_CODE_6 = 6;
	public static int OPT_CODE_7 = 7;
	public static int OPT_CODE_8 = 8;
	public static int OPT_CODE_99 = 99;
	public static int POSITION_MODE = 0;
	public static int IMMEDIATE_MODE = 1;
	
	public static void main(String args[]) throws Exception {
		Day7AmplificationCircuitPart1 day7AmplificationCircuitPart1 = new Day7AmplificationCircuitPart1();
		int intCodes[] = {3,8,1001,8,10,8,105,1,0,0,21,38,55,64,89,114,195,276,357,438,99999,3,9,101,3,9,9,102,3,9,9,1001,9,5,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,101,5,9,9,4,9,99,3,9,101,3,9,9,4,9,99,3,9,1002,9,4,9,101,5,9,9,1002,9,5,9,101,5,9,9,102,3,9,9,4,9,99,3,9,101,3,9,9,1002,9,4,9,101,5,9,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,99};
		Integer initialAmplifierSquence[] = {0, 1, 2, 3, 4};
		
		List<Integer[]> amplifierSquences = day7AmplificationCircuitPart1.getCombinationOfAmplifierSquence(initialAmplifierSquence);
		int highestSignalOutput = 0;
		for (Integer[] amplifierSquence : amplifierSquences) {
			int input1 = 0;
			int input2 = 0;
			int output = 0;
			for (Integer phaseSetting : amplifierSquence) {
				input1 = phaseSetting;
				IntCodeComputer intCodeComputer = new IntCodeComputer(input1, input2);
				output = intCodeComputer.extractAndExecuteOptCodes(intCodes.clone(), 0);
				input2 = output;
			}
			if (output > highestSignalOutput) {
				highestSignalOutput = output;
			}
		}
		System.out.println("What is the highest signal that can be sent to the thrusters--" + highestSignalOutput);		
		
	}
	
	public List<Integer[]> getCombinationOfAmplifierSquence(Integer initialAmplifierSquence[]) {
		List<Integer[]> amplifierSquences = new ArrayList<>();
		permutateCombination(initialAmplifierSquence, 0, amplifierSquences);
		return amplifierSquences;
	}
	
	public void permutateCombination(Integer[] initialAmplifierSquence, int start, List<Integer[]> amplifierSquences) {
		
		if (start == initialAmplifierSquence.length) {
			amplifierSquences.add(initialAmplifierSquence.clone());
		}
		for (int i = start; i <initialAmplifierSquence.length; i++) {
			int temp = initialAmplifierSquence[start];
			initialAmplifierSquence[start] = initialAmplifierSquence[i];
			initialAmplifierSquence[i] = temp;
			
			permutateCombination(initialAmplifierSquence, start + 1, amplifierSquences);
			
			temp = initialAmplifierSquence[start];
			initialAmplifierSquence[start] = initialAmplifierSquence[i];
			initialAmplifierSquence[i] = temp;
		}
	}
	
	static class IntCodeComputer {
		private int input2;
		public int input;

		IntCodeComputer(int input1, int input2) {
			this.input = input1;
			this.input2 = input2;
		}
		public int outputPosition = 0; 
		static List<Integer> outputList = new ArrayList<>();
		public int extractAndExecuteOptCodes(int intCodes[], int startPosition) {
			// System.out.println("Before---extractAndExecuteOptCodes--" + Arrays.toString(intCodes));
			int increamentFactor = startPosition;
			int params[] = getParameters(intCodes[startPosition]);
			int optCode = Integer.parseInt(params[1] + "" + params[0]);
			int firstParmeterMode = params[2];
			int secondParmeterMode = params[3];
			int thirdParmeterMode = params[4];
			if (OPT_CODE_99 == optCode || startPosition >= intCodes.length) {
				return intCodes[outputPosition];
			} else if (OPT_CODE_1 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				int sum = firstParamValue + secondParamValue;
				if (thirdParmeterMode == POSITION_MODE) {
					intCodes[intCodes[startPosition + 3]] = sum;
				} else {
					intCodes[startPosition + 3] = sum;
				}
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_2 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				int product = firstParamValue * secondParamValue;
				if (thirdParmeterMode == POSITION_MODE) {
					intCodes[intCodes[startPosition + 3]] = product;
				} else {
					intCodes[startPosition + 3] = product;
				}
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_3 == optCode) {
				if (firstParmeterMode == POSITION_MODE) {
					intCodes[intCodes[startPosition + 1]] = input;
				} else {
					intCodes[startPosition + 1] = input;
				}
				increamentFactor = startPosition + 2;
			} else if (OPT_CODE_4 == optCode) {
//				if (firstParmeterMode == POSITION_MODE) {
//					outputPosition = intCodes[intCodes[startPosition + 1]];
//				} else {
//					outputPosition = intCodes[startPosition + 1];
//				}
				outputPosition = intCodes[startPosition + 1];
				outputList.add(intCodes[outputPosition]);
				increamentFactor = startPosition + 2;
			} else if (OPT_CODE_5 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				if (firstParamValue != 0) {
					increamentFactor = secondParamValue;
				} else {
					increamentFactor = startPosition + 3;
				}
			} else if (OPT_CODE_6 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				if (firstParamValue == 0) {
					increamentFactor = secondParamValue;
				} else {
					increamentFactor = startPosition + 3;
				}
			} else if (OPT_CODE_7 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				int thirdParmValue = thirdParmeterMode == POSITION_MODE ? intCodes[startPosition + 3] : intCodes[startPosition + 3];
				if (firstParamValue < secondParamValue) {
					intCodes[thirdParmValue] = 1;
				} else {
					intCodes[thirdParmValue] = 0;
				}
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_8 == optCode) {
				int firstParamValue = firstParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 1]] : intCodes[startPosition + 1];
				int secondParamValue = secondParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 2]] : intCodes[startPosition + 2];
				// int thirdParmValue = thirdParmeterMode == POSITION_MODE ? intCodes[startPosition + 3] : intCodes[startPosition + 3];
				int thirdParmValue = intCodes[startPosition + 3];
				if (firstParamValue == secondParamValue) {
					intCodes[thirdParmValue] = 1;
				} else {
					intCodes[thirdParmValue] = 0;
				}
				increamentFactor = startPosition + 4;
			}
			this.input = this.input2;
			// System.out.println("After---extractAndExecuteOptCodes--" + Arrays.toString(intCodes));
			return extractAndExecuteOptCodes(intCodes, increamentFactor);
		}
		
		public int[] getParameters(int currentValue) {
			int params[] = new int[5];
			int index = 0;
			while (currentValue > 0 && index < 5) {
				params[index] = (currentValue % 10);
				currentValue = currentValue /10;
				index++;
			}
			return params;
		}
	}
	
}
