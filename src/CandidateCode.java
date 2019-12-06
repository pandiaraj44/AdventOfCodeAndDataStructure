
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
				(List<String>) Arrays.asList(priceListString.split(CandidateCode.DELIMITER)), numberOfDays);

		// To print the result
		System.out.println(minLoss);
	}

	/**
	 * * To find the minimum loss
	 * 
	 * @param A price list
	 * @return
	 */
	public static long findMinimumLoss(List<String> priceList, int numberOfDays) {
		long minLoss = RESULT_INVALID;
		for (int index = 0; index <= numberOfDays - 2; index++) {
			long buyPrice = Long.parseLong(priceList.get(index));
			// solve(priceList, index + 1, numberOfDays-1);
			for (int sellIndex1 = index+1, sellIndex2 = numberOfDays - 1; sellIndex1 <= sellIndex2; sellIndex1++, sellIndex2--) {
				long loss1 = buyPrice - Long.parseLong(priceList.get(sellIndex1));
				long loss2 = buyPrice - Long.parseLong(priceList.get(sellIndex2));
				
				long loss = loss1 <  loss2 ? loss1 : loss2;
				if (loss >= 0 && (loss < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss;
				}
				
				if (minLoss == 1) {
					break;
				}
			}
		}
		return minLoss;
	}
	
	public static void solve(List<String> priceList, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			solve(priceList, start, mid);
			solve(priceList, mid+1, end);
		}
	}
	
	/**
	 
	 
	 public static long findMinimumLoss(List<String> priceList, int numberOfDays) {
		long minLoss = RESULT_INVALID;
		for (int index = 0; index <= numberOfDays - 2; index++) {
			long buyPrice = Long.parseLong(priceList.get(index));
			
			for (int sellIndex1 = index+1, sellIndex2 = numberOfDays - 1; sellIndex1 <= sellIndex2; sellIndex1++, sellIndex2--) {
				long loss1 = buyPrice - Long.parseLong(priceList.get(sellIndex1));
				long loss2 = buyPrice - Long.parseLong(priceList.get(sellIndex2));
				
				long loss = loss1 <  loss2 ? loss1 : loss2;
				if (loss >= 0 && (loss < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss;
				}
				
				if (minLoss == 1) {
					break;
				}
			}
		}
		return minLoss;
	}
	 
	 */
	
	/**
	 
	 public static long findMinimumLoss(List<String> priceList, int numberOfDays) {
		long minLoss = RESULT_INVALID;
		for (int index = 0; index < numberOfDays - 1; index++) {
			long buyPrice = Long.parseLong(priceList.get(index));
			for (int sellIndex = index + 1; sellIndex < numberOfDays; sellIndex++) {
				long loss = buyPrice - Long.parseLong(priceList.get(sellIndex));
				if (loss >= 0 && (loss < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss;
				}
			}
		}
		return minLoss;
	}
	 
	 */
	
	/**
	 
	 
	 public static long findMinimumLoss(List<Long> priceList, int numberOfDays) {
		long minLoss = CandidateCode.RESULT_INVALID;
		for (int index = 0; index < numberOfDays -1; index++) {
			long buyPrice = priceList.get(index);
			final List<Long> sellList = new ArrayList<Long>();
			sellList.addAll(priceList.subList(index + 1, priceList.size()));
			Collections.sort(sellList);
			
			if (sellList.get(index) >= buyPrice) {
				continue;
			}
			int sellListLength = sellList.size();
			int midIndex = 0 + (sellListLength - 0) / 2;
			
			int sellIndex1 = 0, sellIndex2 = midIndex;
		    Long midPrice = sellList.get(midIndex);
		    if (buyPrice > midPrice) {
		    	sellIndex1 = midIndex;
		    	sellIndex2 = sellListLength -1;
		    }
			for (; sellIndex1 <= sellIndex2; sellIndex1++, sellIndex2--) {
				long loss1 = buyPrice - sellList.get(sellIndex1);
				long loss2 = buyPrice - sellList.get(sellIndex2);
				if (loss1 >= 0 && (loss1 < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss1;
				}
				if (loss2 >= 0 && (loss2 < minLoss)) {
					minLoss = loss2;
				}
				
				if (minLoss == 1) {
					break;
				}
			}
			if (minLoss == 1) {
				break;
			}
			
		}
		return minLoss;
	}
	 
	 */
	
/**	public static long findMinimumLoss(List<String> priceListString, int numberOfDays) {
		final List<Long> priceList = new ArrayList<Long>();
		for (String price : priceListString) {
			priceList.add(Long.parseLong(price));
		}
		long minLoss = CandidateCode.RESULT_INVALID;
		for (int index = 0; index < numberOfDays -1; index++) {
			long buyPrice = priceList.get(index);
			final List<Long> sellList = new ArrayList<Long>();
			sellList.addAll(priceList.subList(index + 1, priceList.size()));
			Collections.sort(sellList);
			int sellListLength = sellList.size();
			int midIndex = 0 + (sellListLength - 0) / 2;
			
			int sellIndex1 = 0, sellIndex2 = midIndex;
		    Long midPrice = sellList.get(midIndex);
		    if (buyPrice > midPrice) {
		    	sellIndex1 = midIndex;
		    	sellIndex2 = sellListLength -1;
		    }
			for (; sellIndex1 <= sellIndex2; sellIndex1++, sellIndex2--) {
				long loss1 = buyPrice - sellList.get(sellIndex1);
				long loss2 = buyPrice - sellList.get(sellIndex2);
				if (loss1 >= 0 && (loss1 < minLoss || minLoss == CandidateCode.RESULT_INVALID)) {
					minLoss = loss1;
				}
				if (loss2 >= 0 && (loss2 < minLoss)) {
					minLoss = loss2;
				}
			}
		}
		return minLoss;
	}*/

}