package com.griffinryan.dungeonadventure.engine;

import javafx.geometry.Point2D;

/**
 * Defines global constants that can be tweaked for fine-tuning gameplay.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 */
public final class Config {

    /* Game engine values. */
    public static final boolean IS_NO_BACKGROUND = false;
    // public static final boolean IS_MENU = true;
    public static final boolean IS_NO_ENEMIES = false;
    public static final boolean IS_NO_POTIONS = false;
    public static final boolean IS_NO_DOORS = false;

    // Quick enabling/disabling of game sound for testing.
    public static final boolean IS_SOUND_ENABLED = true;

    public static final int VIEW_RESOLUTION_X = 1920; // Sets the screen resolution boundary.
    public static final int VIEW_RESOLUTION_Y = 1080; // Sets the screen resolution boundary.

    /*	Game logic values. */
    public static final int PLAYER_MAX_SPEED = 500;
    public static final int PLAYER_MIN_SPEED = 200;

    public static final int ENEMY_MAX_SPEED = 200;
    public static final int ENEMY_MIN_SPEED = 100;

    // public static final Duration INTERVAL = seconds(1.5);
    public static final int OUTSIDE_DISTANCE = 50;
    public static final int SPAWN_DISTANCE = 500;

    public static final int BG_DISTANCE = 10;
    public static final Point2D BG_POINT = new Point2D(-3000, -3000);

    public static final String SAVE_NAME = "adventure_save.db";
}
