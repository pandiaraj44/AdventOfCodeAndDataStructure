import java.text.MessageFormat;
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


/*--- Day 25: Cryostasis ---
As you approach Santa's ship, your sensors report two important details:

First, that you might be too late: the internal temperature is -40 degrees.

Second, that one faint life signature is somewhere on the ship.

The airlock door is locked with a code; your best option is to send in a small droid to investigate the situation. You attach your ship to Santa's, break a small hole in the hull, and let the droid run in before you seal it up again. Before your ship starts freezing, you detach your ship and set it to automatically stay within range of Santa's ship.

This droid can follow basic instructions and report on its surroundings; you can communicate with it through an Intcode program (your puzzle input) running on an ASCII-capable computer.

As the droid moves through its environment, it will describe what it encounters. When it says Command?, you can give it a single instruction terminated with a newline (ASCII code 10). Possible instructions are:

Movement via north, south, east, or west.
To take an item the droid sees in the environment, use the command take <name of item>. For example, if the droid reports seeing a red ball, you can pick it up with take red ball.
To drop an item the droid is carrying, use the command drop <name of item>. For example, if the droid is carrying a green ball, you can drop it with drop green ball.
To get a list of all of the items the droid is currently carrying, use the command inv (for "inventory").
Extra spaces or other characters aren't allowed - instructions must be provided precisely.

Santa's ship is a Reindeer-class starship; these ships use pressure-sensitive floors to determine the identity of droids and crew members. The standard configuration for these starships is for all droids to weigh exactly the same amount to make them easier to detect. If you need to get past such a sensor, you might be able to reach the correct weight by carrying items from the environment.

Look around the ship and see if you can find the password for the main airlock.

Your puzzle answer was 269520896.


// console output
A loud, robotic voice says "Analysis complete! You may proceed." and you enter the cockpit.
Santa notices your small droid, looks puzzled for a moment, realizes what has happened, and radios your ship directly.
"Oh, hello! You should be able to get in by typing 269520896 on the keypad at the main airlock."

*/
public class Day25CryostasisPart1 implements AOC<Void>{

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

	public static final String COMMAND_NEW_LINE = "\n";
	public static final String COMMAND_MOVE_NORTH = "north" + COMMAND_NEW_LINE;
	public static final String COMMAND_MOVE_SOUTH = "south" + COMMAND_NEW_LINE;
	public static final String COMMAND_MOVE_EAST = "east" + COMMAND_NEW_LINE;
	public static final String COMMAND_MOVE_WEST = "west" + COMMAND_NEW_LINE;
	public static final String COMMAND_INV = "inv" + COMMAND_NEW_LINE;
	public static final String COMMAND_TAKE_PREFIX = "take {0}" + COMMAND_NEW_LINE;
	public static final String COMMAND_DROP_PREFIX = "drop {0}" + COMMAND_NEW_LINE;
	
	
	public static final String CANDY_CANE = "candy cane";
	public static final String BOULDER = "boulder";
	public static final String FOOD_RATION = "food ration";
	public static final String ASTERISK = "asterisk";
	public static final String MUTEX = "mutex";
	public static final String MUG = "mug";
	public static final String PRIME_NUMBER = "prime number";
	public static final String LOOM = "loom";
	
	public Map<Character, String>  itemsMap;
	public List<String>  pickuableItemSubSet;
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	

	public static void main(String args[]) throws Exception {
		Day25CryostasisPart1 day25CryostasisPart1 = new Day25CryostasisPart1();
		day25CryostasisPart1.run();
		// Output will be printed in console
	}
	
