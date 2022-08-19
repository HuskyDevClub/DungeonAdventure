package com.griffinryan.dungeonadventure.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.google.gson.Gson;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.almasb.fxgl.dsl.FXGL.play;
import static com.griffinryan.dungeonadventure.engine.Config.IS_SOUND_ENABLED;

/**
 * The DungeonMainMenu class defines the
 * initial Pane users are greeted with
 * and the pause/main menu Panes.
 *
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class DungeonMainMenu extends FXGLMenu {

    VBox menuBox;
    private HashMap<String, String[]> myNamesOfExistingSaves;

    /**
     * DungeonMainMenu() is a constructor
     * used to create a new FXGLMenu object.
     *
     * @see FXGLMenu for more.
     */
    public DungeonMainMenu() {
        super(MenuType.MAIN_MENU);
        if (IS_SOUND_ENABLED) {
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
     */
    private void stopAudio() {
        Music m = FXGL.getAssetLoader().loadMusic("drumloop.mp3");
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
                stopAudio(); // Stops current background music.
                play("menuSelect.mp3");
                playAudio("chordloop.mp3"); // Starts new background music.
                chooseHero();
            }),
            new MenuItem("SETTINGS", () -> {
            }),
            new MenuItem("CREDITS", () -> {
            }),
            new MenuItem("QUIT", Platform::exit)
        );

        // if there is existing saves, add the "Continue" option into the menuBox
        //stopAudio("drumloop.mp3"); // Stops current background music.
        // Starts new background music.
        // playAudio("chordloop.mp3");
        MenuItem CONTINUE_BUTTON = new MenuItem("CONTINUE", () -> {
            //stopAudio("drumloop.mp3"); // Stops current background music.
            play("menuSelect.mp3");
            // Starts new background music.
            // playAudio("chordloop.mp3");
            selectSave();
        });
        menuBox.getChildren().add(0, CONTINUE_BUTTON);
        myNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves();
        CONTINUE_BUTTON.setVisible(myNamesOfExistingSaves.size() > 0);

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

    private void chooseHero() {
        Pane root = getContentRoot();
        root.getChildren().remove(menuBox);
        PlayerInfo playerInfo = new PlayerInfo();

        HBox heroSelect = new HBox(20);
        heroSelect.getChildren().add(new HeroSelect(HeroType.WARRIOR, () -> {
            playerInfo.chosenHero = HeroType.WARRIOR;
            gameStart(playerInfo);
            root.getChildren().add(menuBox);
            root.getChildren().remove(heroSelect);
            root.getChildren().remove(root.getChildren().size() - 2);
        }));
        heroSelect.getChildren().add(new HeroSelect(HeroType.THIEF, () -> {
            playerInfo.chosenHero = HeroType.THIEF;
            gameStart(playerInfo);
            root.getChildren().add(menuBox);
            root.getChildren().remove(heroSelect);
            root.getChildren().remove(root.getChildren().size() - 2);
        }));
        heroSelect.getChildren().add(new HeroSelect(HeroType.PRIEST, () -> {
            playerInfo.chosenHero = HeroType.PRIEST;
            gameStart(playerInfo);
            root.getChildren().add(menuBox);
            root.getChildren().remove(heroSelect);
            root.getChildren().remove(root.getChildren().size() - 2);
        }));

        heroSelect.setTranslateX(250);
        heroSelect.setTranslateY(400);

        root.getChildren().addAll(
            heroSelect
        );

        // implement back button
        MenuItem theBackButton;
        theBackButton = new MenuItem("BACK", () -> {
            root.getChildren().add(menuBox);
            root.getChildren().remove(heroSelect);
            root.getChildren().remove(root.getChildren().size() - 2);
        });
        theBackButton.setTranslateX(250);
        theBackButton.setTranslateY(675);

        root.getChildren().add(theBackButton);
    }

    private void gameStart(PlayerInfo playerInfo) {
        FileWriter fw;

        try {
            fw = new FileWriter("system/PlayerInfo.json");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        PrintWriter out = new PrintWriter(fw, true);
        Gson gson = new Gson();

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
    }

    private void selectSave() {
        Pane root = getContentRoot();
        root.getChildren().remove(menuBox);
        try {
            VBox saveSelectOptionsBox = new VBox(
                5,
                new MenuItem("NEXT PAGE", () -> {
                }),
                new MenuItem("LAST PAGE", () -> {
                })
            );

            VBox saveSelectBox = new VBox(5);
            myNamesOfExistingSaves.forEach(
                (key, value) -> {
                    final LocalDateTime createdAt = LocalDateTime.parse(value[2]);
                    saveSelectBox.getChildren().add(
                        new MenuItem(
                            String.format(
                                "%s\n* created at\n* %s\n* played as %s",
                                value[0], createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), value[1]
                            ), () -> {
                            getWorldProperties().setValue("dungeon_id", key);
                            PlayerInfo playerInfo = new PlayerInfo();
                            playerInfo.chosenHero = HeroType.ofValue(value[1]);
                            gameStart(playerInfo);
                        }
                        )
                    );
                }
            );
            // add back button
            saveSelectOptionsBox.getChildren().add(
                new MenuItem("BACK", () -> {
                    root.getChildren().add(menuBox);
                    root.getChildren().remove(saveSelectOptionsBox);
                    root.getChildren().remove(saveSelectBox);
                })
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
