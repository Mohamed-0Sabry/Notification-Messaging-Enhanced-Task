package com.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load configuration
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/main/resources/config.json"));
        JSONObject appSettings = (JSONObject) config.get("appSettings");

        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_scene.fxml"));
        Parent root = loader.load();

        // Set up the scene
        Scene scene = new Scene(root);
        primaryStage.setTitle((String) appSettings.get("windowTitle"));
        
        // Set initial window size
        double initialWidth = ((Long) appSettings.get("windowWidth")).doubleValue();
        double initialHeight = ((Long) appSettings.get("windowHeight")).doubleValue();
        primaryStage.setWidth(initialWidth);
        primaryStage.setHeight(initialHeight);
        
        // Set minimum window size
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        
        // Enable window resizing
        primaryStage.setResizable(true);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 