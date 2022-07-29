package com.griffinryan.dungeonadventure;

import java.util.Map;

import com.almasb.fxgl.entity.SpawnData;
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

/**
 * BasicGameApp is the main executable class for
 * the DungeonAdventure game engine built
 * on the JavaFX game library FXGL (https://github.com/AlmasB/FXGL).
 *
 * The back-end of the game is handled by the
 * packages found in dungeonadventure.model.
 *
 * The front-end of the application is handled by the packages
 * found in dungeonadventure.controller and dungeonadventure.engine.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class AdventureApp extends GameApplication {

    private Entity player, potion, enemy, background, doorN, doorE, doorW, doorS, dungeon;
	private SpawnData doorData;
	private PlayerComponent playerComponent;

	/* TODO:
	 *			-
	 * 			- Implement model.heroes package....
	 * */
	/**
	 * The main() method runs the launch() method
	 * with args.
	 *
	 * @param args Passed arguments.
	 * @see GameApplication in FXGL.
	 */
    public static void main(String[] args) {
        launch(args);
    }

	/**
	 * initSettings() initializes the game settings like
	 * window size and SceneFactory.
	 *
	 * @param settings FXGL GameSettings parameter to be passed.
	 * @see GameSettings for settings.
	 */
	@Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(VIEW_RESOLUTION_X);
        settings.setHeight(VIEW_RESOLUTION_Y);
		settings.setAppIcon("sprite/potion.png");

        settings.setTitle("Dungeon Adventure");
        settings.setVersion("0.2");
		settings.setDeveloperMenuEnabled(true);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new DungeonMainMenu();
            }
        });
    }

	/**
	 * onPreInit() sets certain game settings before
	 * the initSettings() call.
	 *
	 * @see GameSettings for settings.
	 * */
	@Override
	protected void onPreInit(){
		getSettings().setGlobalSoundVolume(IS_SOUND_ENABLED ? 1.0 : 0.0);
		getSettings().setGlobalMusicVolume(IS_SOUND_ENABLED ? 1.0 : 0.0);
	}

	/**
	 * initGameVars() initializes the world's
	 * property map. Used to store variables like
	 * playerHP, enemyHP, locations, etc.
	 *
	 * @param vars Map<String, Object> for storing properties.
	 * @see com.almasb.fxgl.entity.GameWorld for property map.
	 */
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

		vars.put("doorN", false);
		vars.put("doorE", false);
		vars.put("doorW", false);
		vars.put("doorS", false);

		/* AbstractComponent mapped spawn points.*/
		vars.put("north", false);
		vars.put("east", false);
		vars.put("west", false);
		vars.put("south", false);
	}

	/**
	 * initGame() initializes the main game engine
	 * and creates different Entity objects.
	 *
	 * @see Entity
	 */
    @Override
    protected void initGame() {

		/* 	Create the AdventureFactory object for entities.	*/
		getGameWorld().addEntityFactory(new AdventureFactory());
		getGameScene().setBackgroundColor(Color.color(0.2, 0.2, 0.3, 1.0));

		/* 	Spawn all component entities except player.	*/
		player = spawn("Player");
		playerComponent = player.getComponent(PlayerComponent.class);

		/*
		 Viewport viewport = getGameScene().getViewport();
		 viewport.setZoom(1.20);  ..etc
		*/

		if(!IS_NO_BACKGROUND){
			background = spawn("Background");
		}
		if (!IS_NO_ENEMIES) {
			enemy = spawn("Enemy");
		}
		if (!IS_NO_POTIONS){
			potion = spawn("Potion");
		}
		if(!IS_NO_DOORS){
			doorN = spawn("doorN");	/*	Different names */
			doorE = spawn("doorE"); /*	for each door Entity. */
			doorW = spawn("doorW");
			doorS = spawn("doorS");
		}

		/* 	Add listeners for player/game values.	*/
		DungeonUtility.addWorldPropertyListeners();
    }

	/**
	 * initPhysics() creates the collision handler objects
	 * used to determine collisions.
	 *
	 * @see PlayerEnemyHandler for Player/Enemy collisions.
	 * @see PlayerPotionHandler for Player/Potion collisions.
	 * @see PhysicsWorld for more.
	 */
	@Override
	protected void initPhysics() {
		PhysicsWorld physics = getPhysicsWorld();

		physics.addCollisionHandler(new PlayerEnemyHandler());
		physics.addCollisionHandler(new PlayerPotionHandler());
		physics.addCollisionHandler(new PlayerDoorHandler());
	}

	/**
	 * initInput() creates the action listeners
	 * for user input. It handles the updated state
	 * using the onAction() method.
	 *
	 * @see UserAction for more.
	 */
    @Override
    protected void initInput() {

		FXGL.getInput().addAction(new UserAction("Right") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).moveRight();
				player.getComponent(PlayerComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.D);

		FXGL.getInput().addAction(new UserAction("Left") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).moveLeft();
				player.getComponent(PlayerComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.A);

		FXGL.getInput().addAction(new UserAction("Up") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).moveUp();
				player.getComponent(PlayerComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.S);

		FXGL.getInput().addAction(new UserAction("Down") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).moveDown();
				// player.getComponent(PlayerComponent.class).updatePlayerCoordinates();
			}
		}, KeyCode.W);

	}

	/**
	 * initUI() handles creating the UI for the engine
	 *
	 * @see com.almasb.fxgl.app.scene.GameScene for more.
	 */
    @Override
    protected void initUI() {

		/*
		if(getSettings().isDeveloperMenuEnabled()){
			Text devCoords = new Text(); // x = 600
			devCoords.setTranslateX(500);
			devCoords.setTranslateY(500);
			devCoords.textProperty().bind(getWorldProperties().getValue("playerX"));
			devCoords.textProperty().bind(getWorldProperties().getValue("playerY"));
		}	*/

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