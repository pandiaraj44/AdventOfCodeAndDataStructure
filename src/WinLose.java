/* Read input from STDIN. Print your output to STDOUT*/

import java.io.*;
import java.util.*;
public class WinLose {
   public static void main(String args[] ) throws Exception {

	//Write code here
      int numberOfTestCases = 1;
      for (int testCaseCount = 0; testCaseCount < numberOfTestCases; testCaseCount++) {
         int numberOfPlayers = 5;
         List<Integer> strenthOfVillains= new ArrayList<Integer>();
         strenthOfVillains.add(10);
         strenthOfVillains.add(20);
         strenthOfVillains.add(30);
         strenthOfVillains.add(40);
         strenthOfVillains.add(50);
         List<Integer> energyOfPlayers= new ArrayList<Integer>();
         energyOfPlayers.add(40);
         energyOfPlayers.add(50);
         energyOfPlayers.add(60);
         energyOfPlayers.add(70);
         energyOfPlayers.add(80);
         Collections.sort(strenthOfVillains);
         Collections.reverse(strenthOfVillains);
         Collections.sort(energyOfPlayers);
         Collections.reverse(energyOfPlayers);
         System.out.println((WinLose.isPlayerWin(numberOfPlayers, strenthOfVillains, energyOfPlayers) ? "WIN" : "LOSE"));
      }
      
   }

   public static boolean isPlayerWin(int numberOfPlayers, List<Integer> strenthOfVillains, List<Integer> energyOfPlayers ) {
	  boolean isWin = false;
	  Integer pIndex = 0;
	  for (int sIndex = 0; sIndex < numberOfPlayers; sIndex++) {
		  Integer strength = strenthOfVillains.get(sIndex);
		  Integer energy = energyOfPlayers.get(pIndex);
		  if (pIndex == sIndex && energy <= strength) {
			  return false;
		  } else if (energy > strength) {
			  isWin = true;
			  energy = energy - strength;
			  energyOfPlayers.set(pIndex, energy);
		  } else {
			  pIndex = pIndex + 1;
			  sIndex = sIndex - 1;
			  isWin = false;
		  }
	  }	  
      return isWin;
   }
}
