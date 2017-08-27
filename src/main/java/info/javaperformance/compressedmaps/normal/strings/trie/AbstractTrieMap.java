package info.javaperformance.compressedmaps.normal.strings.trie;

public abstract class AbstractTrieMap<K, V> extends AbstractBitwiseTrie<K, V> {

	private static final long serialVersionUID = 1L;

	private transient TrieEntry<K, V> root = new TrieEntry<>(null, null, -1);

	private transient int size = 0;

	protected transient int modCount = 0;

	protected AbstractTrieMap(final KeyAnalyzer<? super K> keyAnalyzer) {
		super(keyAnalyzer);
	}

	public void clear() {
		root.key = null;
		root.bitIndex = -1;
		root.value = null;

		root.left = root;
		root.right = null;

		size = 0;
		incrementModCount();
	}

	public int size() {
		return size;
	}

	private void incrementSize() {
		size++;
		incrementModCount();
	}

	private void incrementModCount() {
		++modCount;
	}

	public V put(final K key, final V value) {
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		}

		final int lengthInBits = lengthInBits(key);

		if (lengthInBits == 0) {
			if (root.isEmpty()) {
				incrementSize();
			} else {
				incrementModCount();
			}
			return root.setKeyValue(key, value);
		}

		final TrieEntry<K, V> found = getNearestEntryForKey(key, lengthInBits);
		if (compareKeys(key, found.key)) {
			if (found.isEmpty()) {
				incrementSize();
			} else {
				incrementModCount();
			}
			return found.setKeyValue(key, value);
		}

		final int bitIndex = bitIndex(key, found.key);
		if (!KeyAnalyzer.isOutOfBoundsIndex(bitIndex)) {
			if (KeyAnalyzer.isValidBitIndex(bitIndex)) {
				final TrieEntry<K, V> t = new TrieEntry<>(key, value, bitIndex);
				addEntry(t, lengthInBits);
				incrementSize();
				return null;
			} else if (KeyAnalyzer.isNullBitKey(bitIndex)) {
				if (root.isEmpty()) {
					incrementSize();
				} else {
					incrementModCount();
				}
				return root.setKeyValue(key, value);

			} else if (KeyAnalyzer.isEqualBitKey(bitIndex)) {
				if (found != root) {
					incrementModCount();
					return found.setKeyValue(key, value);
				}
			}
		}

		throw new IllegalArgumentException("Failed to put: " + key + " -> " + value + ", " + bitIndex);
	}

	private TrieEntry<K, V> addEntry(final TrieEntry<K, V> entry, final int lengthInBits) {
		TrieEntry<K, V> current = root.left;
		TrieEntry<K, V> path = root;
		while (true) {
			if (current.bitIndex >= entry.bitIndex || current.bitIndex <= path.bitIndex) {

				if (!isBitSet(entry.key, entry.bitIndex, lengthInBits)) {
					entry.left = entry;
					entry.right = current;
				} else {
					entry.left = current;
					entry.right = entry;
				}

				if (path == root || !isBitSet(entry.key, path.bitIndex, lengthInBits)) {
					path.left = entry;
				} else {
					path.right = entry;
				}

				return entry;
			}

			path = current;

			if (!isBitSet(entry.key, current.bitIndex, lengthInBits)) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
	}

	public V get(final Object k) {
		final TrieEntry<K, V> entry = getEntry(k);
		return entry != null ? entry.getValue() : null;
	}

	private TrieEntry<K, V> getEntry(final Object k) {
		final K key = castKey(k);
		if (key == null) {
			return null;
		}

		final int lengthInBits = lengthInBits(key);
		final TrieEntry<K, V> entry = getNearestEntryForKey(key, lengthInBits);
		return !entry.isEmpty() && compareKeys(key, entry.key) ? entry : null;
	}

	public boolean containsKey(final Object k) {
		if (k == null) {
			return false;
		}

		final K key = castKey(k);
		final int lengthInBits = lengthInBits(key);
		final TrieEntry<K, V> entry = getNearestEntryForKey(key, lengthInBits);
		return !entry.isEmpty() && compareKeys(key, entry.key);
	}

	private TrieEntry<K, V> getNearestEntryForKey(final K key, final int lengthInBits) {
		TrieEntry<K, V> current = root.left;
		TrieEntry<K, V> path = root;
		while (true) {
			if (current.bitIndex <= path.bitIndex) {
				return current;
			}

			path = current;
			if (!isBitSet(key, current.bitIndex, lengthInBits)) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
	}

	private static class TrieEntry<K, V> extends BasicEntry<K, V> {

		private static final long serialVersionUID = 1L;

		protected int bitIndex;

		protected TrieEntry<K, V> left;

		protected TrieEntry<K, V> right;

		public TrieEntry(final K key, final V value, final int bitIndex) {
			super(key, value);

			this.bitIndex = bitIndex;

			this.left = this;
			this.right = null;
		}

		public boolean isEmpty() {
			return key == null;
		}

	}

}
