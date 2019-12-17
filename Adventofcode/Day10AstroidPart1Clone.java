import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
--- Day 10: Monitoring Station ---
You fly into the asteroid belt and reach the Ceres monitoring station. The Elves here have an emergency: they're having trouble tracking all of the asteroids and can't be sure they're safe.

The Elves would like to build a new monitoring station in a nearby area of space; they hand you a map of all of the asteroids in that region (your puzzle input).

The map indicates whether each position is empty (.) or contains an asteroid (#). The asteroids are much smaller than they appear on the map, and every asteroid is exactly in the center of its marked position. The asteroids can be described with X,Y coordinates where X is the distance from the left edge and Y is the distance from the top edge (so the top-left corner is 0,0 and the position immediately to its right is 1,0).

Your job is to figure out which asteroid would be the best place to build a new monitoring station. A monitoring station can detect any asteroid to which it has direct line of sight - that is, there cannot be another asteroid exactly between them. This line of sight can be at any angle, not just lines aligned to the grid or diagonally. The best location is the asteroid that can detect the largest number of other asteroids.

For example, consider the following map:

.#..#
.....
#####
....#
...##
The best location for a new monitoring station on this map is the highlighted asteroid at 3,4 because it can detect 8 asteroids, more than any other location. (The only asteroid it cannot detect is the one at 1,0; its view of this asteroid is blocked by the asteroid at 2,2.) All other asteroids are worse locations; they can detect 7 or fewer other asteroids. Here is the number of other asteroids a monitoring station on each asteroid could detect:

.7..7
.....
67775
....7
...87
Here is an asteroid (#) and some examples of the ways its line of sight might be blocked. If there were another asteroid at the location of a capital letter, the locations marked with the corresponding lowercase letter would be blocked and could not be detected:

#.........
...A......
...B..a...
.EDCG....a
..F.c.b...
.....c....
..efd.c.gb
.......c..
....f...c.
...e..d..c
Here are some larger examples:

Best is 5,8 with 33 other asteroids detected:

......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####
Best is 1,2 with 35 other asteroids detected:

#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.
Best is 6,3 with 41 other asteroids detected:

.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..
Best is 11,13 with 210 other asteroids detected:

.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##
Find the best location for a new monitoring station. How many other asteroids can be detected from that location?
Your puzzle answer was 292.


*/
public class Day10AstroidPart1Clone {

	public static final String DIRECTION_RIGHT = "R";
	public static final String DIRECTION_LEFT = "L";
	public static final String DIRECTION_UP = "U";
	public static final String DIRECTION_DOWN = "D";
	
	public static char SYMBOL_ASTROID = '#';
	public static char SYMBOL_EMPTY = '.';
	
	static int mapHeight, width;
	
	static Map<Astroid, List<Astroid>> countMap = new HashMap<Astroid, List<Astroid>>();

	public static void main(String args[]) throws Exception {
		Day10AstroidPart1Clone day10AstroidPart1 = new Day10AstroidPart1Clone();

//		 String input = ".#..#\n" + 
//		 		".....\n" + 
//		 		"#####\n" + 
//		 		"....#\n" + 
//		 		"...##\n" + 
//		 		"";
		 
//		 String input = "#.#...#.#.\n" + 
//		 		".###....#.\n" + 
//		 		".#....#...\n" + 
//		 		"##.#.#.#.#\n" + 
//		 		"....#.#.#.\n" + 
//		 		".##..###.#\n" + 
//		 		"..#...##..\n" + 
//		 		"..##....##\n" + 
//		 		"......#...\n" + 
//		 		".####.###.";
		
		/*String input = ".#..##.###...#######\n" + 
				"##.############..##.\n" + 
				".#.######.########.#\n" + 
				".###.#######.####.#.\n" + 
				"#####.##.#.##.###.##\n" + 
				"..#####..#.#########\n" + 
				"####################\n" + 
				"#.####....###.#.#.##\n" + 
				"##.#################\n" + 
				"#####.##.###..####..\n" + 
				"..######..##.#######\n" + 
				"####.##.####...##..#\n" + 
				".#####..#.######.###\n" + 
				"##...#.##########...\n" + 
				"#.##########.#######\n" + 
				".####.#.###.###.#.##\n" + 
				"....##.##.###..#####\n" + 
				".#.#.###########.###\n" + 
				"#.#.#.#####.####.###\n" + 
				"###.##.####.##.#..##";*/
		
		String input = "#.#....#.#......#.....#......####.\n" + 
				"#....#....##...#..#..##....#.##..#\n" + 
				"#.#..#....#..#....##...###......##\n" + 
				"...........##..##..##.####.#......\n" + 
				"...##..##....##.#.....#.##....#..#\n" + 
				"..##.....#..#.......#.#.........##\n" + 
				"...###..##.###.#..................\n" + 
				".##...###.#.#.......#.#...##..#.#.\n" + 
				"...#...##....#....##.#.....#...#.#\n" + 
				"..##........#.#...#..#...##...##..\n" + 
				"..#.##.......#..#......#.....##..#\n" + 
				"....###..#..#...###...#.###...#.##\n" + 
				"..#........#....#.....##.....#.#.#\n" + 
				"...#....#.....#..#...###........#.\n" + 
				".##...#........#.#...#...##.......\n" + 
				".#....#.#.#.#.....#...........#...\n" + 
				".......###.##...#..#.#....#..##..#\n" + 
				"#..#..###.#.......##....##.#..#...\n" + 
				"..##...#.#.#........##..#..#.#..#.\n" + 
				".#.##..#.......#.#.#.........##.##\n" + 
				"...#.#.....#.#....###.#.........#.\n" + 
				".#..#.##...#......#......#..##....\n" + 
				".##....#.#......##...#....#.##..#.\n" + 
				"#..#..#..#...........#......##...#\n" + 
				"#....##...#......#.###.#..#.#...#.\n" + 
				"#......#.#.#.#....###..##.##...##.\n" + 
				"......#.......#.#.#.#...#...##....\n" + 
				"....##..#.....#.......#....#...#..\n" + 
				".#........#....#...#.#..#....#....\n" + 
				".#.##.##..##.#.#####..........##..\n" + 
				"..####...##.#.....##.............#\n" + 
				"....##......#.#..#....###....##...\n" + 
				"......#..#.#####.#................\n" + 
				".#....#.#..#.###....##.......##.#.";
		 
		 String[] inputArray = input.split("\n");
		 
		 
		 mapHeight = inputArray.length;
		 width = inputArray[0].length();
		 

		 
		 // Astroid[][] astroidArray = new Astroid[mapHeight][width];
		 
		 List<Astroid> astroidList = new ArrayList<>();
		 
		for (int i = 0; i < mapHeight; i++) {
			String line = inputArray[i];
			for (int j = 0; j < line.length(); j++) {
				if (SYMBOL_ASTROID == line.charAt(j)) {
					Astroid astroid = new Astroid(i, j);
					astroidList.add(astroid);
				}
			}

		}
		
		/*day10AstroidPart1.markReachableElement(4, 3, astroidArray);
		
		int astroidCount = day10AstroidPart1.findReachableCount(astroidArray);

		System.out.println("How many other asteroids can be detected from that location?--"
				+ astroidCount);*/
		day10AstroidPart1.process(astroidList);
	}
	
	void process( List<Astroid> astroidList) {
		for (int i = 0; i < astroidList.size(); i++) {
			Astroid point1 = astroidList.get(i);
			for (int j = 0; j < astroidList.size(); j++) {
				if (i != j) {
					Astroid point2 = astroidList.get(j);
					boolean isContains = false;
					for (int k = 0; k < astroidList.size(); k++) {
						if (k !=i && k!=j) {
							Astroid point3 = astroidList.get(k);
							int a = point2.y - point1.y;
							int b = point1.x - point2.x;
							int c = a * (point1.x) + b * (point1.y);  // Line equation
							
							boolean xRange = isInXRange(point1, point2, point3);
							boolean yRange = isInYRange(point1, point2, point3);
							
							boolean isPointInLine = (c == ((a * (point3.x)) + (b * (point3.y))));
							
							
							if (isPointInLine && xRange && yRange) {
								isContains = true;
								break;
							}
						}
						
					}
					if (!isContains) {
						if (countMap.containsKey(point1)) {
							countMap.get(point1).add(point2);
						} else {
							List<Astroid> lineOfSights = new ArrayList<>();
							lineOfSights.add(point2);
							countMap.put(point1, lineOfSights);
						}
					}
										
				}
				
				
			}

		}
		int maxCount = 0;
		for (Entry<Astroid, List<Astroid>>  entrySet : countMap.entrySet()) {
			int matchedCount = entrySet.getValue().size();
			
			if (matchedCount > maxCount) {
				maxCount = matchedCount;
			}
		}
		System.out.println("Max maxCount--->" + 			maxCount);

	}
	
	boolean isInXRange(Astroid p1, Astroid p2, Astroid pointToCheck) {
		return p1.x< p2.x ? isInRange(p1.x, p2.x, pointToCheck.x) : isInRange(p2.x, p1.x, pointToCheck.x);
	}
	
	boolean isInYRange(Astroid p1, Astroid p2, Astroid pointToCheck) {
		return p1.y< p2.y ? isInRange(p1.y, p2.y, pointToCheck.y) : isInRange(p2.y, p1.y, pointToCheck.y);
	}
	
	boolean isInRange(int p1, int p2, int pointToCheck) {
		return p1 <= pointToCheck && p2 >= pointToCheck;
	}

	
	public static class Astroid {
		boolean isEmpty = true;
		private int x;
		private int y;
		private Boolean reachable;
		
		Astroid() {

		}

		Astroid(final int x, final int y) {
			this.x = x;
			this.y = y;
			isEmpty = false;
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

		
		boolean isEmpty() {
			return isEmpty;
		}

		public boolean isReachable() {
			return (null == reachable || reachable == false) ? false : true;
		}

		public void setReachable(boolean reachable) {
			if (this.reachable == null) {
				this.reachable = reachable;
			}
		}

		
		
		

	}
}
