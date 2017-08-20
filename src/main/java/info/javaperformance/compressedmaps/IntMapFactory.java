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

package info.javaperformance.compressedmaps;

import info.javaperformance.compressedmaps.concurrent.ints.IIntDoubleConcurrentMap;
import info.javaperformance.compressedmaps.concurrent.ints.IIntFloatConcurrentMap;
import info.javaperformance.compressedmaps.concurrent.ints.IIntIntConcurrentMap;
import info.javaperformance.compressedmaps.concurrent.ints.IIntLongConcurrentMap;
import info.javaperformance.compressedmaps.concurrent.ints.IIntObjectConcurrentMap;
import info.javaperformance.compressedmaps.concurrent.ints.IntDoubleConcurrentChainedMap;
import info.javaperformance.compressedmaps.concurrent.ints.IntFloatConcurrentChainedMap;
import info.javaperformance.compressedmaps.concurrent.ints.IntIntConcurrentChainedMap;
import info.javaperformance.compressedmaps.concurrent.ints.IntLongConcurrentChainedMap;
import info.javaperformance.compressedmaps.concurrent.ints.IntObjectConcurrentChainedMap;
import info.javaperformance.compressedmaps.normal.ints.IIntDoubleMap;
import info.javaperformance.compressedmaps.normal.ints.IIntFloatMap;
import info.javaperformance.compressedmaps.normal.ints.IIntIntMap;
import info.javaperformance.compressedmaps.normal.ints.IIntLongMap;
import info.javaperformance.compressedmaps.normal.ints.IIntObjectMap;
import info.javaperformance.compressedmaps.normal.ints.IntDoubleChainedMap;
import info.javaperformance.compressedmaps.normal.ints.IntFloatChainedMap;
import info.javaperformance.compressedmaps.normal.ints.IntIntChainedMap;
import info.javaperformance.compressedmaps.normal.ints.IntLongChainedMap;
import info.javaperformance.compressedmaps.normal.ints.IntObjectChainedMap;
import info.javaperformance.malloc.SingleThreadedBlockAllocator;
import info.javaperformance.serializers.DefaultDoubleSerializer;
import info.javaperformance.serializers.DefaultFloatSerializer;
import info.javaperformance.serializers.DefaultIntSerializer;
import info.javaperformance.serializers.DefaultLongSerializer;
import info.javaperformance.serializers.IDoubleSerializer;
import info.javaperformance.serializers.IFloatSerializer;
import info.javaperformance.serializers.IIntSerializer;
import info.javaperformance.serializers.ILongSerializer;
import info.javaperformance.serializers.IObjectSerializer;

/**
 * The entry point for all map users. This class provides the factory methods which allow you to create
 * the maps without binding yourself to the concrete implementations.
 *
 * All factories have the same constraints:
 * - fill factor between 0.01 (exclusive) and 16 (inclusive)
 * - initial size could be greater than {@code Integer.MAX_VALUE} (it makes sense because the fill factor could be greater than 1)
 */
public class IntMapFactory
{
    /////////////////////////////////////////////////////////////
    //  Single threaded maps
    /////////////////////////////////////////////////////////////

    public static IIntIntMap singleThreadedIntIntMap( final long size, final float fillFactor )
    {
        return new IntIntChainedMap( size, fillFactor,
            DefaultIntSerializer.INSTANCE, DefaultIntSerializer.INSTANCE, SingleThreadedBlockAllocator.DEFAULT_RECYCLE_BOUND );
    }

    public static IIntIntMap singleThreadedIntIntMap( final long size, final float fillFactor,
                                                   final IIntSerializer keySerializer, final IIntSerializer valueSerializer,
                                                   final long blockCacheLimit )
    {
        return new IntIntChainedMap( size, fillFactor, keySerializer, valueSerializer, blockCacheLimit );
    }

    public static IIntLongMap singleThreadedIntLongMap( final long size, final float fillFactor )
    {
        return new IntLongChainedMap( size, fillFactor,
            DefaultIntSerializer.INSTANCE, DefaultLongSerializer.INSTANCE, SingleThreadedBlockAllocator.DEFAULT_RECYCLE_BOUND );
    }

    public static IIntLongMap singleThreadedIntLongMap( final long size, final float fillFactor,
                                                   final IIntSerializer keySerializer, final ILongSerializer valueSerializer,
                                                   final long blockCacheLimit )
    {
        return new IntLongChainedMap( size, fillFactor, keySerializer, valueSerializer, blockCacheLimit );
    }

    public static IIntFloatMap singleThreadedIntFloatMap( final long size, final float fillFactor )
    {
        return new IntFloatChainedMap( size, fillFactor,
            DefaultIntSerializer.INSTANCE, DefaultFloatSerializer.INSTANCE, SingleThreadedBlockAllocator.DEFAULT_RECYCLE_BOUND );
    }

