package main;

import java.util.Date;

public class ApplicationUtils {
    
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
        
        return true;
    }
    
    public static String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024.0));
        return String.format("%.1f GB", size / (1024.0 * 1024.0 * 1024.0));
    }
    

    public static String getCurrentTimestamp() {
        return new Date().toString();
    }
}