package org.openjfx.tools.customer;

import Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    }
    

    public static void main(String[] args) {      
        Server.main(args);
    }

}