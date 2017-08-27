package info.javaperformance.compressedmaps.normal.strings.trie;

import java.io.Serializable;
import java.util.Comparator;

public abstract class KeyAnalyzer<K> implements Comparator<K>, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int NULL_BIT_KEY = -1;

	public static final int EQUAL_BIT_KEY = -2;

	public static final int OUT_OF_BOUNDS_BIT_KEY = -3;

	static boolean isOutOfBoundsIndex(final int bitIndex) {
		return bitIndex == OUT_OF_BOUNDS_BIT_KEY;
	}

	static boolean isEqualBitKey(final int bitIndex) {
		return bitIndex == EQUAL_BIT_KEY;
	}

	static boolean isNullBitKey(final int bitIndex) {
		return bitIndex == NULL_BIT_KEY;
	}

	static boolean isValidBitIndex(final int bitIndex) {
		return bitIndex >= 0;
	}

	public abstract int bitsPerElement();

	public abstract int lengthInBits(K key);

	public abstract boolean isBitSet(K key, int bitIndex, int lengthInBits);

	public abstract int bitIndex(K key, int offsetInBits, int lengthInBits, K other, int otherOffsetInBits, int otherLengthInBits);

	public abstract boolean isPrefix(K prefix, int offsetInBits, int lengthInBits, K key);

	@Override
	@SuppressWarnings("unchecked")
	public int compare(final K o1, final K o2) {
		if (o1 == null) {
			return o2 == null ? 0 : -1;
		} else if (o2 == null) {
			return 1;
		}

		return ((Comparable<K>) o1).compareTo(o2);
	}

}
