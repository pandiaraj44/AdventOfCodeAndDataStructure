import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;


/*--- Day 19: Tractor Beam ---
Unsure of the state of Santa's ship, you borrowed the tractor beam technology from Triton. Time to test it out.

When you're safely away from anything else, you activate the tractor beam, but nothing happens. It's hard to tell whether it's working if there's nothing to use it on. Fortunately, your ship's drone system can be configured to deploy a drone to specific coordinates and then check whether it's being pulled. There's even an Intcode program (your puzzle input) that gives you access to the drone system.

The program uses two input instructions to request the X and Y position to which the drone should be deployed. Negative numbers are invalid and will confuse the drone; all numbers should be zero or positive.

Then, the program will output whether the drone is stationary (0) or being pulled by something (1). For example, the coordinate X=0, Y=0 is directly in front of the tractor beam emitter, so the drone control program will always report 1 at that location.

To better understand the tractor beam, it is important to get a good picture of the beam itself. For example, suppose you scan the 10x10 grid of points closest to the emitter:

       X
  0->      9
 0#.........
 |.#........
 v..##......
  ...###....
  ....###...
Y .....####.
  ......####
  ......####
  .......###
 9........##
In this example, the number of points affected by the tractor beam in the 10x10 area closest to the emitter is 27.

However, you'll need to scan a larger area to understand the shape of the beam. How many points are affected by the tractor beam in the 50x50 area closest to the emitter? (For each of X and Y, this will be 0 through 49.)

Your puzzle answer was 220.


*/
public class Day19TractorBeamPart1 implements AOC<Long>{

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
	
	public static final int HALT_CODE_INPUT = 3;
	public static final int HALT_CODE_OUTPUT = 4;
	public static final int HALT_CODE_EXIT = 99;
	public static final int HALT_CODE_NORMAL = 0;

	
	public static int DRONE = 1;
	public static int EMPTY = 0;
	

	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	

	public static void main(String args[]) throws Exception {
		Day19TractorBeamPart1 day19TractorBeamPart1 = new Day19TractorBeamPart1();
		Long dustCollected = day19TractorBeamPart1.run();
    	System.out.println("How many points are affected by the tractor beam in the 50x50 area closest to the emitter? (For each of X and Y, this will be 0 through 49.)--" + dustCollected);

	}
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Override
	public Long run() {
		long intCodes[] = {109L,424L,203L,1L,21102L,1L,11L,0L,1105L,1L,282L,21102L,18L,1L,0L,1106L,0L,259L,2102L,1L,1L,221L,203L,1L,21102L,31L,1L,0L,1105L,1L,282L,21101L,38L,0L,0L,1106L,0L,259L,21002L,23L,1L,2L,22101L,0L,1L,3L,21102L,1L,1L,1L,21101L,57L,0L,0L,1105L,1L,303L,1202L,1L,1L,222L,20102L,1L,221L,3L,20102L,1L,221L,2L,21102L,1L,259L,1L,21102L,80L,1L,0L,1105L,1L,225L,21102L,72L,1L,2L,21101L,91L,0L,0L,1105L,1L,303L,1201L,1L,0L,223L,20102L,1L,222L,4L,21101L,0L,259L,3L,21102L,1L,225L,2L,21102L,1L,225L,1L,21102L,1L,118L,0L,1105L,1L,225L,20102L,1L,222L,3L,21101L,104L,0L,2L,21101L,0L,133L,0L,1105L,1L,303L,21202L,1L,-1L,1L,22001L,223L,1L,1L,21102L,148L,1L,0L,1106L,0L,259L,1201L,1L,0L,223L,20101L,0L,221L,4L,20102L,1L,222L,3L,21101L,0L,18L,2L,1001L,132L,-2L,224L,1002L,224L,2L,224L,1001L,224L,3L,224L,1002L,132L,-1L,132L,1L,224L,132L,224L,21001L,224L,1L,1L,21101L,195L,0L,0L,106L,0L,109L,20207L,1L,223L,2L,20101L,0L,23L,1L,21102L,1L,-1L,3L,21102L,214L,1L,0L,1106L,0L,303L,22101L,1L,1L,1L,204L,1L,99L,0L,0L,0L,0L,109L,5L,2102L,1L,-4L,249L,22102L,1L,-3L,1L,22102L,1L,-2L,2L,22102L,1L,-1L,3L,21101L,250L,0L,0L,1105L,1L,225L,22102L,1L,1L,-4L,109L,-5L,2106L,0L,0L,109L,3L,22107L,0L,-2L,-1L,21202L,-1L,2L,-1L,21201L,-1L,-1L,-1L,22202L,-1L,-2L,-2L,109L,-3L,2105L,1L,0L,109L,3L,21207L,-2L,0L,-1L,1206L,-1L,294L,104L,0L,99L,21202L,-2L,1L,-2L,109L,-3L,2105L,1L,0L,109L,5L,22207L,-3L,-4L,-1L,1206L,-1L,346L,22201L,-4L,-3L,-4L,21202L,-3L,-1L,-1L,22201L,-4L,-1L,2L,21202L,2L,-1L,-1L,22201L,-4L,-1L,1L,21202L,-2L,1L,3L,21101L,0L,343L,0L,1105L,1L,303L,1105L,1L,415L,22207L,-2L,-3L,-1L,1206L,-1L,387L,22201L,-3L,-2L,-3L,21202L,-2L,-1L,-1L,22201L,-3L,-1L,3L,21202L,3L,-1L,-1L,22201L,-3L,-1L,2L,21201L,-4L,0L,1L,21102L,384L,1L,0L,1106L,0L,303L,1105L,1L,415L,21202L,-4L,-1L,-4L,22201L,-4L,-3L,-4L,22202L,-3L,-2L,-2L,22202L,-2L,-4L,-4L,22202L,-3L,-2L,-3L,21202L,-4L,-1L,-2L,22201L,-3L,-2L,1L,21202L,1L,1L,-4L,109L,-5L,2105L,1L,0L};
		long affectedDrones = 0L;
		
		final List<List<Position>> droneList = generateDroneMap();
		
		for (List<Position> drones : droneList) {
			for (Position dronePosition : drones) {
				boolean isXindexInput = true;
				IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
				int haltCode = HALT_CODE_NORMAL;
				while (haltCode != HALT_CODE_EXIT) {
					intCodeComputer.haltCode = HALT_CODE_NORMAL;
					intCodeComputer.extractAndExecuteOptCodes();
					haltCode = intCodeComputer.haltCode;
					
					if (haltCode == HALT_CODE_INPUT) {
						
						int input = 0;
						if (isXindexInput) {
							input = dronePosition.getX();
							isXindexInput = false;
						} else {
							input = dronePosition.getY();
							isXindexInput = true;
						}
						intCodeComputer.inputValues.add(input);
					}
					
					if (haltCode == HALT_CODE_OUTPUT) {
						long code = intCodeComputer.output;
						System.out.println(code);
						if (code == DRONE) {
							affectedDrones++;
						}
					}
				}
				
			}
		}
		
		
		return affectedDrones;
	}
	
