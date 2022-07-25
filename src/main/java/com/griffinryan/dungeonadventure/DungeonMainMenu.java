package com.griffinryan.dungeonadventure;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.view.MouseButtonView;
import com.almasb.fxgl.input.view.TriggerView;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.scene.Scene;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import static com.almasb.fxgl.dsl.FXGL.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import java.io.File;

public class DungeonMainMenu extends FXGLMenu {

    public DungeonMainMenu() {
        super(MenuType.MAIN_MENU);
        loopBGM("bg.mp3");
        createContent(getContentRoot());

    }

    private void playAudio() {

    }

    private void createContent(Pane root) {
        root.setPrefSize(1280, 720);

        Image bgImage = new Image(DungeonMainMenu.class.getResource("dungeonadventure.jpg").toString(),
                1280, 720,
                false, true
        );

        VBox box = new VBox(
                5,
                new MenuItem("START GAME", () -> {
                    play("menuSelect.mp3");
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