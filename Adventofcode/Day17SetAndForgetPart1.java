import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/*--- Day 17: Set and Forget ---
An early warning system detects an incoming solar flare and automatically activates the ship's electromagnetic shield. Unfortunately, this has cut off the Wi-Fi for many small robots that, unaware of the impending danger, are now trapped on exterior scaffolding on the unsafe side of the shield. To rescue them, you'll have to act quickly!

The only tools at your disposal are some wired cameras and a small vacuum robot currently asleep at its charging station. The video quality is poor, but the vacuum robot has a needlessly bright LED that makes it easy to spot no matter where it is.

An Intcode program, the Aft Scaffolding Control and Information Interface (ASCII, your puzzle input), provides access to the cameras and the vacuum robot. Currently, because the vacuum robot is asleep, you can only access the cameras.

Running the ASCII program on your Intcode computer will provide the current view of the scaffolds. This is output, purely coincidentally, as ASCII code: 35 means #, 46 means ., 10 starts a new line of output below the current one, and so on. (Within a line, characters are drawn left-to-right.)

In the camera output, # represents a scaffold and . represents open space. The vacuum robot is visible as ^, v, <, or > depending on whether it is facing up, down, left, or right respectively. When drawn like this, the vacuum robot is always on a scaffold; if the vacuum robot ever walks off of a scaffold and begins tumbling through space uncontrollably, it will instead be visible as X.

In general, the scaffold forms a path, but it sometimes loops back onto itself. For example, suppose you can see the following view from the cameras:

..#..........
..#..........
#######...###
#.#...#...#.#
#############
..#...#...#..
..#####...^..
Here, the vacuum robot, ^ is facing up and sitting at one end of the scaffold near the bottom-right of the image. The scaffold continues up, loops across itself several times, and ends at the top-left of the image.

The first step is to calibrate the cameras by getting the alignment parameters of some well-defined points. Locate all scaffold intersections; for each, its alignment parameter is the distance between its left edge and the left edge of the view multiplied by the distance between its top edge and the top edge of the view. Here, the intersections from the above image are marked O:

..#..........
..#..........
##O####...###
#.#...#...#.#
##O###O###O##
..#...#...#..
..#####...^..
For these intersections:

The top-left intersection is 2 units from the left of the image and 2 units from the top of the image, so its alignment parameter is 2 * 2 = 4.
The bottom-left intersection is 2 units from the left and 4 units from the top, so its alignment parameter is 2 * 4 = 8.
The bottom-middle intersection is 6 from the left and 4 from the top, so its alignment parameter is 24.
The bottom-right intersection's alignment parameter is 40.
To calibrate the cameras, you need the sum of the alignment parameters. In the above example, this is 76.

Run your ASCII program. What is the sum of the alignment parameters for the scaffold intersections?

Answer: 5068

To begin, get your puzzle input.

//Constructed Map From Input
........................#############........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...########0####....................
........................#...#.......#...#....................
........................#...#.......#...#....................
........................#...#.......#...#....................
........................#...#.......####0##..................
........................#...#...........#.#..................
......................##0####...........##0####..............
......................#.#.................#...#..............
................######0##.................#...#..............
................#.....#...................#...#..............
................#.....#...................#...#..............
................#.....#...................#...#..............
................#.....#...................####0##............
................#.....#.......................#.#............
................#.....#.......................##0##########..
................#.....#.........................#.........#..
................#.....#.........................#.........#..
................#.....#.........................#.........#..
..............##0######.........................#.........#..
..............#.#...............................#.........#..
........######0##...............................##########0#^
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
#########.....#...................................#########..
#.............#...................................#..........
#.............#...................................#..........
#.............#...................................#..........
#.............#############.......................#..........
#.........................#.......................#..........
#############.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.................#######..........
............#...............................#................
............#######.........................#................
..................#.........................#................
..................#.........................#................
..................#.........................#................
..................#.................#########................
..................#.................#........................
..................#.................#........................
..................#.................#........................
..................#######...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#############........................


After Applied InterSection points
........................#############........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...########0####....................
........................#...#.......#...#....................
........................#...#.......#...#....................
........................#...#.......#...#....................
........................#...#.......####0##..................
........................#...#...........#.#..................
......................##0####...........##0####..............
......................#.#.................#...#..............
................######0##.................#...#..............
................#.....#...................#...#..............
................#.....#...................#...#..............
................#.....#...................#...#..............
................#.....#...................####0##............
................#.....#.......................#.#............
................#.....#.......................##0##########..
................#.....#.........................#.........#..
................#.....#.........................#.........#..
................#.....#.........................#.........#..
..............##0######.........................#.........#..
..............#.#...............................#.........#..
........######0##...............................##########0#^
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
........#.....#...........................................#..
#########.....#...................................#########..
#.............#...................................#..........
#.............#...................................#..........
#.............#...................................#..........
#.............#############.......................#..........
#.........................#.......................#..........
#############.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.......................#..........
............#.............#.................#######..........
............#...............................#................
............#######.........................#................
..................#.........................#................
..................#.........................#................
..................#.........................#................
..................#.................#########................
..................#.................#........................
..................#.................#........................
..................#.................#........................
..................#######...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#...........#........................
........................#############........................

*/
public class Day17SetAndForgetPart1 implements AOC<Long>{

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

	
	public static final String SCAFFOLD = "#";
	public static final int ASCII_SCAFFOLD = 35;
	public static final String SCAFFOLD_INTERSECTION_POINT = "0";
	
