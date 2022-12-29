import java.sql.*;
import java.util.Scanner;

public class p3 {
    private static String USERID;
    private static String PASSWORD;

    public static void main(String[] args) {

        if (args.length >= 2) {
            USERID = args[0];
            PASSWORD = args[1];
        }  else {
            System.out.println("You need to include your UserID and Password parameters on the command line");
            return;
        }

        if (args.length > 3) {
            System.out.println("Too many arguments");
            return;
        }

        //System.out.println("-------Oracle JDBC COnnection Testing ---------");
        try {
            // Register the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        //System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            // create the connection string
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl", USERID, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        //System.out.println("Oracle JDBC Driver Connected!");

        // Performing the query
        try {
            Scanner scn = new Scanner(System.in);

            Statement stmt = connection.createStatement();

            if (args.length == 2) {
                System.out.println("Include the number of the following menu item as the third parameter on the command line.\n \n" +
                        "1 – Report Patient Information\n" +
                        "2 – Report Employee Information\n" +
                        "3 – Update Employee’s Password\n");
                return;
            } else {
                if (args[2].equals("1")) {
                    System.out.println("Enter Patient First Name:");
                    String firstName = scn.nextLine();
                    System.out.println("Enter Patient Last Name:");
                    String lastName = scn.nextLine();

                    PreparedStatement patientInfo = connection.prepareStatement("SELECT patientID, firstName || ' ' || lastName as FullName, city || ', ' || state as Address FROM PATIENT WHERE firstName = ? AND lastName = ?");
                    patientInfo.setString(1, firstName);
                    patientInfo.setString(2, lastName);
                    ResultSet rsetb = patientInfo.executeQuery();

                    int patientID;
                    String fullName, address;

                    System.out.println("Patient Information");

                    while (rsetb.next()) {
                        patientID = rsetb.getInt("patientID");
                        fullName = rsetb.getString("FullName");
                        address = rsetb.getString("Address");

                        System.out.println("Patient ID: " + patientID + "\n" + "Patient Name: " + fullName + "\n" + "Address: " + address + "\n");
                    }
                    rsetb.close();
                }
                else if (args[2].equals("2")) {
                    System.out.println("Enter Employee ID:");
                    int employeeID = scn.nextInt();

                    PreparedStatement employeeInfo = connection.prepareStatement("SELECT employeeID, NPI, firstName || ' ' || lastName as FullName, username, password, salaryGrade, securityClearance FROM EMPLOYEE WHERE employeeID = ?");
                    employeeInfo.setInt(1, employeeID);
                    ResultSet rsetc = employeeInfo.executeQuery();

                    int ID;
                    String fullName, username, password, securityClearance, NPI, salaryGrade;

                    while (rsetc.next()) {
                        ID = rsetc.getInt("employeeID");
                        NPI = rsetc.getString("NPI");
                        fullName = rsetc.getString("FullName");
                        username = rsetc.getString("username");
                        password = rsetc.getString("password");
                        salaryGrade = rsetc.getString("salaryGrade");
                        securityClearance = rsetc.getString("securityClearance");

                        if ( NPI != null && salaryGrade != null && securityClearance != null) {
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "NPI: " + NPI + "\n" + "Employee Name: Dr. " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Salary Grade: " + salaryGrade + "\n" + "Security Clearance: " + securityClearance + "\n");
                        } else if (NPI != null && salaryGrade != null){
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "NPI: " + NPI + "\n" + "Employee Name: Dr. " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Salary Grade: " + salaryGrade + "\n");
                        } else if (NPI != null && securityClearance != null){
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "NPI: " + NPI + "\n" + "Employee Name: Dr. " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Security Clearance: " + securityClearance + "\n");
                        } else if (salaryGrade != null && securityClearance != null){
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "Employee Name: " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Salary Grade: " + salaryGrade + "\n" + "Security Clearance: " + securityClearance + "\n");
                        } else if (NPI != null){
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "NPI: " + NPI + "\n" + "Employee Name: Dr. " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n");
                        } else if (salaryGrade != null) {
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "Employee Name: " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Salary Grade: " + salaryGrade + "\n");
                        } else if (securityClearance != null){
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "Employee Name: " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n" + "Security Clearance: " + securityClearance + "\n");
                        } else {
                            System.out.println("Employee Information\n" + "Employee ID: " + employeeID + "\n" + "Employee Name: " + fullName + "\n" + "Username: " + username + "\n" + "Password: " + password + "\n");
                        }
                    }

                    rsetc.close();
                }
                else if (args[2].equals("3")) {
                    System.out.println("Enter the employee ID:");
                    int employeeID = scn.nextInt();
                    scn.nextLine();

                    System.out.println("Enter the updated password:");
                    String updPassword = scn.nextLine();

                    String update = "UPDATE EMPLOYEE SET password = '"+ updPassword + "' WHERE employeeID = " + employeeID;

                    int count = stmt.executeUpdate(update);
                    if (count == 1){
                        System.out.println("Your password was updated.");
                    }
                } else {
                    return;
                }
            }
            stmt.close();
            connection.close();
            return;
        } catch (SQLException e) {
        System.out.println("Get Data Failed! Check output console");
        e.printStackTrace();
        return;
        }
    }
}