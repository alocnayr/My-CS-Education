import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class BufferPool {
    private Frame[] buffers;
    private int bufferSize;
    private int lastEvictedFrame;
    private int evictedBlockId;

    public BufferPool() {
        initialize(bufferSize);
    }

    /**
     * Initializes all buffers in the buffer pool with default values.
     */
    public void initialize(int bufferSize) {
        this.bufferSize = bufferSize;
        this.lastEvictedFrame = -1;
        this.evictedBlockId = -1;
        this.buffers = new Frame[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            Frame newFrame = new Frame();
            buffers[i] = newFrame;
            newFrame.initialize();
        }
    }


    /**
     Get the record content at the given record number.
     Content is retrieved from the buffer pool if block already in buffer pool.
     Else, empty frame is looked for or a frame is evicted and the block is read from disk.
     If neither is possible (i.e. all frame are pinned), the record cannot be retrieved.
     @param recordNumber the record number to retrieve
     */
    public void get(int recordNumber){
        int blockId = (recordNumber - 1) / 100 + 1; // find the block id
        int recordInFile = getRecordNumber(recordNumber); // find the record number in the file
        System.out.println("Record " + recordNumber + " is in file #" + blockId + " and record #" + recordInFile);
        int bufferIndex = findBlockID(blockId);
        // if block is in buffer pool, get the record
        if (bufferIndex != -1) {
            System.out.println("Block " + blockId + " is already in the buffer pool at frame # " + (bufferIndex+1));
            String recordContent = buffers[bufferIndex].getRecord(recordInFile);
            System.out.println("Record " + recordInFile + "'s content: " + recordContent);
        }
        else {
            System.out.println("Block " + blockId + " is not in buffer pool. Looking for empty buffer...");
            bufferIndex = findEmptyBuffer();
            // if empty buffer is found, read the block from disk and get the record
            if (bufferIndex != -1) {
                System.out.println("Found empty buffer at buffer frame # " + (bufferIndex+1) + ".");
                readBlockFromDisk(blockId, bufferIndex);
                System.out.println("Block " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame # " + (bufferIndex+1) + ".");
                System.out.println("Block " + blockId + " is in frame " + (bufferIndex+1) + ".");
                String recordContent = buffers[bufferIndex].getRecord(recordInFile);
                System.out.println("Record " + recordInFile + "'s content: " + recordContent);
            } else {
                System.out.println("No empty buffer available. Looking for unpinned buffer to evict...");
                int eviction = evictBlock();
                // if no unpinned buffer is found, nothing can be evicted and record content can't be retrieved
                if (eviction == -1) {
                    System.out.println("BlockId " + blockId + " cannot be read because the memory buffers are full and all frames are pinned.");
                    System.out.println("Read unsuccessful.");
                } else {
                    // an unpinned buffer was found and was evicted
                    readBlockFromDisk(blockId, eviction);
                    System.out.println("BlockId " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame # " + (eviction+1) + " after evicting block " + evictedBlockId + " from frame " + (eviction+1) + ".");
                    System.out.println("Block " + blockId + " is in frame " + (eviction+1) + ".");
                    String recordContent = buffers[eviction].getRecord(recordInFile);
                    System.out.println("Record " + recordInFile + "'s content: " + recordContent);
                }
            }
        }
    }

    /**
     Sets the content of a record at a given record number in a file.
     @param recordNumber the record number to set the content of
     @param recordContent the new content to set
     */
    public void set(int recordNumber, String recordContent){
        int blockId = (recordNumber - 1) / 100 + 1; // the block id
        int recordInFile = getRecordNumber(recordNumber); // the record number in the file
        System.out.println("Record " + recordNumber + " is in file #" + blockId + " and record #" + recordInFile);
        int bufferIndex = findBlockID(blockId);
        // if block is in buffer pool, set the record
        if (bufferIndex != -1) {
            System.out.println("Block " + blockId + " is already in the buffer pool at frame # " + (bufferIndex+1));
            String oldContent = buffers[bufferIndex].getRecord(recordInFile);
            buffers[bufferIndex].setRecord(recordInFile, recordContent);
            String newContent = buffers[bufferIndex].getRecord(recordInFile);
            System.out.println("Record " + recordNumber + " in block " + blockId + " has been changed from " + oldContent + " to " + newContent + "");
            System.out.println("Write successful.");
            buffers[bufferIndex].setDirty(true);
            System.out.println("Block " + blockId + " is now dirty.");
        }
        // look for empty buffer
        else {
            System.out.println("Block " + blockId + " is not in buffer pool. Looking for empty buffer...");
            bufferIndex = findEmptyBuffer();
            // if empty buffer is found, read the block from disk and set the record
            if (bufferIndex != -1) {
                System.out.println("Found empty buffer at buffer frame # " + (bufferIndex+1) + ".");
                readBlockFromDisk(blockId, bufferIndex);
                System.out.println("Block " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame # " + (bufferIndex+1) + ".");
                String oldContent = buffers[bufferIndex].getRecord(recordInFile);
                buffers[bufferIndex].setRecord(recordInFile, recordContent);
                String newContent = buffers[bufferIndex].getRecord(recordInFile);
                System.out.println("Record " + recordNumber + " in block " + blockId + " has been changed from " + oldContent + " to " + newContent + "");
                System.out.println("Write successful.");
                buffers[bufferIndex].setDirty(true);
                System.out.println("Block " + blockId + " is now dirty.");
            } // try to evict a block
            else {
                System.out.println("No empty buffer available. Looking for unpinned buffer to evict...");
                int eviction = evictBlock();
                // if no unpinned buffer is found, nothing can be evicted and record content can't be set
                if (eviction == -1) {
                    System.out.println("BlockId " + blockId + " cannot be read because the memory buffers are full and all frames are pinned.");
                    System.out.println("Write unsuccessful.");
                } // an unpinned buffer was found and was evicted
                else {
                    readBlockFromDisk(blockId, eviction);
                    System.out.println("BlockId " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame # " + (eviction+1) + " after evicting block " + evictedBlockId + " from frame " + (eviction+1) + ".");
                    buffers[eviction].setRecord(getRecordNumber(recordNumber), recordContent);
                    buffers[eviction].setDirty(true);
                    System.out.println("BlockId " + blockId + " is now dirty.");
                    System.out.println("BlockId " + blockId + "'s new content: " + buffers[eviction].getRecord(getRecordNumber(recordNumber)));
                    System.out.println("Write successful.");
                }
            }
        }
    }


    /**
     * Pins a block in the buffer pool.
     * Pinned blocks cannot be evicted from the buffer pool.
     *
     * @param blockId the ID of the block to be pinned
     */
    public void pinBlock(int blockId) {
        int bufferIndex = findBlockID(blockId);
        // if block is in buffer pool, pin it
        if (bufferIndex != -1) {
            System.out.println("Block " + blockId + " is already in the buffer pool at frame # " + (bufferIndex+1));
            boolean isPinned = buffers[bufferIndex].isPinned();
            // don't do anything if block is already pinned
            if(isPinned){
                System.out.println("Block " + blockId + " is already pinned.");
                return;
            }
            buffers[bufferIndex].setPinned(true);
            System.out.println("Block " + blockId + "'s pin flag was false and is now true.");
        } else {
            // block is not in buffer pool, read it from disk and add it to a buffer
            System.out.println("Block " + blockId + " is not in buffer pool. Looking for empty buffer...");
            bufferIndex = findEmptyBuffer();
            if (bufferIndex != -1) {
                System.out.println("Found empty buffer at buffer frame # " + (bufferIndex+1) + ".");
                readBlockFromDisk(blockId, bufferIndex);
                buffers[bufferIndex].setPinned(true);
                System.out.println("Block " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame # " + (bufferIndex+1) + ".");
                System.out.println("BlockId " + blockId + "'s pin flag was false is now set to " + buffers[bufferIndex].isPinned() + ".");
            } else {
                // no empty buffer available, need to evict a frame
                int eviction = evictBlock();
                if(eviction == -1){
                    System.out.println("BlockId " + blockId + " cannot be pinned because the memory buffers are full and all frames are pinned.");
                    System.out.println("Pin unsuccessful.");
                    return;
                }
                readBlockFromDisk(blockId, eviction);
                System.out.println("BlockId " + blockId + " has been successfully read from the disk and has now been added to the buffer pool at frame " + (eviction+1) + " after evicting block " + evictedBlockId + " from frame " + (eviction+1) + ".");
                buffers[eviction].setPinned(true);
                System.out.println("BlockId " + blockId + "'s pin flag was false is now set to " + buffers[eviction].isPinned() + ".");
            }
        }
    }

    /**
     * Unpin the block corresponding to the given block ID.
     * Don't do anything if the block is not in the buffer pool.
     *
     * @param blockId blockID to unpin
     */
    public void unpinBlock(int blockId) {

        int bufferIndex = findBlockID(blockId);

        if (bufferIndex != -1) {
            System.out.println("Block " + blockId + " is already in the buffer pool at frame # " + (bufferIndex+1));
            boolean alreadyPinned = buffers[bufferIndex].isPinned();
            // don't do anything if block is already unpinned
            if(!alreadyPinned){
                System.out.println("Block " + blockId + " is already unpinned.");
                return;
            }
            // unpin the block
            buffers[bufferIndex].setPinned(false);
            System.out.println("Block " + blockId + " was pinned is now unpinned.");
        } else {
            // block is not in buffer pool
            System.out.println("Block " + blockId + " is not in buffer pool. Cannot unpin it.");
        }

    }

    /**
     * Returns the index of an empty buffer in the buffer pool.
     *
     * @return the index of an empty buffer in the buffer pool, or -1 if all buffers are in use
     */
    private int findEmptyBuffer() {
        for (int i = 0; i < bufferSize; i++) {
            if (buffers[i].getBlockId() == -1) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Evicts a block from the buffer pool by selecting a frame to evict based on circular replacement.
     * If the selected frame is dirty, it writes the contents of the frame back to disk before evicting the frame.
     *
     * @return the index of the evicted frame in the buffer pool
     */
    private int evictBlock() {
        int bufferIndex = -1;
        // used for checking additional frames in the event of encountering a pinned frame
        int extraFrames = 0;
        //calculate the starting frame index for circular replacement
        //i.e. if lastEvictedFrame is 2 and bufferSize is 5, then startFrame is 3
        // because (2+1) % 5 = 3
        int startFrame = (lastEvictedFrame + 1) % bufferSize;
        //loop through all the frames in the buffer pool
        //while loop terminates when either an unpinned frame is found or all frames have been checked
        while (extraFrames < bufferSize) {
            //index of the next frame to check for eviction
            int i = (startFrame + extraFrames) % bufferSize;
            if (!buffers[i].isPinned()) {
                bufferIndex = i;
                break;
            }
            // add when a pinned frame is encountered
            extraFrames++;
        }
        //if no unpinned frame is found, return -1
        if (bufferIndex == -1) {
            return -1;
        }
        System.out.println("Found unpinned frame at frame # " + (bufferIndex+1) + " to evict.");
        //if the frame is dirty, write it back to disk
        if (buffers[bufferIndex].isDirty()) {
            try {
                String fileName = String.format("F%d.txt", buffers[bufferIndex].getBlockId());
                Path path = Paths.get(fileName);
                Files.write(path, buffers[bufferIndex].getContent());
                buffers[bufferIndex].setDirty(false);
                System.out.println("Block " + buffers[bufferIndex].getBlockId() + " was dirty and has been written back to disk before eviction.");
            } catch (IOException e) {
                System.err.println("Error writing block to disk: " + e.getMessage());
            }
        }
        this.evictedBlockId = buffers[bufferIndex].getBlockId();
        System.out.println("Evicting block " + buffers[bufferIndex].getBlockId() + " from frame # " + (bufferIndex+1) + ".");
        //reset the frame
        buffers[bufferIndex].setBlockId(-1);
        buffers[bufferIndex].setDirty(false);
        buffers[bufferIndex].setPinned(false);
        Arrays.fill(buffers[bufferIndex].getContent(), (byte) 0);
        lastEvictedFrame = bufferIndex;
        return bufferIndex;
    }

    /**
     * Searches the buffer pool for a block with the given ID.
     *
     * @param blockId the ID of the block to search for
     * @return the index of the buffer containing the block, or -1 if the block is not in the buffer pool
     */
    private int findBlockID(int blockId) {
        for (int i = 0; i < bufferSize; i++) {
            if (buffers[i].getBlockId() == blockId) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Reads the content of a block from disk and stores it in the specified buffer.
     * Throw an exception if the block cannot be read from disk.
     *
     * @param blockId     the ID of the block to read from disk
     * @param bufferIndex the index of the buffer to store the block content in
     */
    private void readBlockFromDisk(int blockId, int bufferIndex) {
        try {
            System.out.println("Reading block " + blockId + " from the disk...");
            String fileName = String.format("F%d.txt", blockId);
            Path path = Paths.get(fileName);
            byte[] blockContent = Files.readAllBytes(path);
            buffers[bufferIndex].setContent(blockContent);
            buffers[bufferIndex].setBlockId(blockId);
        } catch (IOException e) {
            System.err.println("Error reading block from disk: " + e.getMessage());
        }
    }

    /**
     * Given a record index, return the record number corresponding to that index
     *
     * @param recordNumber the record index
     * @return the corresponding record number
     */
    private int getRecordNumber(int recordNumber) {
        if (recordNumber % 100 == 0) {
            return 100;
        } else {
            return recordNumber % 100;
        }
    }



    // Other getters and setters

    public int getBufferSize() {
        return bufferSize;
    }

    public Frame[] getBuffers(){
        return buffers;
    }

    public int getLastEvictedFrame(){
        return lastEvictedFrame;
    }

}

