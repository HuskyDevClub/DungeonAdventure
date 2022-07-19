package com.griffinryan.dungeonadventure;

import com.griffinryan.dungeonadventure.heroes.Warrior;
import com.griffinryan.dungeonadventure.monsters.Ogre;
import com.griffinryan.dungeonadventure.systems.Combat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // just for testing the combat system
        testing();
    }

    public void testing() {
        var monster1 = new Ogre("m1");
        var warrior1 = new Warrior("w1");

        System.out.println(monster1.getMyHealth());
        System.out.println(warrior1.getMyHealth());

        Combat.fight(warrior1, monster1);

        System.out.println(monster1.getMyHealth());
        System.out.println(warrior1.getMyHealth());
    }
}
