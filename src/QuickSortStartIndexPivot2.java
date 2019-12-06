import java.util.Arrays;

public class QuickSortStartIndexPivot2 {

	public static void main(String[] args) {
		int input[] = {4, 3, 2, 1, 7 , 8};
		int input1[] = input.clone();
		System.out.println("Before Sorting>>>" + Arrays.toString(input));
		
		QuickSortStartIndexPivot2 quickSortStartIndexPivot = new QuickSortStartIndexPivot2();
		quickSortStartIndexPivot.quickSort(input, 0, input.length -1);
		System.out.println("After Sorting>>>>" + Arrays.toString(input));

		System.out.println("After Sorting 2>>>>" + Arrays.toString(input1));
	}
	
	public void quickSort(int inputArray[]) {
		quickSort(inputArray, 0, inputArray.length - 1);
	}
	
	public void quickSort(int inputArray[], int lowerBound, int higherBound) {
		if (lowerBound < higherBound) {
			int partitionIndex = partition(inputArray, lowerBound, higherBound);
			quickSort(inputArray, lowerBound, partitionIndex - 1);
			quickSort(inputArray, partitionIndex + 1, higherBound);
		}
	}
	
	private int partition(int inputArray[], int lowerBound, int higherBound) {
		int newPivoitIndex = lowerBound + 1;
		int pivot = inputArray[lowerBound];
		int start = lowerBound + 1;
		for (; start <= higherBound; start ++ ) {
			if (inputArray[start] < pivot) {
				swap(inputArray, newPivoitIndex, start);
				newPivoitIndex  += 1;
			}
		}
		swap(inputArray, lowerBound, newPivoitIndex -1 );
		return newPivoitIndex -1;
	}
	
	private void swap(int inputArray[], int index1, int index2) {
		int temp = inputArray[index1];
		inputArray[index1] = inputArray[index2];
		inputArray[index2] = temp;
	}
	
	

}