	List<List<Position>> generateDroneMap() {
		List<List<Position>> droneList = new ArrayList<List<Position>>();
		for (int yIndex =0; yIndex < HEIGHT ; yIndex++) {
			List<Position> dronePositions = new ArrayList<Position>();
			for (int xIndex =0; xIndex < WIDTH ; xIndex++) {
				Position position = new Position(xIndex, yIndex);
				dronePositions.add(position);
			}
			droneList.add(dronePositions);
		}
		return droneList;
	}
	
	
	static class Position {
		int x;
		int y;
		int data;
		public Position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		
		
		
	}

	static class IntCodeComputer {
		public List<Integer> inputValues;
		boolean isProgramHalted = false;
		int increamentFactor = 0;
		int relativeBase = 0;
		long[] intCodes;
		Map<String, Long> valuesMap = new HashMap<String, Long>();
		int haltCode = HALT_CODE_NORMAL;

		IntCodeComputer(long intCodes[]) {
			this.intCodes = intCodes;
			this.inputValues = new ArrayList<Integer>();
		}

		public long output = 0;
		List<Long> outputList = new ArrayList<>();

		private void extractAndExecuteOptCodes() {
			while(!Day19TractorBeamPart1.HALT_CODES.contains(this.haltCode)) {
				extractAndExecuteOptCodes(this.intCodes, this.increamentFactor);
			}
		}

		private void extractAndExecuteOptCodes(long intCodes[], int startPosition) {
			increamentFactor = startPosition;
			long params[] = getParameters(intCodes[startPosition]);
			long optCode = Integer.parseInt(params[1] + "" + params[0]);
			long firstParmeterMode = params[2];
			long secondParmeterMode = params[3];
			long thirdParmeterMode = params[4];
			if (OPT_CODE_99 == optCode) {
				this.isProgramHalted = true;
				// increamentFactor = 0;
				this.haltCode = HALT_CODE_EXIT;
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
					this.haltCode = HALT_CODE_INPUT;
					return;
				} else {
					input = this.inputValues.get(0);
					this.inputValues.remove(0);
				}
				if (firstParmeterMode == POSITION_MODE || firstParmeterMode == IMMEDIATE_MODE) {
					setValue(getValue(startPosition + 1), input);
				} 
				else {
					setValue(getValue(startPosition + 1) + relativeBase, input);
				}
				increamentFactor = startPosition + 2;
			} else if (OPT_CODE_4 == optCode) {
				long firstParamValue = getValueByParmsMode(firstParmeterMode, startPosition + 1);
				output = firstParamValue;
				increamentFactor = startPosition + 2;
				outputList.add(output);
				// if (outputList.size() == 1) {
					 this.haltCode = HALT_CODE_OUTPUT;
					 return;
				//}
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
			// extractAndExecuteOptCodes(intCodes, increamentFactor);
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
