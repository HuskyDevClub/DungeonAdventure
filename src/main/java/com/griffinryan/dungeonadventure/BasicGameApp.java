package com.griffinryan.dungeonadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

public class BasicGameApp extends GameApplication {

	private Entity player;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	protected void initSettings(GameSettings settings){
		settings.setWidth(600);
		settings.setHeight(600);
		settings.setTitle("Dungeon Adventure");
		settings.setVersion("0.1");
	}

	@Override
	protected void initGame(){
		player = FXGL.entityBuilder().at(300,300)
				.view("mario.png")
				.buildAndAttach();
		/*
		player = FXGL.entityBuilder().at(300,300)
				.view(new Rectangle(25,25,Color.DEEPPINK))
				.buildAndAttach();	*/
	}

	@Override
	protected void initInput(){

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
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("pixelsMoved", 0);
	}

	@Override
	protected void initUI(){
		var marioTexture = FXGL.getAssetLoader().loadTexture("mario.png");
		marioTexture.setTranslateX(50);
		marioTexture.setTranslateY(450);

		Text textPixels = new Text();
		textPixels.setTranslateX(50); // x = 50
		textPixels.setTranslateY(100); // y = 100

		textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());

		FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
		FXGL.getGameScene().addUINode(marioTexture);
	}
}
