package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Hero;

public final class DevelopmentTool {
    private static boolean invincible = false;

    public static void execute(String cmd, Dungeon theDungeon, Hero theHero) {
        switch (cmd) {
            case "!invincible" -> invincible = !invincible;
            case "!pick_up_all_pillars" -> theDungeon.pickUpAllPillars();
            case "!give_healing_position" -> theHero.obtainHealingPotions(1);
            case "!give_vision_position" -> theHero.obtainVisionPotions(1);
        }
    }

    public static boolean isInvincible() {
        return invincible;
    }
}
