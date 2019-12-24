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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;


/*--- Part Two ---
Packets sent to address 255 are handled by a device called a NAT (Not Always Transmitting). The NAT is responsible for managing power consumption of the network by blocking certain packets and watching for idle periods in the computers.

If a packet would be sent to address 255, the NAT receives it instead. The NAT remembers only the last packet it receives; that is, the data in each packet it receives overwrites the NAT's packet memory with the new packet's X and Y values.

The NAT also monitors all computers on the network. If all computers have empty incoming packet queues and are continuously trying to receive packets without sending packets, the network is considered idle.

Once the network is idle, the NAT sends only the last packet it received to address 0; this will cause the computers on the network to resume activity. In this way, the NAT can throttle power consumption of the network when the ship needs power in other areas.

Monitor packets released to the computer at address 0 by the NAT. What is the first Y value delivered by the NAT to the computer at address 0 twice in a row?

Your puzzle answer was 19019.
*/
public class Day23CategorySixPart2 implements AOC<Long>{

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
	
	
	public static List<Integer> HALT_CODES = Arrays.asList(HALT_CODE_INPUT, HALT_CODE_OUTPUT, HALT_CODE_EXIT);
	List<IntCodeComputer> intCodeComputers = new ArrayList<>();

	public static void main(String args[]) throws Exception {
		Day23CategorySixPart2 day23CategorySixPart2 = new Day23CategorySixPart2();
		Long twiceYOfNAT = day23CategorySixPart2.run();
    	System.out.println("What is the Y value of the first packet sent to address 255? " + twiceYOfNAT);
	}
	
