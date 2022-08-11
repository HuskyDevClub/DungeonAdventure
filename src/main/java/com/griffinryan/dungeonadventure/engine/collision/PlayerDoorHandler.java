package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.AdventureFactory;
import com.griffinryan.dungeonadventure.engine.component.DoorComponent;
import com.griffinryan.dungeonadventure.engine.component.RoomComponent;
import javafx.geometry.Point2D;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
		FXGL.getGameScene().getViewport().shakeTranslational(50);
		FXGL.play("stab.wav");
		/*	Do/store damage/object calculations here!	*/

		ArrayList<Entity> list = FXGL.getGameWorld().getEntities();
		DoorComponent temp = door.getComponent(DoorComponent.class);

		String position = temp.mapKey;
		int row = FXGL.getWorldProperties().getInt("row");
		int column = FXGL.getWorldProperties().getInt("column");
		RoomComponent[][] tempRooms = FXGL.getWorldProperties().getObject("rooms");

		if(temp.mapKey.equalsIgnoreCase("doorN")) {
			column--;
		} else if(temp.mapKey.equalsIgnoreCase("doorE")) {
			row++;
		} else if(temp.mapKey.equalsIgnoreCase("doorS")) {
			column++;
		} else if(temp.mapKey.equalsIgnoreCase("doorW")) {
			row--;
		}
		FXGL.getWorldProperties().setValue("row", row);
		FXGL.getWorldProperties().setValue("column", column);

		door.removeFromWorld();
		player.setPosition(new Point2D(FXGL.getAppWidth() - 800, FXGL.getAppHeight() - 500));
		FXGL.getGameWorld().spawn("Enemy");
	}
}
