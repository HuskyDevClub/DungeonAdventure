package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;
import static com.griffinryan.dungeonadventure.engine.EntityType.ENEMY;

public class PlayerEnemyHandler extends CollisionHandler {

	public PlayerEnemyHandler(){
		super(PLAYER, ENEMY);
	}

	@Override
	protected void onCollisionBegin(Entity player, Entity enemy){
		FXGL.getGameScene().getViewport().shakeTranslational(100);

		/* 	Do/store damage calculations here! */
		int initialHP = FXGL.getWorldProperties().getInt("playerHP");
		int enemyHP = FXGL.getWorldProperties().getInt("enemyHP");
		int resultingHP = initialHP - enemyHP;
		int tempHP = enemyHP - initialHP;
		FXGL.getWorldProperties().setValue("playerHP", resultingHP);
		FXGL.getWorldProperties().setValue("enemyHP", tempHP);

		if(tempHP <= 0){
			FXGL.play("laser.wav");
			enemy.removeFromWorld();
		} else if (resultingHP <= 0){
			player.removeFromWorld();
		}
	}

}
