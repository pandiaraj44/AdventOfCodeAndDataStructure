import java.util.Arrays;

public class QuickSortEndIndexPivot {

	public static void main(String[] args) {
		// int input[] = {8, 7, 6, 1, 0 , 9, 2};
		int input[] = {7, 3, 2, 4, 6 , 7, 9, 1, 6, 3};
		// int input[] = {10, 80, 30, 90, 40, 50, 70};
		System.out.println("Before Sorting>>>" + Arrays.toString(input));
		
		QuickSortEndIndexPivot quickSortEndIndexPivot = new QuickSortEndIndexPivot();
		quickSortEndIndexPivot.quickSort(input);
		
		System.out.println("After Sorting>>>>" + Arrays.toString(input));

	}
	
	public void quickSort(int inputArray[]) {
		quickSort(inputArray, 0, inputArray.length - 1);
	}
	
	public void quickSort(int inputArray[], int low, int high) {
		System.out.println("quickSort called");
		if (low < high) {
			int partionInex = partition(inputArray, low, high);
			System.out.println("Partition inputArray>>>> partionInex >>" + partionInex + "--"+ Arrays.toString(inputArray));
			quickSort(inputArray, low, partionInex -1);
			System.out.println("Right Called");
			quickSort(inputArray, partionInex + 1, high);
		}
	}
	
	private int partition(int inputArray[], int low, int high) {
		int pivot = inputArray[high]; // Make last element as pivot
		int partitionIndex = low - 1;
		for (int start = low; start < high; start++) {
			if (inputArray[start] < pivot) {
				partitionIndex ++ ; // Increment the partition index
				
				// To swap smaller element and the partition index element. 
				// It will move the larger element to right.
				swap(inputArray, start, partitionIndex); 
				
			}
		}
		swap(inputArray, partitionIndex +1, high); // To move the pivot element to the partition index
		return partitionIndex + 1; // Return the partition index + 1
	}
	
	private void swap(int inputArray[], int sourceIndex, int destionationIndex) {
		int temp = inputArray[sourceIndex];
		inputArray[sourceIndex] = inputArray[destionationIndex];
		inputArray[destionationIndex] = temp;
	}
	
	

}
