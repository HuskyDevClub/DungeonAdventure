package com.griffinryan.dungeonadventure.model.dungeon;

final class Pit extends AbstractRoom {
    Pit() {
        super(null, 0, 0);
    }

    @Override
    public char getFlag() {
        return 'X';
    }
}