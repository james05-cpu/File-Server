package fileServer;

public class FileDetails {
    private String FileId;
    private String fileName;
    private byte[]fileData;
    private String fileExtension;
    private Float fileSize;
    private String fileLocation;

    public FileDetails(String fileId, String fileName, byte[] fileData,
                       String fileExtension, Float fileSize, String fileLocation) {
        FileId = fileId;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileLocation = fileLocation;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Float getFileSize() {
        return fileSize;
    }

    public void setFileSize(Float fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

}
