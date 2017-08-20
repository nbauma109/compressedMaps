package info.javaperformance.serializers;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

public class GenericBigIntegerSerializerTest extends TestCase {

	public void testConcurrent() throws InterruptedException {
		final int THREADS = 32;
		final GenericBigIntegerSerializer s = new GenericBigIntegerSerializer();
		final CountDownLatch start = new CountDownLatch(THREADS);
		final CountDownLatch end = new CountDownLatch(THREADS);
		for (int i = 0; i < THREADS; ++i) {
			final int id = i;
			final Runnable task = new Runnable() {
				@Override
				public void run() {
					final ByteArray buf = new ByteArray().reset(new byte[367166]);
					start.countDown();
					try {
						start.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					buf.position(0);
					for (int j = 0; j < 100000; ++j) {
						s.write(BigInteger.valueOf(j).add(BigInteger.valueOf(id)), buf);
					}
					buf.position(0);
					for (int j = 0; j < 100000; ++j) {
						assertEquals(BigInteger.valueOf(j).add(BigInteger.valueOf(id)), s.read(buf));
					}
					System.out.println(buf.position());
					end.countDown();
				}
			};
			final Thread t = new Thread(task);
			t.start();
		}
		end.await();

	}

}
