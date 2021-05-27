package sdc;

public final class PrimeNumberHelper {

	/**
	 * Check if specified number is a prime number
	 * @param number Number to check
	 * @return whether number is prime
	 */
	public static boolean isPrimeNumber(int number) {
		if (number < 3) {
			return number == 2;
		}
		int last = (int) (Math.floor(Math.sqrt(number)));
		for (int i = 3; i <= last; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}
}
