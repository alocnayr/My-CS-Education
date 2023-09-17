import java.io.IOException;
import java.util.Arrays;

public class Frame {
    private byte[] content;
    private boolean dirty;
    private boolean pinned;
    private int blockId;
    public Frame() {
        initialize();
    }

    public void initialize(){
        this.content = new byte[4096]; // not sure about this since it should always be 4KB
        this.dirty = false;
        this.pinned = false;
        this.blockId = -1;
    }

    /**
     * Returns the record content given the record index.
     *
     * @param recordIndex the index of the record
     * @return the record content at the index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getRecord(int recordIndex) {

        if(recordIndex <= 0 || recordIndex > 100){
            throw new RuntimeException("Record index needs to be between 1-100");
        }
        //get the byte index of the record
        //subtract 1 for 0 based index and multiply by 40 for each record size
        int startByte = (recordIndex - 1) * 40;
        //return the part of the content array from start to end
        return new String(content, startByte, 40);
    }


    /**
     * Sets the record content given the new content and the index.
     *
     * @param recordIndex the record index
     * @param newContent the new content
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setRecord(int recordIndex, String newContent) {
        int startByte = (recordIndex - 1) * 40;
        byte[] recordBytes = newContent.getBytes();
        // copy the newContent into the array
        System.arraycopy(recordBytes, 0, content, startByte, recordBytes.length);
        dirty = true;
    }


    //Other basic getters and setters

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

}
