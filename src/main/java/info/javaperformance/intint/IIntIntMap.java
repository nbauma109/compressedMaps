package info.javaperformance.intint;

/**
 * These methods will be implemented by all test maps
 */
public interface IIntIntMap {
    int get( final int key );
    int put( final int key, final int value );
    int remove( final int key );
    int size();
}
