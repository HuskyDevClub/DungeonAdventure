package com.griffinryan.dungeonadventure;

import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;

import com.griffinryan.dungeonadventure.engine.AdventureFactory;
import com.griffinryan.dungeonadventure.engine.component.*;
import com.griffinryan.dungeonadventure.engine.utils.DungeonUtility;
import com.griffinryan.dungeonadventure.engine.collision.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.*;

public class BasicGameApp extends GameApplication {

    private Entity player, potion, enemy, background;
	private AnimationComponent playerComponent;

	/* TODO: 	-
	 *			-
	 *			- Add JavaDoc for the engine package.
	 *   		- Create LevelComponent class.
	 * 			- Generate random level.
	 * */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Dungeon Adventure");
        settings.setVersion("0.2");
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
	protected void initGameVars(Map<String, Object> vars) {

		/* TODO: update with logic from model package
		    and add values for LevelComponent. */
		vars.put("playerHP", 100);
		vars.put("enemyHP", 50);

		vars.put("playerX", 0.0);
		vars.put("playerY", 0.0);
		vars.put("enemyX", 0.0);
		vars.put("enemyY", 0.0);
		vars.put("potionHP", 100);
	}

    @Override
    protected void initGame() {

		/* 	Create the AdventureFactory object for entities.	*/
		int dist = OUTSIDE_DISTANCE;
		getGameWorld().addEntityFactory(new AdventureFactory());
		getGameScene().setBackgroundColor(Color.color(0.8, 0.8, 0.8, 1.0));

		/* 	Set the bounds of player movement.	*/
		getGameScene().getViewport().setBounds(-dist, -dist, getAppWidth() + dist, getAppHeight() + dist);

		/* 	Spawn all component entities except player.	*/
		player = spawn("Player");
		playerComponent = player.getComponent(AnimationComponent.class);
		if(IS_BACKGROUND){
			background = spawn("Background");
		}
		if (!IS_NO_ENEMIES) {
			enemy = spawn("Enemy");
		}
		if (!IS_NO_POTIONS){
			potion = spawn("Potion");
		}

		/* 	Add listeners for player/game values.	*/
		DungeonUtility.addWorldPropertyListeners();
    }

	@Override
	protected void initPhysics() {
		PhysicsWorld physics = getPhysicsWorld();

		physics.addCollisionHandler(new PlayerEnemyHandler());
		physics.addCollisionHandler(new PlayerPotionHandler());
	}

    @Override
    protected void initInput() {

		FXGL.getInput().addAction(new UserAction("Right") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveRight();
				player.getComponent(AnimationComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.D);

		FXGL.getInput().addAction(new UserAction("Left") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveLeft();
				player.getComponent(AnimationComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.A);

		FXGL.getInput().addAction(new UserAction("Up") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveUp();
				player.getComponent(AnimationComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.S);

		FXGL.getInput().addAction(new UserAction("Down") {
			@Override
			protected void onAction() {
				player.getComponent(AnimationComponent.class).moveDown();
				player.getComponent(AnimationComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.W);

	}

    @Override
    protected void initUI() {
        var closeUpTexture = FXGL.getAssetLoader().loadTexture("sprite/closeup-1.png");
        closeUpTexture.setTranslateX(50);
        closeUpTexture.setTranslateY(450);

        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("playerHP").asString());

        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
        FXGL.getGameScene().addUINode(closeUpTexture);
    }

}
