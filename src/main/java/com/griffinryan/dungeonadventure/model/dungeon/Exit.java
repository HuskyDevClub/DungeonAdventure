package com.griffinryan.dungeonadventure.model.dungeon;

final class Exit extends AbstractRoom {
    Exit() {
        super(null, 0, 0);
    }

    @Override
    public char getFlag() {
        return 'O';
    }
}
