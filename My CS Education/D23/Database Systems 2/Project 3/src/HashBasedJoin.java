import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class is used to perform hash based join on dataset A and B
public class HashBasedJoin {

    /**
     * Perform hash join on datasetA and datasetB
     * @param datasetA datasetA
     * @param datasetB datasetB
     * @return a list of joined records in string format
     */
    public static List<String> hashJoin(DatasetReader datasetA, DatasetReader datasetB) {
        Map<Integer, List<Record>> hashTableA = buildHashTable(datasetA);
        List<String> joinedRecords = new ArrayList<>();

        // go through all files in datasetB
        for (int i = 1; i <= 99; i++) {
            List<Record> recordsB = datasetB.getRecords(i);
            if(recordsB.size() != 100){
                System.out.println("RecordsB size is not 100");
                return null;
            }
            // for each record in datasetB, find matching records in datasetA to join
            for (Record recordB : recordsB) {
                // hash into 50 buckets based on randomV
                int bucketIndex = recordB.randomV() % 50;
                List<Record> recordsA = hashTableA.get(bucketIndex);
                for (Record recordA : recordsA) {
                    // only join if randomV matches
                    if (recordA.randomV() == recordB.randomV()) {
                        // format records to include id, name, randomV
                        joinedRecords.add(recordA.id() + ", " + recordA.name() + ", "
                                + recordB.id() + ", " + recordB.name());
                    }
                }
            }
        }

        return joinedRecords;
    }

    /**
     * Build hash table for datasetA
     * @param dataset datasetA
     * @return hash table for datasetA
     */
    public static Map<Integer, List<Record>> buildHashTable(DatasetReader dataset) {
        Map<Integer, List<Record>> hashTable = new HashMap<>();
        // 50 buckets
        for (int i = 0; i < 50; i++) {
            hashTable.put(i, new ArrayList<>());
        }
        // 99 files
        for (int i = 1; i <= 99; i++) {
            List<Record> records = dataset.getRecords(i);
            for (Record record : records) {
                // Hash into 50 buckets based on randomV
                int bucketIndex = record.randomV() % 50;
                hashTable.get(bucketIndex).add(record);
            }
        }

        return hashTable;
    }


    // for testing purposes for comparing hash join output with doing full scan join
    public static List<String> fullTableScanJoin(DatasetReader datasetA, DatasetReader datasetB) {
        List<String> output = new ArrayList<>();

        for (int i = 1; i <= 99; i++) {
            System.out.println("Processing file " + i);
            List<Record> recordsB = datasetB.getRecords(i);

            for (Record recordB : recordsB) {
                for (int j = 1; j <= 99; j++) {
                    List<Record> recordsA = datasetA.getRecords(j);
                    for (Record recordA : recordsA) {
                        if (recordA.randomV() == recordB.randomV()) {
                            output.add(recordA.id() + ", " + recordA.name() + ", "
                                    + recordB.id() + ", " + recordB.name());
                        }
                    }
                }
            }
        }

        return output;
    }

}