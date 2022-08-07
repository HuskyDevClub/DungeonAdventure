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
 * The DungeonComponent class instantiates
 * the
 * and determines the layout of the
 * dungeon's maze.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Room
 * @see Ogre
 */
public class DungeonComponent extends AbstractComponent {

	private MinimapView minimapView;
	private HashMap<String, RoomComponent> dungeonMap;
	/* TODO:
	    - Make a RoomComponent
	*  	- Store these in a HashMap in the
	*	  constructor for this.obj
	**/
	private DoorComponent[] doorComponent;
	private EnemyComponent[] enemyComponent;
	private PlayerComponent[] playerComponent;

	/**
	 * DungeonComponent() is a constructor that takes different
	 * AnimationChannel parameters to create an animated Entity.
	 * @see Component
	 */
	public DungeonComponent(){

		//minimapView = new MinimapView();
	}
}
