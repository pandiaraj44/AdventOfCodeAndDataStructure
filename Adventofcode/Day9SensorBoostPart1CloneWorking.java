import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*--- Day 9: Sensor Boost ---
You've just said goodbye to the rebooted rover and left Mars when you receive a faint distress signal coming from the asteroid belt. It must be the Ceres monitoring station!

In order to lock on to the signal, you'll need to boost your sensors. The Elves send up the latest BOOST program - Basic Operation Of System Test.

While BOOST (your puzzle input) is capable of boosting your sensors, for tenuous safety reasons, it refuses to do so until the computer it runs on passes some checks to demonstrate it is a complete Intcode computer.

Your existing Intcode computer is missing one key feature: it needs support for parameters in relative mode.

Parameters in mode 2, relative mode, behave very similarly to parameters in position mode: the parameter is interpreted as a position. Like position mode, parameters in relative mode can be read from or written to.

The important difference is that relative mode parameters don't count from address 0. Instead, they count from a value called the relative base. The relative base starts at 0.

The address a relative mode parameter refers to is itself plus the current relative base. When the relative base is 0, relative mode parameters and position mode parameters with the same value refer to the same address.

For example, given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.

The relative base is modified with the relative base offset instruction:

Opcode 9 adjusts the relative base by the value of its only parameter. The relative base increases (or decreases, if the value is negative) by the value of the parameter.
For example, if the relative base is 2000, then after the instruction 109,19, the relative base would be 2019. If the next instruction were 204,-34, then the value at address 1985 would be output.

Your Intcode computer will also need a few other capabilities:

The computer's available memory should be much larger than the initial program. Memory beyond the initial program starts with the value 0 and can be read or written like any other memory. (It is invalid to try to access memory at a negative address, though.)
The computer should have support for large numbers. Some instructions near the beginning of the BOOST program will verify this capability.
Here are some example programs that use these features:

109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 takes no input and produces a copy of itself as output.
1102,34915192,34915192,7,4,7,99,0 should output a 16-digit number.
104,1125899906842624,99 should output the large number in the middle.
The BOOST program will ask for a single input; run it in test mode by providing it the value 1. It will perform a series of checks on each opcode, output any opcodes (and the associated parameter modes) that seem to be functioning incorrectly, and finally output a BOOST keycode.

Once your Intcode computer is fully functional, the BOOST program should report no malfunctioning opcodes when run in test mode; it should only output a single value, the BOOST keycode. What BOOST keycode does it produce?

Your puzzle answer was 2789104029.
*/
public class Day9SensorBoostPart1CloneWorking {
	public static int OPT_CODE_1 = 1;
	public static int OPT_CODE_2 = 2;
	public static int OPT_CODE_3 = 3;
	public static int OPT_CODE_4 = 4;
	public static int OPT_CODE_5 = 5;
	public static int OPT_CODE_6 = 6;
	public static int OPT_CODE_7 = 7;
	public static int OPT_CODE_8 = 8;
	public static int OPT_CODE_9 = 9;
	public static int OPT_CODE_99 = 99;
	public static int POSITION_MODE = 0;
	public static int IMMEDIATE_MODE = 1;
	public static int RELATIVE_MODE = 2;

