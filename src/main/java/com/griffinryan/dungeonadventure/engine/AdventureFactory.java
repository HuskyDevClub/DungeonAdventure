package com.griffinryan.dungeonadventure.engine;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ManaIntComponent;
import com.almasb.fxgl.dsl.components.view.TextViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.google.gson.Gson;
import com.griffinryan.dungeonadventure.AdventureApp;
import com.griffinryan.dungeonadventure.engine.component.*;
import com.griffinryan.dungeonadventure.menu.HeroType;
import com.griffinryan.dungeonadventure.menu.PlayerInfo;
import javafx.geometry.Point2D;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.griffinryan.dungeonadventure.engine.Config.SPAWN_DISTANCE;

/**
 * AdventureFactory is a user-defined EntityFactory object
 * to handle all entities in game engine.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @see Entity
 */
public class AdventureFactory implements EntityFactory {

    /**
     * A collection of potential
     * spawnPoints in a quick Point2D[] array.
     */
    private static final Point2D[] potionSpawnPoints = new Point2D[]{
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
    private static Point2D getRandomSpawnPoint() {
        return potionSpawnPoints[FXGLMath.random(0, 3)];
    }

    /**
     * spawnDungeon() returns an Entity
     * object appended with LevelComponent().
     *
     * @param data SpawnData object to use.
     * @return Entity
     * @see EntityFactory
     */
    @Spawns("Dungeon")
    public Entity spawnDungeon(final SpawnData data) {
        final DungeonComponent dungeon;
        if (getWorldProperties().exists("dungeon_id")) {
            dungeon = new DungeonComponent(getWorldProperties().getString("dungeon_id"));
            getWorldProperties().remove("dungeon_id");
        } else {
            dungeon = new DungeonComponent(10, 10);
        }
		/* TODO .with()
		    Retrieve from Property Map */
        return FXGL.entityBuilder(data)
            .with()
            .with(new CollidableComponent(false))
            .zIndex(0)
            .with(new TextViewComponent(40, 40, "HP: "))
            .with(new HealthIntComponent())
            .with(new ManaIntComponent(200))
            .with(dungeon)
            .build();
    }

    /**
     * spawnBackground() returns an Entity
     * object appended with LevelComponent().
     *
     * @param data SpawnData object to use.
     * @return Entity
     * @see EntityFactory
     */
    @Spawns("Background")
    public Entity spawnBackground(final SpawnData data) {
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
    public Entity spawnPlayer(final SpawnData data) {

        final Gson gson = new Gson();

        final BufferedReader br;

        final PlayerInfo playerObj;
        try {
            br = new BufferedReader(
                new FileReader("system/PlayerInfo.json"));
            playerObj = gson.fromJson(br, PlayerInfo.class);
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //convert the json string back to object
        final HeroType type = playerObj.chosenHero;


        final PlayerComponent animatedPlayer;

        if (type == HeroType.PRIEST) {
            animatedPlayer = new PlayerComponent("spritesheet/dungeon/menu/wizzard_f_idle_anim_f0.png",
                "spritesheet/dungeon/game/wizzard_f_idle_anim_f.png",
                "spritesheet/dungeon/game/wizzard_f_run_anim_f.png");
        } else if (type == HeroType.THIEF) {
            animatedPlayer = new PlayerComponent("spritesheet/dungeon/menu/elft_m_idle_anim_f0.png",
                "spritesheet/dungeon/game/elf_m_idle_anim_f.png",
                "spritesheet/dungeon/game/elf_m_run_anim_f.png");
        } else {
            animatedPlayer = new PlayerComponent("spritesheet/dungeon/menu/knight_m_idle_anim_f0.png",
                "spritesheet/dungeon/game/knight_m_idle_anim_f.png",
                "spritesheet/dungeon/game/knight_m_run_anim_f.png");
        }

        getWorldProperties().setValue("heroType", type);

        return FXGL.entityBuilder()
            .type(EntityType.PLAYER)
            .at(new Point2D(250, 250)) // Set the spawn and boundary.
            .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(20, 20)))
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
    public Entity spawnEnemy(final SpawnData data) {

        /* Setup parameters to give to the CharacterComponent object. */
        final EnemyComponent animatedEnemy = new EnemyComponent(FXGL.<AdventureApp>getAppCast().getPlayer(), 100);

        final Entity e = entityBuilder(data)
            .type(EntityType.ENEMY)
            .at(new Point2D(500, 500))
            .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(20, 20)))
            .with(new HealthIntComponent(getWorldProperties().getInt("enemyHP")))
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
    public Entity spawnPotion(final SpawnData data) {

        /* Setup parameters to give to the CharacterComponent object. */

        final PotionComponent animatedPotion = new PotionComponent();

        return FXGL.entityBuilder()
            .type(EntityType.POTION)
            .at(getRandomSpawnPoint())
            .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(80, 80)))
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
     */
    @Spawns("door")
    public Entity spawnDoor(SpawnData data) {

        final DoorComponent door = new DoorComponent();

        final Point2D curDoorAnchor = new Point2D(door.getAnchorX(), door.getAnchorY());
        data = new SpawnData(curDoorAnchor);

        final Entity d = FXGL.entityBuilder()
            .type(EntityType.DOOR)
            .at(curDoorAnchor)
            .bbox(new HitBox(new Point2D(0.0, 0.0), BoundingShape.box(80, 80)))
            .with(door)
            .collidable()
            .zIndex(8) // same as player
            .build();
        d.setReusable(true);

        return d;
    }

