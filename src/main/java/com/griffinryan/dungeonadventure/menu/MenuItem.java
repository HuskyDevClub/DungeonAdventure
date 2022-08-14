package com.griffinryan.dungeonadventure.menu;

import javafx.animation.FillTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getAudioPlayer;
import static com.almasb.fxgl.dsl.FXGL.play;

/**
 * MenuItem() creates a new StackPane object
 * for the main menu.
 *
 * @see StackPane for more.
 */
public class MenuItem extends StackPane {
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
