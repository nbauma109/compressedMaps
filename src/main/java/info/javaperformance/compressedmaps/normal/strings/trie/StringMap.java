package info.javaperformance.compressedmaps.normal.strings.trie;

import info.javaperformance.compressedmaps.normal.strings.trie.analyzer.StringKeyAnalyzer;

public class StringMap<E> extends AbstractTrieMap<String, E> {

	private static final long serialVersionUID = 1L;

	public StringMap() {
		super(new StringKeyAnalyzer());
	}

}
