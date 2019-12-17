import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*--- Part Two ---
The game didn't run because you didn't put in any quarters. Unfortunately, you did not bring any quarters. Memory address 0 represents the number of quarters that have been inserted; set it to 2 to play for free.

The arcade cabinet has a joystick that can move left and right. The software reads the position of the joystick with input instructions:

If the joystick is in the neutral position, provide 0.
If the joystick is tilted to the left, provide -1.
If the joystick is tilted to the right, provide 1.
The arcade cabinet also has a segment display capable of showing a single number that represents the player's current score. When three output instructions specify X=-1, Y=0, the third output instruction is not a tile; the value instead specifies the new score to show in the segment display. For example, a sequence of output values like -1,0,12345 would show 12345 as the player's current score.

Beat the game by breaking all the blocks. What is your score after the last block is broken?

Your puzzle answer was 13824.
*/
public class Day13CarePackagePart2 implements AOC<Long>{

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

	
	public static final int TILE_EMPTY = 0;
	public static final int TILE_WALL = 1;
	public static final int TILE_BLOCK = 2;
	public static final int TILE_HORIZONTAL_PADDLE = 3;
	public static final int TILE_BALL = 4;
	
	public static final int JOY_STICK_POSITION_NEUTRAL = 0; 
	public static final int JOY_STICK_POSITION_LEFT = -1;
	public static final int JOY_STICK_POSITION_RIGHT = 1;
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	

	public static void main(String args[]) throws Exception {
		Day13CarePackagePart2 day13CarePackagePart2 = new Day13CarePackagePart2();
		long score = day13CarePackagePart2.run();
		System.out.println("Final score---"+ score);
	}
	
