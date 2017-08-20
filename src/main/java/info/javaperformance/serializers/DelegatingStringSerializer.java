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

package info.javaperformance.serializers;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

public class DelegatingStringSerializer implements IObjectSerializer<String> {

	private static final char[] BASE_10 = "0123456789".toCharArray();
	private static final char[] UPPER_CASE_BASE_11 = "0123456789A".toCharArray();
	private static final char[] UPPER_CASE_BASE_12 = "0123456789AB".toCharArray();
	private static final char[] UPPER_CASE_BASE_13 = "0123456789ABC".toCharArray();
	private static final char[] UPPER_CASE_BASE_14 = "0123456789ABCD".toCharArray();
	private static final char[] UPPER_CASE_BASE_15 = "0123456789ABCDE".toCharArray();
	private static final char[] UPPER_CASE_BASE_16 = "0123456789ABCDEF".toCharArray();
	private static final char[] UPPER_CASE_BASE_17 = "0123456789ABCDEFG".toCharArray();
	private static final char[] UPPER_CASE_BASE_18 = "0123456789ABCDEFGH".toCharArray();
	private static final char[] UPPER_CASE_BASE_19 = "0123456789ABCDEFGHI".toCharArray();
	private static final char[] UPPER_CASE_BASE_20 = "0123456789ABCDEFGHIJ".toCharArray();
	private static final char[] UPPER_CASE_BASE_21 = "0123456789ABCDEFGHIJK".toCharArray();
	private static final char[] UPPER_CASE_BASE_22 = "0123456789ABCDEFGHIJKL".toCharArray();
	private static final char[] UPPER_CASE_BASE_23 = "0123456789ABCDEFGHIJKLM".toCharArray();
	private static final char[] UPPER_CASE_BASE_24 = "0123456789ABCDEFGHIJKLMN".toCharArray();
	private static final char[] UPPER_CASE_BASE_25 = "0123456789ABCDEFGHIJKLMNO".toCharArray();
	private static final char[] UPPER_CASE_BASE_26 = "0123456789ABCDEFGHIJKLMNOP".toCharArray();
	private static final char[] UPPER_CASE_BASE_27 = "0123456789ABCDEFGHIJKLMNOPQ".toCharArray();
	private static final char[] UPPER_CASE_BASE_28 = "0123456789ABCDEFGHIJKLMNOPQR".toCharArray();
	private static final char[] UPPER_CASE_BASE_29 = "0123456789ABCDEFGHIJKLMNOPQRS".toCharArray();
	private static final char[] UPPER_CASE_BASE_30 = "0123456789ABCDEFGHIJKLMNOPQRST".toCharArray();
	private static final char[] UPPER_CASE_BASE_31 = "0123456789ABCDEFGHIJKLMNOPQRSTU".toCharArray();
	private static final char[] UPPER_CASE_BASE_32 = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray();
	private static final char[] UPPER_CASE_BASE_33 = "0123456789ABCDEFGHIJKLMNOPQRSTUVW".toCharArray();
	private static final char[] UPPER_CASE_BASE_34 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWX".toCharArray();
	private static final char[] UPPER_CASE_BASE_35 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray();
	private static final char[] UPPER_CASE_BASE_36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	private static final char[] LOWER_CASE_BASE_11 = "0123456789A".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_12 = "0123456789AB".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_13 = "0123456789ABC".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_14 = "0123456789ABCD".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_15 = "0123456789ABCDE".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_16 = "0123456789ABCDEF".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_17 = "0123456789ABCDEFG".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_18 = "0123456789ABCDEFGH".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_19 = "0123456789ABCDEFGHI".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_20 = "0123456789ABCDEFGHIJ".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_21 = "0123456789ABCDEFGHIJK".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_22 = "0123456789ABCDEFGHIJKL".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_23 = "0123456789ABCDEFGHIJKLM".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_24 = "0123456789ABCDEFGHIJKLMN".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_25 = "0123456789ABCDEFGHIJKLMNO".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_26 = "0123456789ABCDEFGHIJKLMNOP".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_27 = "0123456789ABCDEFGHIJKLMNOPQ".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_28 = "0123456789ABCDEFGHIJKLMNOPQR".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_29 = "0123456789ABCDEFGHIJKLMNOPQRS".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_30 = "0123456789ABCDEFGHIJKLMNOPQRST".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_31 = "0123456789ABCDEFGHIJKLMNOPQRSTU".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_32 = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_33 = "0123456789ABCDEFGHIJKLMNOPQRSTUVW".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_34 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWX".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_35 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXY".toLowerCase().toCharArray();
	private static final char[] LOWER_CASE_BASE_36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();

