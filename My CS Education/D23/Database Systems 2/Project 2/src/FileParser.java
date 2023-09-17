import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileParser {

    private int numberOfFilesRead;

    public FileParser(){
        numberOfFilesRead = 0;
    }

    /**
     * Read/parse all records in all the files
     * This is used to read records from a table scan
     * @param recordParser Functional interface to process each record
     */
    public void readAllRecords(RecordParser recordParser) {
        numberOfFilesRead = 0;
        File directoryName = new File("Project2Dataset");
        File[] files = directoryName.listFiles();
        if (files == null) {
            System.err.println("Could not find files in directory: " + directoryName);
            return;
        }

        // Read every file
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            // Skip non-text files
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                continue;
            }
            byte[] buffer = new byte[40];

            // Read file by file
            try (FileInputStream fileReader = new FileInputStream(file)) {
                int bytesRead;
                int offset = 0;

                // Increment this here (don't want to increment reading the
                // same file but different records/offsets in the same file)
                numberOfFilesRead++;
                while ((bytesRead = fileReader.read(buffer)) != -1) {
                    // Make sure always reading 40 bytes
                    if (bytesRead == 40) {
                        String record = new String(buffer, StandardCharsets.UTF_8);
                        // Get randomV value from record
                        String randomVString = record.substring(33, 37).trim();
                        //System.out.println("Found randomV value: " + randomVStr);
                        if (randomVString.isEmpty()) {
                            System.err.println("Empty randomV value " + randomVString + " found at offset " + offset + " in file " + file.getName());
                            continue;
                        }
                        int randomV = Integer.parseInt(randomVString);
                        recordParser.parseRecord(record, new RecordLocation(i+1, offset), randomV);
                    }
                   // System.out.println("Read " + bytesRead + " bytes from file " + fileNumber + " at offset " + offset);
                    offset += bytesRead;
                }
            } catch (IOException e) {
                System.err.println("Could not read file " + file.getName());
                e.printStackTrace();
            }
        }
    }

    /**
     * Read records from a specific file and offset
     *
     * @param recordLocations List of locations to read records from
     */
    public void readSpecifiedRecords(List<RecordLocation> recordLocations) {
        numberOfFilesRead = 0;
        File directoryName = new File("Project2Dataset");
        File[] files = directoryName.listFiles();

        if (files == null) {
            System.err.println("Could not find files in directory: " + directoryName);
            return;
        }

        // Group record locations by file number to reduce number of file reads
        Map<Integer, List<RecordLocation>> groupedFileNames = new HashMap<>();

        for (RecordLocation recordLocation : recordLocations) {
            int fileNumber = recordLocation.fileNumber();
            if (groupedFileNames.containsKey(fileNumber)) {
                groupedFileNames.get(fileNumber).add(recordLocation);
            } else {
                List<RecordLocation> fileLocations = new ArrayList<>();
                fileLocations.add(recordLocation);
                groupedFileNames.put(fileNumber, fileLocations);
            }
        }

        for (Map.Entry<Integer, List<RecordLocation>> entry : groupedFileNames.entrySet()) {
            int fileNumber = entry.getKey();
            List<RecordLocation> fileLocations = entry.getValue();
            File file = files[fileNumber - 1];

            if (!file.getName().toLowerCase().endsWith(".txt")) {
                System.err.println("Invalid file number: " + fileNumber + ". File number must be a text file.");
                continue;
            }

            // Used to check if the file was read successfully
            boolean fileReadSuccessfully = false;

            // Read all the locations within that file
            try (FileInputStream fileReader = new FileInputStream(file)) {
                // used to read the file at a specific offset
                FileChannel fileChannel = fileReader.getChannel();
                for (RecordLocation location : fileLocations) {
                    int offset = location.offset();
                    // Set the position directly to the offset to avoid reading the entire file
                    fileChannel.position(offset);

                    byte[] buffer = new byte[40];
                    // Make sure only reading 40 bytes
                    if (fileReader.read(buffer) == 40) {
                        fileReadSuccessfully = true;
                        System.out.println("Record found: " + new String(buffer, StandardCharsets.UTF_8));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }

            // Increment the number of files read if the file was read successfully
            if (fileReadSuccessfully) {
                numberOfFilesRead++;
            }
        }
    }




    public int getNumberOfFilesRead() {
        return numberOfFilesRead;
    }

}