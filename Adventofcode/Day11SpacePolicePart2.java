import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import java.util.Map.Entry;
import java.util.Objects;


/*--- Part Two ---
You're not sure what it's trying to paint, but it's definitely not a registration identifier. The Space Police are getting impatient.

Checking your external ship cameras again, you notice a white panel marked "emergency hull painting robot starting panel". The rest of the panels are still black, but it looks like the robot was expecting to start on a white panel, not a black one.

Based on the Space Law Space Brochure that the Space Police attached to one of your windows, a valid registration identifier is always eight capital letters. After starting the robot on a single white panel instead, what registration identifier does it paint on your hull?

Although it hasn't changed, you can still get your puzzle input.
*/
public class Day11SpacePolicePart2 {

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
	
	public static final int TURN_90_DEG_LEFT = 0;
	public static final int TURN_90_DEG_RIGHT = 1;
	
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_UP = 3;
	public static final int DIRECTION_DOWN = 4;
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	

	public static void main(String args[]) throws Exception {
		Day11SpacePolicePart2 day11SpacePolicePart1 = new Day11SpacePolicePart2();
		long intCodes[] = {3L,8L,1005L,8L,325L,1106L,0L,11L,0L,0L,0L,104L,1L,104L,0L,3L,8L,102L,-1L,8L,10L,1001L,10L,1L,10L,4L,10L,108L,0L,8L,10L,4L,10L,101L,0L,8L,28L,2L,3L,7L,10L,2L,1109L,3L,10L,2L,102L,0L,10L,2L,1005L,12L,10L,3L,8L,102L,-1L,8L,10L,101L,1L,10L,10L,4L,10L,1008L,8L,0L,10L,4L,10L,101L,0L,8L,67L,2L,109L,12L,10L,1L,1003L,15L,10L,3L,8L,1002L,8L,-1L,10L,1001L,10L,1L,10L,4L,10L,108L,1L,8L,10L,4L,10L,101L,0L,8L,96L,3L,8L,102L,-1L,8L,10L,101L,1L,10L,10L,4L,10L,1008L,8L,0L,10L,4L,10L,1002L,8L,1L,119L,3L,8L,102L,-1L,8L,10L,1001L,10L,1L,10L,4L,10L,1008L,8L,0L,10L,4L,10L,101L,0L,8L,141L,3L,8L,1002L,8L,-1L,10L,101L,1L,10L,10L,4L,10L,108L,0L,8L,10L,4L,10L,1001L,8L,0L,162L,1L,106L,17L,10L,1006L,0L,52L,1006L,0L,73L,3L,8L,102L,-1L,8L,10L,1001L,10L,1L,10L,4L,10L,108L,1L,8L,10L,4L,10L,1001L,8L,0L,194L,1006L,0L,97L,1L,1004L,6L,10L,1006L,0L,32L,2L,8L,20L,10L,3L,8L,102L,-1L,8L,10L,101L,1L,10L,10L,4L,10L,1008L,8L,1L,10L,4L,10L,102L,1L,8L,231L,1L,1L,15L,10L,1006L,0L,21L,1L,6L,17L,10L,2L,1005L,8L,10L,3L,8L,102L,-1L,8L,10L,101L,1L,10L,10L,4L,10L,108L,1L,8L,10L,4L,10L,102L,1L,8L,267L,2L,1007L,10L,10L,3L,8L,1002L,8L,-1L,10L,1001L,10L,1L,10L,4L,10L,1008L,8L,1L,10L,4L,10L,102L,1L,8L,294L,1006L,0L,74L,2L,1003L,2L,10L,1L,107L,1L,10L,101L,1L,9L,9L,1007L,9L,1042L,10L,1005L,10L,15L,99L,109L,647L,104L,0L,104L,1L,21101L,936333018008L,0L,1L,21101L,342L,0L,0L,1106L,0L,446L,21102L,937121129228L,1L,1L,21101L,0L,353L,0L,1105L,1L,446L,3L,10L,104L,0L,104L,1L,3L,10L,104L,0L,104L,0L,3L,10L,104L,0L,104L,1L,3L,10L,104L,0L,104L,1L,3L,10L,104L,0L,104L,0L,3L,10L,104L,0L,104L,1L,21101L,0L,209383001255L,1L,21102L,400L,1L,0L,1106L,0L,446L,21101L,0L,28994371675L,1L,21101L,411L,0L,0L,1105L,1L,446L,3L,10L,104L,0L,104L,0L,3L,10L,104L,0L,104L,0L,21101L,867961824000L,0L,1L,21101L,0L,434L,0L,1106L,0L,446L,21102L,1L,983925674344L,1L,21101L,0L,445L,0L,1106L,0L,446L,99L,109L,2L,21201L,-1L,0L,1L,21102L,40L,1L,2L,21101L,477L,0L,3L,21102L,467L,1L,0L,1106L,0L,510L,109L,-2L,2106L,0L,0L,0L,1L,0L,0L,1L,109L,2L,3L,10L,204L,-1L,1001L,472L,473L,488L,4L,0L,1001L,472L,1L,472L,108L,4L,472L,10L,1006L,10L,504L,1101L,0L,0L,472L,109L,-2L,2106L,0L,0L,0L,109L,4L,1201L,-1L,0L,509L,1207L,-3L,0L,10L,1006L,10L,527L,21102L,1L,0L,-3L,21202L,-3L,1L,1L,21201L,-2L,0L,2L,21102L,1L,1L,3L,21102L,1L,546L,0L,1106L,0L,551L,109L,-4L,2105L,1L,0L,109L,5L,1207L,-3L,1L,10L,1006L,10L,574L,2207L,-4L,-2L,10L,1006L,10L,574L,22101L,0L,-4L,-4L,1105L,1L,642L,21202L,-4L,1L,1L,21201L,-3L,-1L,2L,21202L,-2L,2L,3L,21101L,0L,593L,0L,1105L,1L,551L,22102L,1L,1L,-4L,21101L,1L,0L,-1L,2207L,-4L,-2L,10L,1006L,10L,612L,21102L,1L,0L,-1L,22202L,-2L,-1L,-2L,2107L,0L,-3L,10L,1006L,10L,634L,21201L,-1L,0L,1L,21101L,634L,0L,0L,105L,1L,509L,21202L,-2L,-1L,-2L,22201L,-4L,-2L,-4L,109L,-5L,2106L,0L,0L};
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		int haltCode = HALT_CODE_NORMAL;
		Position currentPosition = new Position(0, 0);
		Map<String, Long> colorMap = new HashMap<String, Long>();
		colorMap.put("0,0", 0L);
		int lastDirection = DIRECTION_UP;
		
		while (haltCode != HALT_CODE_EXIT) {
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			haltCode = intCodeComputer.haltCode;
			
			if (haltCode == HALT_CODE_INPUT) {
				String key = currentPosition.x + "," + currentPosition.y;
				intCodeComputer.inputValues.add(colorMap.getOrDefault(key, 0L));
			}
			
			if (haltCode == HALT_CODE_OUTPUT && intCodeComputer.outputList.size() == 2) {				
				long colorValue = intCodeComputer.outputList.get(0);
				long directionValue = intCodeComputer.outputList.get(1);
				colorMap.put(currentPosition.x + "," + currentPosition.y, colorValue);
				if (TURN_90_DEG_LEFT == directionValue) {
					switch (lastDirection) {
					case DIRECTION_LEFT:
						lastDirection = DIRECTION_DOWN;
						currentPosition.y = currentPosition.y- 1;
						break;
					case DIRECTION_RIGHT:
						lastDirection = DIRECTION_UP;
						currentPosition.y = currentPosition.y + 1;
						break;
					case DIRECTION_UP:
						lastDirection = DIRECTION_LEFT;
						currentPosition.x = currentPosition.x - 1;
						break;
					case DIRECTION_DOWN:
						lastDirection = DIRECTION_RIGHT;
						currentPosition.x = currentPosition.x + 1;
						break;
					default:
						break;
					}
				} else if (TURN_90_DEG_RIGHT == directionValue) {
					switch (lastDirection) {
					case DIRECTION_LEFT:
						lastDirection = DIRECTION_UP;
						currentPosition.y = currentPosition.y + 1;
						break;
					case DIRECTION_RIGHT:
						lastDirection = DIRECTION_DOWN;
						currentPosition.y = currentPosition.y- 1;
						break;
					case DIRECTION_UP:
						lastDirection = DIRECTION_RIGHT;
						currentPosition.x = currentPosition.x + 1;
						break;
					case DIRECTION_DOWN:
						lastDirection = DIRECTION_LEFT;
						currentPosition.x = currentPosition.x - 1;
						break;
					default:
						break;
					}
				}
				
				intCodeComputer.outputList.clear();
			}
		}
		
		System.out.println("Grid size---"+ colorMap.size());
		
		day11SpacePolicePart1.constructPositionMap(colorMap);
		
		

	}
	
