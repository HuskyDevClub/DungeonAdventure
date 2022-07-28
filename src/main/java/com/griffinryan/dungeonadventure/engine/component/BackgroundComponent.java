package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.*;
import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class BackgroundComponent extends Component {
	private static final double TOP_SPEED = 0.06;
	private static final double MIDDLE_SPEED = 0.02;
	private static final double BOTTOM_SPEED = 0.02;
	private static final double LIGHT_SPEED = 0.025;

	private Texture topLayer;
	private Texture middleLayer;
	private Texture bottomLayer;
	private Texture skyLayer;
	private Texture lightLayer;

	/**
	 * Sets the BackgroundComponent textures.
	 *
	 * @see Component
	 * */
	@Override
	public void onAdded() {
		double w = VIEW_RESOLUTION_X*2; // Size of the layers.png
		double h = VIEW_RESOLUTION_Y*2; // 384 x 162

		topLayer = texture("background/toplayer.png", w, h);
		middleLayer = texture("background/middlelayer.png", w, h);
		bottomLayer = texture("background/bottomlayer.png", w, 3*h/4);
		skyLayer = texture("background/sky.png", w, h);
		lightLayer = texture("background/light.png", w/2, 3*h/4);


		entity.getViewComponent().addChild(topLayer);
		entity.getViewComponent().addChild(middleLayer);
		entity.getViewComponent().addChild(bottomLayer);
		entity.getViewComponent().addChild(lightLayer);
		entity.getTransformComponent().setScaleOrigin(BG_POINT);

		entity.setX(0);
		entity.setY(0);
	}

	/**
	 * Sets the BackgroundComponent textures
	 * location each frame for parallax scrolling.
	 *
	 * @see Component
	 * */
	public void onUpdate(double tpf){

		Entity player = getGameScene().getGameWorld().getEntitiesByType(PLAYER).get(0);
		// var viewport = getGameScene().getViewport();

		topLayer.setTranslateX(-BG_DISTANCE * TOP_SPEED * player.getX());
		topLayer.setTranslateY(-BG_DISTANCE * TOP_SPEED * player.getY());

		middleLayer.setTranslateX(-BG_DISTANCE * MIDDLE_SPEED * player.getX());
		//middleLayer.setTranslateY(-BG_DISTANCE * MIDDLE_SPEED * player.getY());

		// bottomLayer.setTranslateX(-BG_DISTANCE * BOTTOM_SPEED * player.getX());
		// bottomLayer.setTranslateY(-BG_DISTANCE * BOTTOM_SPEED * player.getY());

		lightLayer.setTranslateX(-BG_DISTANCE * LIGHT_SPEED * player.getX());
		lightLayer.setTranslateY(-BG_DISTANCE * LIGHT_SPEED * player.getY());
	}

}
