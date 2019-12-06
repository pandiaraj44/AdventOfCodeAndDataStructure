import java.util.Arrays;

public class FibonacciBottomUpTabulation {

	public static void main(String[] args) {
		int input = 1000;
		final FibonacciBottomUpTabulation fBottomUpTabulation = new FibonacciBottomUpTabulation();
		long start = System.currentTimeMillis();
		int result = fBottomUpTabulation.fibanacci(input);
		long end = System.currentTimeMillis();

		System.out.println("Fib(" + input + ") = " + result);
		System.out.println("end---" + end  + "--start--" + start);

		System.out.println("end-start---" + (end - start));
	}

	public int fibanacci(int number) {
	    int dp[] = new int[number+1];
	    
	    dp[0] = 0;
	    dp[1] = 1;

	    for (int i = 2; i < dp.length; i++) {
			System.out.println("Calculating Fib(" + number + ")");
			dp[i] = dp[i-1] + dp[i-2];
		}
		System.out.println("After Sorting>>>>" + Arrays.toString(dp));

		return dp[number];
	}

}
