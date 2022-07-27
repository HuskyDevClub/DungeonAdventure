package com.griffinryan.dungeonadventure;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.IS_SOUND_ENABLED;
/**
 * The DungeonMainMenu class defines the
 * initial pane users are greeted with.
 *
 * @author Elijah Amian (elijah25@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class DungeonMainMenu extends FXGLMenu {

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

        Image bgImage = new Image(DungeonMainMenu.class.getResource("dungeonadventure.jpg").toString(),
                1280, 720,
                false, true
        );

        VBox box = new VBox(
                5,
                new MenuItem("START GAME", () -> {
					stopAudio("drumloop.mp3"); // Stops current background music.
                    play("menuSelect.mp3");
					playAudio("chordloop.mp3"); // Starts new background music.
					//playAudio("bg.mp3"); // Starts new background music.
                    fireNewGame();
                }),
                new MenuItem("SETTINGS", () -> {
                }),
                new MenuItem("CREDITS", () -> {
                }),
                new MenuItem("QUIT", () -> Platform.exit())
        );

        box.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null)
        ));
        box.setTranslateX(250);
        box.setTranslateY(400);

        root.getChildren().addAll(
                new ImageView(bgImage),
                box
        );
    }

    /**
	 * MenuItem() creates a new StackPane object
	 * for the main menu.
	 *
	 * @see StackPane for more.
	 */
    private static class MenuItem extends StackPane {
        MenuItem(String name, Runnable action) {
            LinearGradient gradient = new LinearGradient(
                    0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("black", 0.75)),
                    new Stop(1.0, Color.web("black", 0.15))
            );
            Rectangle bg0 = new Rectangle(250, 30, gradient);
            Rectangle bg1 = new Rectangle(250, 30, Color.web("black", 0.2));

            FillTransition ft = new FillTransition(Duration.seconds(0.6),
                    bg1, Color.web("black", 0.2), Color.web("white", 0.3));

            ft.setAutoReverse(true);
            ft.setCycleCount(Integer.MAX_VALUE);

            hoverProperty().addListener((o, oldValue, isHovering) -> {
                play("menuHover.wav");
                if (isHovering) {
                    ft.playFromStart();
                } else {
                    ft.stop();
                    bg1.setFill(Color.web("black", 0.2));
                }
            });

            Rectangle line = new Rectangle(5, 30);
            line.widthProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(8).otherwise(5)
            );
            line.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.RED).otherwise(Color.GRAY)
            );

            Text text = new Text(name);
            text.setFont(Font.font(22.0));
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.WHITE).otherwise(Color.GRAY)
            );

            setOnMouseClicked(e -> action.run());

            setOnMousePressed(e -> bg0.setFill(Color.LIGHTBLUE));

            setOnMouseReleased(e -> bg0.setFill(gradient));

            setAlignment(Pos.CENTER_LEFT);

            HBox box = new HBox(15, line, text);
            box.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(bg0, bg1, box);
        }
    }
}
