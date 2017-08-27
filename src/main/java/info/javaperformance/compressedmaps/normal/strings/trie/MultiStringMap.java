package info.javaperformance.compressedmaps.normal.strings.trie;

public abstract class MultiStringMap<T> {

	private StringMap<StringMap<StringMap<T>>> map;

	public T get(String name, String type, String set) {
		if (map == null) {
			map = new StringMap<>();
		}
		StringMap<StringMap<T>> elemsForName = map.get(name);
		if (elemsForName == null) {
			elemsForName = new StringMap<>();
			map.put(name, elemsForName);
		}
		StringMap<T> elemsForType = elemsForName.get(type);
		if (elemsForType == null) {
			elemsForType = new StringMap<>();
			elemsForName.put(type, elemsForType);
		}
		T elemsForSet = elemsForType.get(set);
		if (elemsForSet == null) {
			elemsForSet = loadData(name, type, set);
			elemsForType.put(set, elemsForSet);
		}
		return elemsForSet;
	}

	protected abstract T loadData(String name, String type, String set);
}
