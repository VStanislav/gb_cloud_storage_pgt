package com.voronkov;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ListMessage extends AbstractMessage {
    @Override
    public MessageType getMessageType() {
        return MessageType.LIST;
    }

    private final List<FileData> filesList = new ArrayList<>();

    public List<FileData> getFilesList(){
        return filesList;
    }

    public ListMessage(Path rootPath) throws IOException {
        try (Stream<Path> filesWalk = Files.walk(rootPath)) {
            List<Path> pathsList = filesWalk
                    .filter(new Predicate<Path>() {
                        @Override
                        public boolean test(Path p) {
                            return !p.equals(rootPath);
                        }
                    })
                    .filter(new Predicate<Path>() {
                        @Override
                        public boolean test(Path p) {
                            return p.toFile().isHidden();
                        }
                    })
                    .sorted(new Comparator<Path>() {
                        @Override
                        public int compare(Path p1, Path p2) {
                            return (p1.toFile().isDirectory()==p2.toFile().isDirectory()) ? 0 : (p2.toFile().isDirectory() ? 1 : -1);
                        }
                    })
                    .toList();

            for (Path path :
                    pathsList) {
                this.filesList.add(new FileData(path));
            }
        }
    }
}
