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

    private final int x;
    private final int y;
    private final int size;
    private HashMap<String, DoorComponent> doorComponentMap;
    private PotionComponent potionComponent;
    private EnemyComponent enemyComponent;

    public RoomComponent(final int theSize, final int theX, final int theY) {
        size = theSize;
        x = theX;
        y = theY;
        setDoors();
    }

    private void setDoors() {
        if (x == 0) {
            if (y == 0) {
                doorComponentMap = new HashMap<>(2);
                doorComponentMap.put("doorS", new DoorComponent("south"));
                doorComponentMap.put("doorE", new DoorComponent("east"));
            } else if (y == 3) {
                doorComponentMap = new HashMap<>(2);
                doorComponentMap.put("doorN", new DoorComponent("north"));
                doorComponentMap.put("doorE", new DoorComponent("east"));
            } else {
                doorComponentMap = new HashMap<>(3);
                doorComponentMap.put("doorN", new DoorComponent("north"));
                doorComponentMap.put("doorS", new DoorComponent("south"));
                doorComponentMap.put("doorE", new DoorComponent("east"));
            }
        } else if (x == 3) {
            if (y == 0) {
                doorComponentMap = new HashMap<>(2);
                doorComponentMap.put("doorS", new DoorComponent("south"));
                doorComponentMap.put("doorW", new DoorComponent("west"));
            } else if (y == 3) {
                doorComponentMap = new HashMap<>(2);
                doorComponentMap.put("doorN", new DoorComponent("north"));
                doorComponentMap.put("doorW", new DoorComponent("west"));
            } else {
                doorComponentMap = new HashMap<>(3);
                doorComponentMap.put("doorN", new DoorComponent("north"));
                doorComponentMap.put("doorS", new DoorComponent("south"));
                doorComponentMap.put("doorW", new DoorComponent("west"));
            }

        } else {
            doorComponentMap = new HashMap<>(4);
            doorComponentMap.put("doorN", new DoorComponent("north"));
            doorComponentMap.put("doorS", new DoorComponent("south"));
            doorComponentMap.put("doorE", new DoorComponent("east"));
            doorComponentMap.put("doorW", new DoorComponent("west"));
        }
    }

    public HashMap<String, DoorComponent> getDoorComponentMap() {
        return doorComponentMap;
    }
}
