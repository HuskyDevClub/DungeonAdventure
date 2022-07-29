package com.griffinryan.dungeonadventure.model.rooms;

/**
 * Entrance is a class abstracted by
 * AbstractRoom that constructs an
 * Entrance object used for the doors.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see AbstractRoom
 */
public final class Entrance extends AbstractRoom {

    public Entrance() {
        super(null, 0, 0);
    }

    /**
     * @return the char flag that represent the room type and info
     */
    @Override
    public char getFlag() {
        return 'i';
    }
}
