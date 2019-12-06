
import java.util.*;

public class CandidateCode2 {
	public static final String DELIMITER = " ";
	public static final int RESULT_INVALID = 0;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		long minLoss = CandidateCode2.RESULT_INVALID;
		final int numberOfDays = Integer.parseInt(sc.nextLine());
		final String priceListString = sc.nextLine();
		minLoss = CandidateCode2.findMinimumLoss((List<String>) Arrays.asList(priceListString.split(CandidateCode2.DELIMITER)), numberOfDays);
		sc.close();
		// To print the result
		System.out.println(minLoss);
	}

	/**
	 * * To find the minimum loss
	 * 
	 * @param A price list
	 * @return
	 */
	public static long findMinimumLoss(List<String> priceListString, int numberOfDays) {
		final List<Long> priceList = new ArrayList<Long>();
		for (String price : priceListString) {
			priceList.add(Long.parseLong(price));
		}
		long minLoss = CandidateCode2.RESULT_INVALID;
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
				if (loss1 >= 0 && (loss1 < minLoss || minLoss == CandidateCode2.RESULT_INVALID)) {
					minLoss = loss1;
				}
				if (loss2 >= 0 && (loss2 < minLoss)) {
					minLoss = loss2;
				}
			}
		}
		return minLoss;
	}
	
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