package com.griffinryan.dungeonadventure.model.dungeon;

final class Entrance extends AbstractRoom {
    Entrance() {
        super(null, 0, 0);
    }

    @Override
    public char getFlag() {
        return 'i';
    }
}
