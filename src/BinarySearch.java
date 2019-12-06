import java.util.Arrays;

public class BinarySearch {

	public static void main(String[] args) {
		int input[] = {10, 80, 30, 90, 40, 50, 70};
		int searchValue = 80;
		
		BinarySearch binarySearch = new BinarySearch();
		int searchIndex = binarySearch.binarySearch(input, searchValue);
		
		System.out.println("searchIndex>>>>" + searchIndex);

	}
	
	public int binarySearch(int inputArray[], int searchValue) {
		MergeSort mergeSort = new MergeSort();
		mergeSort.mergeSort(inputArray);
		System.out.println("After Sorting>>>>" + Arrays.toString(inputArray));

		return binarySearch(inputArray, 0, inputArray.length - 1, searchValue);
	}
	
	public int binarySearch(int inputArray[], int left, int right, int searchValue) {
		if (right >= left) {
			int middle = left + (right -1) / 2;
			if (inputArray[middle] == searchValue) {
				return middle;
			}
			
			if (inputArray[middle] > searchValue) {
				return binarySearch(inputArray, left, middle - 1, searchValue);
			} else {
				return binarySearch(inputArray, middle + 1, right, searchValue);
			}
		}
		return -1;
	}
	
	
	

}
