package com.griffinryan.dungeonadventure.menu;

import com.google.gson.Gson;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.IS_SOUND_ENABLED;

/**
 * The DungeonMainMenu class defines the
 * initial Pane users are greeted with
 * and the pause/main menu Panes.
 *
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class DungeonMainMenu extends FXGLMenu {

    private static final String myDatabasePath = "jdbc:sqlite:save.sqlite";

    private HashMap<String, String[]> myNamesOfExistingSaves;

    VBox menuBox;
    /**
	 * DungeonMainMenu() is a constructor
	 * used to create a new FXGLMenu object.
	 *
	 * @see FXGLMenu for more.
	 */
    public DungeonMainMenu() {
        super(MenuType.MAIN_MENU);
		if(IS_SOUND_ENABLED){
			playAudio("drumloop.mp3");
		}
        createContent(getContentRoot());
       // new GSON();
    }

    /**
	 * playAudio() plays a .mp3 file located
	 * in the assets/music path.
	 *
     * @param s Title of .mp3 file to play.
     */
    private void playAudio(String s) {
		Music m = FXGL.getAssetLoader().loadMusic(s);
		FXGL.getAudioPlayer().loopMusic(m);
    }


    /**
	 * stopAudio() stops a .mp3 file located
	 * in the assets/music path.
	 *
     * @param s Title of .mp3 to stop.
     */
    private void stopAudio(String s){
		Music m = FXGL.getAssetLoader().loadMusic(s);
		FXGL.getAudioPlayer().stopMusic(m);
	}


    /**
	 * createContent() is a helper method to create
	 * the Pane object used in the main menu.
	 *
     * @param root The Pane object to build with.
     */
    private void createContent(Pane root) {
        root.setPrefSize(1280, 720);
        //DungeonMainMenu.class.getResource("dungeonadventure.jpg").toString()
        Image bgImage = FXGL.image("background/dungeonadventure.jpg", 1280, 720);

        menuBox = new VBox(
                5,
                new MenuItem("START NEW GAME", () -> {
					stopAudio("drumloop.mp3"); // Stops current background music.
                    play("menuSelect.mp3");
					playAudio("chordloop.mp3"); // Starts new background music.
					//playAudio("bg.mp3"); // Starts new background music.
                    //fireNewGame();
                    chooseHero();
                }),
                new MenuItem("SETTINGS", () -> {
                }),
                new MenuItem("CREDITS", () -> {
                }),
                new MenuItem("QUIT", Platform::exit)
        );

        // if there is existing saves, add the "Continue" option into the menuBox
        myNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves(myDatabasePath);
        if (myNamesOfExistingSaves.size() > 0) {
            menuBox.getChildren().add(
                    0, new MenuItem("CONTINUE", () -> {
                stopAudio("drumloop.mp3"); // Stops current background music.
                play("menuSelect.mp3");
                // Starts new background music.
                playAudio("chordloop.mp3");
                selectSave();
                    }
            )
            );
        }

        menuBox.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null)
        ));
        menuBox.setTranslateX(220);
        menuBox.setTranslateY(400);

        root.getChildren().addAll(
                new ImageView(bgImage),
                menuBox
        );
    }

    private void chooseHero(){
        Pane root = getContentRoot();
        root.getChildren().remove(menuBox);
        PlayerInfo playerInfo = new PlayerInfo();
        try {
            FileWriter fw = new FileWriter("system/PlayerInfo.json");
            PrintWriter out = new PrintWriter(fw, true);
            Gson gson = new Gson();

            HBox heroSelect = new HBox(20,
                    new HeroSelect(HeroType.WARRIOR, () -> {
                        playerInfo.chosenHero = HeroType.WARRIOR;
                        String jsonString = gson.toJson(playerInfo);
                        out.write(jsonString);

                        try {
                            out.flush();
                            fw.flush();
                            out.close();
                            fw.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        fireNewGame();
                    }),
                    new HeroSelect(HeroType.THIEF, () -> {
                        playerInfo.chosenHero = HeroType.THIEF;
                        String jsonString = gson.toJson(playerInfo);
                        out.write(jsonString);

                        try {
                            out.flush();
                            fw.flush();
                            out.close();
                            fw.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fireNewGame();
                    }),
                    new HeroSelect(HeroType.PRIEST, () -> {
                        playerInfo.chosenHero = HeroType.PRIEST;
                        String jsonString = gson.toJson(playerInfo);
                        out.write(jsonString);

                        try {
                            out.flush();
                            fw.flush();
                            out.close();
                            fw.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fireNewGame();
                    })
            );

            heroSelect.setTranslateX(250);
            heroSelect.setTranslateY(400);

            root.getChildren().addAll(
                heroSelect
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectSave() {
        Pane root = getContentRoot();
        root.getChildren().remove(menuBox);
        try {

            VBox saveSelectOptionsBox = new VBox(
                    5,
                    new MenuItem("BACK", () -> {}),
                    new MenuItem("NEXT PAGE", () -> {
                    }),
                    new MenuItem("LAST PAGE", () -> {
                    })
            );

            VBox saveSelectBox = new VBox(5);
            myNamesOfExistingSaves.forEach(
                (key, value) -> {
                    final LocalDateTime createdAt = LocalDateTime.parse(value[1]);
                    saveSelectBox.getChildren().add(
                        new MenuItem(
                            String.format(
                                "%s\n* created at\n* %s",
                                value[0], createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            ), () -> {}
                        )
                    );
                }
            );

            saveSelectBox.setTranslateX(200);
            saveSelectBox.setTranslateY(375);

            saveSelectOptionsBox.setTranslateX(475);
            saveSelectOptionsBox.setTranslateY(425);

            root.getChildren().addAll(
                    saveSelectBox, saveSelectOptionsBox
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