	public static void main(String args[]) throws Exception {
		Day9SensorBoostPart1CloneWorking day9SensorBoostPart1 = new Day9SensorBoostPart1CloneWorking();
		 long intCodes[] = {1102L,34463338L,34463338L,63L,1007L,63L,34463338L,63L,1005L,63L,53L,1102L,1L,3L,1000L,109L,988L,209L,12L,9L,1000L,209L,6L,209L,3L,203L,0L,1008L,1000L,1L,63L,1005L,63L,65L,1008L,1000L,2L,63L,1005L,63L,904L,1008L,1000L,0L,63L,1005L,63L,58L,4L,25L,104L,0L,99L,4L,0L,104L,0L,99L,4L,17L,104L,0L,99L,0L,0L,1102L,1L,1L,1021L,1101L,0L,21L,1009L,1101L,0L,28L,1005L,1102L,1L,27L,1015L,1102L,39L,1L,1016L,1102L,1L,30L,1003L,1102L,25L,1L,1007L,1102L,195L,1L,1028L,1101L,0L,29L,1010L,1102L,26L,1L,1004L,1102L,1L,555L,1024L,1102L,32L,1L,1014L,1101L,0L,23L,1019L,1102L,1L,31L,1008L,1101L,652L,0L,1023L,1102L,20L,1L,1000L,1101L,0L,821L,1026L,1102L,814L,1L,1027L,1102L,1L,36L,1017L,1101L,0L,38L,1006L,1102L,1L,37L,1011L,1102L,33L,1L,1001L,1102L,35L,1L,1013L,1102L,190L,1L,1029L,1102L,1L,22L,1018L,1101L,0L,0L,1020L,1102L,1L,34L,1012L,1102L,24L,1L,1002L,1101L,0L,655L,1022L,1102L,1L,546L,1025L,109L,37L,2106L,0L,-9L,4L,187L,1106L,0L,199L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-32L,1202L,1L,1L,63L,1008L,63L,38L,63L,1005L,63L,225L,4L,205L,1001L,64L,1L,64L,1106L,0L,225L,1002L,64L,2L,64L,109L,6L,1206L,10L,241L,1001L,64L,1L,64L,1106L,0L,243L,4L,231L,1002L,64L,2L,64L,109L,-12L,1207L,2L,32L,63L,1005L,63L,259L,1106L,0L,265L,4L,249L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,2L,2101L,0L,0L,63L,1008L,63L,33L,63L,1005L,63L,291L,4L,271L,1001L,64L,1L,64L,1106L,0L,291L,1002L,64L,2L,64L,109L,21L,1205L,-1L,305L,4L,297L,1106L,0L,309L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-10L,2108L,29L,-7L,63L,1005L,63L,329L,1001L,64L,1L,64L,1106L,0L,331L,4L,315L,1002L,64L,2L,64L,109L,-15L,2107L,26L,10L,63L,1005L,63L,347L,1106L,0L,353L,4L,337L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,13L,21107L,40L,41L,2L,1005L,1012L,375L,4L,359L,1001L,64L,1L,64L,1106L,0L,375L,1002L,64L,2L,64L,109L,7L,21107L,41L,40L,-5L,1005L,1012L,391L,1105L,1L,397L,4L,381L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-6L,21102L,42L,1L,2L,1008L,1013L,40L,63L,1005L,63L,421L,1001L,64L,1L,64L,1105L,1L,423L,4L,403L,1002L,64L,2L,64L,109L,-10L,2107L,23L,1L,63L,1005L,63L,441L,4L,429L,1105L,1L,445L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,3L,1201L,5L,0L,63L,1008L,63L,21L,63L,1005L,63L,467L,4L,451L,1106L,0L,471L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,18L,21108L,43L,43L,-5L,1005L,1017L,489L,4L,477L,1105L,1L,493L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-29L,1207L,7L,21L,63L,1005L,63L,511L,4L,499L,1106L,0L,515L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,23L,21108L,44L,46L,-6L,1005L,1010L,531L,1106L,0L,537L,4L,521L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,11L,2105L,1L,-3L,4L,543L,1001L,64L,1L,64L,1106L,0L,555L,1002L,64L,2L,64L,109L,-3L,1205L,-4L,571L,1001L,64L,1L,64L,1105L,1L,573L,4L,561L,1002L,64L,2L,64L,109L,-7L,2108L,21L,-8L,63L,1005L,63L,595L,4L,579L,1001L,64L,1L,64L,1105L,1L,595L,1002L,64L,2L,64L,109L,-1L,1208L,-8L,28L,63L,1005L,63L,615L,1001L,64L,1L,64L,1106L,0L,617L,4L,601L,1002L,64L,2L,64L,109L,-12L,1202L,4L,1L,63L,1008L,63L,29L,63L,1005L,63L,641L,1001L,64L,1L,64L,1106L,0L,643L,4L,623L,1002L,64L,2L,64L,109L,18L,2105L,1L,1L,1105L,1L,661L,4L,649L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-6L,2102L,1L,-8L,63L,1008L,63L,31L,63L,1005L,63L,687L,4L,667L,1001L,64L,1L,64L,1106L,0L,687L,1002L,64L,2L,64L,109L,-7L,21102L,45L,1L,6L,1008L,1015L,45L,63L,1005L,63L,709L,4L,693L,1106L,0L,713L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,-6L,2101L,0L,0L,63L,1008L,63L,31L,63L,1005L,63L,737L,1001L,64L,1L,64L,1105L,1L,739L,4L,719L,1002L,64L,2L,64L,109L,7L,1208L,-8L,24L,63L,1005L,63L,761L,4L,745L,1001L,64L,1L,64L,1105L,1L,761L,1002L,64L,2L,64L,109L,-12L,2102L,1L,10L,63L,1008L,63L,32L,63L,1005L,63L,781L,1106L,0L,787L,4L,767L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,16L,1206L,6L,801L,4L,793L,1106L,0L,805L,1001L,64L,1L,64L,1002L,64L,2L,64L,109L,14L,2106L,0L,-1L,1001L,64L,1L,64L,1106L,0L,823L,4L,811L,1002L,64L,2L,64L,109L,-18L,1201L,-7L,0L,63L,1008L,63L,27L,63L,1005L,63L,847L,1001L,64L,1L,64L,1105L,1L,849L,4L,829L,1002L,64L,2L,64L,109L,-8L,21101L,46L,0L,10L,1008L,1012L,46L,63L,1005L,63L,875L,4L,855L,1001L,64L,1L,64L,1106L,0L,875L,1002L,64L,2L,64L,109L,13L,21101L,47L,0L,-3L,1008L,1012L,44L,63L,1005L,63L,899L,1001L,64L,1L,64L,1105L,1L,901L,4L,881L,4L,64L,99L,21101L,27L,0L,1L,21102L,1L,915L,0L,1105L,1L,922L,21201L,1L,11564L,1L,204L,1L,99L,109L,3L,1207L,-2L,3L,63L,1005L,63L,964L,21201L,-2L,-1L,1L,21101L,942L,0L,0L,1105L,1L,922L,22101L,0L,1L,-1L,21201L,-2L,-3L,1L,21101L,0L,957L,0L,1106L,0L,922L,22201L,1L,-1L,-2L,1105L,1L,968L,21202L,-2L,1L,-2L,109L,-3L,2105L,1L,0};
		// long intCodes[] = {109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99};
		// long intCodes[] = {104,1125899906842624L,99};
		// long intCodes[] = {1102,34915192,34915192,7,4,7,99,0};
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		intCodeComputer.inputValues.add(1L);
		intCodeComputer.extractAndExecuteOptCodes();
		long output = intCodeComputer.output;
		System.out.println("What BOOST keycode does it produce?---"+ output);
	}

