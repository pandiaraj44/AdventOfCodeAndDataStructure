
public class FibonacciRecursive {

	public static void main(String[] args) {
		int input = 40;
		final FibonacciRecursive fibonacciRecursive = new FibonacciRecursive();
		long start = System.currentTimeMillis();

		int result = fibonacciRecursive.fibanacci(input);
		long end = System.currentTimeMillis();

		System.out.println("Fib(" + input + ") = " + result);
		System.out.println("Fib(" + input + ") = " + end + "---" + start);

		System.out.println("end-start---" + (end - start));
	}

	public int fibanacci(int number) {
		if (number <= 1)
			return number;
		return fibanacci(number - 1) + fibanacci(number - 2);
	}

}
