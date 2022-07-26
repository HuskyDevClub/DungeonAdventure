package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.component.*;

import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;
import static com.griffinryan.dungeonadventure.engine.EntityType.ENEMY;

public class PlayerEnemyHandler extends CollisionHandler {

	public PlayerEnemyHandler(){
		super(PLAYER, ENEMY);
	}

	@Override
	protected void onCollisionBegin(Entity player, Entity enemy){
		FXGL.getGameScene().getViewport().shakeTranslational(30);

		// enemy.getComponent(EnemyComponent.class).die();
		// spawn("Death effect....")
		FXGL.play("laser.wav");
		enemy.removeFromWorld();
	}

}
