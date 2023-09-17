import java.util.ArrayList;
import java.util.List;

public class Queries {
    private final FileParser files;
    private final HashBasedIndex hashBasedIndex;
    private final ArrayBasedIndex arrayBasedIndex;

    public Queries() {
        files = new FileParser();
        hashBasedIndex = new HashBasedIndex();
        arrayBasedIndex = new ArrayBasedIndex();
    }

    /**
     * Create the indexes
     */
    public void createIndexes() {
        // Read all records from the files and add them to the indexes
        files.readAllRecords((recordName, recordLocation, randomV) -> {
            arrayBasedIndex.addRecord(recordLocation.fileNumber(), recordLocation.offset(), randomV);
            hashBasedIndex.addRecord(recordLocation.fileNumber(), recordLocation.offset(), randomV);
        });

        /*
        for(int i = 0; i < arrayBasedIndex.getIndex().length; i++){
            System.out.println(i + " " +arrayBasedIndex.getIndex()[i]);
        }
         */
    }

    /**
     * Lookup the index for a given randomV value using hash index, else table scan
     * @param value the value to lookup
     */
    public void equalityBasedQuery(int value) {

        // If the hash-based index is not empty, use it
        if (!hashBasedIndex.getIndex().isEmpty()) {
            System.out.println("Using Hash-based Index");
            long startTime = System.nanoTime();
            List<RecordLocation> recordLocations = hashBasedIndex.search(value);
            //no record found
            if(recordLocations.size() == 0){
                System.out.println("No record found");
            }
            // reading and printing out the records
            files.readSpecifiedRecords(recordLocations);

            long endTime = System.nanoTime();

            double timeTaken = endTime - startTime;
            // convert nanoseconds to milliseconds and don't truncate
            timeTaken = timeTaken / 1000000;
            System.out.println("Performed a hash-based index lookup");
            System.out.println("Time taken for the query in milli sec: " + timeTaken);
            int filesRead = files.getNumberOfFilesRead();
            System.out.println("Number of data files read: " + filesRead);
        }
        // Hash index not built, use table scan
        else {
            System.out.println("Using Table Scan");
            long startTime = System.currentTimeMillis();
            List<RecordLocation> recordLocations = new ArrayList<>();
            // Table scan needs to read all records in all files
            files.readAllRecords((recordName, recordLocation, randomV) -> {
                if (randomV == value) {
                    System.out.println("Record found: " + recordName);
                    recordLocations.add(recordLocation);
                }
            });

            if(recordLocations.size() == 0){
                System.out.println("No record found");
            }

            long endTime = System.currentTimeMillis();
            double timeTaken = endTime - startTime;
            System.out.println("Performed a table scan");
            System.out.println("Time taken for the query in milli sec: " + timeTaken);
            System.out.println("Number of data files read: " + files.getNumberOfFilesRead());
        }
    }

    /**
     * Query the files using range-based lookup
     * @param v1 the lower bound of the range
     * @param v2 the upper bound of the range
     */
    public void rangeBasedQuery(int v1, int v2) {

        // If the hash-based index is not empty, use it
        if (!arrayBasedIndex.isEmpty()) {
            System.out.println("Using Array-based Index");
            long startTime = System.nanoTime();
            List<RecordLocation> locations = arrayBasedIndex.rangeSearch(v1, v2);

            // Print the records found
            files.readSpecifiedRecords(locations);
            long endTime = System.nanoTime();

            double timeTaken = endTime - startTime;
            timeTaken = timeTaken / 1000000;
            System.out.println("Performed an array-based index lookup");
            System.out.println("Number of data files read: " + files.getNumberOfFilesRead());
            System.out.println("Time taken for the query in milli sec: " + timeTaken);
        }
        // If the array-based index is not empty, use it
        else {
            System.out.println("Using Table Scan");
            long startTime = System.currentTimeMillis();

            files.readAllRecords((record, location, randomV) -> {
                if (randomV > v1 && randomV < v2) {
                    System.out.println("Record found: " + record);
                }
            });

            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;

            System.out.println("Performed a table scan");
            System.out.println("Time taken for the query in milli sec: " + timeTaken);
            System.out.println("Number of data files read: " + files.getNumberOfFilesRead());
        }
    }

    /**
     * Query the files using inequality-based lookup
     * @param value the value to lookup
     */
    public void inequalityBasedQuery(int value) {
        long startTime = System.currentTimeMillis();

        System.out.println("Using Table Scan");
        // Print the records found
        files.readAllRecords((record, location, randomV) -> {
            if (randomV != value) {
                System.out.println("Record found: " + record);
            }
        });

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        System.out.println("Performed a table scan");
        System.out.println("Time taken for the query in milli sec: " + timeTaken);
        System.out.println("Number of data files read: " + files.getNumberOfFilesRead());
    }

}
