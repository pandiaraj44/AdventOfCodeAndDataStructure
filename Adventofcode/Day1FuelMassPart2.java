/*--- Part Two ---
During the second Go / No Go poll, the Elf in charge of the Rocket Equation Double-Checker stops the launch sequence. Apparently, you forgot to include additional fuel for the fuel you just added.

Fuel itself requires fuel just like a module - take its mass, divide by three, round down, and subtract 2. However, that fuel also requires fuel, and that fuel requires fuel, and so on. Any mass that would require negative fuel should instead be treated as if it requires zero fuel; the remaining mass, if any, is instead handled by wishing really hard, which has no mass and is outside the scope of this calculation.

So, for each module mass, calculate its fuel and add it to the total. Then, treat the fuel amount you just calculated as the input mass and repeat the process, continuing until a fuel requirement is zero or negative. For example:

A module of mass 14 requires 2 fuel. This fuel requires no further fuel (2 divided by 3 and rounded down is 0, which would call for a negative fuel), so the total fuel required is still just 2.
At first, a module of mass 1969 requires 654 fuel. Then, this fuel requires 216 more fuel (654 / 3 - 2). 216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel, which requires no further fuel. So, the total fuel required for a module of mass 1969 is 654 + 216 + 70 + 21 + 5 = 966.
The fuel required by a module of mass 100756 and its fuel is: 33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account the mass of the added fuel? (Calculate the fuel requirements for each module separately, then add them all up at the end.)

Although it hasn't changed, you can still get your puzzle input. 

That's the right answer! You are one gold star closer to rescuing Santa.

You have completed Day 1! You can [Share] this victory or [Return to Your Advent Calendar].

out put: 4770725
*/
public class Day1FuelMassPart2 {
	public static void main(String args[]) throws Exception {
		Day1FuelMassPart2 day1FuelMassPart2 = new Day1FuelMassPart2();
		long massArray[] = {51360,95527,72603,128601,68444,138867,67294,134343,62785,53088,134635,137884,97654,103704,138879,87561,83922,68414,84876,105143,76599,98924,57080,63590,50126,111872,55754,64410,78488,56557,105446,127182,59451,87249,61652,131698,148820,95742,68223,121744,65678,99745,64089,75610,106085,100364,116959,122862,56580,109631,82895,79666,133474,50579,83473,140028,125999,68225,131345,90797,84914,81915,65369,71230,50379,106385,118503,119640,138540,70678,95881,100282,123060,147368,93030,82553,131271,147675,111126,115183,82956,145698,99261,52768,99207,123551,64738,117275,98136,111592,78576,118613,130351,68567,72356,85608,129414,66521,76924,130449};
		// long massArray[] = { 100756 };
		long totalFuel = 0;
		for (long mass : massArray) {
			long fuel = day1FuelMassPart2.calculateFuelByMass(mass, 0);
			System.out.println("Fuel By Module---" + fuel);
			totalFuel += fuel;
		}
		System.out.println("Total Fuel---" + totalFuel);
	}
	
	public long calculateFuelByMass(long mass, long totalFuel) {
		long fuel = (mass / 3) - 2;
		if (fuel > 0 ) {
			totalFuel += fuel;
			return calculateFuelByMass(fuel, totalFuel);
		}
		return totalFuel;
	}
}
