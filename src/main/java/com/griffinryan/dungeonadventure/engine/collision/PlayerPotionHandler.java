package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;
import static com.griffinryan.dungeonadventure.engine.EntityType.POTION;

/**
 * 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class PlayerPotionHandler extends CollisionHandler {

	/**
	 * 
	 */
	public PlayerPotionHandler(){
		super(PLAYER, POTION);
	}

	/** 
	 * @param player
	 * @param potion
	 */
	@Override
	protected void onCollisionBegin(Entity player, Entity potion){
		FXGL.getGameScene().getViewport().shakeTranslational(30);

		/*	Do/store damage calculations here!	*/
		int initialHP = FXGL.getWorldProperties().getInt("playerHP");
		int potionHP = FXGL.getWorldProperties().getInt("potionHP");
		int resultingHP = initialHP + potionHP;

		FXGL.getWorldProperties().setValue("playerHP", resultingHP);
		FXGL.play("coin.wav");
		potion.removeFromWorld();
	}
	
}