	@SuppressWarnings({ })
	@Override
	public Void run() {
		long intCodes[] = {109L,4786L,21102L,1L,3124L,1L,21101L,0L,13L,0L,1106L,0L,1424L,21102L,1L,166L,1L,21101L,24L,0L,0L,1106L,0L,1234L,21102L,1L,31L,0L,1106L,0L,1984L,1106L,0L,13L,6L,4L,3L,2L,52L,51L,21L,4L,28L,56L,55L,3L,19L,-9L,-10L,47L,89L,88L,90L,90L,6L,77L,73L,85L,71L,1L,76L,68L,63L,65L,22L,-27L,70L,76L,81L,87L,5L,105L,105L,107L,108L,95L,4L,97L,92L,109L,109L,5L,110L,105L,110L,108L,95L,4L,115L,96L,109L,109L,13L,-3L,59L,101L,85L,92L,97L,13L,84L,80L,92L,78L,34L,-15L,26L,-16L,46L,88L,72L,79L,84L,0L,72L,76L,-3L,85L,74L,79L,75L,-8L,64L,68L,75L,57L,65L,70L,64L,66L,72L,8L,-41L,32L,-22L,56L,77L,82L,-4L,60L,76L,62L,70L,-2L,74L,-11L,55L,52L,68L,67L,73L,56L,60L,52L,-20L,44L,56L,66L,-24L,48L,58L,42L,49L,54L,-16L,-53L,10L,0L,56L,99L,96L,95L,82L,94L,83L,45L,-9L,23L,-13L,61L,85L,88L,74L,71L,82L,73L,79L,73L,89L,67L,65L,-4L,62L,73L,70L,69L,56L,68L,57L,2L,-35L,24L,-14L,64L,85L,90L,4L,70L,67L,79L,7L,83L,-2L,68L,75L,-5L,78L,65L,57L,75L,-10L,76L,53L,76L,0L,-37L,31L,-21L,57L,78L,83L,-3L,64L,74L,72L,0L,76L,-9L,73L,58L,57L,-13L,70L,57L,49L,67L,-18L,54L,64L,48L,55L,-23L,48L,44L,56L,42L,-14L,-51L,14L,-4L,74L,95L,100L,14L,97L,77L,86L,79L,9L,92L,79L,75L,5L,27L,-17L,61L,82L,87L,1L,68L,78L,76L,4L,80L,-5L,66L,58L,78L,60L,-10L,73L,60L,52L,70L,-15L,57L,67L,51L,58L,-6L,-43L,14L,-4L,74L,95L,100L,14L,81L,94L,90L,90L,9L,92L,79L,75L,5L,60L,-50L,23L,42L,38L,-32L,38L,39L,30L,42L,47L,-38L,30L,36L,28L,25L,41L,38L,34L,31L,18L,23L,29L,19L,33L,-52L,20L,29L,-55L,27L,27L,27L,8L,15L,-61L,22L,16L,-64L,24L,13L,18L,-54L,-69L,-70L,-14L,7L,12L,-74L,-8L,-11L,1L,-71L,5L,-80L,-4L,-3L,3L,-15L,-84L,-85L,-109L,29L,-19L,59L,80L,85L,-1L,82L,62L,71L,64L,-6L,77L,64L,60L,-10L,62L,66L,57L,59L,63L,57L,67L,51L,-19L,56L,58L,57L,57L,-10L,-47L,44L,-34L,39L,58L,54L,-16L,60L,61L,57L,64L,48L,56L,-23L,52L,40L,60L,38L,-28L,44L,53L,-31L,55L,32L,55L,-35L,48L,42L,41L,-39L,32L,38L,42L,-42L,-44L,12L,33L,38L,-48L,28L,19L,25L,32L,-52L,-76L,-77L,59L,-49L,13L,55L,-30L,42L,51L,-33L,49L,50L,32L,31L,31L,39L,36L,48L,-42L,24L,35L,32L,34L,29L,21L,35L,19L,25L,37L,-53L,14L,10L,26L,18L,-57L,-59L,-3L,18L,23L,-63L,1L,17L,3L,-67L,1L,-4L,14L,-2L,6L,-73L,-8L,14L,-76L,-12L,-78L,-40L,2L,4L,-13L,-82L,-106L,-107L,35L,-25L,53L,74L,79L,0L,74L,60L,-10L,65L,53L,72L,64L,52L,56L,52L,50L,-19L,53L,57L,62L,56L,-24L,58L,54L,38L,39L,40L,-29L,-31L,2L,56L,35L,-34L,-58L,-59L,138L,-128L,-74L,-108L,-33L,-31L,-26L,-44L,-101L,-114L,-33L,-37L,-51L,-39L,-35L,-47L,-54L,-122L,-37L,-45L,-52L,-59L,-58L,-128L,-46L,-65L,-42L,-49L,-133L,-132L,-102L,-60L,-68L,-56L,-55L,-139L,-141L,-106L,-61L,-65L,-72L,-78L,-64L,-148L,-70L,-72L,-151L,-68L,-81L,-81L,-72L,-156L,-74L,-86L,-86L,-80L,-161L,-97L,-81L,-95L,-165L,-94L,-98L,-103L,-83L,-97L,-102L,-90L,-173L,-90L,-103L,-111L,-99L,-178L,-95L,-108L,-112L,-182L,-115L,-115L,-101L,-117L,-120L,-104L,-120L,-122L,-191L,-106L,-128L,-118L,-110L,-127L,-196L,-196L,-199L,-135L,-123L,-134L,-203L,-115L,-126L,-121L,-207L,-143L,-127L,-141L,-211L,-143L,-139L,-145L,-148L,-132L,-148L,-150L,-219L,-154L,-156L,-155L,-148L,-224L,-141L,-147L,-227L,-144L,-157L,-161L,-231L,-165L,-161L,-165L,-168L,-161L,-157L,-159L,-166L,-162L,-157L,-228L,-265L,138L,-128L,-74L,-108L,-33L,-31L,-26L,-44L,-101L,-114L,-33L,-37L,-51L,-39L,-35L,-47L,-54L,-122L,-37L,-45L,-52L,-59L,-58L,-128L,-46L,-65L,-42L,-49L,-133L,-132L,-102L,-60L,-68L,-56L,-55L,-139L,-141L,-106L,-61L,-65L,-72L,-78L,-64L,-148L,-70L,-72L,-151L,-68L,-81L,-81L,-72L,-156L,-74L,-86L,-86L,-80L,-161L,-97L,-81L,-95L,-165L,-90L,-94L,-97L,-97L,-86L,-102L,-90L,-173L,-90L,-103L,-111L,-99L,-178L,-95L,-108L,-112L,-182L,-115L,-115L,-101L,-117L,-120L,-104L,-120L,-122L,-191L,-106L,-128L,-118L,-110L,-127L,-196L,-196L,-199L,-135L,-123L,-134L,-203L,-115L,-126L,-121L,-207L,-143L,-127L,-141L,-211L,-143L,-139L,-145L,-148L,-132L,-148L,-150L,-219L,-154L,-156L,-155L,-148L,-224L,-141L,-147L,-227L,-144L,-157L,-161L,-231L,-165L,-161L,-165L,-168L,-161L,-157L,-159L,-166L,-162L,-157L,-228L,-265L,263L,-253L,-199L,-233L,-158L,-156L,-151L,-169L,-226L,-239L,-158L,-162L,-176L,-164L,-160L,-172L,-179L,-247L,-162L,-170L,-177L,-184L,-183L,-253L,-171L,-190L,-167L,-174L,-258L,-257L,-227L,-183L,-197L,-187L,-175L,-182L,-193L,-184L,-268L,-202L,-191L,-194L,-192L,-197L,-205L,-191L,-207L,-276L,-278L,-222L,-201L,-196L,-282L,-206L,-219L,-196L,-286L,-207L,-206L,-210L,-223L,-222L,-223L,-225L,-280L,-293L,-296L,-232L,-220L,-231L,-300L,-212L,-223L,-218L,-304L,-236L,-228L,-223L,-239L,-227L,-310L,-227L,-240L,-244L,-314L,-248L,-237L,-250L,-243L,-239L,-247L,-237L,-308L,-345L,-273L,-260L,-248L,-243L,-263L,-329L,-252L,-252L,-248L,-260L,-267L,-266L,-253L,-337L,-249L,-260L,-255L,-259L,-342L,-260L,-267L,-280L,-270L,-271L,-348L,-281L,-268L,-272L,-279L,-285L,-342L,-355L,-280L,-278L,-279L,-284L,-277L,-361L,-282L,-278L,-274L,-275L,-290L,-298L,-300L,-369L,-300L,-292L,-290L,-373L,-309L,-375L,-299L,-298L,-301L,-310L,-302L,-297L,-370L,-383L,-302L,-316L,-321L,-311L,-315L,-299L,-321L,-308L,-392L,-306L,-322L,-330L,-312L,-397L,-326L,-334L,-317L,-401L,-330L,-338L,-324L,-325L,-337L,-329L,-339L,-341L,-398L,-411L,-347L,-335L,-346L,-415L,-334L,-352L,-350L,-346L,-341L,-338L,-422L,-334L,-345L,-340L,-344L,-427L,-345L,-357L,-357L,-351L,-432L,-365L,-361L,-353L,-367L,-370L,-354L,-363L,-351L,-427L,-464L,-441L,-397L,-373L,-434L,-447L,-376L,-380L,-374L,-375L,-373L,-452L,-454L,-398L,-377L,-372L,-458L,-376L,-388L,-382L,-377L,-387L,-396L,-465L,-400L,-398L,-468L,-404L,-404L,-395L,-403L,-473L,-390L,-396L,-476L,-406L,-409L,-395L,-480L,-408L,-404L,-483L,-418L,-396L,-486L,-403L,-399L,-409L,-417L,-413L,-421L,-493L,37L,-5L,73L,71L,-8L,75L,62L,58L,-12L,62L,55L,74L,64L,48L,50L,-19L,45L,63L,-22L,61L,48L,44L,-26L,50L,37L,44L,48L,-31L,33L,40L,48L,41L,43L,30L,37L,-25L,-38L,-63L,0L,0L,109L,7L,21102L,0L,1L,-2L,22208L,-2L,-5L,-1L,1205L,-1L,1169L,22202L,-2L,-4L,1L,22201L,1L,-6L,1L,21201L,-2L,0L,2L,21102L,1162L,1L,0L,2105L,1L,-3L,21201L,-2L,1L,-2L,1106L,0L,1136L,109L,-7L,2105L,1L,0L,109L,6L,1202L,-5L,1L,1182L,20102L,1L,0L,-2L,21102L,0L,1L,-3L,21201L,-5L,1L,-5L,22208L,-3L,-2L,-1L,1205L,-1L,1229L,2201L,-5L,-3L,1205L,20102L,1L,0L,1L,21201L,-3L,0L,2L,21202L,-2L,1L,3L,21102L,1222L,1L,0L,2105L,1L,-4L,21201L,-3L,1L,-3L,1106L,0L,1192L,109L,-6L,2106L,0L,0L,109L,2L,22102L,1L,-1L,1L,21102L,1256L,1L,2L,21101L,1251L,0L,0L,1105L,1L,1174L,109L,-2L,2106L,0L,0L,109L,5L,22201L,-4L,-3L,-1L,22201L,-2L,-1L,-1L,204L,-1L,109L,-5L,2105L,1L,0L,109L,3L,2102L,1L,-2L,1280L,1006L,0L,1303L,104L,45L,104L,32L,1201L,-1L,66L,1292L,20102L,1L,0L,1L,21102L,1301L,1L,0L,1106L,0L,1234L,104L,10L,109L,-3L,2105L,1L,0L,0L,0L,109L,2L,1202L,-1L,1L,1309L,1101L,0L,0L,1308L,21102L,4601L,1L,1L,21102L,1L,13L,2L,21101L,4L,0L,3L,21101L,0L,1353L,4L,21101L,0L,1343L,0L,1105L,1L,1130L,20102L,1L,1308L,-1L,109L,-2L,2105L,1L,0L,60L,109L,3L,2101L,0L,-2L,1360L,20008L,0L,1309L,-1L,1206L,-1L,1419L,1005L,1308L,1398L,1101L,1L,0L,1308L,21008L,1309L,-1L,-1L,1206L,-1L,1387L,21101L,0L,106L,1L,1105L,1L,1391L,21102L,1L,92L,1L,21102L,1L,1398L,0L,1106L,0L,1234L,104L,45L,104L,32L,1201L,-2L,1L,1407L,21001L,0L,0L,1L,21101L,0L,1417L,0L,1106L,0L,1234L,104L,10L,109L,-3L,2106L,0L,0L,109L,3L,2102L,1L,-2L,1128L,21102L,1L,34L,1L,21102L,1L,1441L,0L,1106L,0L,1234L,1001L,1128L,0L,1447L,20101L,0L,0L,1L,21102L,1456L,1L,0L,1105L,1L,1234L,21102L,1L,41L,1L,21102L,1467L,1L,0L,1106L,0L,1234L,1001L,1128L,1L,1473L,20102L,1L,0L,1L,21101L,1482L,0L,0L,1105L,1L,1234L,21102L,1L,46L,1L,21102L,1L,1493L,0L,1106L,0L,1234L,21001L,1128L,3L,1L,21102L,4L,1L,2L,21102L,1L,1L,3L,21102L,1273L,1L,4L,21102L,1516L,1L,0L,1106L,0L,1130L,20102L,1L,1128L,1L,21101L,0L,1527L,0L,1106L,0L,1310L,1001L,1128L,2L,1533L,20102L,1L,0L,-1L,1206L,-1L,1545L,21102L,1545L,1L,0L,2105L,1L,-1L,109L,-3L,2106L,0L,0L,109L,0L,99L,109L,2L,1101L,0L,0L,1550L,21101L,4601L,0L,1L,21101L,13L,0L,2L,21101L,0L,4L,3L,21101L,0L,1664L,4L,21102L,1582L,1L,0L,1106L,0L,1130L,2L,2486L,1352L,1551L,1102L,1L,0L,1552L,20101L,0L,1550L,1L,21102L,33L,1L,2L,21102L,1L,1702L,3L,21101L,1609L,0L,0L,1105L,1L,2722L,21007L,1552L,0L,-1L,1205L,-1L,1630L,20107L,0L,1552L,-1L,1205L,-1L,1637L,21102L,1630L,1L,0L,1106L,0L,1752L,21101L,0L,548L,1L,1106L,0L,1641L,21102L,687L,1L,1L,21101L,1648L,0L,0L,1106L,0L,1234L,21102L,4457L,1L,1L,21102L,1L,1659L,0L,1105L,1L,1424L,109L,-2L,2105L,1L,0L,109L,4L,21202L,-2L,-1L,-2L,1202L,-3L,1L,1675L,21008L,0L,-1L,-1L,1206L,-1L,1697L,1201L,-3L,2L,1687L,20101L,-27L,0L,-3L,22201L,-3L,-2L,-3L,2001L,1550L,-3L,1550L,109L,-4L,2106L,0L,0L,109L,5L,21008L,1552L,0L,-1L,1206L,-1L,1747L,1201L,-3L,1901L,1716L,21002L,0L,1L,-2L,1205L,-4L,1736L,20207L,-2L,1551L,-1L,1205L,-1L,1747L,1102L,-1L,1L,1552L,1106L,0L,1747L,22007L,1551L,-2L,-1L,1205L,-1L,1747L,1101L,1L,0L,1552L,109L,-5L,2106L,0L,0L,109L,1L,21101L,826L,0L,1L,21101L,1765L,0L,0L,1105L,1L,1234L,21001L,1550L,0L,1L,21102L,1L,1776L,0L,1106L,0L,2863L,21102L,1090L,1L,1L,21102L,1787L,1L,0L,1105L,1L,1234L,99L,1106L,0L,1787L,109L,-1L,2106L,0L,0L,109L,1L,21101L,512L,0L,1L,21101L,1809L,0L,0L,1106L,0L,1234L,99L,1106L,0L,1809L,109L,-1L,2105L,1L,0L,109L,1L,1101L,0L,1L,1129L,109L,-1L,2105L,1L,0L,109L,1L,21102L,377L,1L,1L,21101L,1842L,0L,0L,1106L,0L,1234L,1106L,0L,1831L,109L,-1L,2106L,0L,0L,109L,1L,21102L,1L,407L,1L,21101L,0L,1863L,0L,1106L,0L,1234L,99L,1105L,1L,1863L,109L,-1L,2105L,1L,0L,109L,1L,21101L,452L,0L,1L,21102L,1885L,1L,0L,1106L,0L,1234L,99L,1106L,0L,1885L,109L,-1L,2105L,1L,0L,1941L,1947L,1953L,1958L,1965L,1972L,1978L,4644L,4853L,4973L,4783L,5358L,4913L,5097L,5094L,4978L,4807L,4898L,5299L,5404L,4910L,5242L,4681L,5153L,5343L,4709L,4668L,5405L,4869L,5198L,5101L,5201L,4896L,5220L,5237L,5268L,5175L,5235L,4668L,5257L,2281L,2468L,2418L,2450L,2487L,2125L,2505L,5L,95L,108L,104L,104L,23L,5L,96L,91L,108L,108L,1L,4L,101L,105L,112L,3L,6L,104L,104L,106L,107L,94L,-1L,6L,109L,104L,109L,107L,94L,-1L,5L,111L,91L,100L,93L,23L,5L,114L,95L,108L,108L,1L,109L,3L,21101L,1993L,0L,0L,1105L,1L,2634L,1006L,1129L,2010L,21102L,316L,1L,1L,21102L,2007L,1L,0L,1105L,1L,1234L,1105L,1L,2076L,21101L,0L,0L,-1L,1201L,-1L,1894L,2019L,21002L,0L,1L,1L,21101L,0L,0L,2L,21102L,0L,1L,3L,21102L,2037L,1L,0L,1106L,0L,2525L,1206L,1L,2054L,1201L,-1L,1934L,2050L,21101L,2051L,0L,0L,105L,1L,0L,1106L,0L,2076L,21201L,-1L,1L,-1L,21207L,-1L,7L,-2L,1205L,-2L,2014L,21101L,0L,177L,1L,21102L,1L,2076L,0L,1105L,1L,1234L,109L,-3L,2105L,1L,0L,109L,3L,2001L,1128L,-2L,2089L,20102L,1L,0L,-1L,1205L,-1L,2108L,21101L,201L,0L,1L,21101L,0L,2105L,0L,1105L,1L,1234L,1105L,1L,2119L,22102L,1L,-1L,1L,21101L,2119L,0L,0L,1106L,0L,1424L,109L,-3L,2105L,1L,0L,0L,109L,1L,1102L,1L,0L,2124L,21101L,0L,4601L,1L,21102L,1L,13L,2L,21101L,0L,4L,3L,21101L,2173L,0L,4L,21101L,0L,2154L,0L,1105L,1L,1130L,1005L,2124L,2168L,21102L,1L,226L,1L,21102L,1L,2168L,0L,1105L,1L,1234L,109L,-1L,2106L,0L,0L,109L,3L,1005L,2124L,2275L,1201L,-2L,0L,2183L,20008L,0L,1128L,-1L,1206L,-1L,2275L,1201L,-2L,1L,2194L,21001L,0L,0L,-1L,21202L,-1L,1L,1L,21101L,5L,0L,2L,21102L,1L,1L,3L,21102L,1L,2216L,0L,1106L,0L,2525L,1206L,1L,2275L,21101L,0L,258L,1L,21102L,1L,2230L,0L,1105L,1L,1234L,21201L,-1L,0L,1L,21101L,0L,2241L,0L,1106L,0L,1234L,104L,46L,104L,10L,1102L,1L,1L,2124L,1201L,-2L,0L,2256L,1101L,0L,-1L,0L,1201L,-2L,3L,2263L,20102L,1L,0L,-1L,1206L,-1L,2275L,21101L,0L,2275L,0L,2105L,1L,-1L,109L,-3L,2105L,1L,0L,0L,109L,1L,1102L,1L,0L,2280L,21102L,1L,4601L,1L,21101L,13L,0L,2L,21101L,0L,4L,3L,21102L,2329L,1L,4L,21101L,2310L,0L,0L,1106L,0L,1130L,1005L,2280L,2324L,21101L,273L,0L,1L,21102L,1L,2324L,0L,1106L,0L,1234L,109L,-1L,2105L,1L,0L,109L,3L,1005L,2280L,2413L,1201L,-2L,0L,2339L,21008L,0L,-1L,-1L,1206L,-1L,2413L,1201L,-2L,1L,2351L,20102L,1L,0L,-1L,22101L,0L,-1L,1L,21101L,5L,0L,2L,21101L,1L,0L,3L,21101L,0L,2372L,0L,1105L,1L,2525L,1206L,1L,2413L,21102L,1L,301L,1L,21102L,1L,2386L,0L,1105L,1L,1234L,22102L,1L,-1L,1L,21102L,2397L,1L,0L,1105L,1L,1234L,104L,46L,104L,10L,1102L,1L,1L,2280L,1201L,-2L,0L,2412L,1001L,1128L,0L,0L,109L,-3L,2105L,1L,0L,109L,1L,21101L,0L,-1L,1L,21102L,1L,2431L,0L,1106L,0L,1310L,1205L,1L,2445L,21101L,0L,133L,1L,21102L,2445L,1L,0L,1105L,1L,1234L,109L,-1L,2105L,1L,0L,109L,1L,21101L,3L,0L,1L,21102L,2463L,1L,0L,1106L,0L,2081L,109L,-1L,2105L,1L,0L,109L,1L,21101L,4L,0L,1L,21102L,1L,2481L,0L,1106L,0L,2081L,109L,-1L,2106L,0L,0L,89L,109L,1L,21101L,0L,5L,1L,21101L,0L,2500L,0L,1106L,0L,2081L,109L,-1L,2105L,1L,0L,109L,1L,21102L,6L,1L,1L,21101L,2518L,0L,0L,1106L,0L,2081L,109L,-1L,2106L,0L,0L,0L,0L,109L,5L,1201L,-3L,0L,2523L,1101L,1L,0L,2524L,21201L,-4L,0L,1L,21101L,0L,2585L,2L,21102L,2550L,1L,0L,1106L,0L,1174L,1206L,-2L,2576L,2102L,1L,-4L,2558L,2001L,0L,-3L,2566L,101L,3094L,2566L,2566L,21008L,0L,-1L,-1L,1205L,-1L,2576L,1102L,0L,1L,2524L,20102L,1L,2524L,-4L,109L,-5L,2106L,0L,0L,109L,5L,22201L,-4L,-3L,-4L,22201L,-4L,-2L,-4L,21208L,-4L,10L,-1L,1206L,-1L,2606L,21102L,-1L,1L,-4L,201L,-3L,2523L,2616L,1001L,2616L,3094L,2616L,20101L,0L,0L,-1L,22208L,-4L,-1L,-1L,1205L,-1L,2629L,1101L,0L,0L,2524L,109L,-5L,2106L,0L,0L,109L,4L,21102L,1L,3094L,1L,21101L,0L,30L,2L,21102L,1L,1L,3L,21101L,2706L,0L,4L,21102L,1L,2659L,0L,1105L,1L,1130L,21101L,0L,0L,-3L,203L,-2L,21208L,-2L,10L,-1L,1205L,-1L,2701L,21207L,-2L,0L,-1L,1205L,-1L,2663L,21207L,-3L,29L,-1L,1206L,-1L,2663L,2101L,3094L,-3L,2693L,2102L,1L,-2L,0L,21201L,-3L,1L,-3L,1106L,0L,2663L,109L,-4L,2105L,1L,0L,109L,2L,1202L,-1L,1L,2715L,1101L,0L,-1L,0L,109L,-2L,2106L,0L,0L,0L,109L,5L,1201L,-2L,0L,2721L,21207L,-4L,0L,-1L,1206L,-1L,2739L,21101L,0L,0L,-4L,21202L,-4L,1L,1L,22102L,1L,-3L,2L,21101L,1L,0L,3L,21102L,1L,2758L,0L,1105L,1L,2763L,109L,-5L,2105L,1L,0L,109L,6L,21207L,-4L,1L,-1L,1206L,-1L,2786L,22207L,-5L,-3L,-1L,1206L,-1L,2786L,22102L,1L,-5L,-5L,1105L,1L,2858L,21201L,-5L,0L,1L,21201L,-4L,-1L,2L,21202L,-3L,2L,3L,21101L,2805L,0L,0L,1106L,0L,2763L,22101L,0L,1L,-5L,21102L,1L,1L,-2L,22207L,-5L,-3L,-1L,1206L,-1L,2824L,21102L,1L,0L,-2L,22202L,-3L,-2L,-3L,22107L,0L,-4L,-1L,1206L,-1L,2850L,21201L,-2L,0L,1L,21201L,-4L,-1L,2L,21101L,2850L,0L,0L,106L,0L,2721L,21202L,-3L,-1L,-3L,22201L,-5L,-3L,-5L,109L,-6L,2105L,1L,0L,109L,3L,21208L,-2L,0L,-1L,1205L,-1L,2902L,21207L,-2L,0L,-1L,1205L,-1L,2882L,1105L,1L,2888L,104L,45L,21202L,-2L,-1L,-2L,21201L,-2L,0L,1L,21101L,0L,2899L,0L,1105L,1L,2909L,1105L,1L,2904L,104L,48L,109L,-3L,2106L,0L,0L,109L,4L,21202L,-3L,1L,1L,21101L,0L,10L,2L,21102L,2926L,1L,0L,1106L,0L,3010L,22102L,1L,1L,-2L,21202L,2L,1L,-1L,1206L,-2L,2948L,21202L,-2L,1L,1L,21102L,1L,2948L,0L,1105L,1L,2909L,22101L,48L,-1L,-1L,204L,-1L,109L,-4L,2105L,1L,0L,1L,2L,4L,8L,16L,32L,64L,128L,256L,512L,1024L,2048L,4096L,8192L,16384L,32768L,65536L,131072L,262144L,524288L,1048576L,2097152L,4194304L,8388608L,16777216L,33554432L,67108864L,134217728L,268435456L,536870912L,1073741824L,2147483648L,4294967296L,8589934592L,17179869184L,34359738368L,68719476736L,137438953472L,274877906944L,549755813888L,1099511627776L,2199023255552L,4398046511104L,8796093022208L,17592186044416L,35184372088832L,70368744177664L,140737488355328L,281474976710656L,562949953421312L,1125899906842624L,109L,8L,21102L,0L,1L,-4L,21101L,0L,0L,-3L,21101L,0L,51L,-2L,21201L,-2L,-1L,-2L,1201L,-2L,2959L,3034L,20101L,0L,0L,-1L,21202L,-3L,2L,-3L,22207L,-7L,-1L,-5L,1205L,-5L,3059L,21201L,-3L,1L,-3L,22102L,-1L,-1L,-5L,22201L,-7L,-5L,-7L,22207L,-3L,-6L,-5L,1205L,-5L,3078L,22102L,-1L,-6L,-5L,22201L,-3L,-5L,-3L,22201L,-1L,-4L,-4L,1205L,-2L,3024L,21201L,-4L,0L,-7L,22101L,0L,-3L,-6L,109L,-8L,2106L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,3131L,3143L,0L,0L,3252L,3401L,0L,11L,61L,105L,95L,94L,17L,50L,97L,83L,78L,79L,83L,108L,-19L,2L,7L,-79L,-9L,-2L,2L,-83L,-11L,-7L,-86L,-3L,-16L,-7L,-11L,-6L,-21L,-21L,-94L,-30L,-96L,-25L,-19L,-23L,-31L,-101L,-29L,-25L,-104L,-21L,-34L,-38L,-108L,-39L,-34L,-32L,-33L,-31L,-114L,-43L,-47L,-35L,-49L,-105L,-120L,-69L,-43L,-123L,-49L,-56L,-57L,-47L,-128L,-40L,-51L,-46L,-50L,-133L,-51L,-63L,-63L,-57L,-138L,-69L,-58L,-62L,-65L,-143L,-79L,-69L,-63L,-68L,-148L,-79L,-68L,-82L,-83L,-63L,-81L,-77L,-85L,-145L,-158L,-75L,-88L,-92L,-162L,-91L,-85L,-89L,-97L,-167L,-96L,-104L,-87L,-171L,-106L,-104L,-105L,-97L,-176L,-94L,-109L,-114L,-104L,-112L,-114L,-169L,3259L,3273L,0L,3351L,3309L,0L,3124L,13L,54L,100L,86L,103L,15L,63L,98L,77L,93L,94L,78L,90L,90L,35L,49L,68L,64L,-6L,59L,61L,59L,73L,-11L,53L,69L,55L,-15L,49L,59L,58L,-19L,64L,58L,57L,-23L,59L,52L,39L,49L,48L,-29L,40L,48L,50L,-33L,55L,44L,49L,-23L,3316L,3324L,0L,4208L,3641L,0L,3252L,7L,68L,97L,107L,89L,93L,89L,97L,26L,43L,91L,73L,85L,91L,85L,72L,72L,76L,68L,3L,78L,-6L,63L,74L,60L,59L,79L,57L,0L,54L,67L,57L,52L,50L,-5L,3358L,3366L,0L,3474L,3778L,3252L,0L,7L,76L,108L,88L,88L,97L,89L,102L,34L,48L,66L,69L,73L,62L,62L,61L,73L,3L,72L,61L,77L,55L,53L,-2L,-17L,34L,53L,49L,68L,-15L,59L,45L,-25L,39L,49L,48L,-29L,39L,46L,48L,51L,55L,-21L,3408L,3417L,0L,3124L,3566L,0L,3849L,8L,64L,102L,98L,100L,88L,88L,85L,92L,56L,27L,54L,51L,42L,51L,49L,39L,-31L,51L,36L,35L,42L,47L,-37L,46L,40L,-40L,31L,23L,43L,25L,-45L,30L,22L,22L,35L,-50L,22L,32L,-53L,25L,23L,-56L,27L,14L,10L,-60L,-22L,11L,2L,14L,19L,-66L,-28L,14L,4L,-2L,-71L,11L,-4L,10L,9L,-3L,1L,-7L,-65L,3481L,3489L,0L,4025L,0L,3351L,4084L,7L,65L,89L,99L,98L,108L,85L,108L,76L,8L,27L,27L,36L,-48L,16L,32L,18L,13L,-53L,18L,10L,27L,-57L,8L,10L,9L,17L,-62L,16L,16L,19L,7L,10L,5L,21L,-1L,-3L,-72L,-3L,5L,7L,-76L,6L,1L,-2L,-11L,3L,-10L,-10L,-6L,-14L,-59L,-87L,1L,-10L,-5L,-84L,-10L,-24L,-94L,-21L,-11L,-14L,-14L,-99L,-22L,-22L,-18L,-103L,-23L,-20L,-33L,-23L,-39L,-109L,-27L,-26L,-30L,-44L,-114L,-28L,-44L,-52L,-34L,-105L,3573L,3585L,0L,0L,0L,0L,3401L,11L,72L,87L,92L,87L,95L,83L,84L,14L,57L,77L,77L,55L,34L,55L,60L,-26L,56L,41L,40L,-30L,38L,54L,40L,34L,34L,42L,30L,31L,-39L,32L,28L,40L,26L,-44L,34L,24L,-47L,32L,33L,29L,33L,27L,31L,35L,25L,13L,-57L,22L,20L,16L,28L,15L,6L,18L,-65L,2L,2L,15L,4L,1L,7L,-72L,14L,5L,7L,-1L,-63L,3648L,3671L,0L,0L,0L,3706L,3309L,22L,65L,74L,90L,87L,6L,41L,86L,76L,88L,70L,0L,44L,63L,70L,74L,79L,63L,71L,57L,69L,57L,58L,34L,39L,81L,-4L,60L,74L,73L,61L,56L,72L,72L,-12L,71L,65L,-15L,50L,52L,-18L,68L,59L,61L,53L,50L,54L,46L,-26L,51L,51L,53L,47L,34L,44L,43L,55L,-21L,3713L,3722L,0L,3641L,4368L,0L,4139L,8L,72L,88L,105L,104L,85L,90L,87L,100L,55L,29L,48L,44L,63L,-20L,54L,40L,-30L,34L,-32L,43L,39L,49L,48L,39L,31L,-39L,44L,46L,31L,40L,40L,44L,-46L,18L,30L,19L,-50L,32L,32L,12L,28L,29L,17L,21L,13L,-59L,24L,18L,-62L,13L,15L,14L,9L,-67L,-3L,7L,6L,-71L,-7L,3L,-1L,0L,-7L,-63L,3785L,3797L,0L,3929L,0L,0L,3351L,11L,58L,98L,90L,91L,95L,85L,84L,96L,86L,90L,82L,51L,38L,59L,64L,-22L,60L,45L,44L,-26L,38L,-28L,58L,42L,42L,52L,36L,32L,44L,29L,45L,30L,-39L,47L,32L,42L,29L,-44L,35L,30L,18L,30L,34L,-50L,19L,27L,29L,-54L,-4L,24L,25L,15L,19L,11L,7L,20L,16L,9L,3L,-66L,19L,-50L,-55L,3856L,3868L,0L,0L,3401L,0L,0L,11L,68L,86L,102L,87L,99L,102L,80L,98L,92L,94L,100L,60L,24L,43L,39L,51L,37L,-33L,31L,47L,33L,-37L,27L,-39L,30L,28L,45L,-43L,40L,24L,30L,22L,35L,18L,29L,29L,17L,30L,-27L,-55L,28L,15L,11L,30L,-53L,21L,7L,-63L,1L,11L,10L,-67L,-2L,10L,6L,13L,-3L,-5L,-74L,-7L,3L,10L,0L,-67L,-80L,3L,-10L,-4L,1L,-14L,-14L,-73L,3936L,3947L,0L,0L,0L,3778L,0L,10L,68L,86L,106L,92L,89L,82L,100L,88L,93L,91L,77L,6L,38L,18L,36L,36L,33L,-25L,-52L,-2L,30L,27L,9L,21L,10L,10L,8L,-47L,-62L,-15L,12L,4L,-1L,16L,1L,-69L,13L,14L,8L,7L,2L,14L,-76L,0L,-9L,-14L,3L,4L,0L,-14L,-7L,-16L,-8L,-3L,-5L,-89L,-20L,-9L,-13L,-16L,-94L,-25L,-23L,-27L,-14L,-10L,-100L,-18L,-18L,-38L,-22L,-22L,-106L,-23L,-29L,-109L,-28L,-42L,-45L,-48L,-38L,-42L,-50L,-35L,-53L,-35L,-51L,-107L,4032L,4055L,0L,0L,4272L,3474L,0L,22L,50L,88L,92L,7L,41L,77L,83L,70L,81L,77L,65L,83L,67L,-3L,34L,74L,79L,71L,76L,56L,63L,67L,28L,55L,82L,79L,70L,72L,78L,85L,9L,-4L,68L,78L,0L,75L,-9L,73L,73L,61L,63L,62L,-15L,71L,62L,64L,56L,53L,57L,49L,-9L,4091L,4098L,0L,0L,3474L,0L,0L,6L,59L,107L,91L,88L,90L,90L,40L,38L,70L,68L,58L,-12L,66L,56L,-15L,68L,55L,51L,-19L,47L,44L,44L,50L,54L,44L,58L,56L,-28L,54L,39L,38L,45L,-33L,50L,44L,-36L,35L,27L,47L,29L,-41L,38L,36L,43L,24L,36L,-33L,4146L,4167L,0L,0L,3706L,0L,0L,20L,51L,84L,80L,93L,8L,62L,88L,70L,84L,83L,75L,79L,71L,-1L,33L,66L,74L,79L,63L,75L,40L,32L,70L,77L,-11L,57L,63L,69L,54L,-16L,51L,61L,-19L,69L,58L,63L,-23L,63L,57L,39L,53L,-28L,51L,52L,38L,51L,36L,44L,49L,47L,-37L,41L,39L,-40L,43L,30L,26L,-44L,26L,33L,-16L,4215L,4223L,0L,0L,0L,3309L,0L,7L,76L,108L,102L,104L,86L,91L,88L,48L,36L,55L,51L,-19L,46L,58L,66L,46L,59L,-25L,48L,58L,55L,55L,-30L,36L,47L,45L,50L,30L,37L,41L,-38L,38L,39L,41L,27L,-43L,22L,34L,42L,22L,35L,-35L,-50L,-51L,-2L,16L,13L,30L,26L,26L,15L,27L,9L,15L,27L,-49L,4279L,4288L,0L,0L,0L,0L,4025L,8L,59L,102L,104L,103L,93L,87L,97L,99L,79L,5L,24L,20L,-50L,26L,17L,31L,11L,21L,-56L,30L,7L,17L,16L,22L,-62L,2L,14L,3L,-66L,17L,4L,0L,-70L,6L,-3L,11L,-9L,1L,-76L,-7L,-2L,0L,-1L,1L,-82L,-18L,-2L,-16L,-86L,-4L,-12L,-16L,-19L,-19L,-8L,-17L,-5L,-95L,-28L,-24L,-28L,-29L,-31L,-19L,-33L,-25L,-20L,-105L,-39L,-28L,-32L,-30L,-28L,-28L,-98L,-113L,-67L,-33L,-116L,-52L,-36L,-50L,-120L,-37L,-50L,-54L,-35L,-94L,4375L,4384L,0L,0L,4457L,0L,3706L,8L,75L,96L,89L,96L,20L,53L,83L,106L,72L,11L,44L,38L,37L,35L,37L,38L,36L,-48L,17L,29L,33L,20L,-53L,-4L,14L,12L,-44L,-12L,20L,23L,8L,6L,-63L,-14L,4L,7L,11L,0L,0L,-1L,11L,-72L,4L,-5L,-7L,-3L,-10L,-5L,-1L,-11L,-81L,-17L,-5L,-16L,-85L,-4L,-18L,-17L,-4L,-14L,-26L,-10L,-93L,-12L,-26L,-23L,-19L,-30L,-30L,-31L,-19L,-102L,-26L,-35L,-37L,-33L,-40L,-35L,-31L,-41L,-97L,4464L,4484L,0L,4556L,0L,0L,4368L,19L,64L,81L,78L,95L,91L,81L,91L,95L,5L,39L,75L,71L,68L,75L,79L,77L,70L,74L,79L,71L,2L,38L,-41L,42L,29L,25L,-45L,32L,22L,40L,35L,-50L,31L,27L,26L,23L,-43L,-56L,8L,-58L,21L,22L,8L,21L,20L,21L,17L,3L,-54L,15L,0L,8L,12L,1L,11L,-1L,11L,-7L,-77L,-8L,-3L,-1L,-2L,0L,-83L,3L,-12L,-10L,-11L,-88L,-3L,-21L,-9L,-19L,-23L,-5L,-95L,-7L,-18L,-13L,-17L,-100L,-28L,-34L,-34L,-26L,-21L,-33L,-23L,-19L,-95L,4563L,4588L,1553L,0L,0L,4457L,0L,24L,56L,89L,75L,88L,87L,88L,84L,70L,13L,50L,67L,75L,79L,68L,78L,66L,78L,60L,-10L,27L,64L,66L,65L,67L,12L,53L,97L,83L,93L,105L,105L,87L,91L,83L,25L,24L,23L,4139L,4653L,27L,1850L,4025L,4665L,4124L,0L,3252L,4678L,16777245L,0L,4208L,4689L,33554462L,0L,3351L,4694L,31L,1829L,3849L,4708L,1048608L,0L,3566L,4717L,97L,0L,3401L,4729L,36L,0L,4084L,4737L,35L,1818L,3474L,4757L,268435492L,0L,3929L,4763L,32805L,0L,4368L,4767L,38L,1872L,4272L,4775L,39L,1796L,11L,98L,99L,95L,102L,86L,94L,15L,90L,78L,98L,76L,12L,100L,101L,91L,94L,85L,15L,92L,98L,89L,77L,79L,91L,10L,89L,86L,98L,87L,107L,17L,83L,80L,92L,82L,4L,104L,106L,105L,102L,13L,92L,96L,87L,89L,93L,87L,97L,81L,11L,86L,88L,87L,87L,8L,89L,106L,106L,90L,102L,92L,101L,92L,11L,91L,99L,98L,86L,17L,98L,80L,98L,86L,91L,89L,7L,91L,103L,108L,98L,89L,89L,101L,19L,84L,85L,76L,88L,93L,8L,76L,82L,74L,71L,87L,84L,80L,77L,64L,69L,75L,65L,79L,5L,104L,111L,109L,93L,111L,3L,106L,113L,98L,7L,105L,96L,102L,106L,100L,98L,102L,10L,91L,104L,87L,84L,98L,86L,16L,95L,93L,81L};
		
		itemsMap = generateItemsMap();
		pickuableItemSubSet = new SubSetGenerator("12345678").getSubsets();
		
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		int haltCode = HALT_CODE_NORMAL;				

        Queue<String> commandsQueue = generateCommands();
        // printCommandsQueue(commandsQueue);
        
        Queue<Integer> commandsASCIIQueue = converToASCII(commandsQueue);
        // printCommandsASCIIQueue(commandsASCIIQueue);
		
		while (haltCode != HALT_CODE_EXIT) {
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			haltCode = intCodeComputer.haltCode;
			
			if (haltCode == HALT_CODE_INPUT) {
				intCodeComputer.inputValues.add(commandsASCIIQueue.remove());
			}
			
			if (haltCode == HALT_CODE_OUTPUT) {
				long code = intCodeComputer.output;
				System.out.print((char) code);
			}
		}
		return null; // Empty return		
	}
	
