import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

*/
public class Day10AstroidPart1 {

	public static final String DIRECTION_RIGHT = "R";
	public static final String DIRECTION_LEFT = "L";
	public static final String DIRECTION_UP = "U";
	public static final String DIRECTION_DOWN = "D";
	
	public static char SYMBOL_ASTROID = '#';
	public static char SYMBOL_EMPTY = '.';
	
	static int mapHeight, width, totalAstroidCount;
	
	static Map<String, Integer> countMap = new HashMap<String, Integer>();

	public static void main(String args[]) throws Exception {
		Day10AstroidPart1 day10AstroidPart1 = new Day10AstroidPart1();

		 String input = ".#..#\n" + 
		 		".....\n" + 
		 		"#####\n" + 
		 		"....#\n" + 
		 		"...##\n" + 
		 		"";
		 
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
		 
		 String[] inputArray = input.split("\n");
		 
		 
		 mapHeight = inputArray.length;
		 width = inputArray[0].length();
		 
		 totalAstroidCount = 0;
		 
		 Astroid[][] astroidArray = new Astroid[mapHeight][width];
		 
		for (int i = 0; i < mapHeight; i++) {
			String line = inputArray[i];

			for (int k = 0; k < line.length(); k++) {
				if (SYMBOL_ASTROID == line.charAt(k)) {
					Astroid astroid = new Astroid(i, k);
					astroidArray[i][k] = astroid;
					totalAstroidCount++;
				} else if (SYMBOL_EMPTY == line.charAt(k)) {
					Astroid astroid = new Astroid();
					astroidArray[i][k] = astroid;
				}
			}

		}
		
		/*day10AstroidPart1.markReachableElement(4, 3, astroidArray);
		
		int astroidCount = day10AstroidPart1.findReachableCount(astroidArray);

		System.out.println("How many other asteroids can be detected from that location?--"
				+ astroidCount);*/
		day10AstroidPart1.process(astroidArray);
	}
	
