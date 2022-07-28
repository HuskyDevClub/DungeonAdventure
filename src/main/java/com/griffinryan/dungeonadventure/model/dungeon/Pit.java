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
     * @return flag
     */
    @Override
    public char getFlag() {
        return 'X';
    }
}
