/**
 * A functional interface used for parsing a record.
 */
@FunctionalInterface
public interface RecordParser {
    /**
     * Parse/read a record
     * @param recordName the record to parse
     * @param recordLocation the location of the record
     * @param randomV the randomV value of the record
     */
    void parseRecord(String recordName, RecordLocation recordLocation, int randomV);
}