import java.util.ArrayList;
import java.util.List;

// Implementation of an array-based index
public class ArrayBasedIndex {
    private final ArrayList<RecordLocation>[] arrayBasedIndex;

    public ArrayBasedIndex() {
        // Create an array of lists of record locations (size 5000)
        arrayBasedIndex = new ArrayList[5000];
        // Create a list for each index in the array
        for (int i = 0; i < arrayBasedIndex.length; i++) {
            arrayBasedIndex[i] = new ArrayList<>();
        }
    }

    /**
     * Add a record to the index
     * @param randomV the randomV value of the record
     * @param fileNumber the file number of the record
     * @param offset the offset of the record
     */
    public void addRecord(int fileNumber, int offset, int randomV) {
        // Make sure randomV is within the valid range
        if (randomV < 1 || randomV > arrayBasedIndex.length) {
            System.err.println("Could not add randomV value " + randomV + " because it is out of bounds.");
            return;
        }

        //System.out.println("Adding record with randomV " + randomV + " to index");

        RecordLocation recordLocation = new RecordLocation(fileNumber, offset);
        arrayBasedIndex[randomV-1].add(recordLocation);
    }


    /**
     * Lookup the index for a given randomV value
     * Bounds are not inclusive
     * @param v1 the lower bound randomV value to lookup
     * @param v2 the upper bound randomV value to lookup
     * @return a list of record locations or an empty list if the randomV is not in the index
     */
    public ArrayList<RecordLocation> rangeSearch(int v1, int v2) {
        ArrayList<RecordLocation> result = new ArrayList<>();
        for (int i = v1; i < v2-1; i++) {
            result.addAll(arrayBasedIndex[i]);
        }
        return result;
    }

    // get index
    public List<RecordLocation>[] getIndex() {
        return arrayBasedIndex;
    }

    /**
     * Check if the index is empty
     * @return true if the index is empty or false otherwise
     */
    public boolean isEmpty() {
        for (ArrayList<RecordLocation> recordLocations : arrayBasedIndex) {
            if (!recordLocations.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
