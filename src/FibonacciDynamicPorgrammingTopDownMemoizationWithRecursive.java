import java.util.Arrays;

public class FibonacciDynamicPorgrammingTopDownMemoizationWithRecursive {

	public static void main(String[] args) {
		int input = 40;
		final FibonacciDynamicPorgrammingTopDownMemoizationWithRecursive fTopDownMemoizationWithRecursive = new FibonacciDynamicPorgrammingTopDownMemoizationWithRecursive();
		long start = System.currentTimeMillis();
		int result = fTopDownMemoizationWithRecursive.fibanacci(input);
		long end = System.currentTimeMillis();


		System.out.println("Fib(" + input + ") = " + result);
		
		System.out.println("Fib(" + input + ") = " + end + "---" + start);
		
		System.out.println("end-start---" + (end - start));

	}

	public int fibanacci(int number) {
		int memoize[] = new int[number + 1];
		return fibanacci(memoize, number);
	}

	private int fibanacci(int memoize[], int number) {
		// System.out.println("Calculating Fib(" + number + ")");

		if (number <= 1) {
			return number;
		}

		if (memoize[number] != 0) {  
			/* 
			 * If the fibanacci value is already present then return the value instead of calculating, 
			 * ex) fibanacci(2) is present return value, then we need not calculate fibanacci(1) and fibanacci(0)   
			 */
			return memoize[number];
		}
		memoize[number] = fibanacci(memoize, number - 1) + fibanacci(memoize, number - 2);
		// System.out.println("After Sorting>>>>" + Arrays.toString(memoize));

		return memoize[number];
	}

}
