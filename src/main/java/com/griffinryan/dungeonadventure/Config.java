package com.griffinryan.dungeonadventure;

import javafx.util.Duration;

import static javafx.util.Duration.*;

/**
 * Defines global constants that can be tweaked for fine-tuning gameplay.
 *
 */
public final class Config {

	public static final boolean IS_RELEASE = false;

	public static final boolean IS_NO_ENEMIES = true;

	public static final boolean IS_MENU = true;

	public static final boolean IS_BACKGROUND = true;

	public static final boolean IS_SOUND_ENABLED = true;

	public static final int DISTANCE = 200;

	/* SPAWN INTERVALS */

	public static final Duration INTERVAL = seconds(1.5);
	public static final Duration INTERVAL_2 = seconds(2.5);

	public static final int PLAYER_SPEED = 320;

	public static final String SAVE_FILE_NAME = "save.dat";
}
