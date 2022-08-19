package com.griffinryan.dungeonadventure.engine.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.griffinryan.dungeonadventure.engine.component.DoorComponent;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import javafx.geometry.Point2D;

import static com.griffinryan.dungeonadventure.engine.EntityType.DOOR;
import static com.griffinryan.dungeonadventure.engine.EntityType.PLAYER;

public class PlayerDoorHandler extends CollisionHandler {

    /**
     * PlayerEnemyHandler() is the constructor to create
     * a new CollisionHandler for player/enemy collisions.
     *
     * @see CollisionHandler
     */
    public PlayerDoorHandler() {
        super(PLAYER, DOOR);
    }

    /**
     * onCollisionBegin() handles the game logic that happens
     * upon collision of the two Entity objects.
     *
     * @param player Entity for the player.
     * @param door   Entity for the door.
     */
    @Override
    protected void onCollisionBegin(Entity player, Entity door) {
        if (door.isVisible()) {
            FXGL.getGameScene().getViewport().shakeTranslational(50);
            FXGL.play("stab.wav");
            /*	Do/store damage/object calculations here!	*/

            Dungeon theDungeon = FXGL.getWorldProperties().getObject("dungeonComponent_dungeon");
            System.out.println(theDungeon.getCurrentX());
            System.out.println(theDungeon.getCurrentY());
            System.out.println(theDungeon.getCurrentRoom().getInfo());


            DoorComponent temp = door.getComponent(DoorComponent.class);

            if (temp.mapKey.equalsIgnoreCase("doorN")) {
                theDungeon.moveHero(Direction.UP);
            } else if (temp.mapKey.equalsIgnoreCase("doorE")) {
                theDungeon.moveHero(Direction.RIGHT);
            } else if (temp.mapKey.equalsIgnoreCase("doorS")) {
                theDungeon.moveHero(Direction.DOWN);
            } else if (temp.mapKey.equalsIgnoreCase("doorW")) {
                theDungeon.moveHero(Direction.LEFT);
            }

            System.out.println(theDungeon.getCurrentX());
            System.out.println(theDungeon.getCurrentY());
            System.out.println(theDungeon.getCurrentRoom().getInfo());


            player.setPosition(new Point2D(FXGL.getAppWidth() - 800, FXGL.getAppHeight() - 500));

            /* hide entities and respawn for next room */
            Entity doorN = FXGL.getWorldProperties().getObject("dungeonComponent_doorN");
            doorN.setVisible(theDungeon.canHeroMove(Direction.UP));
            Entity doorE = FXGL.getWorldProperties().getObject("dungeonComponent_doorE");
            doorE.setVisible(theDungeon.canHeroMove(Direction.RIGHT));
            Entity doorS = FXGL.getWorldProperties().getObject("dungeonComponent_doorS");
            doorS.setVisible(theDungeon.canHeroMove(Direction.DOWN));
            Entity doorW = FXGL.getWorldProperties().getObject("dungeonComponent_doorW");
            doorW.setVisible(theDungeon.canHeroMove(Direction.LEFT));
        }
    }
}
