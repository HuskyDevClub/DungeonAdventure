package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Hero;

public final class DevelopmentTool {
    private static boolean invincible = false;

    public static void execute(final String cmd, final Dungeon theDungeon, final Hero theHero) {
        switch (cmd) {
            // make the hero invincible or not
            case "!invincible" -> invincible = !invincible;
            // all the pillars will be picked up by the hero immediately
            case "!pick_up_all_pillars" -> theDungeon.pickUpAllPillars();
            // give current player 1 healing position
            case "!give_healing_position" -> theHero.obtainHealingPotions(1);
            // give current player 1 vision position
            case "!give_vision_position" -> theHero.obtainVisionPotions(1);
            // show the entire dungeon
            case "!show_dungeon" -> System.out.println(theDungeon.toString());
        }
    }

    public static boolean isInvincible() {
        return invincible;
    }
}
