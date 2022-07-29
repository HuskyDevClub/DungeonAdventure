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

	private Dungeon dungeon;
	private Ogre ogre;
	private Warrior warrior;

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel idleChannel;

	private MinimapView minimapView;
	private HashMap<String, RoomComponent> dungeonMap;

	/* TODO: 	- Make a RoomComponent
	 *  			- Store these in a HashMap in the
	 *			- constructor for this.obj
	 * 			-
	 * 			- Retrieve from the
	 * 			- Dungeon object.
	 *  */
	private DoorComponent doorComponent;
	private EnemyComponent enemyComponent;
	private PlayerComponent playerComponent;


}
