package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * Pit is a class abstracted by
 * AbstractRoom that constructs a Pit object.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see AbstractRoom
 */
final class Pit extends AbstractRoom {
    Pit() {
        super(null, 0, 0);
    }

    /**
     * @return the char flag that represent the room type and info
     */
    @Override
    char getFlag() {
        return 'X';
    }
}
