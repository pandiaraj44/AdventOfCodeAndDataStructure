import java.util.Arrays;
import java.util.Scanner;

public class SquareMatrix {
	   public static void main(String args[] ) throws Exception {
		 //Write code here
		   Scanner sc = new Scanner(System.in);
		   int size = Integer.parseInt(sc.nextLine());
		   String[][] words = new String[size][size];
		   for (int i =0; i< size ; i++) {
		      String inputString = sc.nextLine();
		      String input[] = inputString.split("#");
		      inputString = inputString.replaceAll("#", "");
		      for (int j=0; j< size; j++) {
		    	  words[i][j] = input[j];
		      }
		   }
		   String searchString = sc.nextLine();
		   sc.close();
		   int output = getNumberOfOcuurances(words, searchString);
		   System.out.print(output);

	   }
	   
	   public static int getNumberOfOcuurances(String[][] words, String searchString) {
		   System.out.println(Arrays.toString(words));
		   int totalCount = 0;
		   return totalCount;
	   }
	
}
