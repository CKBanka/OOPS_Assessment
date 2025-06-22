package main;


import java.util.*;

/**
 * LockedMe.com - File Management Application * 
 * 
 * @author ElderDragon
 * @version 2.0
 */
public class LockedMeApp {
    
    private static final String APP_NAME = "LockedMe.com";
    private static final String DEVELOPER_NAME = "ElderDragon";
    private static final String VERSION = "2.0";
    private static final String ROOT_DIRECTORY = "./lockedme_files";
    
    private static Scanner scanner = new Scanner(System.in);
    
    private static FileManager fileManager = new FileManager(ROOT_DIRECTORY);
    
    public static void main(String[] args) {
        try {
            initializeApplication();
            displayWelcomeScreen();
            runMainMenu();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void initializeApplication() {
        System.out.println("Initializing " + APP_NAME + "...");
        fileManager.createRootDirectory();
        System.out.println("Application initialized successfully!\n");
    }

    private static void displayWelcomeScreen() {
        System.out.println("=".repeat(60));
        System.out.println("           WELCOME TO " + APP_NAME);
        System.out.println("=".repeat(60));
        System.out.println("Application Name: " + APP_NAME);
        System.out.println("Developer: " + DEVELOPER_NAME);
        System.out.println("Version: " + VERSION);
        System.out.println("=".repeat(60));
        System.out.println("\nFile Management System");
        System.out.println("Manage your files with ease!");
        System.out.println("\n" + "=".repeat(60) + "\n");
    }

    private static void runMainMenu() {
        boolean running = true;
        
        while (running) {
            try {
                displayMainMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                        displayFilesInAscendingOrder();
                        break;
                    case 2:
                        runFileOperationsMenu();
                        break;
                    case 3:
                        running = exitApplication();
                        break;
                    default:
                        System.out.println("Invalid option! Please select a valid option (1-3).");
                        waitForUser();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear invalid input
                waitForUser();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                waitForUser();
            }
        }
    }
    

    private static void displayMainMenu() {
        clearScreen();
        System.out.println("MAIN MENU");
        System.out.println("---------");
        System.out.println("1. Display Files in Ascending Order");
        System.out.println("2. File Operations");
        System.out.println("3. Exit Application");
        System.out.print("\nEnter your choice (1-3): ");
    }
    

    private static void runFileOperationsMenu() {
        boolean inSubMenu = true;
        
        while (inSubMenu) {
            try {
                displayFileOperationsMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                        addFile();
                        break;
                    case 2:
                        deleteFile();
                        break;
                    case 3:
                        searchFile();
                        break;
                    case 4:
                        inSubMenu = false; 
                        break;
                    default:
                        System.out.println("Invalid option! Please select a valid option (1-4).");
                        waitForUser();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                scanner.nextLine(); 
                waitForUser();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                waitForUser();
            }
        }
    }
    

    private static void displayFileOperationsMenu() {
        clearScreen();
        System.out.println("FILE OPERATIONS MENU");
        System.out.println("=".repeat(20));
        System.out.println("1. Add a File");
        System.out.println("2. Delete a File");
        System.out.println("3. Search for a File");
        System.out.println("4. Return to Main Menu");
        System.out.print("\nEnter your choice (1-4): ");
    }
    

    private static void addFile() {
        clearScreen();
        System.out.println("ADD FILE");
        System.out.println("=".repeat(15));
        
        try {
            System.out.print("Enter the file name to add (with extension): ");
            scanner.nextLine();
            String fileName = scanner.nextLine().trim();
            
            if (!InputValidator.isValidFileName(fileName)) {
                System.out.println("Error: Invalid file name!");
                System.out.println("File names cannot contain: < > : \" / \\ | ? *");
                System.out.println("File name cannot be empty.");
                waitForUser();
                return;
            }
            
            if (fileManager.fileExists(fileName)) {
                System.out.println("Warning: File '" + fileName + "' already exists!");
                System.out.print("Do you want to overwrite it? (y/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                
                if (!confirmation.equals("y") && !confirmation.equals("yes")) {
                    System.out.println("File addition cancelled.");
                    waitForUser();
                    return;
                }
            }
            
            boolean success = fileManager.addFile(fileName);
            
            if (success) {
                System.out.println("Success: File '" + fileName + "' has been added successfully!");
                System.out.println("Location: " + ROOT_DIRECTORY + "/" + fileName);
            } else {
                System.out.println("Error: Failed to add file '" + fileName + "'");
            }
            
        } catch (FileOperationException e) {
            System.out.println("File Operation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        waitForUser();
    }
    

    private static void deleteFile() {
        clearScreen();
        System.out.println("DELETE FILE");
        System.out.println("=".repeat(15));
        
        try {
            List<String> files = fileManager.getFilesInAscendingOrder();
            
            if (files.isEmpty()) {
                System.out.println("No files available to delete.");
                waitForUser();
                return;
            }
            
            System.out.println("Available files:");
            System.out.println("-".repeat(20));
            for (int i = 0; i < files.size(); i++) {
                System.out.println((i + 1) + ". " + files.get(i));
            }
            System.out.println("-".repeat(20));
            
            System.out.print("\nEnter the exact file name to delete (case-sensitive): ");
            scanner.nextLine(); 
            String fileName = scanner.nextLine().trim();
            
            if (fileName.isEmpty()) {
                System.out.println("Error: File name cannot be empty!");
                waitForUser();
                return;
            }
            
            if (!fileManager.fileExistsCaseSensitive(fileName)) {
                System.out.println("Error: File Not Found (FNF)");
                System.out.println("The file '" + fileName + "' does not exist in the directory.");
                System.out.println("Note: File names are case-sensitive for deletion.");
                waitForUser();
                return;
            }
            
            System.out.print("Are you sure you want to delete '" + fileName + "'? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("y") || confirmation.equals("yes")) {
                boolean success = fileManager.deleteFile(fileName);
                
                if (success) {
                    System.out.println("Success: File '" + fileName + "' has been deleted successfully!");
                } else {
                    System.out.println("Error: Failed to delete file '" + fileName + "'");
                }
            } else {
                System.out.println("File deletion cancelled.");
            }
            
        } catch (FileOperationException e) {
            System.out.println("File Operation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        waitForUser();
    }
    

    private static void searchFile() {
        clearScreen();
        System.out.println("SEARCH FILE");
        System.out.println("=".repeat(15));
        
        try {
            System.out.print("Enter the file name to search (case-sensitive): ");
            scanner.nextLine();
            String fileName = scanner.nextLine().trim();
            
            if (fileName.isEmpty()) {
                System.out.println("Error: File name cannot be empty!");
                waitForUser();
                return;
            }
            
            SearchResult result = fileManager.searchFile(fileName);
            
            if (result.isFound()) {
                System.out.println("SEARCH SUCCESSFUL");
                System.out.println("=".repeat(20));
                System.out.println("File Name: " + result.getFileName());
                System.out.println("Full Path: " + result.getFullPath());
                System.out.println("File Size: " + ApplicationUtils.formatFileSize(result.getFileSize()));
                System.out.println("Last Modified: " + result.getLastModified());
                System.out.println("Status: File found successfully!");
            } else {
                System.out.println("SEARCH UNSUCCESSFUL");
                System.out.println("=".repeat(20));
                System.out.println("File '" + fileName + "' was not found in the directory.");
                System.out.println("Note: Search is case-sensitive.");
                
                List<String> similarFiles = fileManager.findSimilarFiles(fileName);
                if (!similarFiles.isEmpty()) {
                    System.out.println("\nDid you mean one of these files?");
                    for (String similar : similarFiles) {
                        System.out.println("  - " + similar);
                    }
                }
            }
            
        } catch (FileOperationException e) {
            System.out.println("File Operation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        waitForUser();
    }
    

    private static int getUserChoice() {
        return scanner.nextInt();
    }
    
 
    private static void displayFilesInAscendingOrder() {
        clearScreen();
        System.out.println("FILES IN ASCENDING ORDER");
        System.out.println("=".repeat(40));
        
        try {
            List<String> files = fileManager.getFilesInAscendingOrder();
            
            if (files.isEmpty()) {
                System.out.println("No files found in the directory.");
                System.out.println("Directory path: " + ROOT_DIRECTORY);
            } else {
                System.out.println("Found " + files.size() + " file(s):");
                System.out.println("-".repeat(40));
                
                for (int i = 0; i < files.size(); i++) {
                    System.out.println((i + 1) + ". " + files.get(i));
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error retrieving files: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(40));
        waitForUser();
    }
    

    private static boolean exitApplication() {
        clearScreen();
        System.out.println("Are you sure you want to exit? (y/n): ");
        scanner.nextLine(); 
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            System.out.println("\nThank you for using " + APP_NAME + "!");
            System.out.println("Goodbye!");
            return false;
        } else {
            return true;
        }
    }
    

    private static void waitForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine(); 
    }

    private static void clearScreen() {
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
}