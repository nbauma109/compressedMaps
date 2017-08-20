package info.javaperformance.serializers;

import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

public class DelegatingStringSerializerTest extends TestCase {
	public void testConcurrent() throws InterruptedException {
		final int THREADS = 3;
		final DelegatingStringSerializer s = new DelegatingStringSerializer();
		final CountDownLatch start = new CountDownLatch(THREADS);
		final CountDownLatch end = new CountDownLatch(THREADS);
		for (int i = 0; i < THREADS; ++i) {
			final int id = i;
			final Runnable task = new Runnable() {
				@Override
				public void run() {
					final ByteArray buf = new ByteArray().reset(new byte[2400]);
					start.countDown();
					try {
						start.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					buf.position(0);
					for (int j = 0; j < 500; ++j) {
						s.write(id + String.valueOf(j), buf);
					}
					buf.position(0);
					for (int j = 0; j < 500; ++j) {
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
