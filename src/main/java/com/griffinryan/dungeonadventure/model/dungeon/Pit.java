package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
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
