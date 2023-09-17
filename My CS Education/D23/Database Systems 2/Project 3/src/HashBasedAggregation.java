import java.text.DecimalFormat;
import java.util.*;

// This class is used to perform hash aggregation (SUM or AVG) on dataset A or B
public class HashBasedAggregation {

    /**
     * Perform a hash aggregation (SUM or AVG) on dataset A or B
     * @param dataset dataset A or B
     * @param aggregate SUM or AVG
     * @return a map of aggregated values
     */
    public static Map<String, Double> hashAggregation(DatasetReader dataset, String aggregate) {
        // HashTable to store sum values, string is record name, double is sum value
        Map<String, Double> hashTable = new HashMap<>();
        // HashTable to store count values, string is record name, integer is count value
        Map<String, Integer> countTable = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");
        // iterate through all files in dataset
        for (int i = 1; i <= 99; i++) {
            List<Record> records = dataset.getRecords(i);
            // for each record, put sum values into hashTable
            for (Record record : records) {
                String key = record.name();
                double value = record.randomV();
                // default put sum values
                hashTable.put(key, hashTable.getOrDefault(key, 0.0) + value);
                countTable.put(key, countTable.getOrDefault(key, 0) + 1);
            }
        }

        // override sum values with avg values if function is avg
        if (aggregate.equals("AVG")) {
            hashTable.replaceAll((k, v) -> Double.valueOf(df.format(hashTable.get(k) / countTable.get(k))));
        }

        // Sort the hashTable by name (key) using a TreeMap
        return new TreeMap<>(hashTable);
    }
}
