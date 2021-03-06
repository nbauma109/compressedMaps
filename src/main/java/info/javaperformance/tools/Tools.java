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

package info.javaperformance.tools;

/**
 * Miscellaneous methods
 */
public class Tools {
    private static final int NO_SIGN_MASK = 0x7fffffff;

    /**
     * Murmur3 algorithm.
     * See description here: https://code.google.com/p/smhasher/wiki/MurmurHash3
     * @param h Value to process
     * @return Processed value
     */
    public static long murmur3( long h )
    {
        h ^= h >>> 33;
        h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33;
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    public static int murmur3( int h )
    {
        h ^= h >>> 16;
        h *= 0x85ebca6b;
        h ^= h >>> 13;
        h *= 0xc2b2ae35;
        h ^= h >>> 16;
        return h;
    }

    /**
     * Get bucket index for given key and capacity
     * @param key Key
     * @param capacity Map index capacity
     * @return Bucket index (a number between 0 and {@code capacity})
     */
    public static int getIndex( final long key, final int capacity )
    {
        final long v = Tools.murmur3( key );
        int i = ( int ) ( v ^ ( v >>> 32 ) );
        return ( i & NO_SIGN_MASK ) % capacity;
    }

    public static int getIndex( final int key, final int capacity )
    {
        final int v = Tools.murmur3( key );
        int i = ( v ^ ( v >>> 16 ) );
        return ( i & NO_SIGN_MASK ) % capacity;
    }

    public static int getIndexFast( final Object key, final int capacity )
    {
        final int hash = key != null ? key.hashCode() : 0;
        final int i = hash ^ ( hash >>> 16 );
        return (i & NO_SIGN_MASK ) % capacity;
    }

    public static int getIndexFast( final int key, final int capacity )
    {
        final int i = key ^ ( key >>> 16 );
        return ( i & NO_SIGN_MASK ) % capacity;
    }

    public static int getIndexFast( final long key, final int capacity )
    {
        final int i = (int) (key ^ ( key >>> 32 ));
        return ( i & NO_SIGN_MASK ) % capacity;
    }

    public static int getIndexFast( final float key, final int capacity )
    {
        return getIndexFast( Float.floatToIntBits( key ), capacity );
    }

    public static int getIndexFast( final double key, final int capacity )
    {
        return getIndexFast( Double.doubleToLongBits( key ), capacity );
    }

    /**
     * Get the next power of 2
     * @param x Value
     * @return Next power of 2 greater of equal to the argument.
     */
    public static int nextPowerOfTwo( int x ) {
   		if ( x == 0 ) return 1;
   		x--;
   		x |= x >> 1;
   		x |= x >> 2;
   		x |= x >> 4;
   		x |= x >> 8;
   		return ( x | x >> 16 ) + 1;
   	}
    /** Taken from FastUtil implementation */

    /** Return the least power of two greater than or equal to the specified value.
   	 *
   	 * <p>Note that this function will return 1 when the argument is 0.
   	 *
   	 * @param x a long integer smaller than or equal to 2<sup>62</sup>.
   	 * @return the least power of two greater than or equal to the specified value.
   	 */
   	public static long nextPowerOfTwo( long x ) {
   		if ( x == 0 ) return 1;
   		x--;
   		x |= x >> 1;
   		x |= x >> 2;
   		x |= x >> 4;
   		x |= x >> 8;
   		x |= x >> 16;
   		return ( x | x >> 32 ) + 1;
   	}
    /** Returns the least power of two smaller than or equal to 2<sup>30</sup> and larger than or equal to <code>Math.ceil( expected / f )</code>.
  	 *
  	 * @param expected the expected number of elements in a hash table.
  	 * @param f the load factor.
  	 * @return the minimum possible size for a backing array.
  	 * @throws IllegalArgumentException if the necessary size is larger than 2<sup>30</sup>.
  	 */
  	public static int arraySize( final int expected, final float f ) {
  		final long s = Math.max( 2, nextPowerOfTwo( (long)Math.ceil( expected / f ) ) );
  		if ( s > (1 << 30) ) throw new IllegalArgumentException( "Too large (" + expected + " expected elements with load factor " + f + ")" );
  		return (int)s;
  	}

   //taken from FastUtil
   private static final int INT_PHI = 0x9E3779B9;

   public static int phiMix( final int x ) {
  		final int h = x * INT_PHI;
  		return h ^ (h >> 16);
  	}


}
