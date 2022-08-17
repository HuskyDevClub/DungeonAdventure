package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.component.PlayerComponent;

import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;
import static com.griffinryan.dungeonadventure.engine.EntityType.POTION;

/**
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class PlayerPotionHandler extends CollisionHandler {

    /**
     * PlayerPotionHandler() is the constructor to create
     * a new CollisionHandler for player/potion collisions.
     *
     * @see CollisionHandler
     */
    public PlayerPotionHandler() {
        super(PLAYER, POTION);
    }

    /**
     * onCollisionBegin() handles the game logic that happens
     * upon collision of the two Entity objects.
     *
     * @param player Entity for the player.
     * @param potion Entity for the potion.
     */
    @Override
    protected void onCollisionBegin(Entity player, Entity potion) {
        FXGL.getGameScene().getViewport().shakeTranslational(30);

        /*	Do/store damage calculations here!	*/
        int initialHP = FXGL.getWorldProperties().getInt("playerHP");
        int potionHP = FXGL.getWorldProperties().getInt("potionHP");
        int resultingHP = initialHP + potionHP;

        PlayerComponent.updateHp(resultingHP);
        FXGL.play("coin.wav");
        potion.removeFromWorld();
    }

}
