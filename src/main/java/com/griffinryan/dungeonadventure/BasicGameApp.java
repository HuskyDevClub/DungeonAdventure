package com.griffinryan.dungeonadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.AdventureFactory;
import com.griffinryan.dungeonadventure.engine.component.*;
import com.griffinryan.dungeonadventure.engine.EntityType;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.Objects;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.*;


public class BasicGameApp extends GameApplication {

    private Entity player, potion, enemy;
	private AnimationComponent playerComponent;

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
	protected void onPreInit(){
		getSettings().setGlobalSoundVolume(IS_SOUND_ENABLED ? 0.4 : 0.0);
		getSettings().setGlobalMusicVolume(IS_SOUND_ENABLED ? 0.8 : 0.0);
	}

    @Override
    protected void initGame() {

		// add the AdventureFactory for entities.
		getGameWorld().addEntityFactory(new AdventureFactory());
		getGameScene().setBackgroundColor(Color.color(0, 0, 0.05, 1.0));

		// spawn("Background"); // Spawn in various
		player = spawn("Player");		// different entities.
		playerComponent = player.getComponent(AnimationComponent.class);

		int dist = OUTSIDE_DISTANCE;

		// Set the bounds of player movement.
		getGameScene().getViewport().setBounds(-dist, -dist, getAppWidth() + dist, getAppHeight() + dist);
		// getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);

		/*
		// Add a listener for player hp.
		getWorldProperties().<Integer>addListener("player_hp", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});	*/

		/* Spawn enemies and potion entities
		*  here based on the location in Dungeon. */
		if (!IS_NO_ENEMIES) {
			enemy = spawn("Enemy");
		}

		// spawn("Potion");
    }

	@Override
	protected void initPhysics() {
		FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(player, potion) {
			// order of types is the same as passed into the constructor
			@Override
			protected void onCollisionBegin(Entity player, Entity potion) {
				potion.removeFromWorld();
			}
		});
	}

    @Override
    protected void initInput() {

		FXGL.getInput().addAction(new UserAction("Right") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveRight();
			}
		}, KeyCode.D);

		FXGL.getInput().addAction(new UserAction("Left") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveLeft();
			}
		}, KeyCode.A);

		FXGL.getInput().addAction(new UserAction("Up") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveUp();
			}
		}, KeyCode.S);

		FXGL.getInput().addAction(new UserAction("Down") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveDown();
			}
		}, KeyCode.W);

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
