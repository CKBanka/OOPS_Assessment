package main;

public class SearchResult {
    private boolean found;
    private String fileName;
    private String fullPath;
    private long fileSize;
    private String lastModified;
    
    public SearchResult(boolean found, String fileName, String fullPath, long fileSize, String lastModified) {
        this.found = found;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
    }
    
    public boolean isFound() { return found; }
    public String getFileName() { return fileName; }
    public String getFullPath() { return fullPath; }
    public long getFileSize() { return fileSize; }
    public String getLastModified() { return lastModified; }
}