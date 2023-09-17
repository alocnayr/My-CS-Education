/**
 * This record is used to store the file number and offset of a record
 * @param fileNumber the file number of the record
 * @param offset the offset of the record
 */
public record RecordLocation(int fileNumber, int offset) {

    // print out the file number and the record number from the offset given that each record is 40 bytes
    @Override
    public String toString() {
        return "File " + fileNumber + " Record " + (offset / 40 + 1);
    }
}
