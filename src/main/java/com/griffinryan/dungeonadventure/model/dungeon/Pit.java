package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * Pit is a class abstracted by
 * AbstractRoom that constructs a Pit object.
 *
 * @see AbstractRoom
 * @author Yudong Lin (ydlin@uw.edu)
 */
final class Pit extends AbstractRoom {

    /**
     * @return Pit
     */
    Pit() {
        super(null, 0, 0);
    }

    /**
     * @return the char flag that represent the room type and info
     */
    @Override
    public char getFlag() {
        return 'X';
    }
}
