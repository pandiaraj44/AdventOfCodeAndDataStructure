import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*--- Part Two ---
It's no good - in this configuration, the amplifiers can't generate a large enough output signal to produce the thrust you'll need. The Elves quickly talk you through rewiring the amplifiers into a feedback loop:

      O-------O  O-------O  O-------O  O-------O  O-------O
0 -+->| Amp A |->| Amp B |->| Amp C |->| Amp D |->| Amp E |-.
   |  O-------O  O-------O  O-------O  O-------O  O-------O |
   |                                                        |
   '--------------------------------------------------------+
                                                            |
                                                            v
                                                     (to thrusters)
Most of the amplifiers are connected as they were before; amplifier A's output is connected to amplifier B's input, and so on. However, the output from amplifier E is now connected into amplifier A's input. This creates the feedback loop: the signal will be sent through the amplifiers many times.

In feedback loop mode, the amplifiers need totally different phase settings: integers from 5 to 9, again each used exactly once. These settings will cause the Amplifier Controller Software to repeatedly take input and produce output many times before halting. Provide each amplifier its phase setting at its first input instruction; all further input/output instructions are for signals.

Don't restart the Amplifier Controller Software on any amplifier during this process. Each one should continue receiving and sending signals until it halts.

All signals sent or received in this process will be between pairs of amplifiers except the very first signal and the very last signal. To start the process, a 0 signal is sent to amplifier A's input exactly once.

Eventually, the software on the amplifiers will halt after they have processed the final loop. When this happens, the last output signal from amplifier E is sent to the thrusters. Your job is to find the largest output signal that can be sent to the thrusters using the new phase settings and feedback loop arrangement.

Here are some example programs:

Max thruster signal 139629729 (from phase setting sequence 9,8,7,6,5):

3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5
Max thruster signal 18216 (from phase setting sequence 9,7,8,5,6):

3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10
Try every combination of the new phase settings on the amplifier feedback loop. What is the highest signal that can be sent to the thrusters?
*/
public class Day7AmplificationCircuitPart2 {
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
		Day7AmplificationCircuitPart2 day7AmplificationCircuitPart1 = new Day7AmplificationCircuitPart2();
		 int intCodes[] = {3,8,1001,8,10,8,105,1,0,0,21,38,55,64,89,114,195,276,357,438,99999,3,9,101,3,9,9,102,3,9,9,1001,9,5,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,101,5,9,9,4,9,99,3,9,101,3,9,9,4,9,99,3,9,1002,9,4,9,101,5,9,9,1002,9,5,9,101,5,9,9,102,3,9,9,4,9,99,3,9,101,3,9,9,1002,9,4,9,101,5,9,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,99};
		 Integer initialAmplifierSquence[] = {9,8,7,6,5};
		
//		int intCodes[] = {3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
//				27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5};
//		Integer initialAmplifierSquence[] = {9,8,7,6,5};
//		
//		int intCodes[] = {3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
//				-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
//				53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10};
//		Integer initialAmplifierSquence[] = {9,7,8,5,6};
		
		List<Integer[]> amplifierSquences = day7AmplificationCircuitPart1.getCombinationOfAmplifierSquence(initialAmplifierSquence);
		int highestSignalOutput = 0;
//		amplifierSquences.clear();
//		amplifierSquences.add(initialAmplifierSquence);
		for (Integer[] amplifierSquence : amplifierSquences) {
			int output = day7AmplificationCircuitPart1.processSequence(amplifierSquence, intCodes);
			if (output > highestSignalOutput) {
				highestSignalOutput = output;
			}
		}
		System.out.println("What is the highest signal that can be sent to the thrusters--" + highestSignalOutput);		
		
	}
	
	int processSequence(Integer[] amplifierSquence, int[] intCodes) {
		int input1 = 0;
		int input2 = 0;
		int output = 0;
		Map<Integer, IntCodeComputer> intCodeComputerMap = new HashMap<>();
		for (int phaseIndex = 0; phaseIndex < amplifierSquence.length; phaseIndex++) {
			int phaseSetting = amplifierSquence[phaseIndex];
			input1 = phaseSetting;
			if (null == intCodeComputerMap.get(phaseIndex)) {
				IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone(), input1, input2);
				intCodeComputerMap.put(phaseIndex, intCodeComputer);
			} else {
				intCodeComputerMap.get(phaseIndex).input2 = input2;
			}
			if (!intCodeComputerMap.get(phaseIndex).isProgramHalted) {
				output = intCodeComputerMap.get(phaseIndex).extractAndExecuteOptCodes();
				input2 = output;
			}
			 System.out.println("output-" + output);
			if (phaseIndex == amplifierSquence.length-1){
				if (intCodeComputerMap.get(phaseIndex).isProgramHalted) {
					break;
				} else {
					phaseIndex = -1;
				}
			}
		}
		return output;
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
		boolean isProgramHalted = false;
		int increamentFactor = 0;
		int[] intCodes;

		IntCodeComputer(int intCodes[], int input1, int input2) {
			this.input = input1;
			this.input2 = input2;
			this.intCodes = intCodes;
		}
		public int outputPosition = 0; 
		static List<Integer> outputList = new ArrayList<>();
		private int extractAndExecuteOptCodes() {
			return extractAndExecuteOptCodes(this.intCodes, this.increamentFactor);
		}
		private int extractAndExecuteOptCodes(int intCodes[], int startPosition) {
			// System.out.println("Before---extractAndExecuteOptCodes--" + Arrays.toString(intCodes));
			increamentFactor = startPosition;
			int params[] = getParameters(intCodes[startPosition]);
			int optCode = Integer.parseInt(params[1] + "" + params[0]);
			int firstParmeterMode = params[2];
			int secondParmeterMode = params[3];
			int thirdParmeterMode = params[4];
			if (OPT_CODE_99 == optCode) {
				this.isProgramHalted = true;
				increamentFactor = 0;
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
				outputPosition = intCodes[startPosition + 1];
				outputList.add(intCodes[outputPosition]);
				increamentFactor = startPosition + 2;
				return intCodes[outputPosition];
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
				int thirdParmValue = thirdParmeterMode == POSITION_MODE ? intCodes[intCodes[startPosition + 3]] : intCodes[startPosition + 3];
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
