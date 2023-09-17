import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// This class is used to read the files in the dataset
public class DatasetReader {
    private final String dataset;

    public DatasetReader(String dataset) {
        this.dataset = dataset;
    }

    /**
     * Reads and gets the records from a file
     * @param fileNumber file number
     * @return a list of records
     */
    public List<Record> getRecords(int fileNumber) {
        // "A" or "B" depending on dataset
        char datasetLetter = dataset.charAt(dataset.length() - 1);
        // e.g. "A1.txt"
        String fileName = String.format("%s%d%s", datasetLetter, fileNumber, ".txt");
        File directory = new File(dataset);
        // find the file that matches the file name
        File[] files = directory.listFiles((d, name) -> name.matches(fileName));
        List<Record> records = new ArrayList<>();

        if (files == null || files.length == 0) {
            System.out.println("File " + fileName + " does not exist");
            return records;
        }

        File file = files[0];

        // read file
        try {
            // read file line by line
            String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            // split each record by "..."
            String[] allRecords = content.split("\\.\\.\\.");
            for (String currRecord : allRecords) {
                if (!currRecord.trim().isEmpty()) {
                    records.add(Record.stringToRecord(currRecord));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