    public static IIntFloatMap singleThreadedIntFloatMap( final long size, final float fillFactor,
                                                   final IIntSerializer keySerializer, final IFloatSerializer valueSerializer,
                                                   final long blockCacheLimit )
    {
        return new IntFloatChainedMap( size, fillFactor, keySerializer, valueSerializer, blockCacheLimit );
    }

    public static IIntDoubleMap singleThreadedIntDoubleMap( final long size, final float fillFactor )
    {
        return new IntDoubleChainedMap( size, fillFactor,
            DefaultIntSerializer.INSTANCE, DefaultDoubleSerializer.INSTANCE, SingleThreadedBlockAllocator.DEFAULT_RECYCLE_BOUND );
    }

    public static IIntDoubleMap singleThreadedIntDoubleMap( final long size, final float fillFactor,
                                                   final IIntSerializer keySerializer, final IDoubleSerializer valueSerializer,
                                                   final long blockCacheLimit )
    {
        return new IntDoubleChainedMap( size, fillFactor, keySerializer, valueSerializer, blockCacheLimit );
    }

    public static <V> IIntObjectMap<V> singleThreadedIntObjectMap( final long size, final float fillFactor, final IObjectSerializer<V> valueSerializer )
    {
        return new IntObjectChainedMap<>( size, fillFactor,
            DefaultIntSerializer.INSTANCE, valueSerializer, SingleThreadedBlockAllocator.DEFAULT_RECYCLE_BOUND );
    }

    public static<V> IIntObjectMap<V> singleThreadedIntObjectMap( final long size, final float fillFactor,
                                                   final IIntSerializer keySerializer, final IObjectSerializer<V> valueSerializer,
                                                   final long blockCacheLimit )
    {
        return new IntObjectChainedMap<>( size, fillFactor, keySerializer, valueSerializer, blockCacheLimit );
    }

    /////////////////////////////////////////////////////////////
    //  Concurrent maps
    /////////////////////////////////////////////////////////////

    public static IIntIntConcurrentMap concurrentIntIntMap( final long size, final float fillFactor )
    {
        return new IntIntConcurrentChainedMap( size, fillFactor, DefaultIntSerializer.INSTANCE, DefaultIntSerializer.INSTANCE );
    }

    public static  IIntIntConcurrentMap concurrentIntIntMap( final long size, final float fillFactor,
                                                         final IIntSerializer keySerializer,
                                                         final IIntSerializer valueSerializer )
    {
        return new IntIntConcurrentChainedMap( size, fillFactor, keySerializer, valueSerializer );
    }

    public static IIntLongConcurrentMap concurrentIntLongMap( final long size, final float fillFactor )
    {
        return new IntLongConcurrentChainedMap( size, fillFactor, DefaultIntSerializer.INSTANCE, DefaultLongSerializer.INSTANCE );
    }

    public static  IIntLongConcurrentMap concurrentIntLongMap( final long size, final float fillFactor,
                                                         final IIntSerializer keySerializer,
                                                         final ILongSerializer valueSerializer )
    {
        return new IntLongConcurrentChainedMap( size, fillFactor, keySerializer, valueSerializer );
    }

    public static IIntFloatConcurrentMap concurrentIntFloatMap( final long size, final float fillFactor )
    {
        return new IntFloatConcurrentChainedMap( size, fillFactor, DefaultIntSerializer.INSTANCE, DefaultFloatSerializer.INSTANCE );
    }

    public static  IIntFloatConcurrentMap concurrentIntFloatMap( final long size, final float fillFactor,
                                                         final IIntSerializer keySerializer,
                                                         final IFloatSerializer valueSerializer )
    {
        return new IntFloatConcurrentChainedMap( size, fillFactor, keySerializer, valueSerializer );
    }

    public static IIntDoubleConcurrentMap concurrentIntDoubleMap( final long size, final float fillFactor )
    {
        return new IntDoubleConcurrentChainedMap( size, fillFactor, DefaultIntSerializer.INSTANCE, DefaultDoubleSerializer.INSTANCE );
    }

    public static  IIntDoubleConcurrentMap concurrentIntDoubleMap( final long size, final float fillFactor,
                                                         final IIntSerializer keySerializer,
                                                         final IDoubleSerializer valueSerializer )
    {
        return new IntDoubleConcurrentChainedMap( size, fillFactor, keySerializer, valueSerializer );
    }

    public static <V> IIntObjectConcurrentMap<V> concurrentIntObjectMap( final long size, final float fillFactor, final IObjectSerializer<V> valueSerializer )
    {
        return new IntObjectConcurrentChainedMap<>( size, fillFactor, DefaultIntSerializer.INSTANCE, valueSerializer );
    }

    public static <V> IIntObjectConcurrentMap<V> concurrentIntObjectMap( final long size, final float fillFactor,
                                                         final IIntSerializer keySerializer,
                                                         final IObjectSerializer<V> valueSerializer )
    {
        return new IntObjectConcurrentChainedMap<>( size, fillFactor, keySerializer, valueSerializer );
    }

}

