package com.griffinryan.dungeonadventure.menu;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.play;

public class HeroSelect extends StackPane {
    HeroSelect(HeroType type, Runnable action) {
        LinearGradient gradient = new LinearGradient(
            0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
            new Stop(0.1, Color.web("black", 0.75)),
            new Stop(1.0, Color.web("black", 0.15))
        );
        Rectangle bg0 = new Rectangle(250, 250, gradient);
        Rectangle bg1 = new Rectangle(250, 250, Color.web("black", 0.2));

        FillTransition ft = new FillTransition(Duration.seconds(0.6),
            bg1, Color.web("black", 0.2), Color.web("white", 0.3));

        ft.setAutoReverse(true);
        ft.setCycleCount(Integer.MAX_VALUE);

        //Hero Animation
        ImageView imageView = new ImageView();
        List<Image> images = type.getSprites();

        imageView.setTranslateY(-50);
        imageView.setTranslateX(60);

        Transition animation = new Transition() {
            {
                setCycleDuration(Duration.millis(500)); // total time for animation
            }

            @Override
            protected void interpolate(double fraction) {
                int index = (int) (fraction * (images.size() - 1));
                imageView.setImage(images.get(index));
            }
        };
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        imageView.setEffect(desaturate);

        hoverProperty().addListener((o, oldValue, isHovering) -> {
            play("menuHover.wav");

            if (isHovering) {
                ft.playFromStart();
                desaturate.setSaturation(0);
                imageView.setEffect(desaturate);
            } else {
                ft.stop();
                bg1.setFill(Color.web("black", 0.2));
                desaturate.setSaturation(-1);
                imageView.setEffect(desaturate);
            }
        });

        //line
        Rectangle line = new Rectangle(5, 250);
        line.widthProperty().bind(
            Bindings.when(hoverProperty())
                .then(8).otherwise(5)
        );
        line.fillProperty().bind(
            Bindings.when(hoverProperty())
                .then(Color.RED).otherwise(Color.GRAY)
        );

        Text text = new Text(type.toString());
        text.setFont(Font.font(22.0));
        text.fillProperty().bind(
            Bindings.when(hoverProperty())
                .then(Color.WHITE).otherwise(Color.GRAY)
        );

        setOnMouseClicked(e -> action.run());

        setOnMousePressed(e -> bg0.setFill(Color.LIGHTBLUE));

        setOnMouseReleased(e -> bg0.setFill(gradient));

        setAlignment(Pos.CENTER_LEFT);

        HBox box = new HBox(15, text);

        box.setAlignment(Pos.BOTTOM_CENTER);
        /*
        AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/dungeon/knight_m_idle_anim_f.png"),
                4, 16, 28, Duration.seconds(0.6), 0, 3);
        AnimatedTexture texture = new AnimatedTexture(idle);
        texture.loopAnimationChannel(idle);

         */


        getChildren().addAll(bg0, bg1, box, line, imageView);

    }


}