    /**
     * spawnDoor() returns an Entity
     * object appended with DoorComponent.
     *
     * @param data SpawnData object to use.
     * @return Entity north side door
     * @see DoorComponent
     */
    @Spawns("doorN")
    public Entity spawnNorthDoor(SpawnData data) {

        getWorldProperties().setValue("doorN", true);
        final DoorComponent door = new DoorComponent();

        final Point2D curDoorAnchor = new Point2D(door.getAnchorX(), door.getAnchorY());
        data = new SpawnData(curDoorAnchor);

        return FXGL.entityBuilder()
            .type(EntityType.DOOR)
            .at(curDoorAnchor)
            .bbox(new HitBox(new Point2D(0.0, 0.0), BoundingShape.box(80, 80)))
            .with(door)
            .collidable()
            .zIndex(8) // same as player
            .build();
    }

    /**
     * spawnDoor() returns an Entity
     * object appended with DoorComponent.
     *
     * @param data SpawnData object to use.
     * @return Entity north side door
     * @see DoorComponent
     */
    @Spawns("doorS")
    public Entity spawnSouthDoor(final SpawnData data) {
        getWorldProperties().setValue("doorS", true);

        final DoorComponent door = new DoorComponent();
        final Point2D curDoorAnchor = new Point2D(door.getAnchorX(), door.getAnchorY());

        return FXGL.entityBuilder()
            .type(EntityType.DOOR)
            .at(curDoorAnchor)
            .bbox(new HitBox(new Point2D(0.0, 0.0), BoundingShape.box(80, 80)))
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
     */
    @Spawns("doorE")
    public Entity spawnEastDoor(final SpawnData data) {
        getWorldProperties().setValue("doorE", true);

        return buildDoorEntity();
    }

    /**
     * spawnDoor() returns an Entity
     * object appended with DoorComponent.
     *
     * @param data SpawnData object to use.
     * @return Entity north side door
     * @see DoorComponent
     */
    @Spawns("doorW")
    public Entity spawnWestDoor(final SpawnData data) {
        getWorldProperties().setValue("doorW", true);

        return buildDoorEntity();
    }

    /**
     * build the door entity using FXGL
     *
     * @return a Door Entity
     */
    private Entity buildDoorEntity() {
        final DoorComponent door = new DoorComponent();
        final Point2D curDoorAnchor = new Point2D(door.getAnchorX(), door.getAnchorY());
        final HitBox h = door.getHitBox();

        return FXGL.entityBuilder()
            .type(EntityType.DOOR)
            .at(curDoorAnchor)
            .bbox(new HitBox(new Point2D(0.0, 0.0), BoundingShape.box(80, 80)))
            .with(door)
            .with(new CollidableComponent(true))
            .zIndex(8)
            .build();
    }


}