	void constructPositionMap(Map<String, Long> colorMap) {
		Map<Position, Long> positionMap = new HashMap<Position, Long>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		for (Entry<String, Long> entry : colorMap.entrySet()) {
			final String key = entry.getKey();
			final long value = entry.getValue();
			final String xY[] = key.split(",");
			stringBuilder.append("'" + xY[0] + "," +  xY[1] + "' : " + value +",");
			final Position position = new Position(Integer.parseInt(xY[0]), Integer.parseInt(xY[1]));
			positionMap.put(position, value);
		}
		stringBuilder.append("}");
		System.out.print(stringBuilder.toString());
		int minX = positionMap.keySet().stream().map(Position::getX).min(Integer::compareTo).orElse(0);
		int maxX = positionMap.keySet().stream().map(Position::getX).max(Integer::compareTo).orElse(0);
		
		int minY = positionMap.keySet().stream().map(Position::getY).min(Integer::compareTo).orElse(0);
		int maxY = positionMap.keySet().stream().map(Position::getY).max(Integer::compareTo).orElse(0);
		

		BufferedImage bufferedImage = new BufferedImage(Math.abs(maxX - minX), Math.abs(maxY - minY) + 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        for (Map.Entry<Position, Long> entry : positionMap.entrySet()) {
            g2d.setColor(entry.getValue() == 1L ? Color.WHITE : Color.BLACK);
            g2d.fillRect(entry.getKey().getX() - minX, entry.getKey().getY() - minY, 1, 1);
        }

        File file = new File("space.png");
        try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	static class Position {
		int x;
		int y;
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
		
	}

	static class IntCodeComputer {
		public List<Long> inputValues;
		boolean isProgramHalted = false;
		int increamentFactor = 0;
		int relativeBase = 0;
		long[] intCodes;
		Map<String, Long> valuesMap = new HashMap<String, Long>();
		int haltCode = HALT_CODE_NORMAL;

		IntCodeComputer(long intCodes[]) {
			this.intCodes = intCodes;
			this.inputValues = new ArrayList<Long>();
		}

		public long output = 0;
		List<Long> outputList = new ArrayList<>();

		private void extractAndExecuteOptCodes() {
			while(!Day11SpacePolicePart2.HALT_CODES.contains(this.haltCode)) {
				extractAndExecuteOptCodes(this.intCodes, this.increamentFactor);
			}
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
				outputList.add(output);
				if (outputList.size() == 2) {
					this.haltCode = HALT_CODE_OUTPUT;
					return;
				}
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
