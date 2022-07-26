package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

public class EnemyComponent extends Component {

	private int speed = 0;
	private boolean y = true;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel animIdle, animWalk, animWalkBack;

	public EnemyComponent(int moveSpeed, AnimationChannel idle, AnimationChannel walk, AnimationChannel back, Texture bound){
		this.animIdle = idle;
		this.animWalk = walk;
		this.animWalkBack = back;

		this.speed = moveSpeed;
		this.boundTexture = bound;
		this.texture = new AnimatedTexture(animIdle);
	}

	@Override
	public void onAdded(){
		entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(animIdle);
	}

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

	public void moveRight() {
		speed = 250;
		y = false;
		getEntity().setScaleX(1);
	}

	public void moveLeft() {
		speed = -250;
		y = false;
		getEntity().setScaleX(-1);
	}

	public void moveUp() {
		speed = 250;
		y = true;
		getEntity().setScaleY(1);
	}

	public void moveDown() {
		speed = -250;
		y = true;
		getEntity().setScaleY(-1);
	}


}
