package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.views.MinimapView;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.rooms.Room;

import java.util.HashMap;

public class RoomComponent extends AbstractComponent {

	/* TODO:
	 *  		- Store these in a HashMap in the
	 *			  constructor for this.obj
	 * 			*
	 * 			- Retrieve from the
	 * 			  Dungeon object.
	 *  */
	private Dungeon dungeon;
	private Ogre ogre;
	private Warrior warrior;

	private DoorComponent[] doorComponentGroup;
	private PotionComponent potionComponent;
	private EnemyComponent enemyComponent;
	private PlayerComponent playerComponent;

	private HashMap<String, AbstractComponent> roomMap;

	/**
	 * RoomComponent stores information about each
	 * of the game components present in the current room.
	 * All the RoomComponents are stored in the DungeonComponent.
	 *
	 * @see DungeonComponent
	 * */
	public RoomComponent() {

	}

	/**
	 * RoomComponent stores information about each
	 * of the game components present in the current room.
	 * All the RoomComponents are stored in the DungeonComponent.
	 *
	 * @param n True if doorComponentN should spawn.
	 * @param s True if doorComponentS should spawn.
	 * @param e True if doorComponentE should spawn.
	 * @param w True if doorComponentW should spawn.
	 * @param enemy True if enemyComponent should spawn.
	 * @see DungeonComponent
	 * */
	public RoomComponent(boolean n, boolean s, boolean e, boolean w, boolean enemy) {

	}
}