	public static final String OPEN_SPACE = ".";
	public static final int ASCII_OPEN_SPACE = 46;
	
	public static final String VACCUM_ROBOT = "^";
	public static final int ASCII_VACCUM_ROBOT = 94;
	
	public static final int ASCII_NEW_LINE = 10;
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	

	public static void main(String args[]) throws Exception {
		Day17SetAndForgetPart1 day17SetAndForgetPart1 = new Day17SetAndForgetPart1();
		Long sumOfAlignmentParams = day17SetAndForgetPart1.run();
    	System.out.println("What is the sum of the alignment parameters for the scaffold intersections?---" + sumOfAlignmentParams);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unlikely-arg-type" })
	@Override
	public Long run() {
		long intCodes[] = {1L,330L,331L,332L,109L,5242L,1101L,1182L,0L,16L,1102L,1521L,1L,24L,102L,1L,0L,570L,1006L,570L,36L,102L,1L,571L,0L,1001L,570L,-1L,570L,1001L,24L,1L,24L,1106L,0L,18L,1008L,571L,0L,571L,1001L,16L,1L,16L,1008L,16L,1521L,570L,1006L,570L,14L,21101L,0L,58L,0L,1105L,1L,786L,1006L,332L,62L,99L,21102L,333L,1L,1L,21101L,73L,0L,0L,1106L,0L,579L,1101L,0L,0L,572L,1102L,0L,1L,573L,3L,574L,101L,1L,573L,573L,1007L,574L,65L,570L,1005L,570L,151L,107L,67L,574L,570L,1005L,570L,151L,1001L,574L,-64L,574L,1002L,574L,-1L,574L,1001L,572L,1L,572L,1007L,572L,11L,570L,1006L,570L,165L,101L,1182L,572L,127L,101L,0L,574L,0L,3L,574L,101L,1L,573L,573L,1008L,574L,10L,570L,1005L,570L,189L,1008L,574L,44L,570L,1006L,570L,158L,1105L,1L,81L,21102L,340L,1L,1L,1106L,0L,177L,21101L,0L,477L,1L,1106L,0L,177L,21102L,1L,514L,1L,21102L,176L,1L,0L,1105L,1L,579L,99L,21102L,184L,1L,0L,1106L,0L,579L,4L,574L,104L,10L,99L,1007L,573L,22L,570L,1006L,570L,165L,102L,1L,572L,1182L,21101L,375L,0L,1L,21101L,0L,211L,0L,1105L,1L,579L,21101L,1182L,11L,1L,21102L,222L,1L,0L,1106L,0L,979L,21102L,1L,388L,1L,21102L,1L,233L,0L,1106L,0L,579L,21101L,1182L,22L,1L,21101L,244L,0L,0L,1106L,0L,979L,21101L,0L,401L,1L,21102L,255L,1L,0L,1105L,1L,579L,21101L,1182L,33L,1L,21101L,0L,266L,0L,1106L,0L,979L,21102L,1L,414L,1L,21102L,277L,1L,0L,1106L,0L,579L,3L,575L,1008L,575L,89L,570L,1008L,575L,121L,575L,1L,575L,570L,575L,3L,574L,1008L,574L,10L,570L,1006L,570L,291L,104L,10L,21101L,1182L,0L,1L,21101L,313L,0L,0L,1105L,1L,622L,1005L,575L,327L,1101L,1L,0L,575L,21102L,327L,1L,0L,1106L,0L,786L,4L,438L,99L,0L,1L,1L,6L,77L,97L,105L,110L,58L,10L,33L,10L,69L,120L,112L,101L,99L,116L,101L,100L,32L,102L,117L,110L,99L,116L,105L,111L,110L,32L,110L,97L,109L,101L,32L,98L,117L,116L,32L,103L,111L,116L,58L,32L,0L,12L,70L,117L,110L,99L,116L,105L,111L,110L,32L,65L,58L,10L,12L,70L,117L,110L,99L,116L,105L,111L,110L,32L,66L,58L,10L,12L,70L,117L,110L,99L,116L,105L,111L,110L,32L,67L,58L,10L,23L,67L,111L,110L,116L,105L,110L,117L,111L,117L,115L,32L,118L,105L,100L,101L,111L,32L,102L,101L,101L,100L,63L,10L,0L,37L,10L,69L,120L,112L,101L,99L,116L,101L,100L,32L,82L,44L,32L,76L,44L,32L,111L,114L,32L,100L,105L,115L,116L,97L,110L,99L,101L,32L,98L,117L,116L,32L,103L,111L,116L,58L,32L,36L,10L,69L,120L,112L,101L,99L,116L,101L,100L,32L,99L,111L,109L,109L,97L,32L,111L,114L,32L,110L,101L,119L,108L,105L,110L,101L,32L,98L,117L,116L,32L,103L,111L,116L,58L,32L,43L,10L,68L,101L,102L,105L,110L,105L,116L,105L,111L,110L,115L,32L,109L,97L,121L,32L,98L,101L,32L,97L,116L,32L,109L,111L,115L,116L,32L,50L,48L,32L,99L,104L,97L,114L,97L,99L,116L,101L,114L,115L,33L,10L,94L,62L,118L,60L,0L,1L,0L,-1L,-1L,0L,1L,0L,0L,0L,0L,0L,0L,1L,60L,24L,0L,109L,4L,2101L,0L,-3L,586L,21002L,0L,1L,-1L,22101L,1L,-3L,-3L,21102L,1L,0L,-2L,2208L,-2L,-1L,570L,1005L,570L,617L,2201L,-3L,-2L,609L,4L,0L,21201L,-2L,1L,-2L,1105L,1L,597L,109L,-4L,2105L,1L,0L,109L,5L,2101L,0L,-4L,629L,21002L,0L,1L,-2L,22101L,1L,-4L,-4L,21102L,1L,0L,-3L,2208L,-3L,-2L,570L,1005L,570L,781L,2201L,-4L,-3L,653L,20101L,0L,0L,-1L,1208L,-1L,-4L,570L,1005L,570L,709L,1208L,-1L,-5L,570L,1005L,570L,734L,1207L,-1L,0L,570L,1005L,570L,759L,1206L,-1L,774L,1001L,578L,562L,684L,1L,0L,576L,576L,1001L,578L,566L,692L,1L,0L,577L,577L,21102L,1L,702L,0L,1106L,0L,786L,21201L,-1L,-1L,-1L,1106L,0L,676L,1001L,578L,1L,578L,1008L,578L,4L,570L,1006L,570L,724L,1001L,578L,-4L,578L,21101L,731L,0L,0L,1106L,0L,786L,1105L,1L,774L,1001L,578L,-1L,578L,1008L,578L,-1L,570L,1006L,570L,749L,1001L,578L,4L,578L,21101L,756L,0L,0L,1105L,1L,786L,1106L,0L,774L,21202L,-1L,-11L,1L,22101L,1182L,1L,1L,21102L,774L,1L,0L,1106L,0L,622L,21201L,-3L,1L,-3L,1106L,0L,640L,109L,-5L,2105L,1L,0L,109L,7L,1005L,575L,802L,20101L,0L,576L,-6L,21002L,577L,1L,-5L,1105L,1L,814L,21101L,0L,0L,-1L,21101L,0L,0L,-5L,21102L,0L,1L,-6L,20208L,-6L,576L,-2L,208L,-5L,577L,570L,22002L,570L,-2L,-2L,21202L,-5L,61L,-3L,22201L,-6L,-3L,-3L,22101L,1521L,-3L,-3L,2101L,0L,-3L,843L,1005L,0L,863L,21202L,-2L,42L,-4L,22101L,46L,-4L,-4L,1206L,-2L,924L,21102L,1L,1L,-1L,1106L,0L,924L,1205L,-2L,873L,21102L,35L,1L,-4L,1106L,0L,924L,2102L,1L,-3L,878L,1008L,0L,1L,570L,1006L,570L,916L,1001L,374L,1L,374L,2101L,0L,-3L,895L,1101L,2L,0L,0L,2101L,0L,-3L,902L,1001L,438L,0L,438L,2202L,-6L,-5L,570L,1L,570L,374L,570L,1L,570L,438L,438L,1001L,578L,558L,922L,20101L,0L,0L,-4L,1006L,575L,959L,204L,-4L,22101L,1L,-6L,-6L,1208L,-6L,61L,570L,1006L,570L,814L,104L,10L,22101L,1L,-5L,-5L,1208L,-5L,61L,570L,1006L,570L,810L,104L,10L,1206L,-1L,974L,99L,1206L,-1L,974L,1101L,0L,1L,575L,21102L,1L,973L,0L,1106L,0L,786L,99L,109L,-7L,2105L,1L,0L,109L,6L,21101L,0L,0L,-4L,21102L,0L,1L,-3L,203L,-2L,22101L,1L,-3L,-3L,21208L,-2L,82L,-1L,1205L,-1L,1030L,21208L,-2L,76L,-1L,1205L,-1L,1037L,21207L,-2L,48L,-1L,1205L,-1L,1124L,22107L,57L,-2L,-1L,1205L,-1L,1124L,21201L,-2L,-48L,-2L,1106L,0L,1041L,21101L,-4L,0L,-2L,1106L,0L,1041L,21101L,-5L,0L,-2L,21201L,-4L,1L,-4L,21207L,-4L,11L,-1L,1206L,-1L,1138L,2201L,-5L,-4L,1059L,2101L,0L,-2L,0L,203L,-2L,22101L,1L,-3L,-3L,21207L,-2L,48L,-1L,1205L,-1L,1107L,22107L,57L,-2L,-1L,1205L,-1L,1107L,21201L,-2L,-48L,-2L,2201L,-5L,-4L,1090L,20102L,10L,0L,-1L,22201L,-2L,-1L,-2L,2201L,-5L,-4L,1103L,1201L,-2L,0L,0L,1105L,1L,1060L,21208L,-2L,10L,-1L,1205L,-1L,1162L,21208L,-2L,44L,-1L,1206L,-1L,1131L,1106L,0L,989L,21101L,439L,0L,1L,1105L,1L,1150L,21102L,1L,477L,1L,1106L,0L,1150L,21102L,1L,514L,1L,21101L,1149L,0L,0L,1106L,0L,579L,99L,21101L,1157L,0L,0L,1105L,1L,579L,204L,-2L,104L,10L,99L,21207L,-3L,22L,-1L,1206L,-1L,1138L,2101L,0L,-5L,1176L,1202L,-4L,1L,0L,109L,-6L,2105L,1L,0L,24L,13L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,3L,13L,44L,1L,3L,1L,7L,1L,3L,1L,44L,1L,3L,1L,7L,1L,3L,1L,44L,1L,3L,1L,7L,1L,3L,1L,44L,1L,3L,1L,7L,7L,42L,1L,3L,1L,11L,1L,1L,1L,40L,7L,11L,7L,36L,1L,1L,1L,17L,1L,3L,1L,30L,9L,17L,1L,3L,1L,30L,1L,5L,1L,19L,1L,3L,1L,30L,1L,5L,1L,19L,1L,3L,1L,30L,1L,5L,1L,19L,1L,3L,1L,30L,1L,5L,1L,19L,7L,28L,1L,5L,1L,23L,1L,1L,1L,28L,1L,5L,1L,23L,13L,18L,1L,5L,1L,25L,1L,9L,1L,18L,1L,5L,1L,25L,1L,9L,1L,18L,1L,5L,1L,25L,1L,9L,1L,16L,9L,25L,1L,9L,1L,16L,1L,1L,1L,31L,1L,9L,1L,10L,9L,31L,13L,8L,1L,5L,1L,43L,1L,10L,1L,5L,1L,43L,1L,10L,1L,5L,1L,43L,1L,10L,1L,5L,1L,43L,1L,10L,1L,5L,1L,43L,1L,2L,9L,5L,1L,35L,9L,2L,1L,13L,1L,35L,1L,10L,1L,13L,1L,35L,1L,10L,1L,13L,1L,35L,1L,10L,1L,13L,13L,23L,1L,10L,1L,25L,1L,23L,1L,10L,13L,13L,1L,23L,1L,22L,1L,13L,1L,23L,1L,22L,1L,13L,1L,23L,1L,22L,1L,13L,1L,23L,1L,22L,1L,13L,1L,23L,1L,22L,1L,13L,1L,23L,1L,22L,1L,13L,1L,17L,7L,22L,1L,31L,1L,28L,7L,25L,1L,34L,1L,25L,1L,34L,1L,25L,1L,34L,1L,25L,1L,34L,1L,17L,9L,34L,1L,17L,1L,42L,1L,17L,1L,42L,1L,17L,1L,42L,7L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,1L,11L,1L,48L,13L,24L};
		long sumOfAlignmentParams = 0L;
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		int haltCode = HALT_CODE_NORMAL;
		Map<Long, List<String>> scaffoldMap = new HashMap<>();
		
		long startingIndex = 0;
		
		while (haltCode != HALT_CODE_EXIT) {
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			haltCode = intCodeComputer.haltCode;
			
			if (haltCode == HALT_CODE_INPUT) {
				intCodeComputer.inputValues.add(0L);
			}
			
			if (haltCode == HALT_CODE_OUTPUT && intCodeComputer.outputList.size() == 1) {				
				long code = intCodeComputer.outputList.get(0);
				
				if (!scaffoldMap.containsKey(startingIndex)) {
					scaffoldMap.put(startingIndex, new ArrayList<String>());
				}
				List<String> currentLine = scaffoldMap.get(startingIndex);
				
				if (code == ASCII_SCAFFOLD) {
					currentLine.add(SCAFFOLD);
				} else if (code == ASCII_OPEN_SPACE) {
					currentLine.add(OPEN_SPACE);
				} else if (code == ASCII_NEW_LINE) {
					startingIndex++;
					System.out.println("Detected New Line---");
				} else if (code == ASCII_VACCUM_ROBOT) {
					System.out.println("Detected vaccum robot---" + code);
					currentLine.add(VACCUM_ROBOT);
				} else {
					System.out.println("Some other output---" + code);
				}
				intCodeComputer.outputList.clear();
			}
		}
		
		// Remove last element
		scaffoldMap.remove(scaffoldMap.size() - 1);
		
		System.out.println("Scaffold Map size---"+ scaffoldMap.size());
		System.out.println("Scaffold Map ---"+ scaffoldMap);
		
        Map<Long, List<String>> scaffoldTreeMap = new TreeMap<Long, List<String>>(scaffoldMap);
        System.out.println("Sorted scaffold Map size---"+ scaffoldTreeMap);
        
        
		// Print map
        printScaffoldMap(scaffoldTreeMap);
        
        
        // Find alignmentParams
        for (int key = 1; key < scaffoldTreeMap.size()-2; key++) {  // First row and last row never have intersection points
        	List<String> line = scaffoldTreeMap.get(new Long(key));
        	for (int index = 1; index < line.size()-2; index++) {
            	String currentElement = line.get(index);
            	if (SCAFFOLD == currentElement) {
            		String leftElement = line.get(index -1);
                	String rightElement = line.get(index + 1);
                	String topElement = scaffoldTreeMap.get(new Long(key - 1)).get(index);
                	String bottomElement = scaffoldTreeMap.get(new Long(key + 1)).get(index);
                	if (leftElement == SCAFFOLD && rightElement == SCAFFOLD && topElement == SCAFFOLD && bottomElement == SCAFFOLD) {
                		System.out.println("InterSection Index-----" + key + "---," +index);
                		sumOfAlignmentParams += key * index;
                	}
            	}
            	
        	}
        }
        
		// Insert intersection points
		for (int key = 1; key < scaffoldTreeMap.size() - 2; key++) { // First row and last row never have intersection
																		// points
			List<String> line = scaffoldTreeMap.get(new Long(key));
			for (int index = 1; index < line.size() - 2; index++) {
				String currentElement = line.get(index);
				if (SCAFFOLD == currentElement || SCAFFOLD_INTERSECTION_POINT == currentElement) {
					String leftElement = line.get(index - 1);
					String rightElement = line.get(index + 1);
					String topElement = scaffoldTreeMap.get(new Long(key - 1)).get(index);
					String bottomElement = scaffoldTreeMap.get(new Long(key + 1)).get(index);
					if ((leftElement == SCAFFOLD || leftElement == SCAFFOLD_INTERSECTION_POINT)
							&& (rightElement == SCAFFOLD || rightElement == SCAFFOLD_INTERSECTION_POINT)
							&& (topElement == SCAFFOLD || topElement == SCAFFOLD_INTERSECTION_POINT)
							&& (bottomElement == SCAFFOLD || bottomElement == SCAFFOLD_INTERSECTION_POINT)) {
						line.set(index, SCAFFOLD_INTERSECTION_POINT);

					}
				}

			}
		}
		
		// Print map
        printScaffoldMap(scaffoldTreeMap);
		
		return sumOfAlignmentParams;
	}
	
	void printScaffoldMap(Map<Long, List<String>> scaffoldTreeMap) {
	    for (Entry<Long, List<String>> entry : scaffoldTreeMap.entrySet()) {
	    	List<String> line = entry.getValue();
	    	for (String item : line) {
	        	System.out.print(item);
	    	}
	    	System.out.println("");
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
			while(!Day17SetAndForgetPart1.HALT_CODES.contains(this.haltCode)) {
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
