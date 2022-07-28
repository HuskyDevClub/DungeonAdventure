package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.ENEMY_MAX_SPEED;
import static com.griffinryan.dungeonadventure.engine.Config.ENEMY_MIN_SPEED;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class EnemyComponent extends Component {

	private int speed;
	private boolean y = true;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel idleChannel, walkChannel, backChannel;

	/**
	 * EnemyComponent() is a constructor that takes different
	 * AnimationChannel parameters to create an animated Entity.
	 *
	 * @see AnimatedTexture
	 * @see Component
	 */
	public EnemyComponent() {
		int moveSpeed = random(ENEMY_MIN_SPEED, ENEMY_MAX_SPEED);
		var bound = texture("sprite/enemy.png", 60, 60).brighter();
		AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/efront.png"),
				4, 20, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel walk = new AnimationChannel(FXGL.image("spritesheet/eleft.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel back = new AnimationChannel(FXGL.image("spritesheet/eback.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

		this.idleChannel = idle;
		this.walkChannel = walk;
		this.backChannel = back;

		this.speed = moveSpeed;
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
	public void onAdded() {
		entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(idleChannel);

		getWorldProperties().setValue("enemyX", this.getEntity().getX());
		getWorldProperties().setValue("enemyY", this.getEntity().getY());
	}


	/**
	 * onUpdate() sets properties upon updates of
	 * the Component object.
	 *
	 * @param tpf Double value tpf.
	 * @see Component
	 */
	@Override
	public void onUpdate(double tpf) {
		/* update the position of the player. */
		if(y != true){
			entity.translateX(speed * tpf);
			if (speed != 0) {
				if (texture.getAnimationChannel() == idleChannel) {
					texture.loopAnimationChannel(walkChannel);
				}
				speed = (int) (speed * 0.9);
				if (FXGLMath.abs(speed) < 1) {
					speed = 0;
					texture.loopAnimationChannel(idleChannel);
				}
			}

		} else {
			entity.translateY(speed * tpf);
			if (speed != 0) {
				if (texture.getAnimationChannel() == idleChannel) {
					texture.loopAnimationChannel(walkChannel);
				}
				speed = (int) (speed * 0.9);
				if (FXGLMath.abs(speed) < 1) {
					speed = 0;
					texture.loopAnimationChannel(idleChannel);
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
	 * Retrieves the texture used for
	 * boundary collision calculations.
	 *
	 * @see Texture
	 * */
	public Texture getBoundTexture() {
		return boundTexture;
	}
}
