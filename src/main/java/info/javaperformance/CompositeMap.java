package info.javaperformance;

import java.nio.charset.Charset;

import info.javaperformance.compressedmaps.IntMapFactory;
import info.javaperformance.compressedmaps.normal.ints.IIntLongMap;
import info.javaperformance.compressedmaps.normal.ints.IIntObjectMap;
import info.javaperformance.intint.IIntIntMap;
import info.javaperformance.intint.IntIntMap;
import info.javaperformance.serializers.GenericStringSerializer;
import info.javaperformance.tools.BaseN;

public class CompositeMap {

	private static final int LONG_MAX_LEN = Long.toString(Long.MAX_VALUE).length();

	private IIntIntMap intValuesById;
	private IIntLongMap longValuesById;
	private IIntObjectMap<String> stringValuesById;
	private BaseN baseN;
	private int maxLen;

	public CompositeMap(int size, float fillFactor, String digits) {
		intValuesById = new IntIntMap(size, fillFactor);
		longValuesById = IntMapFactory.singleThreadedIntLongMap(size, fillFactor);
		stringValuesById = IntMapFactory.singleThreadedIntObjectMap(size, fillFactor, new GenericStringSerializer(Charset.forName("UTF-8")));
		baseN = new BaseN(digits);
		maxLen = baseN.toString(Long.MAX_VALUE).length();
	}

	public synchronized void put(int key, String value) {
		if (value == null) {
			return;
		}
		try {
			if (value.trim().length() == 0) {
				stringValuesById.put(key, value.intern());
			} else if (value.length() <= LONG_MAX_LEN && value.matches("[0-9]+")) {
				long parsedValue = Long.parseLong(value);
				if (parsedValue >= Integer.MIN_VALUE && parsedValue <= Integer.MAX_VALUE) {
					intValuesById.put(key, (int) parsedValue);
				} else {
					longValuesById.put(key, parsedValue);
				}
			} else if (value.length() <= maxLen && value.matches("[" + baseN.getDigits() + "]+")) {
				longValuesById.put(key, baseN.parseLong(value));
			} else {
				stringValuesById.put(key, value);
			}
		} catch (NumberFormatException e) {
			stringValuesById.put(key, value);
		}
	}

	public synchronized String get(int key) {
		int intValue = intValuesById.get(key);
		if (intValue != 0) {
			return String.valueOf(intValue);
		}
		long longValue = longValuesById.get(key);
		if (longValue != 0) {
			return baseN.toString(longValue);
		}
		return stringValuesById.get(key);
	}

	public static void main(String[] args) {
		CompositeMap compositeMap = new CompositeMap(16, 0.75F, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ#_");
		compositeMap.put(54326, "jhfds76");
		compositeMap.put(87590, "A3#78_1");
		compositeMap.put(76479, "Z49##__IOU0");
		System.out.println(compositeMap.get(54326));
		System.out.println(compositeMap.get(87590));
		System.out.println(compositeMap.get(76479));
	}
}
