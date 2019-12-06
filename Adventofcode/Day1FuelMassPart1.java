/* --- Day 1: The Tyranny of the Rocket Equation ---
Santa has become stranded at the edge of the Solar System while delivering presents to other planets! To accurately calculate his position in space, safely align his warp drive, and return to Earth in time to save Christmas, he needs you to bring him measurements from fifty stars.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

The Elves quickly load you into a spacecraft and prepare to launch.

At the first Go / No Go poll, every Elf is Go until the Fuel Counter-Upper. They haven't determined the amount of fuel required yet.

Fuel required to launch a given module is based on its mass. Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.

For example:

For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
For a mass of 1969, the fuel required is 654.
For a mass of 100756, the fuel required is 33583.
The Fuel Counter-Upper needs to know the total fuel requirement. To find it, individually calculate the fuel needed for the mass of each module (your puzzle input), then add together all the fuel values.

What is the sum of the fuel requirements for all of the modules on your spacecraft?

Your puzzle answer was 3182375.

The first half of this puzzle is complete! It provides one gold star: */
public class Day1FuelMassPart1 {
	public static void main(String args[]) throws Exception {
		Day1FuelMassPart1 day1FuelMassPart1 = new Day1FuelMassPart1();
		long massArray[] = {51360,95527,72603,128601,68444,138867,67294,134343,62785,53088,134635,137884,97654,103704,138879,87561,83922,68414,84876,105143,76599,98924,57080,63590,50126,111872,55754,64410,78488,56557,105446,127182,59451,87249,61652,131698,148820,95742,68223,121744,65678,99745,64089,75610,106085,100364,116959,122862,56580,109631,82895,79666,133474,50579,83473,140028,125999,68225,131345,90797,84914,81915,65369,71230,50379,106385,118503,119640,138540,70678,95881,100282,123060,147368,93030,82553,131271,147675,111126,115183,82956,145698,99261,52768,99207,123551,64738,117275,98136,111592,78576,118613,130351,68567,72356,85608,129414,66521,76924,130449};
		long totalFuel = 0;
		for (long mass : massArray) {
			long fuel = day1FuelMassPart1.calculateFuelByMass(mass);
			System.out.println("Fuel By Module---" + fuel);
			totalFuel += fuel;
		}
		System.out.println("Total Fuel---" + totalFuel);
	}
	
	public long calculateFuelByMass(long mass) {
		long fuel = (mass / 3) - 2;
		return fuel;
	}
}