	@Override
	public Long run() {
		long intCodes[] = {3L,62L,1001L,62L,11L,10L,109L,2267L,105L,1L,0L,1590L,1977L,876L,1120L,1188L,2230L,641L,1155L,1559L,936L,1847L,2189L,2117L,1029L,674L,1917L,1363L,610L,1695L,1880L,2049L,1814L,1493L,1258L,705L,810L,967L,905L,845L,1662L,2014L,1394L,1089L,1631L,2150L,1427L,1332L,1524L,1291L,2080L,1460L,1730L,1948L,571L,1221L,742L,1060L,777L,1761L,998L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,3L,64L,1008L,64L,-1L,62L,1006L,62L,88L,1006L,61L,170L,1106L,0L,73L,3L,65L,21001L,64L,0L,1L,21001L,66L,0L,2L,21101L,0L,105L,0L,1105L,1L,436L,1201L,1L,-1L,64L,1007L,64L,0L,62L,1005L,62L,73L,7L,64L,67L,62L,1006L,62L,73L,1002L,64L,2L,133L,1L,133L,68L,133L,102L,1L,0L,62L,1001L,133L,1L,140L,8L,0L,65L,63L,2L,63L,62L,62L,1005L,62L,73L,1002L,64L,2L,161L,1L,161L,68L,161L,1102L,1L,1L,0L,1001L,161L,1L,169L,102L,1L,65L,0L,1102L,1L,1L,61L,1102L,1L,0L,63L,7L,63L,67L,62L,1006L,62L,203L,1002L,63L,2L,194L,1L,68L,194L,194L,1006L,0L,73L,1001L,63L,1L,63L,1105L,1L,178L,21102L,1L,210L,0L,106L,0L,69L,2102L,1L,1L,70L,1101L,0L,0L,63L,7L,63L,71L,62L,1006L,62L,250L,1002L,63L,2L,234L,1L,72L,234L,234L,4L,0L,101L,1L,234L,240L,4L,0L,4L,70L,1001L,63L,1L,63L,1105L,1L,218L,1105L,1L,73L,109L,4L,21102L,0L,1L,-3L,21102L,0L,1L,-2L,20207L,-2L,67L,-1L,1206L,-1L,293L,1202L,-2L,2L,283L,101L,1L,283L,283L,1L,68L,283L,283L,22001L,0L,-3L,-3L,21201L,-2L,1L,-2L,1106L,0L,263L,21202L,-3L,1L,-3L,109L,-4L,2105L,1L,0L,109L,4L,21101L,1L,0L,-3L,21102L,0L,1L,-2L,20207L,-2L,67L,-1L,1206L,-1L,342L,1202L,-2L,2L,332L,101L,1L,332L,332L,1L,68L,332L,332L,22002L,0L,-3L,-3L,21201L,-2L,1L,-2L,1105L,1L,312L,21201L,-3L,0L,-3L,109L,-4L,2105L,1L,0L,109L,1L,101L,1L,68L,359L,20102L,1L,0L,1L,101L,3L,68L,366L,21002L,0L,1L,2L,21102L,1L,376L,0L,1105L,1L,436L,21202L,1L,1L,0L,109L,-1L,2106L,0L,0L,1L,2L,4L,8L,16L,32L,64L,128L,256L,512L,1024L,2048L,4096L,8192L,16384L,32768L,65536L,131072L,262144L,524288L,1048576L,2097152L,4194304L,8388608L,16777216L,33554432L,67108864L,134217728L,268435456L,536870912L,1073741824L,2147483648L,4294967296L,8589934592L,17179869184L,34359738368L,68719476736L,137438953472L,274877906944L,549755813888L,1099511627776L,2199023255552L,4398046511104L,8796093022208L,17592186044416L,35184372088832L,70368744177664L,140737488355328L,281474976710656L,562949953421312L,1125899906842624L,109L,8L,21202L,-6L,10L,-5L,22207L,-7L,-5L,-5L,1205L,-5L,521L,21101L,0L,0L,-4L,21101L,0L,0L,-3L,21102L,51L,1L,-2L,21201L,-2L,-1L,-2L,1201L,-2L,385L,470L,21001L,0L,0L,-1L,21202L,-3L,2L,-3L,22207L,-7L,-1L,-5L,1205L,-5L,496L,21201L,-3L,1L,-3L,22102L,-1L,-1L,-5L,22201L,-7L,-5L,-7L,22207L,-3L,-6L,-5L,1205L,-5L,515L,22102L,-1L,-6L,-5L,22201L,-3L,-5L,-3L,22201L,-1L,-4L,-4L,1205L,-2L,461L,1106L,0L,547L,21101L,-1L,0L,-4L,21202L,-6L,-1L,-6L,21207L,-7L,0L,-5L,1205L,-5L,547L,22201L,-7L,-6L,-7L,21201L,-4L,1L,-4L,1105L,1L,529L,21201L,-4L,0L,-7L,109L,-8L,2106L,0L,0L,109L,1L,101L,1L,68L,564L,20101L,0L,0L,0L,109L,-1L,2106L,0L,0L,1101L,0L,96059L,66L,1102L,1L,5L,67L,1101L,0L,598L,68L,1101L,0L,302L,69L,1101L,0L,1L,71L,1101L,608L,0L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,29L,96377L,1102L,1L,25589L,66L,1102L,1L,1L,67L,1101L,637L,0L,68L,1102L,556L,1L,69L,1102L,1L,1L,71L,1102L,639L,1L,72L,1105L,1L,73L,1L,-11L,11L,381354L,1101L,0L,38861L,66L,1102L,1L,2L,67L,1102L,1L,668L,68L,1101L,302L,0L,69L,1102L,1L,1L,71L,1102L,672L,1L,72L,1105L,1L,73L,0L,0L,0L,0L,21L,40063L,1102L,10091L,1L,66L,1102L,1L,1L,67L,1102L,1L,701L,68L,1101L,556L,0L,69L,1102L,1L,1L,71L,1102L,703L,1L,72L,1105L,1L,73L,1L,41L,5L,14897L,1101L,69697L,0L,66L,1101L,4L,0L,67L,1101L,732L,0L,68L,1102L,253L,1L,69L,1101L,1L,0L,71L,1101L,0L,740L,72L,1105L,1L,73L,0L,0L,0L,0L,0L,0L,0L,0L,7L,24281L,1102L,1L,72893L,66L,1101L,0L,1L,67L,1102L,1L,769L,68L,1102L,556L,1L,69L,1101L,0L,3L,71L,1101L,771L,0L,72L,1105L,1L,73L,1L,13L,44L,248277L,11L,190677L,19L,15556L,1101L,66851L,0L,66L,1101L,2L,0L,67L,1101L,804L,0L,68L,1102L,302L,1L,69L,1101L,0L,1L,71L,1102L,1L,808L,72L,1106L,0L,73L,0L,0L,0L,0L,34L,409468L,1102L,46681L,1L,66L,1101L,0L,1L,67L,1102L,837L,1L,68L,1101L,0L,556L,69L,1102L,3L,1L,71L,1102L,1L,839L,72L,1105L,1L,73L,1L,5L,39L,57487L,39L,114974L,38L,95138L,1101L,7883L,0L,66L,1102L,1L,1L,67L,1101L,872L,0L,68L,1101L,0L,556L,69L,1102L,1L,1L,71L,1102L,874L,1L,72L,1105L,1L,73L,1L,677L,43L,192118L,1102L,1L,84089L,66L,1101L,0L,1L,67L,1101L,0L,903L,68L,1102L,1L,556L,69L,1102L,0L,1L,71L,1102L,1L,905L,72L,1106L,0L,73L,1L,1634L,1101L,20297L,0L,66L,1102L,1L,1L,67L,1101L,0L,932L,68L,1102L,1L,556L,69L,1102L,1L,1L,71L,1101L,934L,0L,72L,1106L,0L,73L,1L,967L,3L,23362L,1102L,1L,95597L,66L,1102L,1L,1L,67L,1102L,1L,963L,68L,1102L,556L,1L,69L,1102L,1L,1L,71L,1102L,1L,965L,72L,1106L,0L,73L,1L,-231L,19L,11667L,1101L,45181L,0L,66L,1102L,1L,1L,67L,1101L,994L,0L,68L,1102L,1L,556L,69L,1101L,0L,1L,71L,1101L,996L,0L,72L,1105L,1L,73L,1L,125L,39L,172461L,1102L,19087L,1L,66L,1102L,1L,1L,67L,1101L,0L,1025L,68L,1102L,556L,1L,69L,1101L,1L,0L,71L,1101L,1027L,0L,72L,1106L,0L,73L,1L,51L,30L,103991L,1102L,56093L,1L,66L,1102L,1L,1L,67L,1102L,1056L,1L,68L,1101L,0L,556L,69L,1101L,1L,0L,71L,1102L,1L,1058L,72L,1105L,1L,73L,1L,10181L,12L,18061L,1102L,69857L,1L,66L,1102L,1L,1L,67L,1102L,1L,1087L,68L,1101L,0L,556L,69L,1101L,0L,0L,71L,1101L,1089L,0L,72L,1105L,1L,73L,1L,1387L,1101L,53881L,0L,66L,1101L,1L,0L,67L,1101L,1116L,0L,68L,1102L,556L,1L,69L,1102L,1L,1L,71L,1102L,1L,1118L,72L,1106L,0L,73L,1L,37L,43L,288177L,1102L,1L,11681L,66L,1101L,3L,0L,67L,1102L,1147L,1L,68L,1102L,1L,302L,69L,1101L,0L,1L,71L,1102L,1L,1153L,72L,1105L,1L,73L,0L,0L,0L,0L,0L,0L,34L,307101L,1102L,1L,24281L,66L,1102L,1L,2L,67L,1102L,1L,1182L,68L,1102L,351L,1L,69L,1101L,1L,0L,71L,1101L,1186L,0L,72L,1105L,1L,73L,0L,0L,0L,0L,255L,65183L,1102L,1L,1093L,66L,1101L,2L,0L,67L,1102L,1L,1215L,68L,1101L,302L,0L,69L,1102L,1L,1L,71L,1101L,0L,1219L,72L,1105L,1L,73L,0L,0L,0L,0L,31L,24671L,1102L,82759L,1L,66L,1102L,4L,1L,67L,1102L,1L,1248L,68L,1101L,0L,302L,69L,1101L,1L,0L,71L,1102L,1L,1256L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,34L,204734L,1101L,100279L,0L,66L,1102L,1L,1L,67L,1101L,1285L,0L,68L,1102L,556L,1L,69L,1102L,2L,1L,71L,1101L,0L,1287L,72L,1105L,1L,73L,1L,2L,38L,190276L,38L,237845L,1102L,1L,47569L,66L,1101L,0L,6L,67L,1101L,0L,1318L,68L,1102L,1L,302L,69L,1102L,1L,1L,71L,1102L,1330L,1L,72L,1105L,1L,73L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,7L,48562L,1102L,13163L,1L,66L,1101L,0L,1L,67L,1102L,1359L,1L,68L,1101L,556L,0L,69L,1102L,1L,1L,71L,1102L,1361L,1L,72L,1106L,0L,73L,1L,293L,5L,44691L,1102L,1L,101287L,66L,1102L,1L,1L,67L,1102L,1L,1390L,68L,1101L,0L,556L,69L,1101L,0L,1L,71L,1102L,1392L,1L,72L,1106L,0L,73L,1L,97L,11L,317795L,1101L,24671L,0L,66L,1101L,0L,2L,67L,1102L,1L,1421L,68L,1101L,0L,302L,69L,1102L,1L,1L,71L,1102L,1425L,1L,72L,1105L,1L,73L,0L,0L,0L,0L,47L,133702L,1102L,77687L,1L,66L,1101L,2L,0L,67L,1102L,1L,1454L,68L,1102L,1L,302L,69L,1102L,1L,1L,71L,1102L,1L,1458L,72L,1106L,0L,73L,0L,0L,0L,0L,43L,384236L,1102L,78367L,1L,66L,1102L,1L,1L,67L,1101L,1487L,0L,68L,1101L,0L,556L,69L,1101L,2L,0L,71L,1101L,0L,1489L,72L,1106L,0L,73L,1L,10L,39L,229948L,38L,47569L,1102L,1L,3061L,66L,1101L,1L,0L,67L,1101L,1520L,0L,68L,1102L,556L,1L,69L,1101L,0L,1L,71L,1101L,1522L,0L,72L,1105L,1L,73L,1L,523L,30L,207982L,1101L,33181L,0L,66L,1102L,3L,1L,67L,1101L,1551L,0L,68L,1101L,0L,302L,69L,1101L,0L,1L,71L,1101L,1557L,0L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,24L,278788L,1101L,102181L,0L,66L,1101L,1L,0L,67L,1102L,1586L,1L,68L,1101L,0L,556L,69L,1101L,1L,0L,71L,1102L,1588L,1L,72L,1106L,0L,73L,1L,378L,44L,165518L,1101L,65183L,0L,66L,1101L,0L,1L,67L,1102L,1617L,1L,68L,1102L,556L,1L,69L,1101L,0L,6L,71L,1101L,0L,1619L,72L,1106L,0L,73L,1L,25255L,29L,192754L,18L,141326L,18L,211989L,37L,33181L,37L,66362L,37L,99543L,1101L,88799L,0L,66L,1102L,1L,1L,67L,1102L,1L,1658L,68L,1101L,556L,0L,69L,1101L,0L,1L,71L,1101L,1660L,0L,72L,1106L,0L,73L,1L,160L,38L,285414L,1102L,96377L,1L,66L,1102L,1L,2L,67L,1101L,1689L,0L,68L,1102L,1L,302L,69L,1101L,0L,1L,71L,1102L,1L,1693L,72L,1106L,0L,73L,0L,0L,0L,0L,24L,139394L,1101L,70663L,0L,66L,1102L,3L,1L,67L,1101L,0L,1722L,68L,1101L,0L,302L,69L,1102L,1L,1L,71L,1102L,1728L,1L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,24L,209091L,1102L,104173L,1L,66L,1101L,1L,0L,67L,1101L,0L,1757L,68L,1101L,556L,0L,69L,1102L,1L,1L,71L,1101L,1759L,0L,72L,1106L,0L,73L,1L,31L,44L,82759L,1101L,92951L,0L,66L,1102L,1L,1L,67L,1101L,0L,1788L,68L,1101L,556L,0L,69L,1101L,0L,12L,71L,1102L,1L,1790L,72L,1106L,0L,73L,1L,1L,44L,331036L,30L,311973L,4L,2186L,31L,49342L,47L,66851L,3L,11681L,12L,36122L,6L,77722L,21L,80126L,35L,155374L,43L,480295L,19L,7778L,1102L,40063L,1L,66L,1102L,2L,1L,67L,1101L,0L,1841L,68L,1101L,302L,0L,69L,1102L,1L,1L,71L,1102L,1L,1845L,72L,1106L,0L,73L,0L,0L,0L,0L,35L,77687L,1102L,1L,74377L,66L,1101L,1L,0L,67L,1101L,0L,1874L,68L,1102L,1L,556L,69L,1101L,2L,0L,71L,1101L,1876L,0L,72L,1105L,1L,73L,1L,19L,11L,127118L,19L,3889L,1102L,3889L,1L,66L,1102L,4L,1L,67L,1102L,1L,1907L,68L,1102L,302L,1L,69L,1102L,1L,1L,71L,1101L,1915L,0L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,18L,70663L,1102L,1L,8353L,66L,1102L,1L,1L,67L,1102L,1944L,1L,68L,1101L,0L,556L,69L,1101L,0L,1L,71L,1101L,0L,1946L,72L,1106L,0L,73L,1L,-52L,3L,35043L,1101L,0L,31477L,66L,1101L,0L,1L,67L,1102L,1L,1975L,68L,1101L,0L,556L,69L,1101L,0L,0L,71L,1102L,1977L,1L,72L,1106L,0L,73L,1L,1229L,1102L,32159L,1L,66L,1102L,1L,1L,67L,1101L,2004L,0L,68L,1102L,556L,1L,69L,1101L,4L,0L,71L,1102L,1L,2006L,72L,1105L,1L,73L,1L,7L,5L,29794L,5L,59588L,11L,63559L,43L,96059L,1101L,0L,103991L,66L,1102L,1L,3L,67L,1101L,2041L,0L,68L,1102L,302L,1L,69L,1101L,0L,1L,71L,1102L,2047L,1L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,34L,511835L,1101L,0L,78157L,66L,1101L,1L,0L,67L,1102L,2076L,1L,68L,1102L,556L,1L,69L,1101L,1L,0L,71L,1102L,2078L,1L,72L,1106L,0L,73L,1L,-81047L,4L,1093L,1102L,57487L,1L,66L,1101L,0L,4L,67L,1101L,0L,2107L,68L,1102L,1L,302L,69L,1101L,0L,1L,71L,1102L,2115L,1L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,38L,142707L,1102L,18061L,1L,66L,1101L,0L,2L,67L,1102L,2144L,1L,68L,1102L,302L,1L,69L,1101L,1L,0L,71L,1102L,1L,2148L,72L,1105L,1L,73L,0L,0L,0L,0L,6L,38861L,1102L,102367L,1L,66L,1102L,5L,1L,67L,1101L,0L,2177L,68L,1102L,253L,1L,69L,1101L,1L,0L,71L,1102L,1L,2187L,72L,1105L,1L,73L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,11L,254236L,1102L,1L,63559L,66L,1101L,6L,0L,67L,1102L,1L,2216L,68L,1102L,1L,302L,69L,1102L,1L,1L,71L,1102L,2228L,1L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,0L,24L,69697L,1102L,14897L,1L,66L,1101L,0L,4L,67L,1101L,2257L,0L,68L,1101L,0L,302L,69L,1101L,0L,1L,71L,1102L,2265L,1L,72L,1106L,0L,73L,0L,0L,0L,0L,0L,0L,0L,0L,34L,102367L};
		long twiceYOfNAT = 0L;
		Queue<Integer> netWorkAddresses = generateNetworkAddress();
		for (Integer networkAddress : netWorkAddresses) {
			IntCodeComputer intCodeComputer = new IntCodeComputer(intCodes.clone());
			intCodeComputer.haltCode = HALT_CODE_NORMAL;
			intCodeComputer.extractAndExecuteOptCodes();
			if (intCodeComputer.haltCode == HALT_CODE_INPUT) {
				intCodeComputer.inputValues.add((long)networkAddress);
			}
			intCodeComputers.add(intCodeComputer);
		}
		twiceYOfNAT = processIntecodeComputers();
		return twiceYOfNAT;
	}	
	
