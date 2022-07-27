package com.griffinryan.dungeonadventure.engine;

import javafx.util.Duration;

import static javafx.util.Duration.*;

/**
 * Defines global constants that can be tweaked for fine-tuning gameplay.
 * 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Elijah Amian (elijah25@uw.edu)
 */
public final class Config {

	// public static final boolean IS_MENU = true;
	public static final boolean IS_NO_ENEMIES = false;
	public static final boolean IS_NO_POTIONS = false;
	public static final boolean IS_BACKGROUND = false;

	public static final boolean IS_SOUND_ENABLED = true;

	public static final int PLAYER_SPEED = 320;
	public static final int ENEMY_MIN_SPEED = 100;
	public static final int ENEMY_MAX_SPEED = 200;

	public static final int PLAYER_HP = 500;
	public static final int ENEMY_HP = 300;

	public static final Duration INTERVAL = seconds(1.5);
	public static final int OUTSIDE_DISTANCE = 1000;
	public static final int SPAWN_DISTANCE = 100;
	public static final int DISTANCE = 200;

	public static final String SAVE_NAME = "da.dat";
}
