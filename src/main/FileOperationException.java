package main;

@SuppressWarnings("serial")
class FileOperationException extends Exception {
    
    private String operation;
    private String fileName;
    public FileOperationException(String message) {
        super(message);
    }
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    public FileOperationException(String message, String operation, String fileName) {
        super(message);
        this.operation = operation;
        this.fileName = fileName;
    }
    public FileOperationException(String message, Throwable cause, String operation, String fileName) {
        super(message, cause);
        this.operation = operation;
        this.fileName = fileName;
    }

    public String getOperation() {
        return operation;
    }
    public String getFileName() {
        return fileName;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FileOperationException: ").append(getMessage());
        if (operation != null) {
            sb.append(" [Operation: ").append(operation).append("]");
        }
        if (fileName != null) {
            sb.append(" [File: ").append(fileName).append("]");
        }
        return sb.toString();
    }
}


@SuppressWarnings("serial")
class InvalidInputException extends Exception {
    private String inputValue;
    private String expectedFormat;
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidInputException(String message, String inputValue, String expectedFormat) {
        super(message);
        this.inputValue = inputValue;
        this.expectedFormat = expectedFormat;
    }
    public String getInputValue() {
        return inputValue;
    }
    public String getExpectedFormat() {
        return expectedFormat;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InvalidInputException: ").append(getMessage());
        if (inputValue != null) {
            sb.append(" [Input: '").append(inputValue).append("']");
        }
        if (expectedFormat != null) {
            sb.append(" [Expected: ").append(expectedFormat).append("]");
        }
        return sb.toString();
    }
}



class DirectoryAccessException extends Exception {
	private static final long serialVersionUID = 1L;
	private String directoryPath;
    private String accessType;
    public DirectoryAccessException(String message) {
        super(message);
    }
    public DirectoryAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    public DirectoryAccessException(String message, String directoryPath, String accessType) {
        super(message);
        this.directoryPath = directoryPath;
        this.accessType = accessType;
    }
    public String getDirectoryPath() {
        return directoryPath;
    }
    public String getAccessType() {
        return accessType;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DirectoryAccessException: ").append(getMessage());
        if (directoryPath != null) {
            sb.append(" [Directory: ").append(directoryPath).append("]");
        }
        if (accessType != null) {
            sb.append(" [Access Type: ").append(accessType).append("]");
        }
        return sb.toString();
    }
}



@SuppressWarnings("serial")
class ApplicationException extends Exception {
    private String componentName;
    private int errorCode;
    public ApplicationException(String message) {
        super(message);
    }
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
    public ApplicationException(String message, String componentName, int errorCode) {
        super(message);
        this.componentName = componentName;
        this.errorCode = errorCode;
    }
    public String getComponentName() {
        return componentName;
    }
    public int getErrorCode() {
        return errorCode;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ApplicationException: ").append(getMessage());
        if (componentName != null) {
            sb.append(" [Component: ").append(componentName).append("]");
        }
        if (errorCode != 0) {
            sb.append(" [Error Code: ").append(errorCode).append("]");
        }
        return sb.toString();
    }
}