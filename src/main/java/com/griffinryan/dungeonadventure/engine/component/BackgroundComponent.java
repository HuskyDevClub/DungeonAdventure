package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.OUTSIDE_DISTANCE;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class BackgroundComponent extends Component {
	private static final double TOP_SPEED = 0.25;
	private static final double MIDDLE_SPEED = 0.07;
	private static final double BOTTOM_SPEED = 0.02;
	private static final double LIGHT_SPEED = 0.10;

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
		var size = getAppWidth() + OUTSIDE_DISTANCE * 2;

		topLayer = texture("background/toplayer.png", size, size);
		middleLayer = texture("background/middlelayer.png", size, size);
		bottomLayer = texture("background/bottomlayer.png", size, size);
		skyLayer = texture("background/sky.png", size, size);
		lightLayer = texture("background/light.png", size, size);

	}

	/**
	 * Sets the BackgroundComponent textures
	 * location each frame for parallax scrolling.
	 *
	 * @see Component
	 * */
	public void onUpdate(double tpf){
		var viewport = getGameScene().getViewport();
	}

}
