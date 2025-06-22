package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    
    public boolean addFile(String fileName) throws FileOperationException {
        try {
            Path filePath = Paths.get(rootDirectory, fileName);
            
            Files.createFile(filePath);
            
            String defaultContent = "File created by LockedMe.com\n" +
                                  "Created on: " + new Date() + "\n" +
                                  "File name: " + fileName + "\n";
            Files.write(filePath, defaultContent.getBytes());
            
            return true;
            
        } catch (IOException e) {
            throw new FileOperationException("Failed to create file: " + fileName, e);
        }
    }
    

    public boolean deleteFile(String fileName) throws FileOperationException {
        try {
            Path filePath = Paths.get(rootDirectory, fileName);
            
            if (!Files.exists(filePath)) {
                throw new FileOperationException("File not found: " + fileName);
            }
            
            Files.delete(filePath);
            return true;
            
        } catch (IOException e) {
            throw new FileOperationException("Failed to delete file: " + fileName, e);
        }
    }
    

    public SearchResult searchFile(String fileName) throws FileOperationException {
        Path filePath = Paths.get(rootDirectory, fileName);
        
        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            try {
                return new SearchResult(
                    true,
                    fileName,
                    filePath.toString(),
                    Files.size(filePath),
                    Files.getLastModifiedTime(filePath).toString()
                );
            } catch (IOException e) {
                throw new FileOperationException("Error reading file information: " + fileName, e);
            }
        } else {
            return new SearchResult(false, fileName, "", 0, "");
        }
    }
    

    public boolean fileExists(String fileName) {
        return getFilesInAscendingOrder().stream()
                .anyMatch(f -> f.equalsIgnoreCase(fileName));
    }
    

    public boolean fileExistsCaseSensitive(String fileName) {
        Path filePath = Paths.get(rootDirectory, fileName);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }
    

    public List<String> findSimilarFiles(String fileName) {
        return getFilesInAscendingOrder().stream()
                .filter(f -> f.toLowerCase().contains(fileName.toLowerCase()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public String getRootDirectory() {
        return rootDirectory;
    }
    
    public boolean directoryExists() {
        File directory = new File(rootDirectory);
        return directory.exists() && directory.isDirectory();
    }
}