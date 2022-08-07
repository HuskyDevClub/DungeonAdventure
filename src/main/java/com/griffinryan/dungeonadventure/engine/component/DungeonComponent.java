package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.MinimapView;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.rooms.Room;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * The DungeonComponent class instantiates and
 * determines the layout of the dungeon maze.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @see RoomComponent
 */
public class DungeonComponent extends AbstractComponent {

	private MinimapView minimapView;
	private HashMap<Integer, RoomComponent> dungeonMap;
	private int size;

	/**
	 * DungeonComponent() is a constructor that takes different
	 * that creates a new randomly generated Dungeon.
	 * Another constructed can be implemented if
	 * the player chooses to load a save file.
	 *
	 * @param theSize Number of rooms in dungeon.
	 */
	public DungeonComponent(int theSize){

		size = theSize;
		minimapView = new MinimapView(FXGL.getGameWorld(), FXGL.getAppWidth(),
				FXGL.getAppWidth(), 600, 600);

		/* Creates the dungeon/room HashMap */
		dungeonMap = createDungeonMap();
	}

	/**
	 * Creates the initial Dungeon HashMap<int, RoomComponent> with
	 * randomly generated rooms, enemies, doors, and randomly selected
	 * rooms to connect with the pre-determined number of doors.
	 *
	 * @return dungeonMap HashMap to store RoomComponents with.
	 * */
	private HashMap<Integer, RoomComponent> createDungeonMap() {

		HashMap<Integer, RoomComponent> result = new HashMap<>(size);

		/* TODO: Generate all rooms/doors connecting
		*   the rooms randomly here!
		*
		*  TODO: Add a helper method to connect the
		*   random number of generated doors
		*   to randomly selected rooms.
		**********************************************/
		int count = 0;
		while(count < size) {

			/* TODO: The stuff above right here!!!!!!!! */
			result.put(count, new RoomComponent());
			count++;
		}

		return result;
	}

	public HashMap<Integer, RoomComponent> getDungeonMap() {
		return dungeonMap;
	}

	public MinimapView getMinimapView() {
		return minimapView;
	}
}
