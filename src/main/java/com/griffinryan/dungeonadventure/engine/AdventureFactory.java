package com.griffinryan.dungeonadventure.engine;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.griffinryan.dungeonadventure.engine.utils.DungeonUtility;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import com.griffinryan.dungeonadventure.engine.component.*;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
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
	 * spawnBackground() returns an Entity
	 * object appended with LevelComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see EntityFactory
	 */
	@Spawns("Background")
	public Entity spawnBackground(SpawnData data){
		return FXGL.entityBuilder(data)
				.with(new BackgroundComponent())
				.with(new CollidableComponent(false))
				.zIndex(0)
				// .with(new LevelComponent())
				.build();
	}

	/**
	 * spawnPlayer() returns an Entity
	 * object appended with PlayerComponent().
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see PlayerComponent
	 */
	@Spawns("Player")
	public Entity spawnPlayer(SpawnData data){

		/* Setup parameters to give to the CharacterComponent object. */
		PlayerComponent animatedPlayer = new PlayerComponent();

		return FXGL.entityBuilder()
				.type(PLAYER)
				.at(640,360) // Set the spawn and boundary.
				.bbox(new HitBox(new Point2D(0,0), BoundingShape.box(30, 30)))
				.collidable()
				.zIndex(3)
				.with(animatedPlayer)
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
		EnemyComponent animatedEnemy = new EnemyComponent();

		var e = entityBuilder(data)
				.type(ENEMY)
				.at(getRandomSpawnPoint())
				.bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(80, 80)))
				.with(new HealthIntComponent(ENEMY_HP))
				.with(new CollidableComponent(true))
				.zIndex(1)
				.with(animatedEnemy)
				.build();
		e.setReusable(true);

		return e;
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

		PotionComponent animatedPotion = new PotionComponent();

		return FXGL.entityBuilder()
				.type(POTION)
				.at(getRandomSpawnPoint())
				.bbox(new HitBox(new Point2D(14, 21), BoundingShape.box(80, 80)))
				.with(animatedPotion)
				.with(new CollidableComponent(true))
				.zIndex(2)
				.build();
	}

	/**
	 * spawnDoor() returns an Entity
	 * object appended with DoorComponent.
	 *
	 * @param data SpawnData object to use.
	 * @return Entity
	 * @see DoorComponent
	 * */
	@Spawns("Door")
	public Entity spawnDoor(SpawnData data){

		DoorComponent door = new DoorComponent(data);
		Point2D spawnPoint = new Point2D(data.getX(), data.getY());
		double[] bounds = door.getDoorBoundaryBox(spawnPoint);

		var d =FXGL.entityBuilder()
				.type(DOOR)
				.at(spawnPoint)
				.bbox(new HitBox(spawnPoint, BoundingShape.box(bounds[0], bounds[1])))
				.with(door)
				.with(new CollidableComponent(true))
				.zIndex(8)
				.build();

		d.setReusable(true);
		return d;
	}

	/**
	 * Retrieves a random spawn point from array.
	 *
	 * @return Point2D
	 */
	private static Point2D getRandomSpawnPoint(){
		return spawnPoints[FXGLMath.random(0, 3)];
	}

	/**
	 * A collection of potential
	 * spawnPoints in a Point2D[] array.
	 * TO-DO: Sometimes it spawns a weird coordinate?
	 */
	private static final Point2D[] spawnPoints = new Point2D[] {
			new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE),
			new Point2D(SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE)
	};

}
