package main;

public class InputValidator {
    

    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        
        String invalidChars = "<>:\"/\\|?*";
        for (char c : invalidChars.toCharArray()) {
            if (fileName.contains(String.valueOf(c))) {
                return false;
            }
        }
        
        String[] reservedNames = {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", 
                                 "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", 
                                 "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", 
                                 "LPT7", "LPT8", "LPT9"};
        
        String nameWithoutExtension = fileName.split("\\.")[0].toUpperCase();
        for (String reserved : reservedNames) {
            if (nameWithoutExtension.equals(reserved)) {
                return false;
            }
        }
        
        return true;
    }

    public static String sanitizeInput(String input) {
        if (input == null) return "";
        return input.trim().replaceAll("[\\p{Cntrl}]", "");
    }
}


@SuppressWarnings("serial")
class FileOperationException extends Exception {
    public FileOperationException(String message) {
        super(message);
    }
    
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
