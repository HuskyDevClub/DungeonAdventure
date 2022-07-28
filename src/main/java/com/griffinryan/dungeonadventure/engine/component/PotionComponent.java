package com.griffinryan.dungeonadventure.engine.component;

import javafx.geometry.Point2D;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class PotionComponent extends Component {

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel idleChannel;
	private int hpAmount = 100; // 100, 300, 500?

	/**
	 * PotionComponent() is a constructor that takes different
	 * AnimationChannel parameters to create an animated Entity.
	 * @see Component
	 */
	public PotionComponent(){

		var bound = FXGL.texture("potion/potion.png");
		AnimationChannel idle = new AnimationChannel(FXGL.image("potion/lifepotion.png"),
				4, 17, 16, Duration.seconds(0.6), 0, 3);

		this.idleChannel = idle;
		this.boundTexture = bound;

		this.texture = new AnimatedTexture(idleChannel);
	}

	/**
	 * onAdded() sets properties upon instantiation of
	 * the Component object.
	 *
	 * @see Component
	 */
	@Override
	public void onAdded(){
		entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(idleChannel);
		FXGL.getWorldProperties().setValue("potionHP", hpAmount);
	}

	/**
	 * Retrieves the texture used for
	 * boundary collision calculations.
	 *
	 * @see Texture
	 * */
	public Texture getBoundTexture() {
		return boundTexture;
	}
}
