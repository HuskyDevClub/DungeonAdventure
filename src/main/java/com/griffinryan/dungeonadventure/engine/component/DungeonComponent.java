package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.MinimapView;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The DungeonComponent class instantiates and
 * determines the layout of the dungeon maze.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @see RoomComponent
 */
public class DungeonComponent extends AbstractComponent {

	private MinimapView minimapView;
	private HashMap<Integer, RoomComponent> dungeonMap;
	private int size;
	private RoomComponent[][] maze;

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
		maze = createMaze();
	}

	/**
	 * Creates the initial maze using a 2D array.
	 *
	 * @return RoomComponent[][]
	 * */
	private RoomComponent[][] createMaze() {
		maze = new RoomComponent[size/4][size/4];
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze.length; j++) {
				maze[i][j] = new RoomComponent(size, i, j);
			}
		}
		return maze;
	}

	public HashMap<Integer, RoomComponent> getDungeonMap() {
		return dungeonMap;
	}

	public MinimapView getMinimapView() {
		return minimapView;
	}

}
