package com.griffinryan.dungeonadventure.engine.component;

import javafx.geometry.Point2D;
import javafx.util.Duration;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.animationBuilder;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class AnimationComponent extends Component {

	private int speed = 0;
	private boolean y = true;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel animIdle, animWalk, animWalkBack;

	/**
	 * AnimationComponent() is a constructor that takes different
	 * AnimationChannel parameters to create an animated player Entity.
	 *
	 * @param idle Channel for idle animation.
	 * @param walk Channel for walking animation.
	 * @param back Channel for walking backwards animation.
	 * @param bound Texture for boundary box.
	 */
	public AnimationComponent(AnimationChannel idle, AnimationChannel walk, AnimationChannel back, Texture bound){
		this.animIdle = idle;
		this.animWalk = walk;
		this.animWalkBack = back;

		this.boundTexture = bound;
		this.texture = new AnimatedTexture(animIdle);
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
		texture.loopAnimationChannel(animIdle);
	}

	/**
	 * onUpdate() sets properties upon updates of
	 * the Component object.
	 *
	 * @see Component
	 */
	@Override
	public void onUpdate(double tpf) {
		/* update the position of the player. */
		if(y != true){
			entity.translateX(speed * tpf);
			if (speed != 0) {
				if (texture.getAnimationChannel() == animIdle) {
					texture.loopAnimationChannel(animWalk);
				}
				speed = (int) (speed * 0.9);
				if (FXGLMath.abs(speed) < 1) {
					speed = 0;
					texture.loopAnimationChannel(animIdle);
				}
			}

		} else {
			entity.translateY(speed * tpf);
			if (speed != 0) {
				if (texture.getAnimationChannel() == animIdle) {
					texture.loopAnimationChannel(animWalk);
				}
				speed = (int) (speed * 0.9);
				if (FXGLMath.abs(speed) < 1) {
					speed = 0;
					texture.loopAnimationChannel(animIdle);
				}
			}

		}

	}

	/**
	 * moveRight() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveRight() {
		speed = 250;
		y = false;
		getEntity().setScaleX(1);
	}

	/**
	 * moveLeft() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveLeft() {
		speed = -250;
		y = false;
		getEntity().setScaleX(-1);
	}

	/**
	 * moveUp() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveUp() {
		speed = 250;
		y = true;
		getEntity().setScaleY(1);
	}

	/**
	 * moveDown() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveDown() {
		speed = -250;
		y = true;
		getEntity().setScaleY(-1);
	}

	/**
	 * updatePlayerCoordinates() is a
	 * helper method to store the Entity coordinates
	 * in the world property map.
	 *
	 * @see com.almasb.fxgl.entity.GameWorld
	 */
	public void updatePlayerCoordinates() {
		getWorldProperties().setValue("playerX", getEntity().getX());
		getWorldProperties().setValue("playerY", getEntity().getY());
	}

}
