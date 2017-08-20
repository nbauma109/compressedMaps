/*
 * (C) Copyright 2015 Mikhail Vorontsov ( http://java-performance.info/ ) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *      Mikhail Vorontsov
 */

package info.javaperformance.compressedmaps.normal.ints;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.RandomStringUtils;

import info.javaperformance.compressedmaps.IntMapFactory;
import info.javaperformance.serializers.DelegatingStringSerializer;
import junit.framework.TestCase;

public class IntDelegatingChainedMapStringTest extends TestCase {
	// fill factors to be tested
	private final static float[] FILL_FACTORS = { 0.25f, 0.5f, 0.75f, 0.9f, 0.99f, 1f, 2f, 3f, 5f, 16f };
	private final int SIZE = 100000;
	private static final String NOT_PRESENT = null;

	protected IIntObjectMap<String> makeMap(final long size, final float fillFactor) {
		return IntMapFactory.singleThreadedIntObjectMap(size, fillFactor, new DelegatingStringSerializer());
	}

	/**
	 * Add keys 0-SIZE to the map
	 */
	public void testPut() {
		for (final float ff : FILL_FACTORS) {
			testPutHelper(ff);
		}
	}

	private void testPutHelper(final float fillFactor) {
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(NOT_PRESENT, map.put(i, Integer.toString(i, 36).toLowerCase()));

			assertEquals(i + 1, map.size());
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.get(i));
		}
		// now check the final state
		assertEquals(SIZE, map.size());
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.get(i));
		}
	}

	/**
	 * Add a series of negative keys to the map
	 */
	public void testPutNegative() {
		for (final float ff : FILL_FACTORS) {
			testPutNegative(ff);
		}
	}

	private void testPutNegative(final float fillFactor) {
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		for (int i = 0; i < SIZE; ++i) {
			map.put(-i, Integer.toString(-i, 36).toLowerCase());
			assertEquals(i + 1, map.size());
			assertEquals(Integer.toString(-i, 36).toLowerCase(), map.get(-i));
		}
		// now check the final state
		assertEquals(SIZE, map.size());
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(Integer.toString(-i, 36).toLowerCase(), map.get(-i));
		}
	}

	/**
	 * Add a set of keys to the map. Then add it again to test update
	 * operations.
	 */
	public void testPutThenUpdate() {
		for (final float ff : FILL_FACTORS) {
			testPutThenUpdate(ff);
		}
	}

	private void testPutThenUpdate(final float fillFactor) {
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(NOT_PRESENT, map.put(i, Integer.toString(i, 36).toLowerCase()));
			assertEquals(i + 1, map.size());
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.get(i));
		}
		// now check the initial state
		assertEquals(SIZE, map.size());
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.get(i));
		}

		// now try to update all keys
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.put(i, Integer.toString(i + 1, 36).toLowerCase()));
			assertEquals(SIZE, map.size());
			assertEquals(Integer.toString(i + 1, 36).toLowerCase(), map.get(i));
		}
		// and check the final state
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(Integer.toString(i + 1, 36).toLowerCase(), map.get(i));
		}
	}

	/**
	 * Add random keys to the map. We use random seeds for the random generator
	 * (each test run is unique), so we log the seeds used to initialize
	 * Random-s.
	 */
	public void testPutRandom() {
		for (final float ff : FILL_FACTORS) {
			testPutRandom(ff);
		}
	}

	private void testPutRandom(final float fillFactor) {
		final int seed = ThreadLocalRandom.current().nextInt();
		System.out.println("testPutRandom: ff = " + fillFactor + ", seed = " + seed);
		final Random r = new Random(seed);
		final Set<Integer> set = new LinkedHashSet<>(SIZE);
		final int[] vals = new int[SIZE];
		while (set.size() < SIZE) {
			set.add(r.nextInt());
		}
		int i = 0;
		for (final Integer v : set) {
			vals[i++] = v;
		}
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		for (i = 0; i < vals.length; ++i) {
			assertEquals(NOT_PRESENT, map.put(vals[i], Integer.toString(vals[i], 36).toLowerCase()));
			assertEquals(i + 1, map.size());
			assertEquals(Integer.toString(vals[i], 36).toLowerCase(), map.get(vals[i]));
		}
		// now check the final state
		assertEquals(SIZE, map.size());
		for (i = 0; i < vals.length; ++i) {
			assertEquals(Integer.toString(vals[i], 36).toLowerCase(), map.get(vals[i]));
		}
	}

	public void testPutRandomString() {
		for (final float ff : FILL_FACTORS) {
			testPutRandomString(ff);
		}
	}

	private void testPutRandomString(final float fillFactor) {
		final int seed = ThreadLocalRandom.current().nextInt();
		System.out.println("testPutRandomString: ff = " + fillFactor + ", seed = " + seed);
		final Random r = new Random(seed);
		final Set<Integer> keySet = new LinkedHashSet<>(10 * SIZE);
		final Set<String> valSet = new LinkedHashSet<>(10 * SIZE);
		final int[] keys = new int[10 * SIZE];
		final String[] vals = new String[10 * SIZE];
		while (keySet.size() < 10 * SIZE) {
			keySet.add(r.nextInt());
		}
		while (valSet.size() < SIZE * 2) {
			valSet.add(RandomStringUtils.randomNumeric((int) (Math.random() * 20)));
		}
		while (valSet.size() < SIZE * 4) {
			valSet.add(RandomStringUtils.randomAlphanumeric((int) (Math.random() * 20)));
		}
		while (valSet.size() < SIZE * 6) {
			valSet.add(RandomStringUtils.randomNumeric((int) (Math.random() * 20)) + RandomStringUtils.randomAlphabetic(1));
		}
		while (valSet.size() < SIZE * 8) {
			valSet.add(RandomStringUtils.randomAscii((int) (Math.random() * 20)));
		}
		while (valSet.size() < SIZE * 10) {
			valSet.add(RandomStringUtils.random((int) (Math.random() * 20)));
		}
		int i = 0;
		for (final Integer v : keySet) {
			keys[i++] = v;
		}
		i = 0;
		for (String v : valSet) {
			vals[i++] = v;
		}
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		for (i = 0; i < vals.length; ++i) {
			assertEquals(NOT_PRESENT, map.put(keys[i], vals[i]));
			assertEquals(i + 1, map.size());
			assertEquals(vals[i], map.get(keys[i]));
		}
		// now check the final state
		assertEquals(10 * SIZE, map.size());
		for (i = 0; i < vals.length; ++i) {
			assertEquals(vals[i], map.get(keys[i]));
		}
	}

	/**
	 * Interleaved put and remove operations - we remove half of added entries
	 */
	public void testRemove() {
		for (final float ff : FILL_FACTORS) {
			testRemoveHelper(ff);
		}
	}

	private void testRemoveHelper(final float fillFactor) {
		final IIntObjectMap<String> map = makeMap(100, fillFactor);
		int addCnt = 0, removeCnt = 0;
		for (int i = 0; i < SIZE; ++i) {
			assertEquals(NOT_PRESENT, map.put(addCnt, Integer.toString(addCnt, 36).toLowerCase()));
			assertEquals(i + 1, map.size());
			addCnt++;

			assertEquals(NOT_PRESENT, map.put(addCnt, Integer.toString(addCnt, 36).toLowerCase()));
			assertEquals(i + 2, map.size()); // map grows by one element on each
												// iteration
			addCnt++;

			assertEquals(Integer.toString(removeCnt, 36).toLowerCase(), map.remove(removeCnt));
			removeCnt++;

			assertEquals(i + 1, map.size()); // map grows by one element on each
												// iteration
		}

		assertEquals(SIZE, map.size());
		for (int i = removeCnt; i < addCnt; ++i) {
			assertEquals(Integer.toString(i, 36).toLowerCase(), map.get(i));
		}
	}

	public void testRandomRemove() {
		for (final float ff : FILL_FACTORS) {
			testRandomRemoveHelper(ff);
		}
	}

	private void testRandomRemoveHelper(final float ff) {
		final Random r = new Random(1);
		final String[] values = new String[SIZE];
		Set<Integer> ks = new LinkedHashSet<>(SIZE);
		while (ks.size() < SIZE) {
			ks.add(r.nextInt());
		}
		final Integer[] keys = ks.toArray(new Integer[SIZE]);
		ks = null;

		assertEquals(SIZE, keys.length);

		for (int i = 0; i < SIZE; ++i) {
			values[i] = Integer.toString(r.nextInt(), 36).toLowerCase();
		}

		IIntObjectMap<String> m = makeMap(100, ff);
		int add = 0, remove = 0;
		while (add < SIZE) {
			assertEquals(NOT_PRESENT, m.put(keys[add], String.valueOf(values[add])));
			++add;
			assertEquals(NOT_PRESENT, m.put(keys[add], String.valueOf(values[add])));
			++add;

			assertEquals(String.valueOf(values[remove]), m.remove(keys[remove]));
			remove++;

			assertEquals(remove, m.size());
		}

		assertEquals(SIZE / 2, m.size());

		for (int i = 0; i < SIZE / 2; ++i) {
			assertEquals(NOT_PRESENT, m.get(keys[i]));
		}
		for (int i = SIZE / 2; i < SIZE; ++i) {
			assertEquals(String.valueOf(values[i]), m.get(keys[i]));
		}
	}

}
