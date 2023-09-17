import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Queries query = new Queries();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Program is ready and waiting for user command.");

        while (true) {
            String input = scanner.nextLine();
            if ("CREATE INDEX ON Project2Dataset (RandomV)".equalsIgnoreCase(input) || "c".equalsIgnoreCase(input)) {
                query.createIndexes();
                System.out.println("The hash-based and array-based indexes are built successfully.");
                System.out.println("Program is ready and waiting for user command.");
            } else if (input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV = ") || input.startsWith("=")) {
                if(input.startsWith("=")){
                    int value = Integer.parseInt(input.substring(1).trim());
                    query.equalityBasedQuery(value);
                }
                else {
                    String[] parts = input.split(" = ");
                    int value = Integer.parseInt(parts[1]);
                    query.equalityBasedQuery(value);
                }
            } else if ((input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV > ") && input.contains(" AND RandomV < ")) || Character.isDigit(input.charAt(0))) {
                if (Character.isDigit(input.charAt(0))) {
                    // Split the input by whitespace
                    String[] parts = input.trim().split("\\s+");
                    if (parts.length < 2) {
                        System.out.println("Invalid command. Please enter a valid command or type 'exit' to quit.");
                    } else {
                        int value1 = Integer.parseInt(parts[0].trim());
                        int value2 = Integer.parseInt(parts[1].trim());
                        query.rangeBasedQuery(value1, value2);
                    }
                }
                else{
                    String[] parts = input.split(" > | AND RandomV < ");
                    int value1 = Integer.parseInt(parts[1]);
                    int value2 = Integer.parseInt(parts[2]);
                    query.rangeBasedQuery(value1, value2);
                }
            } else if (input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV != ") || input.startsWith("!=")) {
                if(input.startsWith("!=")){
                    int value = Integer.parseInt(input.substring(2).trim());
                    query.inequalityBasedQuery(value);
                }
                else {
                    String[] parts = input.split(" != ");
                    int value = Integer.parseInt(parts[1]);
                    query.inequalityBasedQuery(value);
                }
            } else if ("exit".equalsIgnoreCase(input)) {
                break;
            } else {
                System.out.println("Invalid command. Please enter a valid command or type 'exit' to quit.");
            }
        }

        scanner.close();
    }
}
