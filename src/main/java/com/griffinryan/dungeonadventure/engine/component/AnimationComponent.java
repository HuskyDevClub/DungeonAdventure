package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.animationBuilder;
import static com.griffinryan.dungeonadventure.engine.EntityType.GRID;

public class AnimationComponent extends Component {

	private int speed = 0;
	private boolean y = true;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel animIdle, animWalk, animWalkBack;

	public AnimationComponent(AnimationChannel idle, AnimationChannel walk, AnimationChannel back, Texture bound){
		this.animIdle = idle;
		this.animWalk = walk;
		this.animWalkBack = back;

		this.boundTexture = bound;
		this.texture = new AnimatedTexture(animIdle);
	}

	public AnimationComponent(){
		this.animIdle = new AnimationChannel(FXGL.image("spritesheet/front.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		this.animWalk = new AnimationChannel(FXGL.image("spritesheet/right.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		this.animWalkBack = new AnimationChannel(FXGL.image("spritesheet/down.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

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

	/* For reference:
	public void playSpawnAnimation() {
		for (int i = 0; i < 6; i++) {
			final int j = i;

			runOnce(() -> {
				byType(GRID).get(0).getComponent(GridComponent.class)
						.applyExplosiveForce(1500 + j*100, new Point2D(getAppWidth() / 2.0, getAppHeight() / 2.0), j*50 + 50);
			}, Duration.seconds(i * 0.4));
		}

		var emitter = ParticleEmitters.newExplosionEmitter(450);
		emitter.setSize(1, 16);
		emitter.setBlendMode(BlendMode.SRC_OVER);
		emitter.setStartColor(Color.color(1.0, 1.0, 1.0, 0.5));
		emitter.setEndColor(Color.BLUE);
		emitter.setMaxEmissions(20);
		emitter.setEmissionRate(0.5);

		entityBuilder()
				.at(entity.getCenter().subtract(8, 8))
				.with(new ParticleComponent(emitter))
				.with(new ExpireCleanComponent(Duration.seconds(3)))
				.buildAndAttach();

		animationBuilder()
				.fadeIn(entity)
				.build();
	}	*/
}
