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
import javafx.geometry.Point2D;

import com.griffinryan.dungeonadventure.engine.component.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.ENEMY_HP;
import static com.griffinryan.dungeonadventure.engine.Config.SPAWN_DISTANCE;

/**
 * AdventureFactory is a user-defined EntityFactory object
 * to handle all entities in game engine.
 *
 * @author Griffin Ryan (glryan@uw.edu)
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
				.type(EntityType.PLAYER)
				.at(getRandomSpawnPoint("player")) // Set the spawn and boundary.
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
				.type(EntityType.ENEMY)
				.at(getRandomSpawnPoint("enemy"))
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
				.type(EntityType.POTION)
				.at(getRandomSpawnPoint("potion"))
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
	 * @return Entity north side door
	 * @see DoorComponent
	 * */
	@Spawns("doorN")
	public Entity spawnNorthDoor(SpawnData data){

		getWorldProperties().setValue("doorN", true);
		DoorComponent door = new DoorComponent();

		Point2D curDoorAnchor = new Point2D(door.getAnchor_x(), door.getAnchor_y());
		data = new SpawnData(curDoorAnchor);

		return FXGL.entityBuilder()
				.type(EntityType.DOOR)
				.at(curDoorAnchor)
				.bbox(door.getHitBox()) /* Retrieve the HitBox. */
				.with(door)
				.with(new CollidableComponent(true))
				.zIndex(8)
				.build();
	}

	/**
	 * spawnDoor() returns an Entity
	 * object appended with DoorComponent.
	 *
	 * @param data SpawnData object to use.
	 * @return Entity north side door
	 * @see DoorComponent
	 * */
	@Spawns("doorS")
	public Entity spawnSouthDoor(SpawnData data){
		getWorldProperties().setValue("doorS", true);

		DoorComponent door = new DoorComponent();
		Point2D curDoorAnchor = new Point2D(door.getAnchor_x(), door.getAnchor_y());

		return FXGL.entityBuilder()
				.type(EntityType.DOOR)
				.at(curDoorAnchor)
				.bbox(door.getHitBox()) /* Retrieve the HitBox. */
				.with(door)
				.with(new CollidableComponent(true))
				.zIndex(8)
				.build();
	}

	/**
	 * spawnDoor() returns an Entity
	 * object appended with DoorComponent.
	 *
	 * @param data SpawnData object to use.
	 * @return Entity north side door
	 * @see DoorComponent
	 * */
	@Spawns("doorE")
	public Entity spawnEastDoor(SpawnData data){
		getWorldProperties().setValue("doorE", true);

		DoorComponent door = new DoorComponent();
		Point2D curDoorAnchor = new Point2D(door.getAnchor_x(), door.getAnchor_y());

		return FXGL.entityBuilder()
				.type(EntityType.DOOR)
				.at(curDoorAnchor)
				.bbox(door.getHitBox()) /* Retrieve the HitBox. */
				.with(door)
				.with(new CollidableComponent(true))
				.zIndex(8)
				.build();
	}

	/**
	 * spawnDoor() returns an Entity
	 * object appended with DoorComponent.
	 *
	 * @param data SpawnData object to use.
	 * @return Entity north side door
	 * @see DoorComponent
	 * */
	@Spawns("doorW")
	public Entity spawnWestDoor(SpawnData data){
		getWorldProperties().setValue("doorW", true);

		DoorComponent door = new DoorComponent();
		Point2D curDoorAnchor = new Point2D(door.getAnchor_x(), door.getAnchor_y());

		return FXGL.entityBuilder()
				.type(EntityType.DOOR)
				.at(curDoorAnchor)
				.bbox(door.getHitBox()) /* Retrieve the HitBox. */
				.with(door)
				.with(new CollidableComponent(true))
				.zIndex(8)
				.build();
	}

	/**
	 * Retrieves a random spawn point from array.
	 *
	 * @param type of Entity to get spawn point for.
	 * @return Point2D
	 */
	private static Point2D getRandomSpawnPoint(String type){

		/* return a random spawn point based on String.*/
		if(type == "potion") {
			return potionSpawnPoints[FXGLMath.random(0, 3)];
		} else if(type == "enemy") {
			return potionSpawnPoints[FXGLMath.random(0, 3)];
		} else if(type == "player") {
			return potionSpawnPoints[FXGLMath.random(0, 3)];
		} else if(type == "door") {
			return potionSpawnPoints[FXGLMath.random(0, 3)];
		}
		return potionSpawnPoints[FXGLMath.random(0, 3)];
	}

	/**
	 * A collection of potential
	 * spawnPoints in a quick Point2D[] array.
	 *
	 */
	private static final Point2D[] potionSpawnPoints = new Point2D[] {
			new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE),
			new Point2D(SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE)
	};



}
