package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPillar {

    @Test
    public void testPilarBasicFunctionality() {

        final Pillar thePillar = new Pillar(Pillar.Inheritance);

        assertEquals(thePillar.toString(), Pillar.Inheritance);
        assertFalse(thePillar.hasBeenFound());
        assertArrayEquals(thePillar.getPos(), new int[]{0, 0});

        assertEquals(thePillar.getFlag(), 'I');

        thePillar.setPos(199, 291);
        assertArrayEquals(thePillar.getPos(), new int[]{199, 291});

        thePillar.found();
        assertTrue(thePillar.hasBeenFound());

    }

    @Test
    public void testPilarThrows() {
        assertDoesNotThrow(() -> new Pillar(Pillar.Inheritance));
        assertThrows(IllegalArgumentException.class, () -> new Pillar(""));
    }
}
