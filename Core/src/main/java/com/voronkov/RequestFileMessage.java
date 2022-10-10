package com.voronkov;

public class RequestFileMessage extends AbstractMessage{
    public RequestFileMessage(FileData file) {
        this.file = file.toString();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.REQUEST_FILE;
    }
    private final String file;


    public String getFile() {
        return file;
    }
}
