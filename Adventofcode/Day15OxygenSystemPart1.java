import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;


/*--- Day 15: Oxygen System ---
Out here in deep space, many things can go wrong. Fortunately, many of those things have indicator lights. Unfortunately, one of those lights is lit: the oxygen system for part of the ship has failed!

According to the readouts, the oxygen system must have failed days ago after a rupture in oxygen tank two; that section of the ship was automatically sealed once oxygen levels went dangerously low. A single remotely-operated repair droid is your only option for fixing the oxygen system.

The Elves' care package included an Intcode program (your puzzle input) that you can use to remotely control the repair droid. By running that program, you can direct the repair droid to the oxygen system and fix the problem.

The remote control program executes the following steps in a loop forever:

Accept a movement command via an input instruction.
Send the movement command to the repair droid.
Wait for the repair droid to finish the movement operation.
Report on the status of the repair droid via an output instruction.
Only four movement commands are understood: north (1), south (2), west (3), and east (4). Any other command is invalid. The movements differ in direction, but not in distance: in a long enough east-west hallway, a series of commands like 4,4,4,4,3,3,3,3 would leave the repair droid back where it started.

The repair droid can reply with any of the following status codes:

0: The repair droid hit a wall. Its position has not changed.
1: The repair droid has moved one step in the requested direction.
2: The repair droid has moved one step in the requested direction; its new position is the location of the oxygen system.
You don't know anything about the area around the repair droid, but you can figure it out by watching the status codes.

For example, we can draw the area using D for the droid, # for walls, . for locations the droid can traverse, and empty space for unexplored locations. Then, the initial state looks like this:

      
      
   D  
      
      
To make the droid go north, send it 1. If it replies with 0, you know that location is a wall and that the droid didn't move:

      
   #  
   D  
      
      
To move east, send 4; a reply of 1 means the movement was successful:

      
   #  
   .D 
      
      
Then, perhaps attempts to move north (1), south (2), and east (4) are all met with replies of 0:

      
   ## 
   .D#
    # 
      
Now, you know the repair droid is in a dead end. Backtrack with 3 (which you already know will get a reply of 1 because you already know that location is open):

      
   ## 
   D.#
    # 
      
Then, perhaps west (3) gets a reply of 0, south (2) gets a reply of 1, south again (2) gets a reply of 0, and then west (3) gets a reply of 2:

      
   ## 
  #..#
  D.# 
   #  
Now, because of the reply of 2, you know you've found the oxygen system! In this example, it was only 2 moves away from the repair droid's starting position.

What is the fewest number of movement commands required to move the repair droid from its starting position to the location of the oxygen system?

Your puzzle answer was 252.
*/
public class Day15OxygenSystemPart1 implements AOC<Long>{

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

	
	public static final int DROID_BLOCKED_BY_WALL = 0;
	public static final int DROID_MOVED_ONE_STEP = 1;
	public static final int DROID_MOVED_AND_FOUND_OXYGEN = 2;
	
	public static final long DIRECTION_NORTH_OR_UP = 1;
	public static final long DIRECTION_SOUTH_OR_DOWN = 2;
	public static final long DIRECTION_WEST_OR_LEFT = 3;
	public static final long DIRECTION_EAST_OR_RIGHT = 4;
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	public static List<Long> DIRECTION_LIST = Arrays.asList(DIRECTION_NORTH_OR_UP, DIRECTION_SOUTH_OR_DOWN, DIRECTION_WEST_OR_LEFT, DIRECTION_EAST_OR_RIGHT);
	Long[][] grid;
	

	public static void main(String args[]) throws Exception {
		Day15OxygenSystemPart1 day15OxygenSystemPart1 = new Day15OxygenSystemPart1();
		day15OxygenSystemPart1.run();
	}
	
