package com.griffinryan.dungeonadventure.engine.utils;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;

/**
 * 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class DungeonUtility {

	/**
	 * 
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
	}

}
