package com.griffinryan.dungeonadventure.engine;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.*;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.*;
import javafx.geometry.Point2D;

import com.griffinryan.dungeonadventure.engine.component.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.griffinryan.dungeonadventure.engine.Config.*;
import static com.griffinryan.dungeonadventure.engine.EntityType.*;

public class AdventureFactory implements EntityFactory {

	private static final int SPAWN_DISTANCE = 100;

	private static final Point2D[] spawnPoints = new Point2D[] {
			new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE),
			new Point2D(SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE)
	};

	private static Point2D getRandomSpawnPoint(){
		return spawnPoints[FXGLMath.random(0, 3)];
	}

	@Spawns("Background")
	public Entity spawnBackground(SpawnData data){
		return FXGL.entityBuilder(data)
				.type(LEVEL)
				.with(IS_BACKGROUND ? new BackgroundLevelComponent() : new CollidableComponent(false))
				.with(new LevelComponent())
				.build();
	}

	@Spawns("Player")
	public Entity spawnPlayer(SpawnData data){

		/* Setup parameters to give to the CharacterComponent object. */
		var playerTexture = FXGL.texture("sprite/front-1.png");
		AnimationChannel idleChannel = new AnimationChannel(FXGL.image("spritesheet/front.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel walkChannel = new AnimationChannel(FXGL.image("spritesheet/right.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel backChannel = new AnimationChannel(FXGL.image("spritesheet/down.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

		AnimationComponent animatedPlayer = new AnimationComponent(idleChannel, walkChannel, backChannel, playerTexture);

		return FXGL.entityBuilder()
				.type(PLAYER)
				.viewWithBBox(playerTexture) // boundary setting.
				.collidable()
				.zIndex(1000)
				.with(animatedPlayer)
				.with(new CollidableComponent(true))
				.build();
	}

	@Spawns("Enemy")
	public Entity spawnEnemy(SpawnData data) {

		/* Setup parameters to give to the CharacterComponent object. */
		int moveSpeed = random(ENEMY_MIN_SPEED, ENEMY_MAX_SPEED);
		var bound = texture("sprite/enemy.png", 60, 60).brighter();
		AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/efront.png"),
				4, 20, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel walk = new AnimationChannel(FXGL.image("spritesheet/eleft.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel back = new AnimationChannel(FXGL.image("spritesheet/edown.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

		EnemyComponent animatedEnemy = new EnemyComponent(moveSpeed, idle, walk, back, bound);

		var e = entityBuilder(data)
				.type(ENEMY)
				.at(getRandomSpawnPoint())
				.bbox(new HitBox(new Point2D(30, 30), BoundingShape.box(30, 30)))
				.with(new HealthIntComponent(ENEMY_HP))
				.with(new CollidableComponent(true))
				.with(animatedEnemy)
				.build();

		e.setReusable(true);

		return e;
	}

	/*	For reference:
	@Spawns("ParticleLayer")
	public Entity spawnParticleLayer(SpawnData data){
		return FXGL.entityBuilder(data)
				.type(PARTICLE_LAYER)
				.with(new ParticleCanvasComponent())
				.zIndex(5000) // For parallax-scrolling effect.
				.build();
	}

	@Spawns("Bullet")
	public Entity spawnBullet(SpawnData data) {
		// bullet texture is 54x13, hence 6.5

		var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
		expireClean.pause();

		var t = ImagesKt.fromPixels(54, 13,
				texture("Bullet.png")
						.pixels()
						.stream()
						.map(p -> {
							// texture is 54 in X axis
							double alphaMod = p.getX() / 54.0;

							return new Pixel(p.getX(), p.getY(), Color.color(p.getR(), p.getG(), p.getB(), p.getA() * alphaMod), p.getParent());
						})
						.collect(Collectors.toList())
		);

		var e = entityBuilder(data)
				.at(data.getX(), data.getY() - 6.5)
				.type(BULLET)
				.viewWithBBox(new Texture(t))
				.with(new CollidableComponent(true))
				.with(new ProjectileComponent(data.get("direction"), BULLET_MOVE_SPEED))
				.with(new BulletComponent())
				.with(expireClean)
				.rotationOrigin(0, 6.5)
				.build();

		// creating entities can be expensive on mobile, so pool bullets
		e.setReusable(true);

		return e;
	}	*/

}
