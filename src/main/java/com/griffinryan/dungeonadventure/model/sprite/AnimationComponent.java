package com.griffinryan.dungeonadventure.model.sprite;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.util.Duration;

public class AnimationComponent extends Component {

	private int speed = 0;
	private boolean y;

	private AnimatedTexture texture;
	private AnimationChannel animIdle, animWalk;

	public AnimationComponent(){
		this.animIdle = new AnimationChannel(FXGL.image("spritesheet/front.png"),
				4, 32, 42, Duration.seconds(1), 1, 1);
		this.animWalk = new AnimationChannel(FXGL.image("spritesheet/right.png"),
				4, 32, 42, Duration.seconds(1), 0, 3);

		this.texture = new AnimatedTexture(animIdle);
	}

	@Override
	public void onAdded(){
		entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
		entity.getViewComponent().addChild(texture);
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
		speed = 150;
		y = false;
		getEntity().setScaleX(1);
	}

	public void moveLeft() {
		speed = -150;
		y = false;
		getEntity().setScaleX(-1);
	}

	public void moveUp() {
		speed = 150;
		y = true;
		getEntity().setScaleY(1);
	}

	public void moveDown() {
		speed = -150;
		y = true;
		getEntity().setScaleY(-1);
	}
}
