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
	 * 
	 * @param moveSpeed
	 * @param idle
	 * @param walk
	 * @param back
	 * @param bound
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
	 * 
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
	 * @param tpf
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
	 * 
	 */
	public void moveRight() {
		speed = 250;
		y = false;
		getEntity().setScaleX(1);
	}

	/**
	 * 
	 */
	public void moveLeft() {
		speed = -250;
		y = false;
		getEntity().setScaleX(-1);
	}

	/**
	 * 
	 */
	public void moveUp() {
		speed = 250;
		y = true;
		getEntity().setScaleY(1);
	}

	/**
	 * 
	 */
	public void moveDown() {
		speed = -250;
		y = true;
		getEntity().setScaleY(-1);
	}

}
