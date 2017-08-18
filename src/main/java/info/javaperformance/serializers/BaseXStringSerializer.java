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

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

import uk.co.maxant.util.BaseX;

public class BaseXStringSerializer implements IObjectSerializer<String> {

	private BaseX baseX;

	public BaseXStringSerializer(char[] dictionary) {
		this.baseX = new BaseX(dictionary);
	}

	@Override
	public void write(String v, ByteArray buf) {
		if (v == null) {
			buf.put(-1);
			return;
		}
		if (v.isEmpty()) {
			buf.put(0);
			return;
		}
		BigInteger bigInt = baseX.decode(v);
		byte[] bytes = bigInt.toByteArray();
		byte leadingZeros = countLeadingZeros(v);
		buf.put((byte) bytes.length);
		buf.put(bytes, 0, bytes.length);
		buf.put(leadingZeros);
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
	public String read(ByteArray buf) {
		byte len = buf.get();
		if (len == -1) {
			return null;
		}
		if (len == 0) {
			return "";
		}
		byte[] bytes = new byte[len];
		buf.get(bytes, 0, len);
		byte leadingZeros = buf.get();
		BigInteger bigInt = new BigInteger(bytes);
		String bigIntStr = baseX.encode(bigInt);
		return StringUtils.leftPad(bigIntStr, bigIntStr.length() + leadingZeros, '0');
	}

	@Override
	public void skip(ByteArray buf) {
		final byte len = buf.get();
		if (len > 0) {
			buf.position(buf.position() + len + 1);
		}
	}

	@Override
	public int getMaxLength(String obj) {
		BigInteger bigInt = baseX.decode(obj);
		return bigInt.bitLength() / 8 + 3;
	}

	public static void main(String[] args) {
		System.out.println(countLeadingZeros("07876"));
		System.out.println(countLeadingZeros("7876"));
		System.out.println(countLeadingZeros("0007876"));
		System.out.println(countLeadingZeros("00"));
	}
}