	Map<Character, String> generateItemsMap() {
		Map<Character, String>  itemsMap = new HashMap<Character, String>();
		itemsMap.put('1', CANDY_CANE);
		itemsMap.put('2', BOULDER);
		itemsMap.put('3', FOOD_RATION);
		itemsMap.put('4', ASTERISK);
		itemsMap.put('5', MUTEX);
		itemsMap.put('6', MUG);
		itemsMap.put('7', PRIME_NUMBER);
		itemsMap.put('8', LOOM);
		return itemsMap;
	}
	
	void printCommandsQueue(Queue<String> commandsQueue) {
		System.out.println("-------");
        for (String command : commandsQueue) {
        	System.out.print(command);
        }
        System.out.println("-------");
	}
	
	void printCommandsASCIIQueue(Queue<Integer> commandsASCIIQueue) {
		 System.out.println("-------");
       for (Integer command : commandsASCIIQueue) {
       	System.out.print(command + ",");
       }
       System.out.println("-------");
	}
	
	Queue<Integer> converToASCII(Queue<String> commandsQueue) {
		Queue<Integer> commandsASCIIQueue = new LinkedList<>();
		for (String command : commandsQueue) {
			// Convert command string to ASCII code
			for (char commandCharacter : command.toCharArray()) {
				commandsASCIIQueue.add((int) commandCharacter);
			}
		}
		return commandsASCIIQueue;
	}
			