	@Override
	public Long run() {
		long score = 0L;
		// For initial paddle position we can use it from part 1 grid
/*		Day13CarePackagePart1 day13CarePackagePart1 = new Day13CarePackagePart1();
		Long[][] gameGrid = day13CarePackagePart1.run();
		Position paddlePosition = getPaddlePosition(gameGrid);*/
		Position paddlePosition = null;
		
		long intCodes[] = {2L,380L,379L,385L,1008L,2799L,419438L,381L,1005L,381L,12L,99L,109L,2800L,1102L,0L,1L,383L,1101L,0L,0L,382L,20102L,1L,382L,1L,21001L,383L,0L,2L,21101L,0L,37L,0L,1106L,0L,578L,4L,382L,4L,383L,204L,1L,1001L,382L,1L,382L,1007L,382L,45L,381L,1005L,381L,22L,1001L,383L,1L,383L,1007L,383L,24L,381L,1005L,381L,18L,1006L,385L,69L,99L,104L,-1L,104L,0L,4L,386L,3L,384L,1007L,384L,0L,381L,1005L,381L,94L,107L,0L,384L,381L,1005L,381L,108L,1105L,1L,161L,107L,1L,392L,381L,1006L,381L,161L,1102L,-1L,1L,384L,1106L,0L,119L,1007L,392L,43L,381L,1006L,381L,161L,1102L,1L,1L,384L,20101L,0L,392L,1L,21101L,22L,0L,2L,21102L,1L,0L,3L,21101L,0L,138L,0L,1106L,0L,549L,1L,392L,384L,392L,21001L,392L,0L,1L,21102L,1L,22L,2L,21102L,3L,1L,3L,21102L,1L,161L,0L,1105L,1L,549L,1101L,0L,0L,384L,20001L,388L,390L,1L,20102L,1L,389L,2L,21102L,1L,180L,0L,1105L,1L,578L,1206L,1L,213L,1208L,1L,2L,381L,1006L,381L,205L,20001L,388L,390L,1L,20102L,1L,389L,2L,21102L,1L,205L,0L,1106L,0L,393L,1002L,390L,-1L,390L,1102L,1L,1L,384L,21002L,388L,1L,1L,20001L,389L,391L,2L,21101L,0L,228L,0L,1106L,0L,578L,1206L,1L,261L,1208L,1L,2L,381L,1006L,381L,253L,21001L,388L,0L,1L,20001L,389L,391L,2L,21101L,0L,253L,0L,1105L,1L,393L,1002L,391L,-1L,391L,1102L,1L,1L,384L,1005L,384L,161L,20001L,388L,390L,1L,20001L,389L,391L,2L,21102L,1L,279L,0L,1106L,0L,578L,1206L,1L,316L,1208L,1L,2L,381L,1006L,381L,304L,20001L,388L,390L,1L,20001L,389L,391L,2L,21101L,0L,304L,0L,1105L,1L,393L,1002L,390L,-1L,390L,1002L,391L,-1L,391L,1101L,0L,1L,384L,1005L,384L,161L,20101L,0L,388L,1L,20101L,0L,389L,2L,21101L,0L,0L,3L,21102L,338L,1L,0L,1106L,0L,549L,1L,388L,390L,388L,1L,389L,391L,389L,21002L,388L,1L,1L,20101L,0L,389L,2L,21102L,4L,1L,3L,21101L,365L,0L,0L,1106L,0L,549L,1007L,389L,23L,381L,1005L,381L,75L,104L,-1L,104L,0L,104L,0L,99L,0L,1L,0L,0L,0L,0L,0L,0L,296L,20L,19L,1L,1L,22L,109L,3L,22101L,0L,-2L,1L,21202L,-1L,1L,2L,21102L,1L,0L,3L,21102L,414L,1L,0L,1105L,1L,549L,21201L,-2L,0L,1L,21202L,-1L,1L,2L,21102L,429L,1L,0L,1106L,0L,601L,2101L,0L,1L,435L,1L,386L,0L,386L,104L,-1L,104L,0L,4L,386L,1001L,387L,-1L,387L,1005L,387L,451L,99L,109L,-3L,2106L,0L,0L,109L,8L,22202L,-7L,-6L,-3L,22201L,-3L,-5L,-3L,21202L,-4L,64L,-2L,2207L,-3L,-2L,381L,1005L,381L,492L,21202L,-2L,-1L,-1L,22201L,-3L,-1L,-3L,2207L,-3L,-2L,381L,1006L,381L,481L,21202L,-4L,8L,-2L,2207L,-3L,-2L,381L,1005L,381L,518L,21202L,-2L,-1L,-1L,22201L,-3L,-1L,-3L,2207L,-3L,-2L,381L,1006L,381L,507L,2207L,-3L,-4L,381L,1005L,381L,540L,21202L,-4L,-1L,-1L,22201L,-3L,-1L,-3L,2207L,-3L,-4L,381L,1006L,381L,529L,21201L,-3L,0L,-7L,109L,-8L,2106L,0L,0L,109L,4L,1202L,-2L,45L,566L,201L,-3L,566L,566L,101L,639L,566L,566L,1202L,-1L,1L,0L,204L,-3L,204L,-2L,204L,-1L,109L,-4L,2106L,0L,0L,109L,3L,1202L,-1L,45L,594L,201L,-2L,594L,594L,101L,639L,594L,594L,20102L,1L,0L,-2L,109L,-3L,2106L,0L,0L,109L,3L,22102L,24L,-2L,1L,22201L,1L,-1L,1L,21102L,1L,547L,2L,21102L,850L,1L,3L,21102L,1L,1080L,4L,21102L,630L,1L,0L,1106L,0L,456L,21201L,1L,1719L,-2L,109L,-3L,2105L,1L,0L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,2L,0L,2L,0L,0L,2L,2L,2L,2L,2L,2L,0L,2L,0L,0L,0L,0L,0L,2L,0L,0L,0L,2L,2L,2L,2L,2L,2L,2L,2L,2L,2L,0L,0L,0L,0L,2L,0L,0L,2L,0L,1L,1L,0L,0L,2L,2L,0L,0L,2L,2L,0L,0L,0L,2L,2L,0L,2L,0L,2L,0L,0L,0L,0L,0L,2L,0L,0L,2L,2L,2L,0L,2L,0L,2L,0L,2L,0L,2L,0L,0L,0L,0L,0L,2L,0L,1L,1L,0L,0L,2L,0L,2L,0L,0L,0L,0L,2L,2L,0L,0L,2L,2L,2L,0L,2L,2L,0L,0L,0L,2L,2L,2L,2L,2L,2L,0L,0L,2L,0L,2L,0L,2L,2L,2L,2L,2L,0L,0L,0L,0L,1L,1L,0L,2L,2L,0L,0L,0L,2L,0L,2L,0L,0L,2L,2L,2L,2L,0L,2L,0L,2L,2L,0L,0L,0L,0L,2L,0L,0L,0L,2L,2L,0L,0L,0L,2L,0L,2L,0L,0L,0L,0L,2L,0L,0L,1L,1L,0L,2L,2L,2L,0L,0L,2L,2L,0L,0L,2L,0L,0L,0L,2L,2L,0L,0L,0L,0L,0L,0L,0L,2L,0L,0L,0L,2L,2L,0L,2L,0L,2L,0L,0L,0L,2L,2L,2L,0L,2L,2L,0L,1L,1L,0L,0L,0L,2L,2L,2L,2L,0L,0L,0L,2L,0L,0L,2L,0L,0L,0L,2L,2L,2L,2L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,2L,2L,2L,2L,0L,2L,0L,2L,2L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,2L,2L,2L,2L,0L,0L,0L,0L,2L,2L,2L,0L,0L,2L,0L,2L,0L,0L,2L,0L,2L,0L,2L,0L,2L,0L,0L,0L,0L,0L,2L,0L,0L,2L,2L,2L,0L,1L,1L,0L,2L,2L,2L,0L,2L,0L,0L,0L,2L,0L,2L,2L,2L,2L,2L,0L,2L,2L,0L,2L,0L,0L,2L,0L,2L,0L,0L,0L,2L,2L,0L,2L,0L,0L,2L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,2L,0L,2L,2L,2L,2L,0L,0L,2L,0L,0L,2L,0L,0L,0L,2L,2L,0L,0L,2L,0L,0L,0L,0L,0L,0L,0L,0L,2L,0L,2L,0L,0L,0L,2L,0L,0L,0L,0L,2L,0L,1L,1L,0L,0L,0L,0L,2L,0L,2L,2L,0L,0L,2L,2L,0L,2L,0L,0L,0L,0L,2L,0L,2L,2L,0L,2L,0L,2L,2L,2L,0L,0L,2L,0L,0L,2L,0L,2L,2L,2L,0L,0L,0L,0L,0L,1L,1L,0L,2L,0L,2L,0L,2L,2L,2L,0L,2L,2L,2L,2L,0L,2L,0L,0L,2L,0L,2L,0L,0L,2L,2L,2L,0L,0L,0L,0L,2L,2L,0L,0L,0L,0L,0L,2L,0L,0L,0L,0L,0L,0L,1L,1L,0L,2L,2L,2L,2L,2L,2L,2L,0L,2L,2L,0L,2L,0L,0L,2L,0L,0L,2L,0L,0L,0L,0L,0L,0L,2L,0L,2L,2L,2L,2L,0L,0L,0L,0L,0L,2L,2L,2L,0L,2L,2L,0L,1L,1L,0L,0L,2L,0L,0L,2L,0L,0L,2L,0L,2L,0L,0L,0L,0L,2L,0L,2L,2L,2L,0L,0L,0L,2L,0L,0L,2L,0L,2L,0L,2L,2L,0L,0L,2L,2L,0L,2L,0L,0L,2L,2L,0L,1L,1L,0L,0L,0L,2L,2L,0L,2L,2L,2L,0L,2L,0L,2L,0L,0L,0L,0L,2L,2L,2L,2L,2L,2L,0L,2L,0L,2L,0L,0L,2L,0L,2L,2L,0L,0L,0L,2L,0L,0L,0L,2L,2L,0L,1L,1L,0L,2L,0L,0L,2L,0L,0L,2L,2L,0L,2L,2L,2L,2L,0L,0L,2L,0L,0L,2L,0L,0L,2L,2L,0L,0L,2L,0L,0L,0L,0L,2L,0L,0L,0L,2L,2L,2L,2L,2L,0L,2L,0L,1L,1L,0L,2L,0L,0L,0L,0L,0L,0L,2L,2L,0L,0L,2L,2L,0L,0L,2L,0L,0L,2L,2L,0L,0L,0L,2L,0L,2L,2L,0L,0L,2L,0L,2L,0L,2L,2L,2L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,4L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,3L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,39L,45L,82L,25L,27L,92L,1L,12L,84L,89L,45L,7L,17L,8L,90L,61L,27L,2L,9L,32L,79L,47L,45L,7L,90L,22L,83L,77L,1L,47L,86L,47L,94L,50L,27L,63L,37L,1L,77L,68L,68L,15L,33L,11L,96L,37L,66L,65L,17L,75L,51L,90L,52L,14L,54L,36L,46L,2L,56L,3L,74L,76L,43L,3L,43L,38L,42L,27L,85L,72L,57L,24L,90L,94L,2L,26L,9L,23L,60L,46L,51L,10L,78L,60L,46L,15L,2L,86L,29L,97L,97L,29L,95L,10L,7L,74L,16L,85L,33L,31L,91L,52L,49L,98L,61L,9L,56L,70L,25L,36L,35L,38L,73L,19L,36L,8L,53L,5L,46L,18L,10L,33L,19L,44L,31L,68L,98L,74L,55L,37L,97L,49L,80L,23L,61L,49L,71L,95L,29L,5L,70L,16L,71L,11L,71L,55L,75L,41L,35L,9L,35L,37L,90L,81L,68L,8L,27L,75L,87L,6L,46L,89L,77L,77L,82L,11L,89L,81L,43L,46L,90L,24L,2L,76L,20L,87L,54L,91L,18L,13L,21L,81L,90L,16L,66L,95L,26L,95L,26L,23L,17L,83L,28L,70L,7L,54L,75L,56L,49L,31L,79L,13L,98L,38L,88L,47L,59L,13L,79L,95L,59L,64L,67L,68L,20L,44L,55L,23L,23L,26L,91L,86L,59L,56L,49L,57L,13L,31L,36L,30L,46L,45L,65L,12L,38L,68L,68L,38L,71L,15L,44L,87L,34L,75L,12L,96L,20L,48L,8L,68L,58L,63L,37L,15L,53L,96L,47L,55L,70L,46L,61L,66L,11L,54L,60L,70L,73L,65L,57L,96L,13L,32L,55L,44L,76L,57L,50L,19L,56L,57L,41L,37L,51L,58L,70L,48L,85L,91L,89L,28L,33L,22L,47L,22L,77L,17L,78L,11L,77L,83L,95L,19L,26L,73L,72L,4L,48L,62L,58L,32L,33L,94L,34L,71L,55L,92L,22L,60L,12L,92L,9L,49L,8L,22L,62L,37L,40L,73L,37L,70L,26L,10L,52L,38L,35L,40L,1L,5L,33L,74L,59L,35L,10L,53L,75L,43L,98L,86L,11L,95L,73L,28L,31L,20L,46L,84L,88L,1L,96L,77L,14L,45L,62L,98L,38L,44L,83L,7L,73L,39L,17L,80L,12L,97L,87L,83L,95L,24L,13L,13L,67L,12L,42L,86L,75L,43L,50L,63L,72L,28L,48L,44L,14L,31L,45L,19L,98L,55L,30L,27L,81L,23L,17L,78L,94L,1L,75L,4L,13L,7L,25L,86L,3L,36L,80L,71L,6L,8L,52L,26L,10L,4L,70L,95L,12L,52L,71L,9L,98L,23L,14L,56L,11L,45L,82L,50L,52L,48L,2L,34L,4L,32L,50L,64L,94L,13L,70L,24L,98L,48L,48L,80L,43L,27L,95L,89L,14L,36L,89L,9L,83L,34L,96L,13L,27L,86L,82L,45L,78L,82L,36L,58L,86L,88L,47L,33L,92L,27L,16L,87L,62L,14L,30L,35L,44L,56L,20L,39L,48L,83L,30L,43L,33L,68L,70L,58L,83L,45L,60L,16L,17L,57L,9L,56L,21L,36L,55L,36L,93L,95L,84L,50L,54L,29L,42L,72L,67L,59L,4L,61L,79L,3L,67L,60L,9L,41L,12L,85L,4L,69L,31L,70L,78L,7L,86L,33L,54L,39L,38L,47L,88L,4L,31L,97L,35L,41L,22L,88L,67L,76L,80L,52L,33L,11L,42L,67L,26L,84L,96L,31L,88L,58L,38L,35L,94L,78L,47L,83L,72L,85L,18L,33L,57L,46L,9L,14L,81L,71L,79L,12L,1L,47L,16L,8L,25L,78L,21L,94L,61L,33L,73L,57L,21L,28L,32L,5L,11L,40L,92L,56L,11L,18L,83L,84L,12L,53L,66L,65L,24L,61L,90L,77L,76L,25L,48L,83L,65L,82L,45L,74L,96L,23L,2L,66L,91L,66L,95L,48L,8L,21L,60L,21L,60L,59L,80L,44L,40L,71L,69L,25L,50L,55L,22L,36L,82L,86L,8L,37L,2L,67L,54L,66L,64L,17L,5L,25L,23L,9L,52L,41L,38L,28L,23L,97L,44L,26L,39L,68L,19L,82L,20L,43L,85L,40L,63L,82L,56L,18L,60L,15L,64L,92L,72L,48L,46L,87L,7L,94L,98L,21L,63L,85L,24L,21L,16L,66L,60L,17L,49L,83L,49L,69L,36L,34L,39L,66L,43L,85L,3L,33L,47L,26L,29L,27L,7L,5L,76L,12L,5L,21L,53L,25L,52L,83L,69L,97L,38L,53L,50L,15L,80L,27L,92L,13L,15L,66L,2L,20L,71L,90L,52L,51L,14L,86L,85L,69L,51L,15L,81L,69L,31L,67L,91L,95L,32L,62L,46L,42L,98L,86L,74L,1L,24L,47L,65L,90L,16L,65L,94L,27L,73L,45L,80L,22L,18L,95L,75L,35L,31L,41L,84L,57L,35L,35L,86L,21L,47L,46L,73L,48L,48L,32L,65L,98L,44L,81L,79L,10L,43L,76L,64L,77L,74L,27L,91L,25L,68L,40L,77L,68L,22L,64L,63L,15L,57L,50L,33L,42L,80L,15L,62L,24L,16L,34L,92L,37L,95L,55L,80L,34L,29L,75L,83L,27L,47L,57L,55L,98L,45L,42L,32L,28L,15L,45L,79L,73L,98L,62L,58L,8L,3L,96L,13L,72L,64L,49L,77L,32L,86L,92L,5L,2L,26L,7L,6L,73L,23L,73L,79L,46L,4L,46L,9L,76L,1L,86L,60L,90L,40L,32L,40L,83L,29L,78L,1L,72L,66L,12L,13L,72L,31L,11L,31L,84L,41L,5L,63L,6L,75L,75L,57L,30L,8L,90L,69L,10L,79L,98L,21L,90L,65L,23L,52L,11L,27L,48L,62L,13L,80L,85L,53L,77L,45L,69L,90L,88L,27L,87L,95L,58L,69L,23L,57L,73L,54L,35L,12L,39L,96L,86L,68L,64L,30L,82L,21L,60L,20L,10L,54L,31L,66L,96L,17L,16L,85L,4L,35L,21L,98L,31L,55L,11L,12L,2L,5L,47L,19L,1L,76L,27L,60L,29L,46L,36L,90L,21L,62L,47L,64L,9L,65L,63L,70L,11L,64L,38L,57L,37L,96L,59L,40L,23L,77L,87L,89L,98L,37L,24L,31L,52L,38L,76L,54L,29L,85L,4L,31L,76L,9L,74L,50L,48L,4L,32L,76L,24L,19L,44L,51L,63L,34L,53L,80L,42L,27L,46L,33L,58L,27L,64L,84L,60L,90L,98L,46L,72L,25L,77L,59L,59L,6L,33L,29L,50L,7L,1L,71L,48L,31L,3L,47L,34L,54L,20L,55L,22L,20L,14L,70L,77L,47L,49L,66L,40L,25L,87L,36L,51L,3L,59L,80L,66L,89L,98L,61L,26L,22L,23L,22L,96L,419438L};
		
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		
		int haltCode = HALT_CODE_NORMAL;
		long currentJoystickPosition = JOY_STICK_POSITION_NEUTRAL;
		boolean isFirstTime = true;
		while (haltCode != HALT_CODE_EXIT) {
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			haltCode = intCodeComputer.haltCode;
			
			if (haltCode == HALT_CODE_INPUT) {
				intCodeComputer.inputValues.add(currentJoystickPosition);
			}
			
			if (haltCode == HALT_CODE_OUTPUT && intCodeComputer.outputList.size() == 3) {				
				long distanceFromLeft = intCodeComputer.outputList.get(0);
				long distanceFromTop = intCodeComputer.outputList.get(1);
				long tileId = intCodeComputer.outputList.get(2);
				if (isFirstTime == true) {
					if (tileId == TILE_HORIZONTAL_PADDLE) {
						paddlePosition = new Position(distanceFromLeft, distanceFromTop);
					}
				}
				if (distanceFromLeft == -1 && distanceFromTop == 0) {
					score = tileId;
					isFirstTime = false;
				} else if (isFirstTime == false &&  tileId == TILE_BALL) {
					if (distanceFromLeft < paddlePosition.getX()) {
						currentJoystickPosition = JOY_STICK_POSITION_LEFT;
					} else if (distanceFromLeft > paddlePosition.getX()) {
						currentJoystickPosition = JOY_STICK_POSITION_RIGHT;
					}
					paddlePosition.setX(distanceFromLeft);
				}
				intCodeComputer.outputList.clear();
			}
		}
				
		return score;

	}
	
	Position getPaddlePosition(Long[][] gameGrid) {
		Position paddlePosition = null;
		for (int i = 0; i<gameGrid.length; i++) {
			Long[] gridItems = gameGrid[i];
			for (int j = 0; j<gridItems.length; j++) {
				long item = gridItems[j];
				if (item == TILE_HORIZONTAL_PADDLE) {
					paddlePosition = new Position(i, j);
					return paddlePosition;
				}
			}
		}
		return paddlePosition;
	}

	
	static class Position {
		long x;
		long y;
		public Position(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}
		public long getX() {
			return x;
		}
		public long getY() {
			return y;
		}
		public void setX(long x) {
			this.x = x;
		}
		public void setY(long y) {
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
			while(!Day13CarePackagePart2.HALT_CODES.contains(this.haltCode)) {
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
				if (outputList.size() == 3) {
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
