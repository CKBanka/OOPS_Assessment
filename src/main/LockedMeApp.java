package main;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LockedMe.com - File Management Application
 * 
 * @author ElderDragon
 * @version 1.0
 */
public class LockedMeApp {
    
    private static final String APP_NAME = "LockedMe.com";
    private static final String DEVELOPER_NAME = "ElderDragon";
    private static final String VERSION = "1.0";
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
                        System.out.println("File operations menu will be implemented in Sprint 2");
                        waitForUser();
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
                scanner.nextLine(); 
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
        System.out.println("2. File Operations (Coming in Sprint 2)");
        System.out.println("3. Exit Application");
        System.out.print("\nEnter your choice (1-3): ");
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
        scanner.nextLine(); // Clear buffer
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
        scanner.nextLine(); 
    }
    
    private static void clearScreen() {
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
}
