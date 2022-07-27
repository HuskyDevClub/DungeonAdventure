package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
final class Pit extends AbstractRoom {
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
