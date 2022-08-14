package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.AdventureFactory;
import com.griffinryan.dungeonadventure.engine.component.AbstractComponent;
import com.griffinryan.dungeonadventure.engine.component.DoorComponent;
import com.griffinryan.dungeonadventure.engine.component.DungeonComponent;
import com.griffinryan.dungeonadventure.engine.component.RoomComponent;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import javafx.geometry.Point2D;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.griffinryan.dungeonadventure.engine.Config.SPAWN_DISTANCE;
import static com.griffinryan.dungeonadventure.engine.EntityType.*;

public class PlayerDoorHandler extends CollisionHandler {

	/**
	 * PlayerEnemyHandler() is the constructor to create
	 * a new CollisionHandler for player/enemy collisions.
	 *
	 * @see CollisionHandler
	 */
	public PlayerDoorHandler(){
		super(PLAYER, DOOR);
	}

	/**
	 * onCollisionBegin() handles the game logic that happens
	 * upon collision of the two Entity objects.
	 *
	 * @param player Entity for the player.
	 * @param door Entity for the door.
	 */
	@Override
	protected void onCollisionBegin(Entity player, Entity door){
		if (door.isVisible()) {
			FXGL.getGameScene().getViewport().shakeTranslational(50);
			FXGL.play("stab.wav");
			/*	Do/store damage/object calculations here!	*/

			Dungeon theDungeon = FXGL.getWorldProperties().getObject("dungeonComponent_dungeon");
			System.out.println(theDungeon.getCurrentX());
			System.out.println(theDungeon.getCurrentY());
			System.out.println(theDungeon.getCurrentRoom().toString());

			DoorComponent temp = door.getComponent(DoorComponent.class);

			if (temp.mapKey.equalsIgnoreCase("doorN")) {
				theDungeon.move(Direction.UP);
			} else if (temp.mapKey.equalsIgnoreCase("doorE")) {
				theDungeon.move(Direction.RIGHT);
			} else if (temp.mapKey.equalsIgnoreCase("doorS")) {
				theDungeon.move(Direction.DOWN);
			} else if (temp.mapKey.equalsIgnoreCase("doorW")) {
				theDungeon.move(Direction.LEFT);
			}

			System.out.println(theDungeon.getCurrentX());
			System.out.println(theDungeon.getCurrentY());
			System.out.println(theDungeon.getCurrentRoom().toString());


			player.setPosition(new Point2D(FXGL.getAppWidth() - 800, FXGL.getAppHeight() - 500));

			/* hide entities and respawn for next room.. */
			Entity doorN = FXGL.getWorldProperties().getObject("dungeonComponent_doorN");
			doorN.setVisible(theDungeon.canMove(Direction.UP));
			Entity doorE = FXGL.getWorldProperties().getObject("dungeonComponent_doorE");
			doorE.setVisible(theDungeon.canMove(Direction.RIGHT));
			Entity doorS = FXGL.getWorldProperties().getObject("dungeonComponent_doorS");
			doorS.setVisible(theDungeon.canMove(Direction.DOWN));
			Entity doorW = FXGL.getWorldProperties().getObject("dungeonComponent_doorW");
			doorW.setVisible(theDungeon.canMove(Direction.LEFT));
		}
	}
}
