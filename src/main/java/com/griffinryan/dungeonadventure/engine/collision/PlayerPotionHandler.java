package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;
import static com.griffinryan.dungeonadventure.engine.EntityType.POTION;

public class PlayerPotionHandler extends CollisionHandler {

	public PlayerPotionHandler(){
		super(PLAYER, POTION);
	}

	@Override
	protected void onCollisionBegin(Entity player, Entity potion){
		FXGL.getGameScene().getViewport().shakeTranslational(60);

		// enemy.getComponent(EnemyComponent.class).die();
		// spawn("Death effect....")
		FXGL.play("chord.wav");
		potion.removeFromWorld();
	}
}
