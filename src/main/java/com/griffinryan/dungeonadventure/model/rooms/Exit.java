package com.griffinryan.dungeonadventure.model.rooms;

import java.util.ArrayList;

/**
 * Exit is a class abstracted by
 * AbstractRoom that constructs
 * an Exit object.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see AbstractRoom
 */
public final class Exit extends AbstractRoom {
    public Exit() {
        super(new ArrayList<>(), 0, 0);
    }

    /**
     * @return the char flag that represent the room type and info
     */
    @Override
    public char getFlag() {
        return 'O';
    }
}
