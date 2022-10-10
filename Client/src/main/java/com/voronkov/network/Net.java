package com.voronkov.network;

import com.voronkov.AbstractMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Net {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9000;

    private static Net INSTANCE;

    private final Socket socket;
    private final ObjectEncoderOutputStream outputStream;
    private final ObjectDecoderInputStream inputStream;

    public Net(String host,int port) throws IOException {
        this.socket = new Socket(host,port);
        this.outputStream = new ObjectEncoderOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectDecoderInputStream(socket.getInputStream());
    }

    public static Net getINSTANCE() throws IOException {
        if (INSTANCE==null){
            INSTANCE=new Net(SERVER_HOST,SERVER_PORT);
        }
        return INSTANCE;
    }

    public AbstractMessage read() throws IOException, ClassNotFoundException {
        return (AbstractMessage) inputStream.readObject();
    }
    public void write(AbstractMessage msg) throws IOException {
        outputStream.writeObject(msg);
    }
    public void closeConnection(){
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (IOException e){
            System.out.println("connection close error");
            e.printStackTrace();
        }
    }
}
