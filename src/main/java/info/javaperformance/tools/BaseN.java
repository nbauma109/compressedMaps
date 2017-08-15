package info.javaperformance.tools;

public class BaseN {

	private String digits;

	public BaseN(String digits) {
		this.digits = digits;
	}

	public String toString(long i) {

		char[] buf = new char[65];
		int charPos = 64;
		boolean negative = (i < 0);

		if (!negative) {
			i = -i;
		}

		while (i <= -digits.length()) {
			buf[charPos--] = digits.charAt((int) (-(i % digits.length())));
			i = i / digits.length();
		}
		buf[charPos] = digits.charAt((int) (-i));

		if (negative) {
			buf[--charPos] = '-';
		}

		return new String(buf, charPos, (65 - charPos));
	}

	public long parseLong(String s) throws NumberFormatException {
		if (s == null) {
			throw new NumberFormatException("null");
		}

		long result = 0;
		boolean negative = false;
		int i = 0, len = s.length();
		long limit = -Long.MAX_VALUE;
		long multmin;
		int digit;

		if (len > 0) {
			multmin = limit / digits.length();
			while (i < len) {
				char currentChar = s.charAt(i++);
				digit = digits.indexOf(currentChar);
				if (digit < 0) {
					throw new NumberFormatException(s);
				}
				if (result < multmin) {
					throw new NumberFormatException(s);
				}
				result *= digits.length();
				if (result < (limit + digit)) {
					throw new NumberFormatException(s);
				}
				result -= digit;
			}
		} else {
			throw new NumberFormatException(s);
		}
		return negative ? result : -result;
	}

	public String getDigits() {
		return digits;
	}

	public static void main(String[] args) {
		String original = "Z#AAE#034_28";
		System.out.println("Original: " + original);
		BaseN bn = new BaseN("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ#_");
		long decoded = bn.parseLong(original);
		System.out.println("decoded: " + decoded);
		String encoded = bn.toString(decoded);
		System.out.println("encoded: " + encoded);
		if (original.equals(encoded.toString())) {
			System.out.println("Passed! encoded value is the same as the original.");
		} else {
			System.err.println("FAILED! encoded value is NOT the same as the original!!");
		}
	}

}
