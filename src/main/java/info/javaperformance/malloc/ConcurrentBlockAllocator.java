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

package info.javaperformance.malloc;

import info.javaperformance.buckets.LongBucketEncoding;
import info.javaperformance.tools.ConcurrentIntObjectMap;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Memory block allocator for concurrent maps
 */
public class ConcurrentBlockAllocator implements IBlockAllocator {
    /** Data blocks are stored here */
    //private final BlockMap m_blocks = new BlockMap();
    private final ConcurrentIntObjectMap<Block> m_blocks = new ConcurrentIntObjectMap<>( 1024 );
    /** Always take next value for block allocation */
    private final AtomicInteger m_nextBlock = new AtomicInteger( 0 );

    //must not be static - we don't want to share updateable objects
    private final ThreadLocal<Block> m_currentBlock = new ThreadLocal<>();

    @Override
    public Block getBlockByIndex( final int index )
    {
        return m_blocks.get( index );
    }

    @Override
    public void removeBlock( final int blockId )
    {
        m_blocks.remove( blockId );
    }

    /**
     * Allocate a new block of a given size.
     * @param blockSize Block size, should be defined by callers
     * @return A new block
     */
    private Block allocateNewBlock( final int blockSize )
    {
        final int id = m_nextBlock.incrementAndGet();

        final Block b = new Block( this, id, blockSize );
        m_blocks.put( id, b );
        return b;
    }

    /**
     * Get current or allocate a new thread local block
     * @param forceNew True to force a new block allocation
     * @return A block managed by a current thread
     */
    private Block getCurrentThreadBlock( final boolean forceNew )
    {
        if ( forceNew )
        {
            final Block res = allocateNewBlock( LongBucketEncoding.getBlockSize( m_blocks.size() ) );
            m_currentBlock.set( res );
            return res;
        }
        else {
            Block res = m_currentBlock.get();
            if ( res == null )
                m_currentBlock.set(res = allocateNewBlock( LongBucketEncoding.getBlockSize( m_blocks.size() ) ));
            return res;
        }
    }

    /**
     * Get a thread local block which can contain the requested amount of data
     * @param requiredSize Required space
     * @return A block
     */
    public Block getThreadLocalBlock( final int requiredSize )
    {
        Block cur = getCurrentThreadBlock(false);
        if ( !cur.hasSpace( requiredSize ) ) {
            cur.writeFinished();
            cur = getCurrentThreadBlock(true);
        }
        return cur;
    }

}
