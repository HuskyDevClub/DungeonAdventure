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

	private HashMap<String, DoorComponent> doorComponentMap;
	private PotionComponent potionComponent;
	private EnemyComponent enemyComponent;
	private PlayerComponent playerComponent;

	private HashMap<String, Component> roomMap;

	/**
	 * RoomComponent stores information about each
	 * of the game components present in the current room.
	 * All the RoomComponents are stored in the DungeonComponent.
	 *
	 * @see DungeonComponent
	 * */
	public RoomComponent() {

		if(getDoorNBoolean()) {
			doorComponentMap.put("doorN", new DoorComponent());
		}
		if(getDoorSBoolean()) {
			doorComponentMap.put("doorS", new DoorComponent());
		}
		if(getDoorEBoolean()) {
			doorComponentMap.put("doorE", new DoorComponent());
		}
		if (getDoorWBoolean()) {
			doorComponentMap.put("doorW", new DoorComponent());
		}

	}

	public HashMap<String, DoorComponent> getDoorComponentMap() {
		return doorComponentMap;
	}

	private boolean getDoorNBoolean() {
		return FXGL.getWorldProperties().getBoolean("doorN");
	}

	private boolean getDoorSBoolean() {
		return FXGL.getWorldProperties().getBoolean("doorS");
	}

	private boolean getDoorEBoolean() {
		return FXGL.getWorldProperties().getBoolean("doorE");
	}

	private boolean getDoorWBoolean() {
		return FXGL.getWorldProperties().getBoolean("doorW");
	}
}
