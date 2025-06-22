package test;

import java.util.List;

import main.*;

class LockedMeTest {
    
    public static void runTests() {
        System.out.println("Running basic tests...");
        
        testFileManager();
        
        testApplicationUtils();
        
        System.out.println("All tests completed!");
    }
    
    private static void testFileManager() {
        System.out.println("Testing FileManager...");
        
        FileManager fm = new FileManager("./test_directory");
        fm.createRootDirectory();
        
        assert fm.directoryExists() : "Directory should exist after creation";
        
        List<String> files = fm.getFilesInAscendingOrder();
        assert files != null : "File list should not be null";
        
        System.out.println("FileManager tests passed!");
    }
    
    private static void testApplicationUtils() {
        System.out.println("Testing ApplicationUtils...");
        
        assert ApplicationUtils.isValidFileName("test.txt") : "Valid filename should pass";
        assert !ApplicationUtils.isValidFileName("") : "Empty filename should fail";
        assert !ApplicationUtils.isValidFileName("test<.txt") : "Invalid characters should fail";
        
        assert ApplicationUtils.formatFileSize(1024).equals("1.0 KB") : "Size formatting should work";
        
        System.out.println("ApplicationUtils tests passed!");
    }
}