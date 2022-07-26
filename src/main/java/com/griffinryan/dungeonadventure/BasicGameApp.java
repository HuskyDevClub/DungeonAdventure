package com.griffinryan.dungeonadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.model.engine.AnimationComponent;
import com.griffinryan.dungeonadventure.model.engine.EntityType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Map;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.griffinryan.dungeonadventure.Config.*;
import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;


public class BasicGameApp extends GameApplication {

    private Entity player, potion, enemy;

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

        this.player = entityBuilder().type(EntityType.PLAYER)
				.at(300, 300)
				.with(new CollidableComponent(true), new AnimationComponent())
                .buildAndAttach();

		this.potion = entityBuilder().type(EntityType.POTION)
				.at(500,200)
				.viewWithBBox(new Circle(15,15,15,Color.DODGERBLUE))
				.with(new CollidableComponent(true))
				.buildAndAttach();
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
