package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.griffinryan.dungeonadventure.menu.HeroType;
import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;

import java.io.IOException;
import java.sql.SQLException;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;

/**
 * The DungeonComponent class instantiates and
 * determines the layout of the dungeon maze.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @see RoomComponent
 */
public class DungeonComponent extends AbstractComponent {

    //private MinimapView minimapView;

    private final Dungeon myDungeon;

    /**
     * DungeonComponent() is a constructor that takes different
     * that creates a new randomly generated Dungeon.
     * Another constructed can be implemented if
     * the player chooses to load a save file.
     *
     * @param theWidth  the width of the dungeon.
     * @param theHeight the height of the dungeon.
     */
    public DungeonComponent(int theWidth, int theHeight) {
        HeroType selectedHeroType = getWorldProperties().getObject("heroType");
        myDungeon = new Dungeon(HeroesFactory.spawn(selectedHeroType, selectedHeroType.toString()), theWidth, theHeight, 0, 0);
        FXGL.getWorldProperties().setValue("playerHP", myDungeon.getHero().getHealth());

    }

    /**
     * DungeonComponent() is a constructor that takes different
     * that creates a new randomly generated Dungeon.
     * Another constructed can be implemented if
     * the player chooses to load a save file.
     */
    public DungeonComponent(String theSaveId) {
        try {
            myDungeon = DungeonSqliteInterface.load(theSaveId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("Unable to load the Dungeon");
            throw new RuntimeException(e);
        }
        FXGL.getWorldProperties().setValue("playerHP", myDungeon.getHero().getHealth());

    }

    public Dungeon getDungeon() {
        return myDungeon;
    }
}