	@Override
	public Long run() {
		long intCodes[] = {3L,1033L,1008L,1033L,1L,1032L,1005L,1032L,31L,1008L,1033L,2L,1032L,1005L,1032L,58L,1008L,1033L,3L,1032L,1005L,1032L,81L,1008L,1033L,4L,1032L,1005L,1032L,104L,99L,1002L,1034L,1L,1039L,1001L,1036L,0L,1041L,1001L,1035L,-1L,1040L,1008L,1038L,0L,1043L,102L,-1L,1043L,1032L,1L,1037L,1032L,1042L,1105L,1L,124L,102L,1L,1034L,1039L,1001L,1036L,0L,1041L,1001L,1035L,1L,1040L,1008L,1038L,0L,1043L,1L,1037L,1038L,1042L,1106L,0L,124L,1001L,1034L,-1L,1039L,1008L,1036L,0L,1041L,1002L,1035L,1L,1040L,1002L,1038L,1L,1043L,101L,0L,1037L,1042L,1106L,0L,124L,1001L,1034L,1L,1039L,1008L,1036L,0L,1041L,101L,0L,1035L,1040L,1002L,1038L,1L,1043L,101L,0L,1037L,1042L,1006L,1039L,217L,1006L,1040L,217L,1008L,1039L,40L,1032L,1005L,1032L,217L,1008L,1040L,40L,1032L,1005L,1032L,217L,1008L,1039L,37L,1032L,1006L,1032L,165L,1008L,1040L,9L,1032L,1006L,1032L,165L,1101L,2L,0L,1044L,1105L,1L,224L,2L,1041L,1043L,1032L,1006L,1032L,179L,1101L,1L,0L,1044L,1106L,0L,224L,1L,1041L,1043L,1032L,1006L,1032L,217L,1L,1042L,1043L,1032L,1001L,1032L,-1L,1032L,1002L,1032L,39L,1032L,1L,1032L,1039L,1032L,101L,-1L,1032L,1032L,101L,252L,1032L,211L,1007L,0L,50L,1044L,1106L,0L,224L,1102L,0L,1L,1044L,1105L,1L,224L,1006L,1044L,247L,1001L,1039L,0L,1034L,102L,1L,1040L,1035L,102L,1L,1041L,1036L,101L,0L,1043L,1038L,102L,1L,1042L,1037L,4L,1044L,1106L,0L,0L,37L,22L,74L,27L,37L,99L,30L,8L,72L,31L,49L,29L,51L,32L,85L,21L,39L,72L,2L,2L,43L,94L,31L,11L,76L,43L,95L,21L,38L,8L,90L,13L,39L,97L,54L,47L,14L,6L,20L,49L,5L,30L,97L,9L,99L,64L,71L,24L,36L,87L,52L,94L,36L,18L,52L,42L,83L,38L,98L,53L,26L,87L,69L,32L,18L,94L,2L,93L,97L,15L,65L,65L,21L,40L,99L,19L,91L,13L,4L,89L,38L,70L,65L,41L,73L,49L,62L,54L,37L,46L,14L,49L,88L,86L,13L,89L,23L,89L,10L,3L,48L,57L,92L,43L,65L,4L,35L,97L,48L,10L,19L,64L,3L,79L,38L,87L,6L,13L,71L,49L,74L,43L,92L,8L,4L,71L,6L,35L,85L,98L,94L,6L,38L,59L,80L,65L,46L,62L,63L,62L,49L,61L,68L,6L,7L,64L,66L,40L,56L,82L,59L,30L,85L,45L,57L,36L,86L,70L,25L,83L,31L,96L,65L,19L,16L,67L,55L,36L,49L,54L,29L,75L,69L,3L,3L,37L,75L,49L,23L,65L,22L,6L,52L,75L,31L,7L,87L,85L,19L,48L,97L,65L,51L,78L,10L,35L,40L,59L,54L,14L,85L,6L,30L,94L,68L,42L,87L,46L,75L,26L,82L,36L,21L,65L,90L,16L,59L,14L,76L,55L,37L,41L,99L,80L,9L,79L,12L,59L,17L,75L,2L,40L,52L,45L,76L,45L,16L,82L,13L,55L,61L,14L,11L,49L,97L,81L,99L,38L,35L,20L,98L,51L,64L,13L,24L,85L,94L,38L,25L,87L,1L,42L,89L,18L,32L,54L,55L,17L,15L,84L,98L,25L,31L,21L,55L,44L,57L,59L,11L,78L,49L,72L,87L,20L,7L,33L,91L,80L,75L,18L,33L,37L,52L,7L,26L,87L,65L,36L,52L,92L,6L,8L,95L,89L,37L,38L,57L,25L,23L,71L,75L,47L,20L,87L,90L,37L,54L,38L,77L,32L,39L,67L,16L,69L,62L,15L,96L,47L,91L,95L,18L,96L,24L,45L,21L,64L,9L,72L,2L,54L,65L,39L,36L,54L,23L,71L,74L,18L,26L,97L,35L,44L,29L,87L,54L,48L,31L,55L,33L,85L,74L,13L,99L,82L,39L,35L,97L,43L,20L,62L,58L,86L,98L,41L,47L,92L,79L,74L,10L,85L,28L,66L,86L,18L,35L,5L,84L,67L,13L,91L,47L,44L,1L,84L,56L,32L,96L,7L,77L,21L,88L,92L,38L,31L,65L,82L,87L,45L,55L,4L,60L,58L,64L,49L,53L,3L,63L,32L,52L,43L,10L,66L,75L,96L,53L,11L,95L,44L,36L,16L,65L,91L,47L,32L,9L,3L,73L,29L,25L,93L,29L,18L,88L,45L,41L,46L,12L,94L,13L,89L,5L,36L,94L,88L,33L,10L,10L,2L,52L,90L,19L,63L,26L,84L,12L,76L,16L,42L,75L,63L,39L,32L,72L,72L,84L,70L,2L,63L,33L,74L,43L,68L,38L,84L,72L,44L,89L,18L,24L,78L,69L,4L,80L,41L,54L,75L,72L,4L,16L,91L,5L,48L,30L,64L,38L,4L,52L,38L,30L,95L,99L,32L,38L,52L,35L,58L,71L,38L,89L,86L,25L,84L,88L,41L,39L,32L,56L,79L,12L,52L,19L,80L,46L,66L,38L,32L,69L,67L,6L,87L,88L,36L,59L,51L,5L,33L,46L,45L,82L,15L,57L,80L,91L,12L,86L,29L,34L,15L,61L,19L,73L,46L,82L,60L,73L,13L,52L,36L,67L,3L,49L,87L,39L,12L,98L,58L,87L,32L,82L,47L,65L,6L,87L,71L,13L,17L,65L,69L,14L,34L,42L,82L,42L,1L,77L,63L,10L,63L,28L,90L,24L,13L,99L,19L,38L,68L,62L,44L,2L,65L,81L,95L,7L,54L,24L,58L,16L,58L,48L,95L,9L,80L,9L,51L,73L,23L,96L,49L,64L,58L,1L,6L,72L,69L,39L,2L,10L,63L,36L,9L,85L,59L,90L,41L,2L,72L,77L,23L,23L,80L,75L,33L,6L,20L,18L,59L,39L,36L,89L,35L,89L,42L,42L,22L,37L,24L,30L,51L,53L,43L,78L,48L,27L,76L,84L,22L,81L,72L,25L,95L,28L,15L,51L,58L,48L,7L,1L,90L,72L,19L,37L,52L,60L,39L,81L,20L,70L,6L,39L,82L,26L,77L,14L,96L,52L,30L,84L,33L,66L,80L,5L,52L,15L,72L,46L,55L,2L,21L,8L,97L,79L,43L,8L,91L,27L,67L,5L,18L,74L,71L,34L,51L,6L,83L,25L,52L,92L,5L,15L,85L,11L,72L,33L,85L,30L,59L,6L,84L,29L,51L,77L,99L,43L,95L,44L,83L,95L,89L,27L,54L,16L,85L,90L,82L,34L,98L,59L,87L,12L,73L,25L,74L,29L,95L,82L,51L,5L,81L,46L,51L,0L,0L,21L,21L,1L,10L,1L,0L,0L,0L,0L,0L,0L};
		Long movementCount = 0L;
		
		Position initialPosition = new Position(0, 0);
		List<Position> positions = new ArrayList<>();
		positions.add(initialPosition);
		IntCode initialInput = new IntCode();
		initialInput.intCodes  = intCodes;
		initialInput.valuesMap = new HashMap<String, Long>(); 

		initialPosition.intCode = initialInput;
		
//		Map<String, Position> tileMap = new HashMap<String, Position>();
//		tileMap.put(initialPosition.getX() + "," + initialPosition.getY(), initialPosition);
//		String currentPositionString = initialPosition.getX() + "," + initialPosition.getY();
		
		Set<String> isVisited = new HashSet<String>();
		
		boolean isOxygonFound = false;
		while (!isOxygonFound) {
			Position currentPosition = positions.get(0);
			positions.remove(0);
			if (!isVisited.contains(currentPosition.getX() + "," + currentPosition.getY()) ) {
				isVisited.add(currentPosition.getX() + "," + currentPosition.getY());
				int numberOfNewMoves = currentPosition.numberOfMoves + 1;	
				for (Long direction : DIRECTION_LIST) {
					Position nextPosition = new Position();
					if (direction == DIRECTION_WEST_OR_LEFT) {
						nextPosition.x = currentPosition.x - 1;
						nextPosition.y = currentPosition.y;
					}	else if (direction == DIRECTION_EAST_OR_RIGHT) {
						nextPosition.x = currentPosition.x + 1;
						nextPosition.y = currentPosition.y;
					}	else if (direction == DIRECTION_NORTH_OR_UP) {
						nextPosition.x = currentPosition.x;
						nextPosition.y = currentPosition.y + 1;
					}	else if (direction == DIRECTION_SOUTH_OR_DOWN) {
						nextPosition.x = currentPosition.x;
						nextPosition.y = currentPosition.y - 1;
					}
					IntCode nextIntCode = new IntCode();
					nextIntCode.increamentFactor = currentPosition.intCode.increamentFactor;
					nextIntCode.relativeBase = currentPosition.intCode.relativeBase;
					nextIntCode.intCodes = Arrays.copyOfRange(currentPosition.intCode.intCodes, 0, currentPosition.intCode.intCodes.length);
					nextIntCode.valuesMap = currentPosition.intCode.valuesMap;
					
					IntCode restult = execute(direction, nextIntCode);
					long filledBy = restult.output;
					if (filledBy == DROID_MOVED_AND_FOUND_OXYGEN) {
						isOxygonFound = true;
						System.out.println("DROID_MOVED_AND_FOUND_OXYGEN==" + numberOfNewMoves);
						break;
					} else if (filledBy == DROID_BLOCKED_BY_WALL) {
						isVisited.add(currentPosition.getX() + "," + currentPosition.getY());
					} else if (filledBy == DROID_MOVED_ONE_STEP) {
						nextPosition.intCode = restult;
						nextPosition.numberOfMoves = numberOfNewMoves;
						positions.add(nextPosition);
					}
					

				}
			}
		}
		
		return movementCount;
	}
	
