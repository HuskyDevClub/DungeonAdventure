package com.griffinryan.dungeonadventure.engine.component;

import javafx.geometry.Point2D;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;

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
	 * @param moveSpeed Integer value for Entity movement rate.
	 * @param idle Channel for idle animation.
	 * @param walk Channel for walking animation.
	 * @param back Channel for walking backwards animation.
	 * @param bound Texture for boundary box.
	 */
	public EnemyComponent(int moveSpeed, AnimationChannel idle, AnimationChannel walk, AnimationChannel back, Texture bound){
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
	public void onAdded(){
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

}
