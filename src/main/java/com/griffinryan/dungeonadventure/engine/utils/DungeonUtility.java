package com.griffinryan.dungeonadventure.engine.utils;

import com.almasb.fxgl.physics.BoundingShape;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.griffinryan.dungeonadventure.engine.Config.VIEW_RESOLUTION_X;
import static com.griffinryan.dungeonadventure.engine.Config.VIEW_RESOLUTION_Y;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class DungeonUtility {

	/**
	 * addWorldPropertyListeners() is a utility method
	 * to create actionListener objects and append them
	 * to the world property map.
	 *
	 * @see com.almasb.fxgl.core.collection.PropertyMap
	 */
	public static void addWorldPropertyListeners() {
		getWorldProperties().<Integer>addListener("playerHP", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Integer>addListener("enemyHP", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("playerX", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("playerY", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("enemyX", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("enemyY", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});

		getWorldProperties().<Double>addListener("doorN", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("doorE", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("doorW", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
		getWorldProperties().<Double>addListener("doorS", (prev, now) -> {
			if(!Objects.equals(prev, now)){
				now = prev;
			}
		});
	}

	/**
	 * Collection of centered door spawn
	 * coordinates on the edge of the map.
	 * doorSpawnPoints[0] = North.
	 * doorSpawnPoints[1] = East.
	 * doorSpawnPoints[2] = West.
	 * doorSpawnPoints[3] = South.
	 */
	public static final Point2D[] doorSpawnPoints = new Point2D[] {
			new Point2D(5*VIEW_RESOLUTION_X/12, 0),
			new Point2D(11*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6),
			new Point2D(VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6),
			new Point2D(5*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6)
	};

}
