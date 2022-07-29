package com.griffinryan.dungeonadventure.model.rooms;

/**
 * Pit is a class abstracted by
 * AbstractRoom that constructs a Pit object.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see AbstractRoom
 */
public final class Pit extends AbstractRoom {
    public Pit() {
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
