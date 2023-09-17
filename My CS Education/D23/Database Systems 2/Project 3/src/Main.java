import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class takes in user input and calls the appropriate methods to perform the query
public class Main {

    public static void main(String[] args) {

        DatasetReader datasetA = new DatasetReader("Project3Dataset-A");
        DatasetReader datasetB = new DatasetReader("Project3Dataset-B");

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (!userInput.equalsIgnoreCase("exit")) {
            System.out.println("Enter the SQL command (type 'exit' to quit):");
            userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
            } else if (userInput.startsWith("SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV") || userInput.equalsIgnoreCase("h")) {
                System.out.println("Performing hash-based join...");
                long startTime = System.currentTimeMillis();
                List<String> hashJoin = HashBasedJoin.hashJoin(datasetA, datasetB);
                long endTime = System.currentTimeMillis();

                System.out.println("Joined records:");
                for (String record : hashJoin) {
                    System.out.println(record);
                }
                System.out.println("Performed hash-based join on dataset A and dataset B");
                System.out.println("Execution time: " + (endTime - startTime) + " ms");
                System.out.println("Total number of output records: " + hashJoin.size());


            } else if (userInput.startsWith("SELECT count(*) FROM A, B WHERE A.RandomV > B.RandomV") || userInput.equalsIgnoreCase("n")) {
                System.out.println("Performing block-level nested-loop join...");
                int count = NestedLoopJoin.blockLevelJoin(datasetA, datasetB);
                System.out.println("Count of qualifying records: " + count);
            } else {
                String selectedDataset;
                String aggregate;
                boolean validCommand = false;
                Pattern aggregationPattern = Pattern.compile("SELECT Col2, (SUM|AVG)\\(RandomV\\) FROM (A|B) GROUP BY Col2");
                Matcher matcher = aggregationPattern.matcher(userInput);
                if (matcher.matches()) {
                    selectedDataset = matcher.group(2).toUpperCase();
                    aggregate = matcher.group(1).toUpperCase();
                    validCommand = true;
                } // for shortcut commands
                else {
                    Pattern shortcutPattern = Pattern.compile("(?i)(sum|avg)[ ]*[a|b]");
                    Matcher shortcutMatcher = shortcutPattern.matcher(userInput);
                    if (shortcutMatcher.matches()) {
                        selectedDataset = userInput.toUpperCase().substring(3).trim();
                        aggregate = userInput.toUpperCase().substring(0, 3);
                        validCommand = true;
                    } else {
                        System.out.println("Invalid command. Please try again.");
                        selectedDataset = "";
                        aggregate = "";
                    }
                }

                if (validCommand) {
                    System.out.println("Performing hash-based aggregation...");
                    DatasetReader dataset = selectedDataset.equals("A") ? datasetA : datasetB;
                    long startTime = System.currentTimeMillis();
                    Map<String, Double> aggregationOutput = HashBasedAggregation.hashAggregation(dataset, aggregate);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Output records:");
                    for (Map.Entry<String, Double> entry : aggregationOutput.entrySet()) {
                        System.out.println(entry.getKey() + ", " + entry.getValue());
                    }
                    System.out.println("Execution time: " + (endTime - startTime) + " ms");
                }
            }
        }
        scanner.close();
    }
}
