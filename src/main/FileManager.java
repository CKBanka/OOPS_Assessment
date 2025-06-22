package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
    private String rootDirectory;
    
    public FileManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
    

    public void createRootDirectory() {
        File directory = new File(rootDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created directory: " + rootDirectory);
            } else {
                System.out.println("Failed to create directory: " + rootDirectory);
            }
        }
    }
    

    public List<String> getFilesInAscendingOrder() {
        File directory = new File(rootDirectory);
        List<String> fileNames = new ArrayList<>();
        
        if (!directory.exists()) {
            throw new RuntimeException("Directory does not exist: " + rootDirectory);
        }
        
        if (!directory.isDirectory()) {
            throw new RuntimeException("Path is not a directory: " + rootDirectory);
        }
        
        File[] files = directory.listFiles();
        
        if (files != null) {
            fileNames = Arrays.stream(files)
                    .filter(File::isFile)
                    .map(File::getName)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.toList());
        }
        
        return fileNames;
    }
    

    public String getRootDirectory() {
        return rootDirectory;
    }
    

    public boolean directoryExists() {
        File directory = new File(rootDirectory);
        return directory.exists() && directory.isDirectory();
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