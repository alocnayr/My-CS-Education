import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Implementation of a hash-based index
public class HashBasedIndex {

    // Map<randomV, List<Record>>
    // Record contains fileNumber and offset
    private final Map<Integer, ArrayList<RecordLocation>> hashBasedIndex;

    public HashBasedIndex() {
        hashBasedIndex = new HashMap<>();
    }

    /**
     * Add a record to the index
     * @param randomV the randomV value of the record
     * @param fileNumber the file number of the record
     * @param offset the offset of the record
     */
    public void addRecord(int fileNumber, int offset, int randomV) {
        RecordLocation record = new RecordLocation(fileNumber, offset);
        // If the randomV is already in the index, add the record to the list
        if (hashBasedIndex.containsKey(randomV)) {
            // Get the list of record locations for the randomV
            hashBasedIndex.get(randomV).add(record);
        } else {
            // If the randomV is not in the index, create a new list and add the record to the map
            ArrayList<RecordLocation> listOfRecords = new ArrayList<>();
            listOfRecords.add(record);
            hashBasedIndex.put(randomV, listOfRecords);
        }
    }

    /**
     * Lookup the index for a given randomV value
     * @param randomV the randomV value to lookup
     * @return a list of record locations or an empty list if the randomV is not in the index
     */
    public List<RecordLocation> search(int randomV) {
        return hashBasedIndex.getOrDefault(randomV, new ArrayList<>());
    }

    public Map<Integer, ArrayList<RecordLocation>> getIndex() {
    	return hashBasedIndex;
    }
}
