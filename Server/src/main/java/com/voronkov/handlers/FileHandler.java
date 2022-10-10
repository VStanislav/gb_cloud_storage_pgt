package com.voronkov.handlers;

import com.voronkov.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    private final Path serverRoot;

    public FileHandler(String serverRoot) {
        this.serverRoot = Path.of(serverRoot);
    }

    public void channelActive (ChannelHandlerContext ctx) throws IOException {
        ctx.writeAndFlush(new ListMessage(serverRoot));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        switch (msg.getMessageType()){
            case FILE -> {
                FileMessage fileMessage =(FileMessage) msg;
                writeFile(fileMessage.getFilePath(), fileMessage.getName(), fileMessage.getBytes() );
                ctx.writeAndFlush(new ListMessage(serverRoot));
            }
            case DROP -> {
                DropMessage dropMessage = (DropMessage) msg;
                Path filePath = serverRoot.resolve(dropMessage.getFile());
                if (Files.exists(filePath)){
                    Files.delete(filePath);
                    ctx.writeAndFlush(new ListMessage(serverRoot));
                }
            }
            case REQUEST_FILE -> {
                RequestFileMessage cmdRequest = (RequestFileMessage) msg;
                Path filePath = serverRoot.resolve(cmdRequest.getFile());
                if (Files.exists(filePath)){
                    FileMessage fileMessage = new FileMessage(new FileData(filePath));
                    ctx.writeAndFlush(fileMessage);
                }
            }
            default -> System.out.printf("Не удалось выполнить команду %S",msg.getMessageType().getName());
        }
    }

    private void writeFile(String writePath, String fileName, byte[] fileBytes) throws IOException {
        Path copyRoot = serverRoot.resolve(writePath);
        if (!Files.exists(copyRoot)){
            Files.createDirectories(copyRoot);
        }
        Files.write(copyRoot.resolve(fileName),fileBytes);
    }
}
