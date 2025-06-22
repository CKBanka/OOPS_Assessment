package main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    
    private static final Pattern VALID_FILENAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9._\\-\\s]*[a-zA-Z0-9]$|^[a-zA-Z0-9]$");
    
    private static final Pattern UNSAFE_FILENAME_PATTERN = 
        Pattern.compile(".*[<>:\"/\\\\|\\?\\*].*");
    
    private static final String[] RESERVED_NAMES = {
        "CON", "PRN", "AUX", "NUL",
        "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
        "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"
    };
    
    private static final int MAX_FILENAME_LENGTH = 255;
    private static final int MAX_ATTEMPTS = 3;

    public int getValidMenuChoice(Scanner scanner, int minOption, int maxOption) {
        int attempts = 0;
        
        while (attempts < MAX_ATTEMPTS) {
            try {
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.print("Input cannot be empty. Please enter a number (" + 
                                   minOption + "-" + maxOption + "): ");
                    attempts++;
                    continue;
                }
                
                int choice = Integer.parseInt(input);
                
                if (choice >= minOption && choice <= maxOption) {
                    return choice;
                } else {
                    System.out.print("Invalid option. Please select a number between " + 
                                   minOption + " and " + maxOption + ": ");
                }
                
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number (" + 
                               minOption + "-" + maxOption + "): ");
            }
            
            attempts++;
        }
        
        System.out.println("Maximum attempts exceeded. Returning to previous menu.");
        return -1; 
    }

    public boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.err.println("Validation Error: Filename cannot be empty");
            return false;
        }
        
        String trimmedName = fileName.trim();
        
        if (trimmedName.length() > MAX_FILENAME_LENGTH) {
            System.err.println("Validation Error: Filename too long (max " + 
                             MAX_FILENAME_LENGTH + " characters)");
            return false;
        }
        
        if (UNSAFE_FILENAME_PATTERN.matcher(trimmedName).matches()) {
            System.err.println("Validation Error: Filename contains invalid characters");
            System.err.println("Invalid characters: < > : \" / \\ | ? *");
            return false;
        }
        
        String nameWithoutExtension = trimmedName.contains(".") ? 
            trimmedName.substring(0, trimmedName.lastIndexOf(".")) : trimmedName;
        
        for (String reserved : RESERVED_NAMES) {
            if (reserved.equalsIgnoreCase(nameWithoutExtension)) {
                System.err.println("Validation Error: '" + trimmedName + 
                                 "' is a reserved filename");
                return false;
            }
        }
        if (!VALID_FILENAME_PATTERN.matcher(trimmedName).matches()) {
            System.err.println("Validation Error: Invalid filename format");
            System.err.println("Filename should start and end with alphanumeric characters");
            return false;
        }
        if (trimmedName.startsWith(" ") || trimmedName.endsWith(" ") ||
            trimmedName.startsWith(".") || trimmedName.endsWith(".")) {
            System.err.println("Validation Error: Filename cannot start or end with spaces or dots");
            return false;
        }
        return true;
    }

    public String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "";
        }
        String sanitized = fileName.trim();
        sanitized = sanitized.replaceAll("[<>:\"/\\\\|\\?\\*]", "_");
        sanitized = sanitized.replaceAll("^[.\\s]+|[.\\s]+$", "");
        if (sanitized.length() > MAX_FILENAME_LENGTH) {
            sanitized = sanitized.substring(0, MAX_FILENAME_LENGTH);
        }
        if (sanitized.isEmpty()) {
            sanitized = "unnamed_file";
        }
        
        return sanitized;
    }

    public String getValidFileName(Scanner scanner, String promptMessage) {
        int attempts = 0;
        
        System.out.print(promptMessage + ": ");
        
        while (attempts < MAX_ATTEMPTS) {
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("cancel") || input.equalsIgnoreCase("exit")) {
                System.out.println("Operation cancelled.");
                return null;
            }
            
            if (isValidFileName(input)) {
                return input;
            }
            
            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.print("Please try again (or type 'cancel' to abort): ");
            }
        }
        
        System.out.println("Maximum attempts exceeded. Operation cancelled.");
        return null;
    }
    

    public boolean getYesNoConfirmation(Scanner scanner, String promptMessage) {
        System.out.print(promptMessage + " (y/n): ");
        
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.print("Please enter 'y' for yes or 'n' for no: ");
            }
        }
    }
    

    public boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
    

    public boolean isAlphanumeric(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("^[a-zA-Z0-9]+$");
    }
    

    public boolean isValidLength(String input, int minLength, int maxLength) {
        if (input == null) {
            return false;
        }
        int length = input.trim().length();
        return length >= minLength && length <= maxLength;
    }
    

    public void displayValidationError(String errorType, String details) {
        System.err.println("\n----------------------------------------");
        System.err.println("VALIDATION ERROR: " + errorType);
        if (details != null && !details.isEmpty()) {
            System.err.println("Details: " + details);
        }
        System.err.println("----------------------------------------");
    }
    

    public String getSafeInput(Scanner scanner, String promptMessage, boolean allowEmpty) {
        System.out.print(promptMessage + ": ");
        
        try {
            String input = scanner.nextLine();
            
            if (input == null) {
                return allowEmpty ? "" : null;
            }
            
            String trimmedInput = input.trim();
            
            if (!allowEmpty && trimmedInput.isEmpty()) {
                System.err.println("Input cannot be empty.");
                return null;
            }
            
            return trimmedInput;
            
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
            return null;
        }
    }

    public Integer validateAndParseInteger(String input, int minValue, int maxValue) {
        try {
            if (input == null || input.trim().isEmpty()) {
                return null;
            }
            int value = Integer.parseInt(input.trim());
            if (value < minValue || value > maxValue) {
                System.err.println("Value must be between " + minValue + " and " + maxValue);
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + input);
            return null;
        }
    }
    

    public boolean hasValidExtension(String fileName, String[] allowedExtensions) {
        if (fileName == null || allowedExtensions == null || allowedExtensions.length == 0) {
            return true; // No restriction
        }
        String lowerFileName = fileName.toLowerCase();
        for (String extension : allowedExtensions) {
            if (lowerFileName.endsWith("." + extension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String suggestValidFileName(String invalidFileName) {
        if (invalidFileName == null || invalidFileName.trim().isEmpty()) {
            return "unnamed_file.txt";
        }
        String suggestion = sanitizeFileName(invalidFileName);
        if (!suggestion.contains(".")) {
            suggestion += ".txt";
        }
        return suggestion;
    }
}