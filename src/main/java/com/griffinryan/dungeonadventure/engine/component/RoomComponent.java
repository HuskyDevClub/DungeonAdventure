package com.griffinryan.dungeonadventure.engine.component;

import java.util.HashMap;

/**
 * The RoomComponent class instantiates and
 * determines a single room in DungeonComponent.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @see DungeonComponent
 */
public class RoomComponent extends AbstractComponent {

	private HashMap<String, DoorComponent> doorComponentMap;
	private PotionComponent potionComponent;
	private EnemyComponent enemyComponent;

	private int x, y;
	private int size;

	public RoomComponent(int theSize, int theX, int theY) {
		size = theSize;
		x = theX;
		y = theY;
		setDoors();
	}

	private void setDoors() {
		if(x == 0) {
			if(y == 0) {

			}
		}
	}

	public HashMap<String, DoorComponent> getDoorComponentMap() {
		return doorComponentMap;
	}
}