	@SuppressWarnings("null")
	Long processIntecodeComputers() {
		Long xNAT = null;
		Long yNAT = null;
		Long lastYOfNAT = null;
		while (true) {
			for (IntCodeComputer intCodeComputer : intCodeComputers) {
				while (!intCodeComputer.outputList.isEmpty()) {
					long address = intCodeComputer.outputList.get(0);
					long xValue = intCodeComputer.outputList.get(1);
					long yValue = intCodeComputer.outputList.get(2);
					intCodeComputer.outputList.remove(0);
					intCodeComputer.outputList.remove(0);
					intCodeComputer.outputList.remove(0);
					if (address < 50 && address >=0) {
						IntCodeComputer destinationIntCodeComputer = intCodeComputers.get((int)address);
						destinationIntCodeComputer.inputValues.add( xValue);
						destinationIntCodeComputer.inputValues.add( yValue);
					} else if (address == 255) {
						System.out.println("address ---X-->" + xValue + "Y----" + yValue);
						// To set NAT inputs
						xNAT = xValue;
						yNAT = yValue;
			        }
				}
			}
			for (IntCodeComputer intCodeComputer : intCodeComputers) {
				if (intCodeComputer.inputValues.isEmpty()) {
					intCodeComputer.inputValues.add(-1L);
				}
				intCodeComputer.haltCode = HALT_CODE_NORMAL;
				intCodeComputer.extractAndExecuteOptCodes();
			}
			// To handle NAT
			// To check if all computers are in idle
			boolean isIdle = intCodeComputers.stream().filter(
					intCodeComputer -> intCodeComputer.inputValues.isEmpty() 
					&& intCodeComputer.outputList.isEmpty()).count() == intCodeComputers.size();
			if (isIdle && xNAT !=null && yNAT !=null) {
				IntCodeComputer zerothIntcodeComputer = intCodeComputers.get(0);
				zerothIntcodeComputer.inputValues.add( xNAT);
				zerothIntcodeComputer.inputValues.add( yNAT);
				System.out.println("---yNAT-->" + yNAT);
				if (yNAT == lastYOfNAT) {
					return lastYOfNAT;
				}
				lastYOfNAT = yNAT;
			}
			
		}
	}
	
	Queue<Integer> generateNetworkAddress() {
		Queue<Integer> netWorkAddress = new LinkedList<>();
		for (int i = 0; i< 50 ; i++) {
			netWorkAddress.add(i);
		}
		return netWorkAddress;
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
			while(!Day23CategorySixPart2.HALT_CODES.contains(this.haltCode)) {
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
//				if (outputList.size() == 3) {
//					 this.haltCode = HALT_CODE_OUTPUT;
//					 return;
//				}
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
