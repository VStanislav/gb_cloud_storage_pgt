package com.voronkov;

import com.voronkov.network.Net;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Сетевое хранилище");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        Net.getINSTANCE().closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}
