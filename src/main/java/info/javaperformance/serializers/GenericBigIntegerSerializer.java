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

import info.javaperformance.tools.VarLen;

/**
 * BigInteger serializer accepting an encoding as an argument
 */
public class GenericBigIntegerSerializer implements IObjectSerializer<BigInteger> {

	@Override
	public void write(final BigInteger v, final ByteArray buf) {
		if (v == null) {
			VarLen.writeSignedInt(-1, buf);
		} else if (v.equals(BigInteger.ZERO)) {
			VarLen.writeSignedInt(0, buf);
		} else {
			byte[] bytes = v.toByteArray();
			VarLen.writeSignedInt(bytes.length, buf);
			buf.put(bytes, 0, bytes.length);
		}
	}

	@Override
	public BigInteger read(final ByteArray buf) {
		final int len = VarLen.readSignedInt(buf);
		switch (len) {
		case -1:
			return null;
		case 0:
			return BigInteger.ZERO;
		default:
			byte[] bytes = new byte[len];
			buf.get(bytes, 0, len);
			return new BigInteger(bytes);
		}
	}

	@Override
	public void skip(final ByteArray buf) {
		final int len = VarLen.readSignedInt(buf);
		if (len > 0) {
			buf.position(buf.position() + len);
		}
	}

	@Override
	public int getMaxLength(final BigInteger obj) {
		return obj == null || obj.equals(BigInteger.ZERO) ? 1 : obj.bitLength() / 8 + 1;
	}

}
