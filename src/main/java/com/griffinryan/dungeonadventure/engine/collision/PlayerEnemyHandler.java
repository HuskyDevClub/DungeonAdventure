package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.component.PlayerComponent;

import static com.griffinryan.dungeonadventure.engine.EntityType.ENEMY;
import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;

/**
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class PlayerEnemyHandler extends CollisionHandler {

    /**
     * PlayerEnemyHandler() is the constructor to create
     * a new CollisionHandler for player/enemy collisions.
     *
     * @see CollisionHandler
     */
    public PlayerEnemyHandler() {
        super(PLAYER, ENEMY);
    }

    /**
     * onCollisionBegin() handles the game logic that happens
     * upon collision of the two Entity objects.
     *
     * @param player Entity for the player.
     * @param enemy  Entity for the enemy.
     */
    @Override
    protected void onCollisionBegin(final Entity player, final Entity enemy) {
        FXGL.getGameScene().getViewport().shakeTranslational(100);

        /* 	Do/store damage calculations here! */
        final int initialHP = FXGL.getWorldProperties().getInt("playerHP");
        final int enemyHP = FXGL.getWorldProperties().getInt("enemyHP");
        final int resultingHP = initialHP - enemyHP;
        final int tempHP = enemyHP - initialHP;
        PlayerComponent.updateHp(resultingHP);
        FXGL.getWorldProperties().setValue("enemyHP", tempHP);

        if (tempHP <= 0) {
            FXGL.play("laser.wav");
            enemy.removeFromWorld();
        } else if (resultingHP <= 0) {
            player.removeFromWorld();
        }
    }

}
