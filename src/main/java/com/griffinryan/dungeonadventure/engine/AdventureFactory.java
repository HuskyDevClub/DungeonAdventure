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
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.griffinryan.dungeonadventure.engine.Config.*;
import static com.griffinryan.dungeonadventure.engine.EntityType.*;

/**
 * AdventureFactory is a user-defined EntityFactory object
 * to handle all entities in game engine.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class AdventureFactory implements EntityFactory {

	/**
	 * A collection of potential
	 * spawnPoints in a Point2D[] array.
	 */
	private static final Point2D[] spawnPoints = new Point2D[] {
			new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE),
			new Point2D(SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE)
	};

	/**
	 * Retrieves a random spawn point from array.
	 *
	 * @return Point2D
	 */
	private static Point2D getRandomSpawnPoint(){
		return spawnPoints[FXGLMath.random(0, 3)];
	}

	/**
	 * spawnBackground() returns an Entity
	 * object appended with LevelComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see LevelComponent
	 */
	@Spawns("Background")
	public Entity spawnBackground(SpawnData data){
		return FXGL.entityBuilder(data)
				.type(LEVEL)
				.with(IS_BACKGROUND ? new BackgroundLevelComponent() : new CollidableComponent(false))
				.with(new LevelComponent())
				.build();
	}

	/**
	 * spawnPotion() returns an Entity
	 * object appended with PotionComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see PotionComponent
	 */
	@Spawns("Potion")
	public Entity spawnPotion(SpawnData data){

		/* Setup parameters to give to the CharacterComponent object. */
		var potionTexture = FXGL.texture("potion/potion.png");
		AnimationChannel idleChannel = new AnimationChannel(FXGL.image("potion/lifepotion.png"),
				4, 17, 16, Duration.seconds(0.6), 0, 3);

		PotionComponent animatedPotion = new PotionComponent(idleChannel, potionTexture);

		return FXGL.entityBuilder()
				.type(POTION)
				.at(getRandomSpawnPoint())
				.viewWithBBox(potionTexture) // boundary setting.
				.zIndex(1000)
				.with(animatedPotion)
				.with(new CollidableComponent(true))
				.build();
	}

	/**
	 * spawnPlayer() returns an Entity
	 * object appended with AnimationComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see AnimationComponent
	 */
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
				// .with(new CollidableComponent(true))
				.build();
	}

	/**
	 * spawnEnemy() returns an Entity
	 * object appended with EnemyComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see EnemyComponent
	 */
	@Spawns("Enemy")
	public Entity spawnEnemy(SpawnData data) {

		/* Setup parameters to give to the CharacterComponent object. */
		int moveSpeed = random(ENEMY_MIN_SPEED, ENEMY_MAX_SPEED);
		var bound = texture("sprite/enemy.png", 60, 60).brighter();
		AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/efront.png"),
				4, 20, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel walk = new AnimationChannel(FXGL.image("spritesheet/eleft.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);
		AnimationChannel back = new AnimationChannel(FXGL.image("spritesheet/eback.png"),
				4, 15, 30, Duration.seconds(0.4), 0, 3);

		EnemyComponent animatedEnemy = new EnemyComponent(moveSpeed, idle, walk, back, bound);

		var e = entityBuilder(data)
				.type(ENEMY)
				.at(getRandomSpawnPoint())
				.bbox(new HitBox(new Point2D(14, 21), BoundingShape.box(100, 100)))
				.with(new HealthIntComponent(ENEMY_HP))
				.with(new CollidableComponent(true))
				.with(animatedEnemy)
				.build();

		e.setReusable(true);

		return e;
	}

}
