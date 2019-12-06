import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		// int input[] = {4, 3, 2, 1, 7 , 8};
		int input[] = {10, 80, 30, 90, 40, 50, 70};
		System.out.println("Before Sorting>>>" + Arrays.toString(input));
		
		MergeSort mergeSort = new MergeSort();
		mergeSort.mergeSort(input);
		
		System.out.println("After Sorting>>>>" + Arrays.toString(input));

	}
	
	public void mergeSort(int inputArray[]) {
		mergeSort(inputArray, 0, inputArray.length - 1);
	}
	
	public void mergeSort(int inputArray[], int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;
			mergeSort(inputArray, left, middle);
			mergeSort(inputArray, middle + 1, right);
			merge(inputArray, left, middle, right);

		}
	}
	
	private void merge(int inputArray[], int left, int middle, int right) {
		int n1 = (middle - left) + 1; // Length of the left array
		int n2 = right - middle; // Length of the right array
		
		int L[] = new int [n1]; // Initialize left sub-array
		int R[] = new int [n2]; // Initialize right sub-array
		
		for (int i = 0; i < n1; i ++) {  // Copy values to temp array
			L[i] = inputArray[left + i];
		}
		
		for (int j = 0; j < n2; j ++) {  // Copy values to temp array
			R[j] = inputArray[middle + 1 + j];
		}
		System.out.println("----------- -- ");

		System.out.println("Arrays.toString(Left) -- " + Arrays.toString(L));
		System.out.println("Arrays.toString(Right) -- " + Arrays.toString(R));
		System.out.println("----------- -- ");


		
		// Merge the temp arrays
		int k = left;
		int i = 0;
		int j = 0;
		while (i < n1 && j< n2) {
			if (L[i] < R[j]) {     // Check left and right values
				inputArray[k] = L[i];
				i++;
			} else {
				inputArray[k] = R[j];
				j++;
			}
			k++;
		}
		
		while (i < n1) {   // Copy remaining values in Left array
			inputArray[k] = L[i];
			i++;
			k++;
		}
		
		while (j < n2) { // Copy remaining values in Right array
			inputArray[k] = R[j];
			j++;
			k++;
		}
		
	}
	

}
