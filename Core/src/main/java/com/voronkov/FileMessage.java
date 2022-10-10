package com.voronkov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMessage extends AbstractMessage{
    private final String name;
    private final byte[] bytes;
    private final String filePath;

    public FileMessage(FileData fileData) throws IOException {
        this.name = fileData.getFileName();
        this.bytes = Files.readAllBytes(Path.of(fileData.getFullName()));
        this.filePath = fileData.getParent();
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.FILE;
    }
}
