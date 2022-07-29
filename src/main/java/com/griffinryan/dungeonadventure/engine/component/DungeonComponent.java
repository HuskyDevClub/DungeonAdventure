package com.griffinryan.dungeonadventure.engine.component;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.rooms.Room;


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

	private Dungeon dungeon;
	private Ogre ogre;
	private Warrior warrior;

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