	Queue<String> generateCommands() {
		Queue<String> commands = new LinkedList<>();
		commands.add(COMMAND_MOVE_SOUTH);
		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, BOULDER));
		commands.add(COMMAND_MOVE_WEST);
		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, ASTERISK));
		commands.add(COMMAND_MOVE_EAST);
		commands.add(COMMAND_MOVE_EAST);
		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, FOOD_RATION));
		commands.add(COMMAND_MOVE_WEST);
		commands.add(COMMAND_MOVE_NORTH);
		
		
//		commands.add(COMMAND_MOVE_EAST);
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, CANDY_CANE));
//		commands.add(COMMAND_MOVE_NORTH);
//		commands.add(COMMAND_MOVE_NORTH);
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, MUTEX));
//		commands.add(COMMAND_MOVE_NORTH);
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, PRIME_NUMBER));
//		commands.add(COMMAND_MOVE_SOUTH);
//		commands.add(COMMAND_MOVE_SOUTH);
//		commands.add(COMMAND_MOVE_EAST);
//		commands.add(COMMAND_MOVE_NORTH);
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, MUG));
//		commands.add(COMMAND_MOVE_SOUTH);
//		commands.add(COMMAND_MOVE_WEST);
//		commands.add(COMMAND_MOVE_SOUTH);
//		
//		commands.add(COMMAND_MOVE_EAST);
//		commands.add(COMMAND_MOVE_NORTH);
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, LOOM));
//		commands.add(COMMAND_MOVE_SOUTH);
//		
//		commands.add(COMMAND_MOVE_EAST);
//		commands.add(COMMAND_MOVE_SOUTH);
//		commands.add(COMMAND_MOVE_EAST);
//		
//		commands.add(COMMAND_MOVE_EAST);
//		commands.add(COMMAND_INV);
//		
//		// Drop all items in security point
//		for (Entry<Character, String> itemEntry : itemsMap.entrySet()) {
//			commands.add(MessageFormat.format(COMMAND_DROP_PREFIX, itemEntry.getValue()));
//		}
//		for (String pickableItemIdList: pickuableItemSubSet) { // Iterate items sub set until we get correct password
//			// Add take commands for the selected sub set items
//			for (Character itemId : pickableItemIdList.toCharArray()) {
//				commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, itemsMap.get(itemId)));
//			}
//			// Add command to enter into pressure-sensitive room
//			commands.add(COMMAND_MOVE_NORTH);
//			// If security check failed it will again return into security end point
//			// Again add drop commands to the selected sub set items
//			for (Character itemId : pickableItemIdList.toCharArray()) {
//				commands.add(MessageFormat.format(COMMAND_DROP_PREFIX, itemsMap.get(itemId)));
//			}
//		}
		
//		commands.add(MessageFormat.format(COMMAND_TAKE_PREFIX, LOOM));
//		commands.add(COMMAND_INV);
//		commands.add(COMMAND_MOVE_NORTH);


		return commands;
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
			while(!Day25CryostasisPart1.HALT_CODES.contains(this.haltCode)) {
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
	
	public class SubSetGenerator {
		final String input;
		final List<String> subSetList = new ArrayList<String>();

		public SubSetGenerator(String input) {
			this.input = input;
		}

		List<String> getSubsets() {
			for (int i = 0; i < input.length(); i++) {
				subSet("", i);
			}
			return subSetList;
		}

		void subSet(String substr, int index) {
			String temp = "" + input.charAt(index);
			temp = substr + temp;
			subSetList.add(temp);
			for (int i = index + 1; i < input.length(); i++) {
				subSet(temp, i);
			}

		}
	}

}