	static class IntCodeComputer {
		public List<Long> inputValues;
		boolean isProgramHalted = false;
		int increamentFactor = 0;
		int relativeBase = 0;
		long[] intCodes;
		Map<String, Long> valuesMap = new HashMap<String, Long>();

		IntCodeComputer(long intCodes[]) {
			this.intCodes = intCodes;
			this.inputValues = new ArrayList<Long>();
		}

		public long output = 0;
		static List<Long> outputList = new ArrayList<>();

		private void extractAndExecuteOptCodes() {
			extractAndExecuteOptCodes(this.intCodes, this.increamentFactor);
		}

		private void extractAndExecuteOptCodes(long intCodes[], int startPosition) {
			// System.out.println("Before---extractAndExecuteOptCodes--" +
			// Arrays.toString(intCodes));
			increamentFactor = startPosition;
			long params[] = getParameters(intCodes[startPosition]);
			long optCode = Integer.parseInt(params[1] + "" + params[0]);
			long firstParmeterMode = params[2];
			long secondParmeterMode = params[3];
			long thirdParmeterMode = params[4];
			if (OPT_CODE_99 == optCode) {
				this.isProgramHalted = true;
				// increamentFactor = 0;
				return;
			} else if (OPT_CODE_1 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				long finalPosition = thirdParmeterMode == 2 ? relativeBase + getValue(startPosition + 3) : getValue(startPosition + 3);						
				long sum = firstParamValue + secondParamValue;
				setValue(finalPosition, sum);
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_2 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				long finalPosition = thirdParmeterMode == 2 ? relativeBase + getValue(startPosition + 3) : getValue(startPosition + 3);					
				long product = firstParamValue * secondParamValue;
				setValue(finalPosition, product);
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_3 == optCode) {
				long input;
				if (this.inputValues.isEmpty()) {
					return;
				} else {
					input = this.inputValues.get(0);
					// this.inputValues.remove(0);
				}
				if (firstParmeterMode == POSITION_MODE || firstParmeterMode == IMMEDIATE_MODE) {
					// intCodes[(int) intCodes[startPosition + 1]] = input;
					setValue(getValue(startPosition + 1), input);
				} 
//				else if (firstParmeterMode == IMMEDIATE_MODE) {
//					intCodes[startPosition + 1] = input;
//				} 
				else {
					setValue(getValue(startPosition + 1) + relativeBase, input);
				}
				increamentFactor = startPosition + 2;
			} else if (OPT_CODE_4 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				output = firstParamValue;
				increamentFactor = startPosition + 2;
			} else if (OPT_CODE_5 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				
				if (firstParamValue != 0) {
					increamentFactor = (int) secondParamValue;
				} else {
					increamentFactor = startPosition + 3;
				}
			} else if (OPT_CODE_6 == optCode) {				
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				
				if (firstParamValue == 0) {
					increamentFactor = (int) secondParamValue;
				} else {
					increamentFactor = startPosition + 3;
				}
			} else if (OPT_CODE_7 == optCode) {				
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				long finalPosition = thirdParmeterMode == 2 ? relativeBase + getValue(startPosition + 3) : getValue(startPosition + 3);					
				long finalValue = 0;
				
				if (firstParamValue < secondParamValue) {
					finalValue = 1;
				} else {
					finalValue = 0;
				}
				setValue(finalPosition, finalValue);
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_8 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				long secondParamValue = getValueByParmsMode(secondParmeterMode, startPosition + 2);
				long finalPosition = thirdParmeterMode == 2 ? relativeBase + getValue(startPosition + 3) : getValue(startPosition + 3);				
				long finalValue = 0;
				
				if (firstParamValue == secondParamValue) {
					finalValue = 1;
				} else {
					finalValue = 0;
				}
				setValue(finalPosition, finalValue);
	
				
				increamentFactor = startPosition + 4;
			} else if (OPT_CODE_9 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				
				relativeBase += firstParamValue;
				increamentFactor = startPosition + 2;
			}
			extractAndExecuteOptCodes(intCodes, increamentFactor);
		}

		public long[] getParameters(long currentValue) {
			long params[] = new long[5];
			int index = 0;
			while (currentValue > 0 && index < 5) {
				params[index] = (currentValue % 10);
				currentValue = currentValue / 10;
				index++;
			}
			return params;
		}
		
		public long getValueByParmsMode(long mode, long instructionIndex) {
			if (mode == POSITION_MODE) {
				return getValue(getValue(instructionIndex));
			} else if (mode == IMMEDIATE_MODE) {
				return getValue(instructionIndex);
			} else if (mode == RELATIVE_MODE) {
				return getValue(getValue(instructionIndex) + relativeBase);
			} else {
				return 0L;
			}
		}
		
		public long getValue(long index) {
			if (index >= intCodes.length) {
				return valuesMap.getOrDefault(String.valueOf(index), 0L);
			} else {
				return intCodes[(int) index];
			}
		}
		
		
		public void setValue(long sourceIndex, long value ) {
			if (sourceIndex >= intCodes.length) {
				valuesMap.put(String.valueOf(sourceIndex), value);
			} else {
				intCodes[(int)sourceIndex] = value;
			}
		}
	}

}
