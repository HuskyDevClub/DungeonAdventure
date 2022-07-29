package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

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
		/*	Do/store damage/object calculations here!	*/

		FXGL.play("stab.wav");
		door.removeFromWorld();
	}
}
