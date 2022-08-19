package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class DevelopmentTool {
    static boolean canLeaveWithoutKillingAllMonsters = false;
    private static boolean invincible = false;

    /**
     * execute() is the driver method for the
     * DevelopmentTool class.
     *
     * @param cmd        Commands passed as String.
     * @param theDungeon Object for Dungeon.
     */
    public static void execute(final String cmd, final Dungeon theDungeon) {
        switch (cmd) {
            // make the hero invincible or not
            case "!invincible" -> invincible = !invincible;
            // all the pillars will be picked up by the hero immediately
            case "!pick_up_all_pillars" -> theDungeon.pickUpAllPillars();
            // give current player 1 healing position
            case "!give_healing_position" -> theDungeon.getHero().obtainHealingPotions(1);
            // give current player 1 vision position
            case "!give_vision_position" -> theDungeon.getHero().obtainVisionPotions(1);
            // show the entire dungeon
            case "!show_dungeon" -> System.out.println(theDungeon.toString());
            // whether the player can leave without killing all monsters
            case "!leave_without_killing_all_monsters" ->
                canLeaveWithoutKillingAllMonsters = !canLeaveWithoutKillingAllMonsters;
            case "!i_cannot_take_it_anymore" -> {
                theDungeon.getHero().suicide();
                System.out.println("Your hero decides that he cannot take it anymore and choose to kill himself.");
            }
            case "!ez" -> {
                theDungeon.moveHeroTo(theDungeon.getExitX(), theDungeon.getExitY());
                theDungeon.pickUpAllPillars();
            }
        }
    }

    /**
     * Returns whether the player is invincible or not
     *
     * @return true if not invincible or vice versa
     */
    public static boolean isNotInvincible() {
        return !invincible;
    }
}
