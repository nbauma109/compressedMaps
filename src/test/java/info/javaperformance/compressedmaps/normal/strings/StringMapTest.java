package info.javaperformance.compressedmaps.normal.strings;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.github.jamm.MemoryMeter;
import org.junit.Test;

import info.javaperformance.compressedmaps.normal.strings.trie.StringMap;
import info.javaperformance.objobj.ObjObjMap;
import junit.framework.TestCase;

public class StringMapTest extends TestCase {

	@Test
	public void test() {
		HashMap<String, Double> stringMap1 = new HashMap<>();
		StringMap<Double> stringMap2 = new StringMap<>();
		ObjObjMap<String, Double> stringMap3 = new ObjObjMap<>(16, 0.75f);
		long getTime1 = 0;
		long getTime2 = 0;
		long getTime3 = 0;
		long putTime1 = 0;
		long putTime2 = 0;
		long putTime3 = 0;
		Random r = new Random();
		long l;
		int i;
		for (l = Long.MAX_VALUE, i = 0; i < 100000; i++, l--) {
			String key = Long.toString(l, 36);
			double value = r.nextDouble();
			putTime1 -= System.currentTimeMillis();
			stringMap1.put(key, value);
			putTime1 += System.currentTimeMillis();
			putTime2 -= System.currentTimeMillis();
			stringMap2.put(key, value);
			putTime2 += System.currentTimeMillis();
			putTime3 -= System.currentTimeMillis();
			stringMap3.put(key, value);
			putTime3 += System.currentTimeMillis();
			getTime1 -= System.currentTimeMillis();
			Double val1 = stringMap1.get(key);
			getTime1 += System.currentTimeMillis();
			getTime2 -= System.currentTimeMillis();
			Double val2 = stringMap2.get(key);
			getTime2 += System.currentTimeMillis();
			getTime3 -= System.currentTimeMillis();
			Double val3 = stringMap3.get(key);
			getTime3 += System.currentTimeMillis();
			assertEquals(val3, val2);
			assertEquals(val3, val1);

			if (i % 10000 == 0) {
				System.out.println(i);
				showSize(stringMap1, stringMap2, stringMap3);
				showTimes(getTime1, getTime2, getTime3, putTime1, putTime2, putTime3);
			}
		}
		showTimes(getTime1, getTime2, getTime3, putTime1, putTime2, putTime3);
		showSize(stringMap1, stringMap2, stringMap3);
		System.out.println(stringMap2);
	}

	public void showTimes(long getTime1, long getTime2, long getTime3, long putTime1, long putTime2, long putTime3) {
		System.out.println("getTime1: " + DurationFormatUtils.formatDurationHMS(getTime1));
		System.out.println("getTime2: " + DurationFormatUtils.formatDurationHMS(getTime2));
		System.out.println("getTime3: " + DurationFormatUtils.formatDurationHMS(getTime3));
		System.out.println("putTime1: " + DurationFormatUtils.formatDurationHMS(putTime1));
		System.out.println("putTime2: " + DurationFormatUtils.formatDurationHMS(putTime2));
		System.out.println("putTime3: " + DurationFormatUtils.formatDurationHMS(putTime3));
	}

	public void showSize(HashMap<String, Double> stringMap1, StringMap<Double> stringMap2, ObjObjMap<String, Double> stringMap3) {
		org.github.jamm.MemoryMeter memoryMeter = new MemoryMeter();
		long sizeOfMap1 = memoryMeter.measureDeep(stringMap1);
		System.out.println("HashMap: " + sizeOfMap1);
		long sizeOfMap2 = memoryMeter.measureDeep(stringMap2);
		System.out.println("StrgMap: " + sizeOfMap2);
		long sizeOfMap3 = memoryMeter.measureDeep(stringMap3);
		System.out.println("ObjOMap: " + sizeOfMap3);
	}

}
