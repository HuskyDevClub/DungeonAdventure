package com.griffinryan.dungeonadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.*;

import java.util.Map;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.griffinryan.dungeonadventure.DungeonMainMenu;

import com.almasb.fxgl.app.scene.SimpleGameMenu;


public class BasicGameApp extends GameApplication {

    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Dungeon Adventure");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new DungeonMainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder().at(300, 300)
                .view("sprite/front-1.png")
                .buildAndAttach();
    }

    @Override
    protected void initInput() {

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5); // Move right.
            FXGL.inc("pixelsMoved", +5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5); // Move left.
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5); // Move up.
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5); // Move down.
        });

		FXGL.onKeyDown(KeyCode.F, () -> {
			FXGL.play("drop.wav");
		});
	}

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    @Override
    protected void initUI() {
        var closeUpTexture = FXGL.getAssetLoader().loadTexture("sprite/closeup-1.png");
        closeUpTexture.setTranslateX(50);
        closeUpTexture.setTranslateY(450);

        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());

        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
        FXGL.getGameScene().addUINode(closeUpTexture);
    }
}
