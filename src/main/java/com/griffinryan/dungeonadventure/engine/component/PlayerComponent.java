package com.griffinryan.dungeonadventure.engine.component;

import javafx.geometry.Point2D;
import javafx.util.Duration;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.animationBuilder;
import static com.griffinryan.dungeonadventure.engine.Config.*;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class PlayerComponent extends AbstractComponent {

	private int speed = 0;
	private int pspeed = PLAYER_MAX_SPEED;
	private boolean y = true;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel animIdle, animWalk, animWalkBack;

	/**
	 * PlayerComponent() is a constructor that takes different
	 * AnimationChannel parameters to create an animated player Entity.
	 *
	 * @see AnimatedTexture
	 * @see Component
	 */
	public PlayerComponent(){
		int moveSpeed = random(PLAYER_MIN_SPEED, PLAYER_MAX_SPEED);
		var bound = texture("sprite/front-1.png").brighter();

		AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/front.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel walk = new AnimationChannel(FXGL.image("spritesheet/right.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel back = new AnimationChannel(FXGL.image("spritesheet/down.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

		this.animIdle = idle;
		this.animWalk = walk;
		this.animWalkBack = back;

		this.boundTexture = bound;
		this.texture = new AnimatedTexture(animIdle);
	}

	public PlayerComponent(String bounding, String front, String right){
		int moveSpeed = random(PLAYER_MIN_SPEED, PLAYER_MAX_SPEED);
		var bound = texture(bounding).brighter();

		AnimationChannel idle = new AnimationChannel(FXGL.image(front),
				4, 16, 28, Duration.seconds(.7), 0, 3);

		AnimationChannel walk = new AnimationChannel(FXGL.image(right),
				4, 16, 28, Duration.seconds(.7), 0, 3);
		AnimationChannel back = new AnimationChannel(FXGL.image(right),
				4, 16, 28, Duration.seconds(.7), 0, 3);

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
		// entity.getTransformComponent().setScaleOrigin(new Point2D(0,0));
		entity.setScaleX(2);
		entity.setScaleY(2);
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
		if(!y){
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
		speed = pspeed;
		y = false;
		getEntity().setScaleX(2);
	}

	/**
	 * moveLeft() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveLeft() {
		speed = -pspeed;
		y = false;
		getEntity().setScaleX(-2);
	}

	/**
	 * moveUp() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveUp() {
		speed = pspeed;
		y = true;
	}

	/**
	 * moveDown() handles the
	 * Entity object movement.
	 *
	 * @see Component
	 */
	public void moveDown() {
		speed = -pspeed;
		y = true;
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
