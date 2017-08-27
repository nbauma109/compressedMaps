package info.javaperformance.compressedmaps.normal.strings.trie;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractBitwiseTrie<K, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final KeyAnalyzer<? super K> keyAnalyzer;

	protected AbstractBitwiseTrie(final KeyAnalyzer<? super K> keyAnalyzer) {
		if (keyAnalyzer == null) {
			throw new NullPointerException("keyAnalyzer");
		}

		this.keyAnalyzer = keyAnalyzer;
	}

	protected KeyAnalyzer<? super K> getKeyAnalyzer() {
		return keyAnalyzer;
	}

	@SuppressWarnings("unchecked")
	protected final K castKey(final Object key) {
		return (K) key;
	}

	protected final int lengthInBits(final K key) {
		if (key == null) {
			return 0;
		}

		return keyAnalyzer.lengthInBits(key);
	}

	protected final boolean isBitSet(final K key, final int bitIndex, final int lengthInBits) {
		if (key == null) {
			return false;
		}
		return keyAnalyzer.isBitSet(key, bitIndex, lengthInBits);
	}

	protected final int bitIndex(final K key, final K foundKey) {
		return keyAnalyzer.bitIndex(key, 0, lengthInBits(key), foundKey, 0, lengthInBits(foundKey));
	}

	protected final boolean compareKeys(final K key, final K other) {
		if (key == null) {
			return other == null;
		} else if (other == null) {
			return false;
		}

		return keyAnalyzer.compare(key, other) == 0;
	}

	public static boolean compare(final Object a, final Object b) {
		return a == null ? b == null : a.equals(b);
	}

	abstract static class BasicEntry<K, V> implements Map.Entry<K, V>, Serializable {

		private static final long serialVersionUID = 1L;

		protected K key;

		protected V value;

		public BasicEntry(final K key) {
			this.key = key;
		}

		public BasicEntry(final K key, final V value) {
			this.key = key;
			this.value = value;
		}

		public V setKeyValue(final K key, final V value) {
			this.key = key;
			return setValue(value);
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(final V value) {
			final V previous = this.value;
			this.value = value;
			return previous;
		}

		@Override
		public int hashCode() {
			return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
		}

		@Override
		public boolean equals(final Object o) {
			if (o == this) {
				return true;
			} else if (!(o instanceof Map.Entry)) {
				return false;
			}

			final Map.Entry<?, ?> other = (Map.Entry<?, ?>) o;
			if (compare(key, other.getKey()) && compare(value, other.getValue())) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
}
