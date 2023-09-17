import java.util.List;

public class NestedLoopJoin {

    /**
     * Perform nested loop join on datasetA and datasetB
     * @param datasetA datasetA
     * @param datasetB datasetB
     * @return count of joined records
     */
    public static int blockLevelJoin(DatasetReader datasetA, DatasetReader datasetB) {

        long startTime = System.currentTimeMillis();
        int count = 0;

        // go through all files in datasetA
        for (int i = 1; i <= 99; i++) {
            List<Record> recordsA = datasetA.getRecords(i);
            // go through all files in datasetB
            for (int j = 1; j <= 99; j++) {
                List<Record> recordsB = datasetB.getRecords(j);
                // for each record in datasetA, find records with > randomV than datasetB to join
                for (Record recordA : recordsA) {
                    for (Record recordB : recordsB) {
                        if (recordA.randomV() > recordB.randomV()) {
                            count++;
                        }
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        return count;
    }
}
