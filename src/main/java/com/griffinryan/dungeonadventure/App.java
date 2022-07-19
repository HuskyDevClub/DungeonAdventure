package com.griffinryan.dungeonadventure;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

		Controller cont = new Controller();
        stage.setTitle("Dungeon Adventure");
        stage.setScene(cont.getScene());

		// here

        stage.show();
    }
}
