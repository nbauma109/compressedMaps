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

	private IObjectSerializer<String> genericStringSerializer;
	private IObjectSerializer<String> baseXStringSerializer;
	private char[] dictionary;

	public DelegatingStringSerializer(char[] dictionary) {
		this.dictionary = dictionary;
		genericStringSerializer = new GenericStringSerializer(Charset.forName("UTF-8"));
		baseXStringSerializer = new BaseXStringSerializer(dictionary);
	}

	@Override
	public void write(String v, ByteArray buf) {
		boolean useBaseX = StringUtils.containsOnly(v, dictionary);
		if (useBaseX) {
			buf.put(1);
			baseXStringSerializer.write(v, buf);
		} else {
			buf.put(0);
			genericStringSerializer.write(v, buf);
		}
	}

	@Override
	public String read(ByteArray buf) {
		byte useBaseX = buf.get();
		if (useBaseX == 1) {
			return baseXStringSerializer.read(buf);
		} else {
			return genericStringSerializer.read(buf);
		}
	}

	@Override
	public void skip(ByteArray buf) {
		byte useBaseX = buf.get();
		if (useBaseX == 1) {
			baseXStringSerializer.skip(buf);
		} else {
			genericStringSerializer.skip(buf);
		}
	}

	@Override
	public int getMaxLength(String obj) {
		if (StringUtils.containsOnly(obj, dictionary)) {
			return 1 + baseXStringSerializer.getMaxLength(obj);
		}
		return 1 + genericStringSerializer.getMaxLength(obj);
	}
}
