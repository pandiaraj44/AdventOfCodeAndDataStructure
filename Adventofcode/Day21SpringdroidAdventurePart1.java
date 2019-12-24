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


/*--- Day 21: Springdroid Adventure ---
You lift off from Pluto and start flying in the direction of Santa.

While experimenting further with the tractor beam, you accidentally pull an asteroid directly into your ship! It deals significant damage to your hull and causes your ship to begin tumbling violently.

You can send a droid out to investigate, but the tumbling is causing enough artificial gravity that one wrong step could send the droid through a hole in the hull and flying out into space.

The clear choice for this mission is a droid that can jump over the holes in the hull - a springdroid.

You can use an Intcode program (your puzzle input) running on an ASCII-capable computer to program the springdroid. However, springdroids don't run Intcode; instead, they run a simplified assembly language called springscript.

While a springdroid is certainly capable of navigating the artificial gravity and giant holes, it has one downside: it can only remember at most 15 springscript instructions.

The springdroid will move forward automatically, constantly thinking about whether to jump. The springscript program defines the logic for this decision.

Springscript programs only use Boolean values, not numbers or strings. Two registers are available: T, the temporary value register, and J, the jump register. If the jump register is true at the end of the springscript program, the springdroid will try to jump. Both of these registers start with the value false.

Springdroids have a sensor that can detect whether there is ground at various distances in the direction it is facing; these values are provided in read-only registers. Your springdroid can detect ground at four distances: one tile away (A), two tiles away (B), three tiles away (C), and four tiles away (D). If there is ground at the given distance, the register will be true; if there is a hole, the register will be false.

There are only three instructions available in springscript:

AND X Y sets Y to true if both X and Y are true; otherwise, it sets Y to false.
OR X Y sets Y to true if at least one of X or Y is true; otherwise, it sets Y to false.
NOT X Y sets Y to true if X is false; otherwise, it sets Y to false.
In all three instructions, the second argument (Y) needs to be a writable register (either T or J). The first argument (X) can be any register (including A, B, C, or D).

For example, the one-instruction program NOT A J means "if the tile immediately in front of me is not ground, jump".

Or, here is a program that jumps if a three-tile-wide hole (with ground on the other side of the hole) is detected:

NOT A J
NOT B T
AND T J
NOT C T
AND T J
AND D J
The Intcode program expects ASCII inputs and outputs. It will begin by displaying a prompt; then, input the desired instructions one per line. End each line with a newline (ASCII code 10). When you have finished entering your program, provide the command WALK followed by a newline to instruct the springdroid to begin surveying the hull.

If the springdroid falls into space, an ASCII rendering of the last moments of its life will be produced. In these, @ is the springdroid, # is hull, and . is empty space. For example, suppose you program the springdroid like this:

NOT D J
WALK
This one-instruction program sets J to true if and only if there is no ground four tiles away. In other words, it attempts to jump into any hole it finds:

.................
.................
@................
#####.###########

.................
.................
.@...............
#####.###########

.................
..@..............
.................
#####.###########

...@.............
.................
.................
#####.###########

.................
....@............
.................
#####.###########

.................
.................
.....@...........
#####.###########

.................
.................
.................
#####@###########
However, if the springdroid successfully makes it across, it will use an output instruction to indicate the amount of damage to the hull as a single giant integer outside the normal ASCII range.

Program the springdroid with logic that allows it to survey the hull without falling into space. What amount of hull damage does it report?

Your puzzle answer was 19348840.

*/
public class Day21SpringdroidAdventurePart1 implements AOC<Long>{

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
		Day21SpringdroidAdventurePart1 day21SpringdroidAdventurePart1 = new Day21SpringdroidAdventurePart1();
		Long amoutOfDamageReported = day21SpringdroidAdventurePart1.run();
    	System.out.println("What amount of hull damage does it report?" + amoutOfDamageReported);

	}
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Override
	public Long run() {
		long intCodes[] = {109L,2050L,21102L,1L,966L,1L,21102L,13L,1L,0L,1106L,0L,1378L,21101L,20L,0L,0L,1105L,1L,1337L,21101L,27L,0L,0L,1106L,0L,1279L,1208L,1L,65L,748L,1005L,748L,73L,1208L,1L,79L,748L,1005L,748L,110L,1208L,1L,78L,748L,1005L,748L,132L,1208L,1L,87L,748L,1005L,748L,169L,1208L,1L,82L,748L,1005L,748L,239L,21102L,1L,1041L,1L,21102L,1L,73L,0L,1106L,0L,1421L,21102L,1L,78L,1L,21102L,1041L,1L,2L,21102L,1L,88L,0L,1105L,1L,1301L,21101L,0L,68L,1L,21101L,0L,1041L,2L,21102L,103L,1L,0L,1105L,1L,1301L,1102L,1L,1L,750L,1105L,1L,298L,21101L,0L,82L,1L,21102L,1041L,1L,2L,21102L,125L,1L,0L,1105L,1L,1301L,1101L,2L,0L,750L,1105L,1L,298L,21101L,0L,79L,1L,21101L,1041L,0L,2L,21101L,147L,0L,0L,1105L,1L,1301L,21102L,84L,1L,1L,21102L,1L,1041L,2L,21102L,1L,162L,0L,1105L,1L,1301L,1101L,3L,0L,750L,1106L,0L,298L,21102L,1L,65L,1L,21101L,1041L,0L,2L,21102L,1L,184L,0L,1105L,1L,1301L,21101L,76L,0L,1L,21101L,0L,1041L,2L,21101L,199L,0L,0L,1105L,1L,1301L,21102L,1L,75L,1L,21102L,1L,1041L,2L,21102L,214L,1L,0L,1105L,1L,1301L,21101L,221L,0L,0L,1106L,0L,1337L,21102L,1L,10L,1L,21101L,1041L,0L,2L,21102L,1L,236L,0L,1106L,0L,1301L,1105L,1L,553L,21101L,0L,85L,1L,21102L,1041L,1L,2L,21102L,254L,1L,0L,1106L,0L,1301L,21101L,0L,78L,1L,21102L,1041L,1L,2L,21101L,0L,269L,0L,1105L,1L,1301L,21102L,276L,1L,0L,1105L,1L,1337L,21102L,10L,1L,1L,21102L,1L,1041L,2L,21101L,291L,0L,0L,1105L,1L,1301L,1102L,1L,1L,755L,1105L,1L,553L,21101L,0L,32L,1L,21101L,1041L,0L,2L,21102L,1L,313L,0L,1106L,0L,1301L,21101L,320L,0L,0L,1105L,1L,1337L,21101L,0L,327L,0L,1105L,1L,1279L,1201L,1L,0L,749L,21102L,1L,65L,2L,21101L,0L,73L,3L,21102L,1L,346L,0L,1106L,0L,1889L,1206L,1L,367L,1007L,749L,69L,748L,1005L,748L,360L,1101L,0L,1L,756L,1001L,749L,-64L,751L,1106L,0L,406L,1008L,749L,74L,748L,1006L,748L,381L,1102L,1L,-1L,751L,1105L,1L,406L,1008L,749L,84L,748L,1006L,748L,395L,1102L,1L,-2L,751L,1106L,0L,406L,21101L,1100L,0L,1L,21101L,0L,406L,0L,1106L,0L,1421L,21102L,1L,32L,1L,21102L,1100L,1L,2L,21102L,1L,421L,0L,1106L,0L,1301L,21101L,0L,428L,0L,1106L,0L,1337L,21101L,435L,0L,0L,1105L,1L,1279L,2102L,1L,1L,749L,1008L,749L,74L,748L,1006L,748L,453L,1101L,0L,-1L,752L,1106L,0L,478L,1008L,749L,84L,748L,1006L,748L,467L,1102L,-2L,1L,752L,1105L,1L,478L,21101L,0L,1168L,1L,21101L,478L,0L,0L,1106L,0L,1421L,21102L,485L,1L,0L,1106L,0L,1337L,21102L,1L,10L,1L,21101L,0L,1168L,2L,21101L,500L,0L,0L,1106L,0L,1301L,1007L,920L,15L,748L,1005L,748L,518L,21101L,0L,1209L,1L,21102L,1L,518L,0L,1106L,0L,1421L,1002L,920L,3L,529L,1001L,529L,921L,529L,1001L,750L,0L,0L,1001L,529L,1L,537L,101L,0L,751L,0L,1001L,537L,1L,545L,1001L,752L,0L,0L,1001L,920L,1L,920L,1105L,1L,13L,1005L,755L,577L,1006L,756L,570L,21102L,1L,1100L,1L,21101L,0L,570L,0L,1105L,1L,1421L,21102L,1L,987L,1L,1105L,1L,581L,21102L,1L,1001L,1L,21101L,588L,0L,0L,1106L,0L,1378L,1102L,1L,758L,594L,102L,1L,0L,753L,1006L,753L,654L,21002L,753L,1L,1L,21102L,1L,610L,0L,1105L,1L,667L,21101L,0L,0L,1L,21102L,1L,621L,0L,1106L,0L,1463L,1205L,1L,647L,21102L,1015L,1L,1L,21101L,0L,635L,0L,1106L,0L,1378L,21102L,1L,1L,1L,21101L,646L,0L,0L,1105L,1L,1463L,99L,1001L,594L,1L,594L,1105L,1L,592L,1006L,755L,664L,1101L,0L,0L,755L,1106L,0L,647L,4L,754L,99L,109L,2L,1102L,726L,1L,757L,21202L,-1L,1L,1L,21102L,1L,9L,2L,21101L,0L,697L,3L,21101L,0L,692L,0L,1105L,1L,1913L,109L,-2L,2106L,0L,0L,109L,2L,102L,1L,757L,706L,2102L,1L,-1L,0L,1001L,757L,1L,757L,109L,-2L,2105L,1L,0L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,1L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,255L,63L,223L,159L,95L,191L,127L,0L,169L,106L,198L,218L,54L,46L,171L,246L,220L,34L,142L,163L,79L,158L,190L,138L,217L,201L,122L,152L,43L,214L,157L,114L,92L,235L,51L,227L,205L,93L,109L,248L,172L,221L,125L,243L,107L,87L,60L,183L,119L,249L,188L,156L,71L,49L,219L,168L,98L,99L,115L,245L,202L,103L,252L,239L,226L,124L,162L,247L,244L,42L,141L,123L,228L,94L,203L,126L,153L,233L,116L,39L,35L,182L,231L,207L,212L,38L,187L,53L,185L,154L,222L,139L,117L,253L,62L,110L,241L,136L,179L,166L,84L,70L,101L,242L,189L,204L,251L,234L,155L,102L,170L,186L,250L,254L,196L,238L,177L,236L,181L,85L,50L,59L,121L,100L,118L,229L,206L,213L,76L,137L,178L,61L,167L,68L,77L,56L,140L,200L,108L,120L,216L,57L,232L,175L,143L,184L,197L,174L,55L,113L,111L,199L,237L,78L,173L,69L,230L,215L,47L,58L,86L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,20L,73L,110L,112L,117L,116L,32L,105L,110L,115L,116L,114L,117L,99L,116L,105L,111L,110L,115L,58L,10L,13L,10L,87L,97L,108L,107L,105L,110L,103L,46L,46L,46L,10L,10L,13L,10L,82L,117L,110L,110L,105L,110L,103L,46L,46L,46L,10L,10L,25L,10L,68L,105L,100L,110L,39L,116L,32L,109L,97L,107L,101L,32L,105L,116L,32L,97L,99L,114L,111L,115L,115L,58L,10L,10L,58L,73L,110L,118L,97L,108L,105L,100L,32L,111L,112L,101L,114L,97L,116L,105L,111L,110L,59L,32L,101L,120L,112L,101L,99L,116L,101L,100L,32L,115L,111L,109L,101L,116L,104L,105L,110L,103L,32L,108L,105L,107L,101L,32L,65L,78L,68L,44L,32L,79L,82L,44L,32L,111L,114L,32L,78L,79L,84L,67L,73L,110L,118L,97L,108L,105L,100L,32L,102L,105L,114L,115L,116L,32L,97L,114L,103L,117L,109L,101L,110L,116L,59L,32L,101L,120L,112L,101L,99L,116L,101L,100L,32L,115L,111L,109L,101L,116L,104L,105L,110L,103L,32L,108L,105L,107L,101L,32L,65L,44L,32L,66L,44L,32L,67L,44L,32L,68L,44L,32L,74L,44L,32L,111L,114L,32L,84L,40L,73L,110L,118L,97L,108L,105L,100L,32L,115L,101L,99L,111L,110L,100L,32L,97L,114L,103L,117L,109L,101L,110L,116L,59L,32L,101L,120L,112L,101L,99L,116L,101L,100L,32L,74L,32L,111L,114L,32L,84L,52L,79L,117L,116L,32L,111L,102L,32L,109L,101L,109L,111L,114L,121L,59L,32L,97L,116L,32L,109L,111L,115L,116L,32L,49L,53L,32L,105L,110L,115L,116L,114L,117L,99L,116L,105L,111L,110L,115L,32L,99L,97L,110L,32L,98L,101L,32L,115L,116L,111L,114L,101L,100L,0L,109L,1L,1005L,1262L,1270L,3L,1262L,20102L,1L,1262L,0L,109L,-1L,2105L,1L,0L,109L,1L,21101L,0L,1288L,0L,1106L,0L,1263L,21002L,1262L,1L,0L,1102L,0L,1L,1262L,109L,-1L,2106L,0L,0L,109L,5L,21102L,1310L,1L,0L,1105L,1L,1279L,21201L,1L,0L,-2L,22208L,-2L,-4L,-1L,1205L,-1L,1332L,22101L,0L,-3L,1L,21102L,1L,1332L,0L,1105L,1L,1421L,109L,-5L,2106L,0L,0L,109L,2L,21102L,1L,1346L,0L,1106L,0L,1263L,21208L,1L,32L,-1L,1205L,-1L,1363L,21208L,1L,9L,-1L,1205L,-1L,1363L,1105L,1L,1373L,21101L,1370L,0L,0L,1106L,0L,1279L,1105L,1L,1339L,109L,-2L,2105L,1L,0L,109L,5L,1201L,-4L,0L,1385L,21002L,0L,1L,-2L,22101L,1L,-4L,-4L,21102L,1L,0L,-3L,22208L,-3L,-2L,-1L,1205L,-1L,1416L,2201L,-4L,-3L,1408L,4L,0L,21201L,-3L,1L,-3L,1105L,1L,1396L,109L,-5L,2106L,0L,0L,109L,2L,104L,10L,22101L,0L,-1L,1L,21102L,1436L,1L,0L,1105L,1L,1378L,104L,10L,99L,109L,-2L,2105L,1L,0L,109L,3L,20002L,594L,753L,-1L,22202L,-1L,-2L,-1L,201L,-1L,754L,754L,109L,-3L,2105L,1L,0L,109L,10L,21102L,1L,5L,-5L,21102L,1L,1L,-4L,21102L,0L,1L,-3L,1206L,-9L,1555L,21102L,3L,1L,-6L,21102L,5L,1L,-7L,22208L,-7L,-5L,-8L,1206L,-8L,1507L,22208L,-6L,-4L,-8L,1206L,-8L,1507L,104L,64L,1105L,1L,1529L,1205L,-6L,1527L,1201L,-7L,716L,1515L,21002L,0L,-11L,-8L,21201L,-8L,46L,-8L,204L,-8L,1106L,0L,1529L,104L,46L,21201L,-7L,1L,-7L,21207L,-7L,22L,-8L,1205L,-8L,1488L,104L,10L,21201L,-6L,-1L,-6L,21207L,-6L,0L,-8L,1206L,-8L,1484L,104L,10L,21207L,-4L,1L,-8L,1206L,-8L,1569L,21101L,0L,0L,-9L,1106L,0L,1689L,21208L,-5L,21L,-8L,1206L,-8L,1583L,21101L,0L,1L,-9L,1106L,0L,1689L,1201L,-5L,716L,1589L,20102L,1L,0L,-2L,21208L,-4L,1L,-1L,22202L,-2L,-1L,-1L,1205L,-2L,1613L,21201L,-5L,0L,1L,21102L,1613L,1L,0L,1106L,0L,1444L,1206L,-1L,1634L,22101L,0L,-5L,1L,21102L,1L,1627L,0L,1106L,0L,1694L,1206L,1L,1634L,21101L,2L,0L,-3L,22107L,1L,-4L,-8L,22201L,-1L,-8L,-8L,1206L,-8L,1649L,21201L,-5L,1L,-5L,1206L,-3L,1663L,21201L,-3L,-1L,-3L,21201L,-4L,1L,-4L,1106L,0L,1667L,21201L,-4L,-1L,-4L,21208L,-4L,0L,-1L,1201L,-5L,716L,1676L,22002L,0L,-1L,-1L,1206L,-1L,1686L,21102L,1L,1L,-4L,1105L,1L,1477L,109L,-10L,2105L,1L,0L,109L,11L,21102L,1L,0L,-6L,21101L,0L,0L,-8L,21101L,0L,0L,-7L,20208L,-6L,920L,-9L,1205L,-9L,1880L,21202L,-6L,3L,-9L,1201L,-9L,921L,1724L,21001L,0L,0L,-5L,1001L,1724L,1L,1733L,20102L,1L,0L,-4L,21202L,-4L,1L,1L,21101L,0L,1L,2L,21102L,9L,1L,3L,21101L,1754L,0L,0L,1106L,0L,1889L,1206L,1L,1772L,2201L,-10L,-4L,1767L,1001L,1767L,716L,1767L,20102L,1L,0L,-3L,1106L,0L,1790L,21208L,-4L,-1L,-9L,1206L,-9L,1786L,22102L,1L,-8L,-3L,1105L,1L,1790L,21202L,-7L,1L,-3L,1001L,1733L,1L,1795L,21002L,0L,1L,-2L,21208L,-2L,-1L,-9L,1206L,-9L,1812L,21202L,-8L,1L,-1L,1105L,1L,1816L,21201L,-7L,0L,-1L,21208L,-5L,1L,-9L,1205L,-9L,1837L,21208L,-5L,2L,-9L,1205L,-9L,1844L,21208L,-3L,0L,-1L,1106L,0L,1855L,22202L,-3L,-1L,-1L,1105L,1L,1855L,22201L,-3L,-1L,-1L,22107L,0L,-1L,-1L,1105L,1L,1855L,21208L,-2L,-1L,-9L,1206L,-9L,1869L,22101L,0L,-1L,-8L,1106L,0L,1873L,21202L,-1L,1L,-7L,21201L,-6L,1L,-6L,1106L,0L,1708L,22102L,1L,-8L,-10L,109L,-11L,2106L,0L,0L,109L,7L,22207L,-6L,-5L,-3L,22207L,-4L,-6L,-2L,22201L,-3L,-2L,-1L,21208L,-1L,0L,-6L,109L,-7L,2106L,0L,0L,0L,109L,5L,1202L,-2L,1L,1912L,21207L,-4L,0L,-1L,1206L,-1L,1930L,21102L,0L,1L,-4L,22101L,0L,-4L,1L,21201L,-3L,0L,2L,21102L,1L,1L,3L,21102L,1949L,1L,0L,1105L,1L,1954L,109L,-5L,2106L,0L,0L,109L,6L,21207L,-4L,1L,-1L,1206L,-1L,1977L,22207L,-5L,-3L,-1L,1206L,-1L,1977L,21202L,-5L,1L,-5L,1105L,1L,2045L,22102L,1L,-5L,1L,21201L,-4L,-1L,2L,21202L,-3L,2L,3L,21102L,1996L,1L,0L,1105L,1L,1954L,22102L,1L,1L,-5L,21101L,1L,0L,-2L,22207L,-5L,-3L,-1L,1206L,-1L,2015L,21102L,0L,1L,-2L,22202L,-3L,-2L,-3L,22107L,0L,-4L,-1L,1206L,-1L,2037L,21202L,-2L,1L,1L,21102L,1L,2037L,0L,106L,0L,1912L,21202L,-3L,-1L,-3L,22201L,-5L,-3L,-5L,109L,-6L,2105L,1L,0};
		long amoutOfDamageReported = 0L;
		IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
		int haltCode = HALT_CODE_NORMAL;				

        Queue<String> instructionQueue = generateInstructionFunction();
        printInstructionQueue(instructionQueue);
        
        Queue<Integer> instructionQueueASCIIQueue = converToASCII(instructionQueue);
        printInstructionASCIIQueue(instructionQueueASCIIQueue);
		
		while (haltCode != HALT_CODE_EXIT) {
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			haltCode = intCodeComputer.haltCode;
			
			if (haltCode == HALT_CODE_INPUT) {
				intCodeComputer.inputValues.add(instructionQueueASCIIQueue.remove());
			}
			
			if (haltCode == HALT_CODE_OUTPUT) {
				long code = intCodeComputer.output;
				System.out.print((char) code);
			}
		}
		amoutOfDamageReported = intCodeComputer.output;
		System.out.print((char) amoutOfDamageReported);

		
		return amoutOfDamageReported;
	}
	
	void printInstructionQueue(Queue<String> instructionQueue) {
		 System.out.println("-------");
        for (String instruction : instructionQueue) {
        	System.out.print(instruction);
        }
        System.out.println("-------");
	}
	
	void printInstructionASCIIQueue(Queue<Integer> instructionQueueASCIIQueue) {
		 System.out.println("-------");
       for (Integer instruction : instructionQueueASCIIQueue) {
       	System.out.print(instruction + ",");
       }
	}
	
	Queue<Integer> converToASCII(Queue<String> instructionQueue) {
		Queue<Integer> instructionQueueASCIIQueue = new LinkedList<>();
		for (String instruction : instructionQueue) {
			// Convert instruction string to ASCII code
			for (char instructionCharacter : instruction.toCharArray()) {
				instructionQueueASCIIQueue.add((int) instructionCharacter);
			}
		}
		return instructionQueueASCIIQueue;
	}
			
	Queue<String> generateInstructionFunction() {
		List<String> instructionFunction = new ArrayList<>();
		instructionFunction.add("NOT A T");
		instructionFunction.add("OR T J");
		instructionFunction.add("NOT B T");
		instructionFunction.add("OR T J");
		instructionFunction.add("NOT C T");
		instructionFunction.add("OR T J");
		instructionFunction.add("NOT D T");
		instructionFunction.add("NOT T T");
		instructionFunction.add("AND T J");
		instructionFunction.add("RUN");
	
		Queue<String> instructionQueue = new LinkedList<>();
		
		for (int instructionIndex = 0; instructionIndex < instructionFunction.size(); instructionIndex++) {
			instructionQueue.add(instructionFunction.get(instructionIndex));
			instructionQueue.add("\n");
		}
		
				
		return instructionQueue;
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
			while(!Day21SpringdroidAdventurePart1.HALT_CODES.contains(this.haltCode)) {
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
