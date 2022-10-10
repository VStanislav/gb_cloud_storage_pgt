package com.voronkov;

public class DropMessage extends AbstractMessage{
    private final String fileName;
    private final String file;

    public DropMessage(FileData fileData) {
        this.fileName = fileData.getFileName();
        this.file = fileName.toString();
    }

    public String getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public MessageType getMessageType(){
        return MessageType.DROP;
    }
}
