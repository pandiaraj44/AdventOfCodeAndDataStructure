
import java.util.*;

public class Loss2 {
	public static final String DELIMITER = " ";
	public static final int RESULT_INVALID = 0;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int numberOfTestCases = Integer.parseInt(sc.nextLine());
		int minLoss = Loss2.RESULT_INVALID;
		// Iterate each test case
		for (int testCaseCount = 0; testCaseCount < numberOfTestCases; testCaseCount++) {
			final int numberOfDays = Integer.parseInt(sc.nextLine());
			final String priceListString = sc.nextLine();
			int currentMinLoss = Loss2.findMinimumLoss((List<String>) Arrays.asList(priceListString.split(Loss2.DELIMITER)), numberOfDays);
			if (Loss2.RESULT_INVALID != currentMinLoss && (currentMinLoss < minLoss || minLoss == Loss2.RESULT_INVALID)) {
				minLoss = currentMinLoss;
			}
		}
		sc.close();
		// To print the result
		System.out.print(minLoss);
	}

	/**
	 * * To find the minimum loss
	 * 
	 * @param A price list
	 * @return
	 */
	public static int findMinimumLoss(List<String> priceListString, int numberOfDays) {
		final List<Integer> priceList = new ArrayList<Integer>();
		for (String price : priceListString) {
			priceList.add(Integer.parseInt(price));
		}
		int minLoss = RESULT_INVALID;
		for (int index = 0; index < numberOfDays -1; index++) {
			int buyPrice = priceList.get(index);
			for (int sellIndex = index + 1; sellIndex < numberOfDays; sellIndex++) {
				int loss = buyPrice - priceList.get(sellIndex);
				if (loss >= 0 && (loss < minLoss || minLoss == Loss2.RESULT_INVALID)) {
					minLoss = loss;
				}
			}
		}
		return minLoss;
	}

}


/*
import java.io.*;
import java.util.*;
public class CandidateCode {
   public static final String DELIMITER = " ";
	public static final int RESULT_INVALID = 0;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		long minLoss = CandidateCode.RESULT_INVALID;
		final int numberOfDays = Integer.parseInt(sc.nextLine());
		final String priceListString = sc.nextLine();
		sc.close();
		minLoss = CandidateCode.findMinimumLoss(
				priceListString.split(CandidateCode.DELIMITER), numberOfDays);

		// To print the result
	}


	public static long findMinimumLoss(String[] priceListString, int numberOfDays) {
		final List<Long> priceList = new ArrayList<Long>();
		for (String price : priceListString) {
			priceList.add(Long.parseLong(price));
		}
		long minLoss = RESULT_INVALID;
		for (int index = 0; index < numberOfDays -1; index++) {
			long buyPrice = priceList.get(index);
			for (int sellIndex = index + 1; sellIndex < numberOfDays; sellIndex++) {
				long loss = buyPrice - priceList.get(sellIndex);
				if (loss >= 0 && (loss < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss;
				}
			}
		}
		return minLoss;
	}
}
*/
