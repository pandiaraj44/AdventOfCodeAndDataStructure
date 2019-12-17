import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/*
--- Part Two ---
Once you give them the coordinates, the Elves quickly deploy an Instant Monitoring Station to the location and discover the worst: there are simply too many asteroids.

The only solution is complete vaporization by giant laser.

Fortunately, in addition to an asteroid scanner, the new monitoring station also comes equipped with a giant rotating laser perfect for vaporizing asteroids. The laser starts by pointing up and always rotates clockwise, vaporizing any asteroid it hits.

If multiple asteroids are exactly in line with the station, the laser only has enough power to vaporize one of them before continuing its rotation. In other words, the same asteroids that can be detected can be vaporized, but if vaporizing one asteroid makes another one detectable, the newly-detected asteroid won't be vaporized until the laser has returned to the same position by rotating a full 360 degrees.

For example, consider the following map, where the asteroid with the new monitoring station (and laser) is marked X:

.#....#####...#..
##...##.#####..##
##...#...#.#####.
..#.....X...###..
..#.#.....#....##
The first nine asteroids to get vaporized, in order, would be:

.#....###24...#..
##...##.13#67..9#
##...#...5.8####.
..#.....X...###..
..#.#.....#....##
Note that some asteroids (the ones behind the asteroids marked 1, 5, and 7) won't have a chance to be vaporized until the next full rotation. The laser continues rotating; the next nine to be vaporized are:

.#....###.....#..
##...##...#.....#
##...#......1234.
..#.....X...5##..
..#.9.....8....76
The next nine to be vaporized are then:

.8....###.....#..
56...9#...#.....#
34...7...........
..2.....X....##..
..1..............
Finally, the laser completes its first full rotation (1 through 3), a second rotation (4 through 8), and vaporizes the last asteroid (9) partway through its third rotation:

......234.....6..
......1...5.....7
.................
........X....89..
.................
In the large example above (the one with the best monitoring station location at 11,13):

The 1st asteroid to be vaporized is at 11,12.
The 2nd asteroid to be vaporized is at 12,1.
The 3rd asteroid to be vaporized is at 12,2.
The 10th asteroid to be vaporized is at 12,8.
The 20th asteroid to be vaporized is at 16,0.
The 50th asteroid to be vaporized is at 16,9.
The 100th asteroid to be vaporized is at 10,16.
The 199th asteroid to be vaporized is at 9,6.
The 200th asteroid to be vaporized is at 8,2.
The 201st asteroid to be vaporized is at 10,9.
The 299th and final asteroid to be vaporized is at 11,1.
The Elves are placing bets on which will be the 200th asteroid to be vaporized. Win the bet by determining which asteroid that will be; what do you get if you multiply its X coordinate by 100 and then add its Y coordinate? (For example, 8,2 becomes 802.)

Your puzzle answer was 317.


*/
public class Day10AstroidPart2Clone {

	public static final String DIRECTION_RIGHT = "R";
	public static final String DIRECTION_LEFT = "L";
	public static final String DIRECTION_UP = "U";
	public static final String DIRECTION_DOWN = "D";
	
	public static char SYMBOL_ASTROID = '#';
	public static char SYMBOL_EMPTY = '.';
	
	static int mapHeight, width;
	
	static Map<Astroid, List<Astroid>> countMap = new HashMap<Astroid, List<Astroid>>();

	public static void main(String args[]) throws Exception {
		Day10AstroidPart2Clone day10AstroidPart1 = new Day10AstroidPart2Clone();

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
		Astroid monitorStation = null;
		List<Astroid> lineOfSights = null;
		for (Entry<Astroid, List<Astroid>>  entrySet : countMap.entrySet()) {
			int matchedCount = entrySet.getValue().size();
			
			if (matchedCount > maxCount) {
				maxCount = matchedCount;
				monitorStation = entrySet.getKey();
				lineOfSights = entrySet.getValue();
			}
		}
		System.out.println("Max maxCount--->" + 			maxCount);
		fire(monitorStation, lineOfSights);
	}
	
	void fire(Astroid monitorStation, List<Astroid> lineOfSights) {
		Astroid A1 = monitorStation;
		Astroid A2 = new Astroid(monitorStation.x, 10000);
		Astroid B1 = monitorStation; 
		List<Astroid> LOS = lineOfSights;
		
		/*StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < LOS.size();  i++) {
		Astroid point_B =  LOS.get(i);

			stringBuilder.append("{");
			stringBuilder.append("x:" + point_B.getY() + ",");
			stringBuilder.append("y:" + point_B.getX() + ",");
			stringBuilder.append("},");
		}
		stringBuilder.append("]");
		System.out.print(stringBuilder.toString());*/
		
		for (int i = 0; i < LOS.size(); i++) {
			Astroid point_B = LOS.get(i);
			Astroid B2 = new Astroid(point_B.y, point_B.x);
			double dAx = A2.x - A1.x;
			double dAy = A2.y - A1.y;
			double dBx = B2.x - B1.x;
			double dBy = B2.y - B1.y;
			double angle = Math.atan2(dAx * dBy - dAy * dBx, dAx * dBx + dAy * dBy);
			double degree_angle = angle * (180 / Math.PI);
			LOS.get(i).setAngle(degree_angle);
		}
//		  LOS.map((point_B, index) => {
//		    //console.log('--------point_B-----  ', point_B)
//		    LOS[index].los_points = [];
//		    let B2 = {x: point_B.x, y: point_B.y}
//		    let dAx = A2.x - A1.x;
//		    let dAy = A2.y - A1.y;
//		    let dBx = B2.x - B1.x;
//		    let dBy = B2.y - B1.y;
//		    let angle = Math.atan2(dAx * dBy - dAy * dBx, dAx * dBx + dAy * dBy);
//		    let degree_angle = angle * (180 / Math.PI);
//		    LOS[index]["angle"] = degree_angle; 
//		  })
		Collections.sort(LOS, new Comparator<Astroid>() {
			@Override
			public int compare(Astroid p1, Astroid p2) {
//				// TODO Auto-generated method stub
//				// return (int) (o1.angle - o2.angle);
//				if (p1.angle < p2.angle) return -1;
//		        if (p1.angle > p2.angle) return 1;
		        return Double.compare(p1.angle, p2.angle);
			}
		});
		
		for (int i = 0; i < LOS.size();  i++) {
			 Astroid point =  LOS.get(i);
			 System.out.println("point -- " + point.x + "--pointY -- " + point.y  + "--angle -- " + point.angle );
		}
		
		Astroid result = LOS.get(199);
		System.out.println("Result> X---" + 			result.x +"- Y--" +result.y + "--" + result.angle);

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
		private double angle;
		
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

		public double getAngle() {
			return angle;
		}

		public void setAngle(double angle) {
			this.angle = angle;
		}

		
		
		

	}
}
