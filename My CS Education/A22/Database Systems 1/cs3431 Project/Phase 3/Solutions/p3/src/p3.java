/**
 * A simple solution for project phase 3. There are many possibilities including
 * ones that are more efficient with respect to the number of database calls.
 */

import java.sql.*;
import java.util.Scanner;

public class p3 {
    private static final String ORACLE_DRIVER = "jdbc:oracle:thin";
    private static final String SERVER_HOSTNAME = "csorcl.cs.wpi.edu";
    private static final int PORT = 1521;
    private static final String SERVICE_NAME = "orcl";

    public static void main(String[] args) {

        String USERID = "";
        String PASSWORD = "";
        int CHOICE = 0;

        switch (args.length) {
            case 0:
            case 1:
            default:
                System.out.println("You need to include your UserID and Password on the command line");
                return;
            case 2:
                System.out.println("Include the number of the following menu item as the third parameter on the command line.\n");
                System.out.println("1 – Report Participant  Information");
                System.out.println("2 – Report Employee Information");
                System.out.println("3 – Update Employee Password");
                return;
            case 3:
                USERID = args[0];
                PASSWORD = args[1];
                CHOICE = Integer.parseInt(args[2]);
                Connection connection = DBConnect(USERID, PASSWORD);

                switch (CHOICE) {
                    case 1:
                        ReportPatientInfo(connection);
                        break;

                    case 2:
                        ReportEmployeeInfo(connection);
                        break;

                    case 3:
                        UpdateEmployeePassword(connection);
                        break;

                    default:
                        System.out.println("Invalid argument. Choose a value from 1-3.");
                        break;
                }
                break;
        }
    }

    public static Connection DBConnect(String userID, String password) {
        // System.out.println("-------Oracle JDBC Connection Testing ---------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            // e.printStackTrace();
            return null;
        }

        // System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    ORACLE_DRIVER + ":@" + SERVER_HOSTNAME + ":" + PORT + ":" + SERVICE_NAME, userID, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

        // System.out.println("Oracle JDBC Driver Connected!");
        return connection;
    }


    /**
     * 1.   When the program is executed with an argument 1 as follows:
     *          > java p3 <username> <password> 1
     *
     *      The program now enters the “Patient Information” mode. The program should print out the following line:
     *          Enter Patient First Name : <and wait for user’s input>
     *          Enter Patient Last Name : <and wait for user’s input>
     *
     *      When the user enters the patient’s first name and last name, the program should execute a query of the
     *      Patient table, display the following results, and then terminate.
     *          Patient Information
     *          Patient ID: …
     *          Patient Name: …	(first name, space, last name)
     *          Address: …		(city followed by a comma and then state)
     *
     *          Patient ID: …		(if there is more than one result)
     *          …
     *
     *      Name should include first name and last name on a single line. Address should include city and state
     *      separated by a comma on a single line.
     */
    static void ReportPatientInfo(Connection conn) {

        // Ask for user input
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Patient First Name : ");
        String patientFirstName = input.nextLine();

        System.out.print("Enter Patient Last Name : ");
        String patientLastName = input.nextLine();

        input.close();



        try {
            // Create & Execute the query
            String query = ("SELECT patientID, firstName, lastName, city, state FROM Patient WHERE firstName = '" + patientFirstName + "' and lastName = '" + patientLastName + "'" );

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            // Get & print the results of the query
            while (rset.next()) {
                String patientID = rset.getString("patientID");
                String firstName = rset.getString("FirstName");
                String lastName = rset.getString("LastName");
                String city = rset.getString("City");
                String state = rset.getString("State");

                // Print out all the data
                System.out.println("Patient Information");
                System.out.println("Patient ID: " + patientID);
                System.out.println("Patient Name: " + firstName + " " + lastName);
                System.out.println("Patient Address: " + city + ", " + state);
                System.out.println(); // Add an extra line between results
            }

            // Close the result set & the statement
            rset.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        input.close();
    }


    /**
     * 2.   When the program is executed with an argument 2 as follows:
     *          > java p3 <username> <password> 2
     *
     *      The program enters the “Employee Information” mode. The program should print out the following line:
     *          Enter Employee ID: <and wait for user’s input>
     *
     *      When the user enters the Employee ID, the program should execute a query of the Employee and related
     *      tables and print on the screen the following Employee information as follows:
     *          Employee Information
     *          Employee ID: …
     *          NPI: …
     *          Employee Name: … (first name, space, last name)
     *          Username: …
     *          Password: …
     *          Salary Grade: …
     *          Security Clearance: …
     *
     *      Only display the NPI, Salary Grade, and Security Clearance lines if there is a non-null value. Employee
     *      name should include first name and last name on a single line. If the employee has an NPI number, then
     *      Add “Dr.” to the beginning of their name. The program terminates after this display. Display the password
     *      as this is needed for you to test part 3.
     */
    static void ReportEmployeeInfo(Connection conn) {

        // Ask for user input
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Employee ID: ");
        String id = input.nextLine();
        input.close();

        try {
            // Create & Execute the query
            String query = "SELECT employeeID, NPI, firstName, lastName, username, password, salaryGrade, securityClearance FROM Employee WHERE EmployeeID = " + id;

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            // Get & print the results of the query
            if (rset.next()) {

                int employeeID = rset.getInt("employeeID");
                String npi = rset.getString("NPI");
                String firstName = rset.getString("firstName");
                String lastName = rset.getString("lastName");
                String username = rset.getString("username");
                String password = rset.getString("password");
                int salaryGrade = rset.getInt("salaryGrade");
                String securityClearance = rset.getString("securityClearance");

                // Print out all the data
                System.out.println("Employee Information");
                System.out.println("Employee ID: " + employeeID);
                if(npi != null){
                    System.out.println("NPI: " + npi);
                    System.out.println("Employee Name: Dr." + firstName + " " + lastName);
                }
                else{
                    System.out.println("Employee Name: " + firstName + " " + lastName);
                }
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                if(salaryGrade != 0){
                    System.out.println("Salary Grade: " + salaryGrade);
                }
                if(securityClearance != null){
                    System.out.println("Security Clearance: " + securityClearance);
                }
                System.out.println(); // Add an extra line after results

                // Close the result set & the statement
                rset.close();
                stmt.close();
            }
            else {
                System.err.println("No employee with this ID found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 3.   When the program is executed with an argument 3 as follows:
     *          > java p3 <username> <password> 3
     *
     *      The program now enters the “Update Employee’s Password” mode. The program should print out
     *      the following line:
     *          Enter the employee ID: <and wait for user’s input>
     *          Enter the updated password: <and wait for user’s input>
     *
     *      Then your program should update the password for the employee ID in the Employee table.
     *      If the update statement returns 1, the program states “Your password was updated.”
     *      Then the program terminates. If you execute the program with option 2 again with the employee ID,
     *      you should see the updated password.
     */
    static void UpdateEmployeePassword(Connection conn) {

        // Ask for user input
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the employee ID: ");
        String employeeID = input.nextLine();

        System.out.print("Enter the updated password: ");
        String newPassword = input.nextLine();

        input.close();

        // Creates & executes the query
        try {

            String query =  "UPDATE Employee " +
                    "SET password = '" + newPassword +
                    "' WHERE employeeID = " + employeeID;

            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            if (result == 1)
                System.out.println("Your password was updated.");

            // Close the result set & the statement
            stmt.close();

        } catch (SQLException e) {
            System.err.println("No employee with this ID found");
        }
    }

}