	static class IntCode {
		public int increamentFactor = 0;
		public int relativeBase = 0;
		public long[] intCodes;
		public long output = 0;
		public Map<String, Long> valuesMap;
	}
	
	IntCode execute(Long direction, IntCode intCode) {
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCode.intCodes.clone());
		intCodeComputer.inputValues.add(direction);
		intCodeComputer.haltCode = HALT_CODE_NORMAL;
		intCodeComputer.increamentFactor = intCode.increamentFactor;
		intCodeComputer.relativeBase = intCode.relativeBase;
		
		intCodeComputer.extractAndExecuteOptCodes();
		
		int haltCode = intCodeComputer.haltCode;
		
		IntCode resultIntCode = new IntCode();

		
		if (haltCode == HALT_CODE_OUTPUT && intCodeComputer.outputList.size() == 1) {
			resultIntCode.output = intCodeComputer.outputList.get(0);
		}
		resultIntCode.increamentFactor = intCodeComputer.increamentFactor;
		resultIntCode.intCodes = intCodeComputer.intCodes;
		resultIntCode.relativeBase = intCodeComputer.relativeBase;
		resultIntCode.valuesMap = intCodeComputer.valuesMap;
						
		return resultIntCode;
	}
	
	
	Long[][] constructGrid(Map<String, Long> tileMap) {
		Map<Position, Long> positionMap = new HashMap<Position, Long>();
		for (Entry<String, Long> entry : tileMap.entrySet()) {
			final String key = entry.getKey();
			final long value = entry.getValue();
			final String xY[] = key.split(",");
			final Position position = new Position(Integer.parseInt(xY[0]), Integer.parseInt(xY[1]));
			positionMap.put(position, value);
		}
		// To get the size of the grid
		int maxX = positionMap.keySet().stream().map(Position::getX).max(Integer::compareTo).orElse(0);
		int maxY = positionMap.keySet().stream().map(Position::getY).max(Integer::compareTo).orElse(0);
		
		Long[][] grid = new Long[maxY + 1][maxX + 1];
		
		for (Map.Entry<Position, Long> entry : positionMap.entrySet()) {
            grid[entry.getKey().getY()][entry.getKey().getX()] = entry.getValue();
        }
		return grid;
	}
	
	static class Position {
		int x;
		int y;
		int numberOfMoves;
		IntCode intCode;
		
		Long filledBy;
				
		public Position() {
			
		}
		
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
		public Long getFilledBy() {
			return filledBy;
		}
		public void setFilledBy(Long filledBy) {
			this.filledBy = filledBy;
		}
		
	}

	static class IntCodeComputer implements Cloneable {
		
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
		
		public List<Long> inputValues;
		public int increamentFactor = 0;
		public int relativeBase = 0;
		public long[] intCodes;
		public Map<String, Long> valuesMap = new HashMap<String, Long>();
		public int haltCode = HALT_CODE_NORMAL;

		IntCodeComputer(long intCodes[]) {
			this.intCodes = intCodes;
			this.inputValues = new ArrayList<Long>();
		}

		public long output = 0;
		List<Long> outputList = new ArrayList<>();

		private void extractAndExecuteOptCodes() {
			while(!Day15OxygenSystemPart1.HALT_CODES.contains(this.haltCode)) {
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
					// this.inputValues.remove(0);
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
				if (outputList.size() == 1) {
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
