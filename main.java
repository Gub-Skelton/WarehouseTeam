import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        //The current files we have.
        String csvFile_UserFile = "user_1.csv";
        String csvFile_ProductFile = "products_1.csv";
        //A test user using the basic User class
        User testUser = new User("John", "Doe", "jdoe1", "password", "jdoe1@email.com", "Intern", 100.00);
        String userTable_commandList = "(Add User, Log Out, Print Table, Find Row, Find Column, Help, Change Table)";
        String productTable_commandList = "(Add Product, Log Out, Print Table, Find Row, Find Column, Help, Change Table)";
        String total_commandList = "(Add User, Add Product, Log Out, Print Table, Find Row, Find Column, Help, Change Table)";
        String totalTableList = "(User Table, Product Table)";
        String currentTable = "";

        boolean running = true;
        boolean logged_in = false;
        String session_username = "";
        String session_password = "";
        String user_action = "";
        Scanner scanner = new Scanner(System.in);
        while (running) {
            if (!logged_in) {
                System.out.println("Enter Your Username: ");
                session_username = scanner.nextLine();
                System.out.println("Enter Your Password: ");
                session_password = scanner.nextLine();

                if (findInFile(session_username, csvFile_UserFile) && findInFile(session_password, csvFile_UserFile)) {
                    System.out.println("Welcome to the Warehouse Management System.");
                    logged_in = true;
                } else {
                    System.out.println("Username and/or Password was incorrect. \n" +
                            "Unauthorized Access is prohibited.");
                    running = false;
                    break;
                }
            }
            if (currentTable.equals("")){
                currentTable = chooseTable(totalTableList);
            }
            if (currentTable.equals("User Table")) {
                System.out.println("Please select a command" + userTable_commandList + ": ");
            } else if (currentTable.equals("Product Table")) {
                System.out.println("Please select a command" + productTable_commandList + ": ");
            }
            user_action = scanner.nextLine();
            if (user_action.equals("Add User")) {
                if (currentTable.equals("User Table")) {
                    addUser(scanner, csvFile_UserFile);
                } else {
                    System.out.println("You cannot add a user to this table.");
                }
            }else if (user_action.equals("Add Product")) {
                if (currentTable.equals("Product Table")) {
                    addProduct(scanner, csvFile_ProductFile);
                } else {
                    System.out.println("You cannot add a product to this table.");
                }
            } else if (user_action.equals("Print Table")) {
                System.out.println("Which table would you like to print: " + totalTableList);
                user_action = scanner.nextLine();
                if (user_action.equals("User Table")) {
                    printTable(csvFile_UserFile);
                } else if (user_action.equals("Product Table")) {
                    printTable(csvFile_ProductFile);
                } else {
                    System.out.println("Sorry, that table does not seem to exist. Returning to menu.");
                }
            } else if (user_action.equals("Find Row")) {
                System.out.println("Enter target row value:");
                user_action = scanner.nextLine();
                if (currentTable.equals("User Table")) {
                    printRow(user_action, csvFile_UserFile);
                } else if (currentTable.equals("Product Table")) {
                    printRow(user_action, csvFile_ProductFile);
                }
            } else if (user_action.equals("Find Column")) {
                System.out.println("Enter target column value:");
                user_action = scanner.nextLine();
                if (currentTable.equals("User Table")) {
                    printCol(user_action, csvFile_UserFile);
                } else if (currentTable.equals("Product Table")) {
                    printCol(user_action, csvFile_ProductFile);
                }
            } else if (user_action.equals("Change Table")) {
                currentTable = chooseTable(totalTableList);
            } else if (user_action.equals("Help")) {
                System.out.println("Enter command name for more information" + total_commandList + ": ");
                user_action = scanner.nextLine();
                if (user_action.equals("Add User")) {
                    System.out.println("Prompts the user for a new user's details to add to the User table.");
                } else if (user_action.equals("Add Product")) {
                    System.out.println("Prompts the user for a new product's details to add to the Product table.");
                } else if (user_action.equals("Print Table")) {
                    System.out.println("Prints out the table that the user provides the name of.");
                } else if (user_action.equals("Find Row")) {
                    System.out.println("Finds all rows with the target value in it.");
                } else if (user_action.equals("Find Column")) {
                    System.out.println("Finds a specific column and prints all information in that column.");
                } else if (user_action.equals("Log Out")) {
                    System.out.println("Logs the user out.");
                } else if (user_action.equals("Change Table")) {
                    System.out.println("Changes the current table.");
                } else {
                    System.out.println("Command name not recognized. Returning to menu.");
                }
            } else if (user_action.equals("Log Out")) {
                running = false;
            } else {
                System.out.println("Command not recognized. Please try again.");
            }
        }
    }

    public static void addUser(Scanner scanner, String csvFile) throws IOException {
        boolean userAlreadyExists;
        //Collect new user details
        System.out.println("Enter the new user's first name: ");
        String newUserFirstName = scanner.nextLine();
        System.out.println("Enter the new user's last name: ");
        String newUserLastName = scanner.nextLine();
        System.out.println("Enter the new user's username: ");
        String newUserUsername = scanner.nextLine();
        //To see if a user with this username already exists
        userAlreadyExists = findInFile(newUserUsername, csvFile);
        System.out.println("Enter the new user's password: ");
        String newUserPassword = scanner.nextLine();
        System.out.println("Enter the new user's email: ");
        String newUserEmail = scanner.nextLine();
        System.out.println("Enter the new user's role: ");
        String newUserRole = scanner.nextLine();
        System.out.println("Enter the new user's salary(double): ");
        double newUserSalary = Double.parseDouble(scanner.nextLine());
        //New user object
        User newUser = new User(newUserFirstName, newUserLastName, newUserUsername, newUserPassword, newUserEmail, newUserRole, newUserSalary);
        //Open the file and add them the new user to the file
        FileWriter csvWriter_newUser = new FileWriter(csvFile, true);

        if (userAlreadyExists) {
            System.out.println("User with that username already exists. Usernames must be unique. \nPlease try again.");
            return;
        } else {
            csvWriter_newUser.write(newUser.getFirstName() + "," +
                    newUser.getLastName() + "," +
                    newUser.getUsername() + "," +
                    newUser.getPassword() + "," +
                    newUser.getEmail() + "," +
                    newUser.getRole() + "," +
                    newUser.getSalary());
            csvWriter_newUser.write("\n");
            csvWriter_newUser.close();
            System.out.println("User added successfully.");
            System.out.println("Returning to menu.");
            return;
        }
    }

    public static void addProduct(Scanner scanner, String csvFile) throws IOException {
        boolean productAlreadyExists;
        //Collect new product details
        System.out.println("Enter the new product's name:");
        String productName = scanner.nextLine();
        System.out.println("Enter the new product's ID(Product ID must be unique):");
        String productID = scanner.nextLine();
        //To see if a user with this username already exists
        productAlreadyExists = findInFile(productID, csvFile);
        System.out.println("Enter the new product's location:");
        String productLocation = scanner.nextLine();
        System.out.println("Enter the new product's price(double):");
        double productPrice = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter the new product's producer:");
        String productProducer = scanner.nextLine();
        System.out.println("Enter the new product's retailer:");
        String productRetailer = scanner.nextLine();

        //New product object
        Product newProduct = new Product(productName, productID, productLocation, productPrice, productProducer, productRetailer);
        //Open the file and add them the new product to the file
        FileWriter csvWriter_newProduct = new FileWriter(csvFile, true);

        if (productAlreadyExists) {
            System.out.println("Product with that ID already exists. ID's must be unique. \nPlease try again.");
            return;
        } else {
            csvWriter_newProduct.write(newProduct.getName() + "," +
                    newProduct.getID() + "," +
                    newProduct.getLocation() + "," +
                    newProduct.getPrice() + "," +
                    newProduct.getProducer() + "," +
                    newProduct.getRetailer());
            csvWriter_newProduct.write("\n");
            csvWriter_newProduct.close();
            System.out.println("Product added successfully.");
            System.out.println("Returning to menu.");
            return;
        }
    }


    public static boolean findInFile(String searchTerm, String csvFile) throws FileNotFoundException {
        Scanner fileInput = new Scanner(new File(csvFile));
        String line;
        while (fileInput.hasNextLine()) {
            line = fileInput.nextLine().trim();
            //Skip blank lines (if any)
            if (line.equals("")) {
                continue;
            }
            // Split the curently read line into a String Array
            // based on  the comma (,) delimiter
            String[] lineParts = line.split("\\s{0,},\\s{0,}"); // Split on any comma/space situation.
            // Iterate through the lineParts array to see if any
            // delimited portion equals the search term.
            for (int i = 0; i < lineParts.length; i++) {
                if (lineParts[i].equals(searchTerm)) {
                    // Found a match
                    return true;
                    // Get out of this loop. Don't need it anymore for this line.
                }
            }
        }
        return false;
    }

    public static void printTable(String csvFile) throws FileNotFoundException {
        //This method prints out an entire csv file.
        Scanner filePrinterScanner = new Scanner(new File(csvFile));
        String line;
        while (filePrinterScanner.hasNextLine()) {
            line = filePrinterScanner.nextLine();
            System.out.println(line);
        }
    }

    public static void printRow(String targetRowValue, String csvFile) throws FileNotFoundException {
        Scanner rowScanner = new Scanner(new File(csvFile));
        String line;
        while (rowScanner.hasNextLine()) {
            line = rowScanner.nextLine();
            if (line.contains(targetRowValue)) {
                System.out.println(line);
            }
        }
    }

    public static void printCol(String targetColValue, String csvFile) throws FileNotFoundException {
        Scanner colScanner = new Scanner(new File(csvFile));
        String line = colScanner.nextLine().trim();
        String[] lineParts = line.split("\\s{0,},\\s{0,}"); // Split on any comma/space situation.
        int colNum = -1;
        for (int i = 0; i < lineParts.length; i++) {
            if (lineParts[i].equals(targetColValue)) {
                colNum = i;
            }
        }
        int rowIterator = 0;
        if (colNum != -1) {
            while (colScanner.hasNextLine()) {
                line = colScanner.nextLine().trim();
                lineParts = line.split("\\s{0,},\\s{0,}");
                System.out.println(lineParts[colNum]);
            }
        } else if (colNum == -1) {
            System.out.println("Column name unrecognized.");
        }
    }

    public static String chooseTable(String totalTableList) {
        System.out.println("Please choose which to work with: " + totalTableList);
        while (true) {
            Scanner tableScanner = new Scanner(System.in);
            String userInput = tableScanner.nextLine();
            if (totalTableList.contains(userInput)) {
                return userInput;
            } else {
                System.out.println("Please enter a valid table.");
            }
        }
    }
}


