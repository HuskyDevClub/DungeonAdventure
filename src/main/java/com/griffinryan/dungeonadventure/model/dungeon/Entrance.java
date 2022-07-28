package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * Entrance is a class abstracted by
 * AbstractRoom that constructs an
 * Entrance object used for the doors.
 *
 * @see AbstractRoom
 * @author Yudong Lin (ydlin@uw.edu)
 */
final class Entrance extends AbstractRoom {

    /**
	 *
	 */
    Entrance() {
        super(null, 0, 0);
    }

    /**
     * @return char
     */
    @Override
    public char getFlag() {
        return 'i';
    }
}
