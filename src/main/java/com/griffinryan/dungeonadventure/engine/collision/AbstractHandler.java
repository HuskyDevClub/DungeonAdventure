package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.EntityType;

public abstract class AbstractHandler extends CollisionHandler {

    public AbstractHandler(EntityType player, EntityType enemy) {
        super(player, enemy);
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
        FXGL.getGameScene().getViewport().shakeTranslational(1000);

        /*	Do/store damage/object calculations here!	*/
        int initialHP = FXGL.getWorldProperties().getInt("playerHP");
        int potionHP = FXGL.getWorldProperties().getInt("potionHP");
        /* Use the model.heroes and model.monsters packages! */

        /* Set values in the Property Map here! */
        //FXGL.getWorldProperties().setValue("playerHP", 10111);
        FXGL.play("coin.wav");
    }
}
