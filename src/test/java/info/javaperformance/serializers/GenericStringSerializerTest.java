package info.javaperformance.serializers;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

public class GenericStringSerializerTest extends TestCase {
	public void testConcurrent() throws InterruptedException {
		final int THREADS = 3;
		final GenericStringSerializer s = new GenericStringSerializer(StandardCharsets.UTF_8);
		final CountDownLatch start = new CountDownLatch(THREADS);
		final CountDownLatch end = new CountDownLatch(THREADS);
		for (int i = 0; i < THREADS; ++i) {
			final int id = i;
			final Runnable task = new Runnable() {
				@Override
				public void run() {
					final ByteArray buf = new ByteArray().reset(new byte[788890]);
					start.countDown();
					try {
						start.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					buf.position(0);
					for (int j = 0; j < 100000; ++j) {
						s.write(id + String.valueOf(j), buf);
					}
					buf.position(0);
					for (int j = 0; j < 100000; ++j) {
						assertEquals(id + String.valueOf(j), s.read(buf));
					}
					end.countDown();
				}
			};
			final Thread t = new Thread(task);
			t.start();
		}
		end.await();

	}
}
