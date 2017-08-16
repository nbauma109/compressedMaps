package info.javaperformance;

import java.math.BigInteger;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

import info.javaperformance.compressedmaps.IntMapFactory;
import info.javaperformance.compressedmaps.normal.ints.IIntObjectMap;
import info.javaperformance.serializers.GenericBigIntegerSerializer;
import info.javaperformance.serializers.GenericStringSerializer;
import uk.co.maxant.util.BaseX;

public class CompositeMap {

	private IIntObjectMap<String> stringValuesById;
	private IIntObjectMap<BigInteger> bigIntValuesById;
	private char[] dictionary;
	private BaseX baseX;

	public CompositeMap(int size, float fillFactor, char[] dictionary) {
		this.dictionary = dictionary;
		baseX = new BaseX(dictionary);
		stringValuesById = IntMapFactory.singleThreadedIntObjectMap(size, fillFactor, new GenericStringSerializer(Charset.forName("UTF-8")));
		bigIntValuesById = IntMapFactory.singleThreadedIntObjectMap(size, fillFactor, new GenericBigIntegerSerializer());
	}

	public synchronized void put(int key, String value) {
		if (value == null) {
			return;
		}
		if ("0".equals(value) || !value.startsWith("0") && StringUtils.containsOnly(value, dictionary)) {
			bigIntValuesById.put(key, baseX.decode(value));
		} else {
			stringValuesById.put(key, value);
		}
	}

	public synchronized String get(int key) {
		BigInteger intValue = bigIntValuesById.get(key);
		if (intValue != null) {
			return baseX.encode(intValue);
		}
		return stringValuesById.get(key);
	}

	public static void main(String[] args) {
		CompositeMap compositeMap = new CompositeMap(16, 16, BaseX.DICTIONARY_16);
		compositeMap.put(54326, "AB123");
		compositeMap.put(87590, "A3#78_1");
		compositeMap.put(76479, "Z49##__IOU0");
		System.out.println(compositeMap.get(54326));
		System.out.println(compositeMap.get(87590));
		System.out.println(compositeMap.get(76479));
	}
}
