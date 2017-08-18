package info.javaperformance.serializers;

import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import uk.co.maxant.util.BaseX;

public class DelegatingStringSerializerTest extends TestCase {
    public void testConcurrent() throws InterruptedException {
        final int THREADS = 32;
        final DelegatingStringSerializer s = new DelegatingStringSerializer(BaseX.DICTIONARY_16);
        final CountDownLatch start = new CountDownLatch( THREADS );
        final CountDownLatch end = new CountDownLatch( THREADS );
        for ( int i = 0; i < THREADS; ++i )
        {
            final int id = i;
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    final ByteArray buf = new ByteArray().reset( new byte[ 10000000 ] );
                    start.countDown();
                    try {
                        start.await();
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                    buf.position( 0 );
                    for ( int j = 0; j < 500000; ++j )
                        s.write( id + String.valueOf( j ), buf );
                    buf.position( 0 );
                    for ( int j = 0; j < 100000; ++j )
                        assertEquals( id + String.valueOf( j ), s.read( buf ) );
                    end.countDown();
                }
            };
            final Thread t = new Thread( task );
            t.start();
        }
        end.await();

    }
}