	private static final int INT_BASE_10_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 10).length();
	private static final int INT_BASE_11_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 11).length();
	private static final int INT_BASE_12_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 12).length();
	private static final int INT_BASE_13_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 13).length();
	private static final int INT_BASE_14_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 14).length();
	private static final int INT_BASE_15_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 15).length();
	private static final int INT_BASE_16_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 16).length();
	private static final int INT_BASE_17_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 17).length();
	private static final int INT_BASE_18_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 18).length();
	private static final int INT_BASE_19_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 19).length();
	private static final int INT_BASE_20_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 20).length();
	private static final int INT_BASE_21_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 21).length();
	private static final int INT_BASE_22_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 22).length();
	private static final int INT_BASE_23_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 23).length();
	private static final int INT_BASE_24_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 24).length();
	private static final int INT_BASE_25_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 25).length();
	private static final int INT_BASE_26_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 26).length();
	private static final int INT_BASE_27_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 27).length();
	private static final int INT_BASE_28_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 28).length();
	private static final int INT_BASE_29_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 29).length();
	private static final int INT_BASE_30_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 30).length();
	private static final int INT_BASE_31_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 31).length();
	private static final int INT_BASE_32_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 32).length();
	private static final int INT_BASE_33_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 33).length();
	private static final int INT_BASE_34_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 34).length();
	private static final int INT_BASE_35_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 35).length();
	private static final int INT_BASE_36_MAX_LEN = Integer.toString(Integer.MAX_VALUE, 36).length();

	private static final long LONG_BASE_10_MAX_LEN = Long.toString(Long.MAX_VALUE, 10).length();
	private static final long LONG_BASE_11_MAX_LEN = Long.toString(Long.MAX_VALUE, 11).length();
	private static final long LONG_BASE_12_MAX_LEN = Long.toString(Long.MAX_VALUE, 12).length();
	private static final long LONG_BASE_13_MAX_LEN = Long.toString(Long.MAX_VALUE, 13).length();
	private static final long LONG_BASE_14_MAX_LEN = Long.toString(Long.MAX_VALUE, 14).length();
	private static final long LONG_BASE_15_MAX_LEN = Long.toString(Long.MAX_VALUE, 15).length();
	private static final long LONG_BASE_16_MAX_LEN = Long.toString(Long.MAX_VALUE, 16).length();
	private static final long LONG_BASE_17_MAX_LEN = Long.toString(Long.MAX_VALUE, 17).length();
	private static final long LONG_BASE_18_MAX_LEN = Long.toString(Long.MAX_VALUE, 18).length();
	private static final long LONG_BASE_19_MAX_LEN = Long.toString(Long.MAX_VALUE, 19).length();
	private static final long LONG_BASE_20_MAX_LEN = Long.toString(Long.MAX_VALUE, 20).length();
	private static final long LONG_BASE_21_MAX_LEN = Long.toString(Long.MAX_VALUE, 21).length();
	private static final long LONG_BASE_22_MAX_LEN = Long.toString(Long.MAX_VALUE, 22).length();
	private static final long LONG_BASE_23_MAX_LEN = Long.toString(Long.MAX_VALUE, 23).length();
	private static final long LONG_BASE_24_MAX_LEN = Long.toString(Long.MAX_VALUE, 24).length();
	private static final long LONG_BASE_25_MAX_LEN = Long.toString(Long.MAX_VALUE, 25).length();
	private static final long LONG_BASE_26_MAX_LEN = Long.toString(Long.MAX_VALUE, 26).length();
	private static final long LONG_BASE_27_MAX_LEN = Long.toString(Long.MAX_VALUE, 27).length();
	private static final long LONG_BASE_28_MAX_LEN = Long.toString(Long.MAX_VALUE, 28).length();
	private static final long LONG_BASE_29_MAX_LEN = Long.toString(Long.MAX_VALUE, 29).length();
	private static final long LONG_BASE_30_MAX_LEN = Long.toString(Long.MAX_VALUE, 30).length();
	private static final long LONG_BASE_31_MAX_LEN = Long.toString(Long.MAX_VALUE, 31).length();
	private static final long LONG_BASE_32_MAX_LEN = Long.toString(Long.MAX_VALUE, 32).length();
	private static final long LONG_BASE_33_MAX_LEN = Long.toString(Long.MAX_VALUE, 33).length();
	private static final long LONG_BASE_34_MAX_LEN = Long.toString(Long.MAX_VALUE, 34).length();
	private static final long LONG_BASE_35_MAX_LEN = Long.toString(Long.MAX_VALUE, 35).length();
	private static final long LONG_BASE_36_MAX_LEN = Long.toString(Long.MAX_VALUE, 36).length();

	private IObjectSerializer<String> genericStringSerializer;

	public DelegatingStringSerializer() {
		genericStringSerializer = new GenericStringSerializer(Charset.forName("UTF-8"));
	}

	public static byte countLeadingZeros(String v) {
		byte leadingZeros = 0;
		int i = 0;
		while (i < v.length() - 1 && v.charAt(i++) == '0') {
			leadingZeros++;
		}
		return leadingZeros;
	}

	@Override
	public void write(String v, ByteArray buf) {
		if (" ".equals(v) || "0".equals(v)) {
			buf.put(" ".equals(v) ? (byte) -1 : (byte) -2);
			return;
		}
		if (StringUtils.isEmpty(v)) {
			buf.put((byte) 0);
			genericStringSerializer.write(v, buf);
		} else if (v.length() < INT_BASE_10_MAX_LEN && StringUtils.containsOnly(v, BASE_10)) {
			buf.put((byte) 10);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 10), buf);
		} else if (v.length() < INT_BASE_11_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_11)) {
			buf.put((byte) 11);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 11), buf);
		} else if (v.length() < INT_BASE_12_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_12)) {
			buf.put((byte) 12);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 12), buf);
		} else if (v.length() < INT_BASE_13_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_13)) {
			buf.put((byte) 13);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 13), buf);
		} else if (v.length() < INT_BASE_14_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_14)) {
			buf.put((byte) 14);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 14), buf);
		} else if (v.length() < INT_BASE_15_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_15)) {
			buf.put((byte) 15);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 15), buf);
		} else if (v.length() < INT_BASE_16_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_16)) {
			buf.put((byte) 16);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 16), buf);
		} else if (v.length() < INT_BASE_17_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_17)) {
			buf.put((byte) 17);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 17), buf);
		} else if (v.length() < INT_BASE_18_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_18)) {
			buf.put((byte) 18);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 18), buf);
		} else if (v.length() < INT_BASE_19_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_19)) {
			buf.put((byte) 19);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 19), buf);
		} else if (v.length() < INT_BASE_20_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_20)) {
			buf.put((byte) 20);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 20), buf);
		} else if (v.length() < INT_BASE_21_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_21)) {
			buf.put((byte) 21);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 21), buf);
		} else if (v.length() < INT_BASE_22_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_22)) {
			buf.put((byte) 22);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 22), buf);
		} else if (v.length() < INT_BASE_23_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_23)) {
			buf.put((byte) 23);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 23), buf);
		} else if (v.length() < INT_BASE_24_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_24)) {
			buf.put((byte) 24);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 24), buf);
		} else if (v.length() < INT_BASE_25_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_25)) {
			buf.put((byte) 25);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 25), buf);
		} else if (v.length() < INT_BASE_26_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_26)) {
			buf.put((byte) 26);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 26), buf);
		} else if (v.length() < INT_BASE_27_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_27)) {
			buf.put((byte) 27);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 27), buf);
		} else if (v.length() < INT_BASE_28_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_28)) {
			buf.put((byte) 28);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 28), buf);
		} else if (v.length() < INT_BASE_29_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_29)) {
			buf.put((byte) 29);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 29), buf);
		} else if (v.length() < INT_BASE_30_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_30)) {
			buf.put((byte) 30);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 30), buf);
		} else if (v.length() < INT_BASE_31_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_31)) {
			buf.put((byte) 31);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 31), buf);
		} else if (v.length() < INT_BASE_32_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_32)) {
			buf.put((byte) 32);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 32), buf);
		} else if (v.length() < INT_BASE_33_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_33)) {
			buf.put((byte) 33);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 33), buf);
		} else if (v.length() < INT_BASE_34_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_34)) {
			buf.put((byte) 34);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 34), buf);
		} else if (v.length() < INT_BASE_35_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_35)) {
			buf.put((byte) 35);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 35), buf);
		} else if (v.length() < INT_BASE_36_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_36)) {
			buf.put((byte) 36);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 36), buf);
		} else if (v.length() < LONG_BASE_10_MAX_LEN && StringUtils.containsOnly(v, BASE_10)) {
			buf.put((byte) -10);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 10), buf);
		} else if (v.length() < LONG_BASE_11_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_11)) {
			buf.put((byte) -11);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 11), buf);
		} else if (v.length() < LONG_BASE_12_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_12)) {
			buf.put((byte) -12);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 12), buf);
		} else if (v.length() < LONG_BASE_13_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_13)) {
			buf.put((byte) -13);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 13), buf);
		} else if (v.length() < LONG_BASE_14_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_14)) {
			buf.put((byte) -14);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 14), buf);
		} else if (v.length() < LONG_BASE_15_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_15)) {
			buf.put((byte) -15);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 15), buf);
		} else if (v.length() < LONG_BASE_16_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_16)) {
			buf.put((byte) -16);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 16), buf);
		} else if (v.length() < LONG_BASE_17_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_17)) {
			buf.put((byte) -17);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 17), buf);
		} else if (v.length() < LONG_BASE_18_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_18)) {
			buf.put((byte) -18);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 18), buf);
		} else if (v.length() < LONG_BASE_19_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_19)) {
			buf.put((byte) -19);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 19), buf);
		} else if (v.length() < LONG_BASE_20_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_20)) {
			buf.put((byte) -20);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 20), buf);
		} else if (v.length() < LONG_BASE_21_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_21)) {
			buf.put((byte) -21);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 21), buf);
		} else if (v.length() < LONG_BASE_22_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_22)) {
			buf.put((byte) -22);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 22), buf);
		} else if (v.length() < LONG_BASE_23_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_23)) {
			buf.put((byte) -23);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 23), buf);
		} else if (v.length() < LONG_BASE_24_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_24)) {
			buf.put((byte) -24);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 24), buf);
		} else if (v.length() < LONG_BASE_25_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_25)) {
			buf.put((byte) -25);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 25), buf);
		} else if (v.length() < LONG_BASE_26_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_26)) {
			buf.put((byte) -26);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 26), buf);
		} else if (v.length() < LONG_BASE_27_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_27)) {
			buf.put((byte) -27);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 27), buf);
		} else if (v.length() < LONG_BASE_28_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_28)) {
			buf.put((byte) -28);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 28), buf);
		} else if (v.length() < LONG_BASE_29_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_29)) {
			buf.put((byte) -29);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 29), buf);
		} else if (v.length() < LONG_BASE_30_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_30)) {
			buf.put((byte) -30);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 30), buf);
		} else if (v.length() < LONG_BASE_31_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_31)) {
			buf.put((byte) -31);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 31), buf);
		} else if (v.length() < LONG_BASE_32_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_32)) {
			buf.put((byte) -32);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 32), buf);
		} else if (v.length() < LONG_BASE_33_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_33)) {
			buf.put((byte) -33);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 33), buf);
		} else if (v.length() < LONG_BASE_34_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_34)) {
			buf.put((byte) -34);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 34), buf);
		} else if (v.length() < LONG_BASE_35_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_35)) {
			buf.put((byte) -35);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 35), buf);
		} else if (v.length() < LONG_BASE_36_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_36)) {
			buf.put((byte) -36);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 36), buf);
		} else if (v.length() < INT_BASE_11_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_11)) {
			buf.put((byte) 101);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 11), buf);
		} else if (v.length() < INT_BASE_12_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_12)) {
			buf.put((byte) 102);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 12), buf);
		} else if (v.length() < INT_BASE_13_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_13)) {
			buf.put((byte) 103);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 13), buf);
		} else if (v.length() < INT_BASE_14_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_14)) {
			buf.put((byte) 104);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 14), buf);
		} else if (v.length() < INT_BASE_15_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_15)) {
			buf.put((byte) 105);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 15), buf);
		} else if (v.length() < INT_BASE_16_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_16)) {
			buf.put((byte) 106);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 16), buf);
		} else if (v.length() < INT_BASE_17_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_17)) {
			buf.put((byte) 107);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 17), buf);
		} else if (v.length() < INT_BASE_18_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_18)) {
			buf.put((byte) 108);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 18), buf);
		} else if (v.length() < INT_BASE_19_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_19)) {
			buf.put((byte) 109);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 19), buf);
		} else if (v.length() < INT_BASE_20_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_20)) {
			buf.put((byte) 110);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 20), buf);
		} else if (v.length() < INT_BASE_21_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_21)) {
			buf.put((byte) 111);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 21), buf);
		} else if (v.length() < INT_BASE_22_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_22)) {
			buf.put((byte) 112);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 22), buf);
		} else if (v.length() < INT_BASE_23_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_23)) {
			buf.put((byte) 113);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 23), buf);
		} else if (v.length() < INT_BASE_24_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_24)) {
			buf.put((byte) 114);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 24), buf);
		} else if (v.length() < INT_BASE_25_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_25)) {
			buf.put((byte) 115);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 25), buf);
		} else if (v.length() < INT_BASE_26_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_26)) {
			buf.put((byte) 116);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 26), buf);
		} else if (v.length() < INT_BASE_27_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_27)) {
			buf.put((byte) 117);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 27), buf);
		} else if (v.length() < INT_BASE_28_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_28)) {
			buf.put((byte) 118);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 28), buf);
		} else if (v.length() < INT_BASE_29_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_29)) {
			buf.put((byte) 119);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 29), buf);
		} else if (v.length() < INT_BASE_30_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_30)) {
			buf.put((byte) 120);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 30), buf);
		} else if (v.length() < INT_BASE_31_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_31)) {
			buf.put((byte) 121);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 31), buf);
		} else if (v.length() < INT_BASE_32_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_32)) {
			buf.put((byte) 122);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 32), buf);
		} else if (v.length() < INT_BASE_33_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_33)) {
			buf.put((byte) 123);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 33), buf);
		} else if (v.length() < INT_BASE_34_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_34)) {
			buf.put((byte) 124);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 34), buf);
		} else if (v.length() < INT_BASE_35_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_35)) {
			buf.put((byte) 125);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 35), buf);
		} else if (v.length() < INT_BASE_36_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_36)) {
			buf.put((byte) 126);
			buf.put(countLeadingZeros(v));
			DefaultIntSerializer.INSTANCE.write(Integer.parseInt(v, 36), buf);
		} else if (v.length() < LONG_BASE_11_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_11)) {
			buf.put((byte) -101);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 11), buf);
		} else if (v.length() < LONG_BASE_12_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_12)) {
			buf.put((byte) -102);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 12), buf);
		} else if (v.length() < LONG_BASE_13_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_13)) {
			buf.put((byte) -103);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 13), buf);
		} else if (v.length() < LONG_BASE_14_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_14)) {
			buf.put((byte) -104);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 14), buf);
		} else if (v.length() < LONG_BASE_15_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_15)) {
			buf.put((byte) -105);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 15), buf);
		} else if (v.length() < LONG_BASE_16_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_16)) {
			buf.put((byte) -106);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 16), buf);
		} else if (v.length() < LONG_BASE_17_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_17)) {
			buf.put((byte) -107);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 17), buf);
		} else if (v.length() < LONG_BASE_18_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_18)) {
			buf.put((byte) -108);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 18), buf);
		} else if (v.length() < LONG_BASE_19_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_19)) {
			buf.put((byte) -109);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 19), buf);
		} else if (v.length() < LONG_BASE_20_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_20)) {
			buf.put((byte) -110);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 20), buf);
		} else if (v.length() < LONG_BASE_21_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_21)) {
			buf.put((byte) -111);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 21), buf);
		} else if (v.length() < LONG_BASE_22_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_22)) {
			buf.put((byte) -112);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 22), buf);
		} else if (v.length() < LONG_BASE_23_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_23)) {
			buf.put((byte) -113);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 23), buf);
		} else if (v.length() < LONG_BASE_24_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_24)) {
			buf.put((byte) -114);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 24), buf);
		} else if (v.length() < LONG_BASE_25_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_25)) {
			buf.put((byte) -115);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 25), buf);
		} else if (v.length() < LONG_BASE_26_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_26)) {
			buf.put((byte) -116);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 26), buf);
		} else if (v.length() < LONG_BASE_27_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_27)) {
			buf.put((byte) -117);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 27), buf);
		} else if (v.length() < LONG_BASE_28_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_28)) {
			buf.put((byte) -118);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 28), buf);
		} else if (v.length() < LONG_BASE_29_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_29)) {
			buf.put((byte) -119);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 29), buf);
		} else if (v.length() < LONG_BASE_30_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_30)) {
			buf.put((byte) -120);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 30), buf);
		} else if (v.length() < LONG_BASE_31_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_31)) {
			buf.put((byte) -121);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 31), buf);
		} else if (v.length() < LONG_BASE_32_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_32)) {
			buf.put((byte) -122);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 32), buf);
		} else if (v.length() < LONG_BASE_33_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_33)) {
			buf.put((byte) -123);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 33), buf);
		} else if (v.length() < LONG_BASE_34_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_34)) {
			buf.put((byte) -124);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 34), buf);
		} else if (v.length() < LONG_BASE_35_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_35)) {
			buf.put((byte) -125);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 35), buf);
		} else if (v.length() < LONG_BASE_36_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_36)) {
			buf.put((byte) -126);
			buf.put(countLeadingZeros(v));
			DefaultLongSerializer.INSTANCE.write(Long.parseLong(v, 36), buf);
		} else {
			buf.put((byte) 0);
			genericStringSerializer.write(v, buf);
		}
	}

	@Override
	public String read(ByteArray buf) {
		switch (buf.get()) {
		case 10:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 10).toUpperCase();
		case 11:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 11).toUpperCase();
		case 12:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 12).toUpperCase();
		case 13:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 13).toUpperCase();
		case 14:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 14).toUpperCase();
		case 15:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 15).toUpperCase();
		case 16:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 16).toUpperCase();
		case 17:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 17).toUpperCase();
		case 18:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 18).toUpperCase();
		case 19:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 19).toUpperCase();
		case 20:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 20).toUpperCase();
		case 21:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 21).toUpperCase();
		case 22:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 22).toUpperCase();
		case 23:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 23).toUpperCase();
		case 24:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 24).toUpperCase();
		case 25:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 25).toUpperCase();
		case 26:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 26).toUpperCase();
		case 27:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 27).toUpperCase();
		case 28:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 28).toUpperCase();
		case 29:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 29).toUpperCase();
		case 30:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 30).toUpperCase();
		case 31:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 31).toUpperCase();
		case 32:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 32).toUpperCase();
		case 33:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 33).toUpperCase();
		case 34:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 34).toUpperCase();
		case 35:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 35).toUpperCase();
		case 36:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 36).toUpperCase();
		case -10:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 10).toUpperCase();
		case -11:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 11).toUpperCase();
		case -12:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 12).toUpperCase();
		case -13:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 13).toUpperCase();
		case -14:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 14).toUpperCase();
		case -15:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 15).toUpperCase();
		case -16:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 16).toUpperCase();
		case -17:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 17).toUpperCase();
		case -18:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 18).toUpperCase();
		case -19:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 19).toUpperCase();
		case -20:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 20).toUpperCase();
		case -21:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 21).toUpperCase();
		case -22:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 22).toUpperCase();
		case -23:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 23).toUpperCase();
		case -24:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 24).toUpperCase();
		case -25:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 25).toUpperCase();
		case -26:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 26).toUpperCase();
		case -27:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 27).toUpperCase();
		case -28:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 28).toUpperCase();
		case -29:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 29).toUpperCase();
		case -30:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 30).toUpperCase();
		case -31:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 31).toUpperCase();
		case -32:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 32).toUpperCase();
		case -33:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 33).toUpperCase();
		case -34:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 34).toUpperCase();
		case -35:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 35).toUpperCase();
		case -36:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 36).toUpperCase();
		case 101:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 11).toLowerCase();
		case 102:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 12).toLowerCase();
		case 103:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 13).toLowerCase();
		case 104:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 14).toLowerCase();
		case 105:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 15).toLowerCase();
		case 106:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 16).toLowerCase();
		case 107:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 17).toLowerCase();
		case 108:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 18).toLowerCase();
		case 109:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 19).toLowerCase();
		case 110:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 20).toLowerCase();
		case 111:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 21).toLowerCase();
		case 112:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 22).toLowerCase();
		case 113:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 23).toLowerCase();
		case 114:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 24).toLowerCase();
		case 115:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 25).toLowerCase();
		case 116:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 26).toLowerCase();
		case 117:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 27).toLowerCase();
		case 118:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 28).toLowerCase();
		case 119:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 29).toLowerCase();
		case 120:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 30).toLowerCase();
		case 121:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 31).toLowerCase();
		case 122:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 32).toLowerCase();
		case 123:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 33).toLowerCase();
		case 124:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 34).toLowerCase();
		case 125:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 35).toLowerCase();
		case 126:
			return StringUtils.repeat("0", buf.get()) + Integer.toString(DefaultIntSerializer.INSTANCE.read(buf), 36).toLowerCase();
		case -101:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 11).toLowerCase();
		case -102:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 12).toLowerCase();
		case -103:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 13).toLowerCase();
		case -104:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 14).toLowerCase();
		case -105:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 15).toLowerCase();
		case -106:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 16).toLowerCase();
		case -107:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 17).toLowerCase();
		case -108:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 18).toLowerCase();
		case -109:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 19).toLowerCase();
		case -110:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 20).toLowerCase();
		case -111:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 21).toLowerCase();
		case -112:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 22).toLowerCase();
		case -113:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 23).toLowerCase();
		case -114:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 24).toLowerCase();
		case -115:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 25).toLowerCase();
		case -116:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 26).toLowerCase();
		case -117:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 27).toLowerCase();
		case -118:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 28).toLowerCase();
		case -119:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 29).toLowerCase();
		case -120:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 30).toLowerCase();
		case -121:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 31).toLowerCase();
		case -122:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 32).toLowerCase();
		case -123:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 33).toLowerCase();
		case -124:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 34).toLowerCase();
		case -125:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 35).toLowerCase();
		case -126:
			return StringUtils.repeat("0", buf.get()) + Long.toString(DefaultLongSerializer.INSTANCE.read(buf), 36).toLowerCase();
		case -1:
			return " ";
		case -2:
			return "0";
		default:
			return genericStringSerializer.read(buf);
		}
	}

	@Override
	public void skip(ByteArray buf) {
		switch (buf.get()) {
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 101:
		case 102:
		case 103:
		case 104:
		case 105:
		case 106:
		case 107:
		case 108:
		case 109:
		case 110:
		case 111:
		case 112:
		case 113:
		case 114:
		case 115:
		case 116:
		case 117:
		case 118:
		case 119:
		case 120:
		case 121:
		case 122:
		case 123:
		case 124:
		case 125:
		case 126:
			buf.position(buf.position() + 1);
			DefaultIntSerializer.INSTANCE.skip(buf);
			return;
		case -10:
		case -11:
		case -12:
		case -13:
		case -14:
		case -15:
		case -16:
		case -17:
		case -18:
		case -19:
		case -20:
		case -21:
		case -22:
		case -23:
		case -24:
		case -25:
		case -26:
		case -27:
		case -28:
		case -29:
		case -30:
		case -31:
		case -32:
		case -33:
		case -34:
		case -35:
		case -36:
		case -101:
		case -102:
		case -103:
		case -104:
		case -105:
		case -106:
		case -107:
		case -108:
		case -109:
		case -110:
		case -111:
		case -112:
		case -113:
		case -114:
		case -115:
		case -116:
		case -117:
		case -118:
		case -119:
		case -120:
		case -121:
		case -122:
		case -123:
		case -124:
		case -125:
		case -126:
			buf.position(buf.position() + 1);
			DefaultLongSerializer.INSTANCE.skip(buf);
			return;
		case 0:
			genericStringSerializer.skip(buf);
			return;
		default:
			return;
		}
	}

	@Override
	public int getMaxLength(String v) {
		if (" ".equals(v) || "0".equals(v)) {
			return 1;
		}
		if (StringUtils.isEmpty(v)) {
			return genericStringSerializer.getMaxLength(v);
		} else if (v.length() < INT_BASE_10_MAX_LEN && StringUtils.containsOnly(v, BASE_10)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_11_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_11)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_12_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_12)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_13_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_13)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_14_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_14)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_15_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_15)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_16_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_16)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_17_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_17)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_18_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_18)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_19_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_19)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_20_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_20)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_21_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_21)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_22_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_22)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_23_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_23)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_24_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_24)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_25_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_25)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_26_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_26)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_27_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_27)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_28_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_28)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_29_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_29)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_30_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_30)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_31_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_31)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_32_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_32)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_33_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_33)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_34_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_34)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_35_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_35)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_36_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_36)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_10_MAX_LEN && StringUtils.containsOnly(v, BASE_10)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_11_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_11)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_12_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_12)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_13_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_13)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_14_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_14)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_15_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_15)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_16_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_16)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_17_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_17)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_18_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_18)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_19_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_19)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_20_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_20)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_21_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_21)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_22_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_22)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_23_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_23)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_24_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_24)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_25_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_25)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_26_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_26)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_27_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_27)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_28_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_28)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_29_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_29)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_30_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_30)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_31_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_31)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_32_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_32)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_33_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_33)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_34_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_34)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_35_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_35)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_36_MAX_LEN && StringUtils.containsOnly(v, UPPER_CASE_BASE_36)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_11_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_11)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_12_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_12)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_13_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_13)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_14_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_14)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_15_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_15)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_16_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_16)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_17_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_17)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_18_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_18)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_19_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_19)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_20_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_20)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_21_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_21)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_22_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_22)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_23_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_23)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_24_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_24)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_25_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_25)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_26_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_26)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_27_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_27)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_28_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_28)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_29_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_29)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_30_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_30)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_31_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_31)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_32_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_32)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_33_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_33)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_34_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_34)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_35_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_35)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < INT_BASE_36_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_36)) {
			return DefaultIntSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_11_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_11)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_12_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_12)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_13_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_13)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_14_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_14)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_15_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_15)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_16_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_16)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_17_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_17)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_18_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_18)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_19_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_19)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_20_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_20)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_21_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_21)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_22_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_22)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_23_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_23)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_24_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_24)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_25_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_25)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_26_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_26)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_27_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_27)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_28_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_28)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_29_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_29)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_30_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_30)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_31_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_31)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_32_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_32)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_33_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_33)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_34_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_34)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_35_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_35)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else if (v.length() < LONG_BASE_36_MAX_LEN && StringUtils.containsOnly(v, LOWER_CASE_BASE_36)) {
			return DefaultLongSerializer.INSTANCE.getMaxLength() + 2;
		} else {
			return genericStringSerializer.getMaxLength(v) + 1;
		}
	}
}