	void process(Astroid[][] astroidArray) {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < width; j++) {
				Astroid astroid = astroidArray[i][j];
				if (!astroid.isEmpty()) {
					markReachableElement(i, j, astroidArray);
					int astroidCount = findReachableCount(astroidArray);
					countMap.put("(" + i + "," + j + ")", astroidCount);
					clearReachedFalg(astroidArray);	
				}
				
			}

		}
		System.out.println("Count Map--->" + countMap);
	}
	
	void clearReachedFalg(Astroid[][] astroidArray) {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < width; j++) {
				Astroid astroid = astroidArray[i][j];
				astroid.setReachable(false);
			}

		}
	}
	
	int findReachableCount(Astroid[][] astroidArray) {
		int reachableCount = 0;
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < width; j++) {
				Astroid astroid = astroidArray[i][j];
				if (!astroid.isEmpty() && astroid.isReachable()) {
					reachableCount ++;
				}
			}

		}
		return reachableCount;
	}
	
	void markReachableElement(int x, int y, Astroid[][] astroidArray) {
		markRightElement(x, y, astroidArray);
		markLeftElement(x, y, astroidArray);
		markTopElement(x, y, astroidArray);
		markBottomElement(x, y, astroidArray);
		
		markRightDiagonalElement(x, y, astroidArray);
		markLeftDiagonalElement(x, y, astroidArray);
		markTopDiagonalElement(x, y, astroidArray);
		markBottomDiagonalElement(x, y, astroidArray);
		
		markRightColumn(x, y, astroidArray);
		markLeftColumn(x, y, astroidArray);
		
		markRightAdjancyColumn(x, y, astroidArray);
		markLeftAdjacnyColumn(x, y, astroidArray);
		markLeftDiagonalAdjacnyColumn(x, y, astroidArray);
	}
	
	void markRightColumn(int x, int y, Astroid[][] astroidArray) {
		int indexX = 0;
		int indexY = y + 1;
		while (indexX < mapHeight && indexY < width) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (!astroid.isEmpty()) {
				astroid.setReachable(true);
			}
			indexX++;
		}
	}
	
	void markRightAdjancyColumn(int x, int y, Astroid[][] astroidArray) {
		int indexX = x;
		int indexY = y + 1;
		while (indexX < mapHeight && indexY < width) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (!astroid.isEmpty()) {
				if (indexY != y) {
					if ( indexX+1 < mapHeight && indexY + 1 < width) {
						Astroid adjacentAstroid = astroidArray[indexX+1][indexY + 1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(false);
						}
					}
				} else {
					if ( indexX+1 < mapHeight && indexY + 1 < width) {
						Astroid adjacentAstroid = astroidArray[indexX+1][indexY + 1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
						}
					}
				}
			} else {
				if (indexY != y) {
					if ( indexX+1 < mapHeight && indexY + 1 < width) {
						Astroid adjacentAstroid = astroidArray[indexX+1][indexY + 1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
							markRightDiagonalElement(indexX+1, indexY + 1, astroidArray);
						}
					}
				} else {
					if (indexX+1 < mapHeight && indexY + 1 < width) {
						Astroid adjacentAstroid = astroidArray[indexX+1][indexY + 1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
							markRightDiagonalElement(indexX+1, indexY + 1, astroidArray);
						}
					}
				}
			}
			indexX++;
		}
	}
	
	void markLeftAdjacnyColumn(int x, int y, Astroid[][] astroidArray) {
		int indexX = x;
		int indexY = y - 1;
		while (indexX < mapHeight && indexY >= 0) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (!astroid.isEmpty()) {
				if (indexY != y) {
					if ( indexX +1 <mapHeight && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX +1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(false);
						}
					}
				} else {
					if ( indexX +1 <0 && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX +1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
						}
					}
				}
			} else {
				if (indexY != y) {
					if ( indexX +1 < mapHeight && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX +1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
							markLeftAdjacnyColumn(indexX + 1, indexY -1, astroidArray);
						}
					}
				} else {
					if ( indexX +1 < mapHeight && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX + 1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
						}
					}
				}
			}
			
			indexX++;
		}
	}
	
	void markLeftDiagonalAdjacnyColumn(int x, int y, Astroid[][] astroidArray) {
		int indexX = x;
		int indexY = y - 1;
		while (indexX >=0 && indexY >= 0) {
			Astroid astroid = astroidArray[indexX][indexY];			
			if (!astroid.isEmpty()) {
				if (indexX != x) {
					if ( indexX -1 >=0 && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX -1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(false);
						}
					}
				} else {
					if ( indexX -1 >=0 && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX -1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
						}
					}
				}
			} else {
				if (indexX != x) {
					if ( indexX -1 >=0 && indexY - 1 >= 0) {
						Astroid adjacentAstroid = astroidArray[indexX -1][indexY -1];
						if (!astroid.isEmpty()) {
							adjacentAstroid.setReachable(true);
							markLeftDiagonalAdjacnyColumn(indexX -1, indexY -1, astroidArray);
						}
					} else {
						if ( indexX -1 >=0 && indexY - 1 >= 0) {
							Astroid adjacentAstroid = astroidArray[indexX -1][indexY -1];
							if (!astroid.isEmpty()) {
								adjacentAstroid.setReachable(true);
								markLeftDiagonalAdjacnyColumn(indexX -1, indexY -1, astroidArray);
							}
						}
					}
				}
			}
			
			indexX--;
		}
	}
	
	void markLeftColumn(int x, int y, Astroid[][] astroidArray) {
		int indexX = 0;
		int indexY = y - 1;
		while (indexX < mapHeight && indexY >= 0) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (!astroid.isEmpty()) {
				astroid.setReachable(true);
			}
			indexX++;
		}
	}
	
	void markRightElement(int x, int y, Astroid[][] astroidArray) {
		int indexY = y + 1;
		boolean isMathced = false;
		while (indexY < width) {
			Astroid astroid = astroidArray[x][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexY++;
		}
	}
	
	
	
	public void markLeftElement(int x, int y, Astroid[][] astroidArray) {
		int indexY = y - 1;
		boolean isMathced = false;
		while (indexY >= 0) {
			Astroid astroid = astroidArray[x][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexY--;
		}
	}
	
	public void markTopElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x + 1;
		boolean isMathced = false;
		while (indexX < mapHeight) {
			Astroid astroid = astroidArray[indexX][y];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX++;
		}
	}
	
	public void markBottomElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x - 1;
		boolean isMathced = false;
		while (indexX >= 0) {
			Astroid astroid = astroidArray[indexX][y];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX--;
		}
	}
	
	public void markRightDiagonalElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x + 1;
		int indexY = y + 1;
		boolean isMathced = false;
		while (indexX < mapHeight && indexY < width) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX++;
			indexY++;
		}
	}
	
	public void markLeftDiagonalElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x - 1;
		int indexY = y - 1;
		boolean isMathced = false;
		while (indexX >= 0 && indexY >= 0) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX--;
			indexY--;
		}
	}
	
	public void markBottomDiagonalElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x - 1;
		int indexY = y + 1;
		boolean isMathced = false;
		while (indexX >=0 && indexY < width) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX--;
			indexY++;
		}
	}
	
	public void markTopDiagonalElement(int x, int y, Astroid[][] astroidArray) {
		int indexX = x + 1;
		int indexY = y - 1;
		boolean isMathced = false;
		while (indexX <mapHeight && indexY >= 0) {
			Astroid astroid = astroidArray[indexX][indexY];
			if (isMathced == false && !astroid.isEmpty()) {
				isMathced = true;
				astroid.setReachable(true);
			} else if (!astroid.isEmpty()){
				astroid.setReachable(false);
			}
			indexX++;
			indexY--;
		}
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
