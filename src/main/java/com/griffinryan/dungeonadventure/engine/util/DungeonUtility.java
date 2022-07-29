package com.griffinryan.dungeonadventure.engine.utils;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
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


}
