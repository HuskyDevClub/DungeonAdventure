package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.EntityType;

public abstract class AbstractHandler extends CollisionHandler {

    public AbstractHandler(final EntityType player, final EntityType enemy) {
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
    protected void onCollisionBegin(final Entity player, final Entity potion) {
        FXGL.getGameScene().getViewport().shakeTranslational(1000);

        /*	Do/store damage/object calculations here!	*/
        final int initialHP = FXGL.getWorldProperties().getInt("playerHP");
        final int potionHP = FXGL.getWorldProperties().getInt("potionHP");
        /* Use the model.heroes and model.monsters packages! */

        /* Set values in the Property Map here! */
        //FXGL.getWorldProperties().setValue("playerHP", 10111);
        FXGL.play("coin.wav");
    }
}
