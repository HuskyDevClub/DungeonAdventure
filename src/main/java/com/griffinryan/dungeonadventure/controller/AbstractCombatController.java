package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public abstract class AbstractCombatController {

    // the chance that a hero will use skill during combat
    private static final int CHANCE_TO_USE_SKILL = 10;

    // contain all the messages history
    protected final ArrayList<String> messageHistory = new ArrayList<>();
    protected Dungeon myDungeon;
    protected boolean isPlaying = true;


    /**
     * move() moves the player given a direction enum.
     *
     * @param theDirection Direction in which to move.
     * @return whether you successfully moved or not
     * @see Direction
     */
    protected boolean move(final Direction theDirection) {
        // the player cannot leave this room without killing all monsters
        if (DevelopmentTool.canLeaveWithoutKillingAllMonsters || this.myDungeon.getCurrentRoom().getNumberOfMonsters() <= 0) {
            // check whether the hero can move to certain direction, move if the hero can
            if (myDungeon.move(theDirection)) {
                log("Moved!");
                // if current room is a Pit, then subtract hp from the hero
                if (myDungeon.isCurrentRoomPit()) {
                    final int theDamage = RandomSingleton.nextInt(1, 20);
                    log(String.format("But since there is a pit in the room, you lost %d hit points", theDamage));
                    myDungeon.getHero().injury(theDamage);
                }
                // if current room is the Exit, the player win
                else if (myDungeon.isCurrentRoomExit()) {
                    if (myDungeon.areAllPillarsFound()) {
                        log("Mission succeed, your find the exit and escape with all the pillars.");
                        isPlaying = false;
                    } else {
                        log("Your find the exit, but you cannot escape because you did not find all the pillars.");
                    }
                }
                return true;
            } else {
                log("Fail to move Because there is a wall!");
            }
        } else {
            log("You cannot leave without killing all monsters!");
        }
        return false;
    }

    /**
     * now is only print message to the console, wait for the functionality to print stuff to GUI
     *
     * @param theMessage The message that needs to be print to the console or screen
     */
    protected void log(final String theMessage) {
        this.messageHistory.add(theMessage);
    }

    /**
     * show all the messages
     */
    protected void showMessages() {
        for (final String theMsg : messageHistory) {
            System.out.println(theMsg);
        }
    }

    /**
     * fightMonster() is a method to handle fight calculations.
     *
     * @param index the index of the monster inside the array
     */
    protected void fightMonster(int index) {
        if (myDungeon.getCurrentRoom().getNumberOfMonsters() > index) {
            // pop the target monster out of the array and start the auto battle
            autoCombat(myDungeon.getCurrentRoom().removeMonster(index));
        } else if (myDungeon.getCurrentRoom().getNumberOfMonsters() == 0) {
            log("There is no monsters in current room");
        } else {
            throw new IllegalArgumentException("Index out of bound!");
        }
    }

    /**
     * autoCombat() is a method to handle automatic fight between hero and a monster.
     *
     * @param theMonster Monster objects for enemy.
     * @see DungeonCharacter
     */
    private void autoCombat(final Monster theMonster) {

        final int attackCost = Integer.min(myDungeon.getHero().getMaxAttackSpeed(), theMonster.getMaxAttackSpeed());

        while (true) {
            if (myDungeon.getHero().getCurrentAttackSpeed() >= theMonster.getCurrentAttackSpeed()) {
                if (RandomSingleton.isSuccessful(CHANCE_TO_USE_SKILL)) {
                    myDungeon.getHero().skill(theMonster, attackCost);
                    log(String.format("The %s %s uses the skill!", myDungeon.getHero().getClass().getSimpleName(), myDungeon.getHero().getName()));
                } else {
                    oneAttackAnother(myDungeon.getHero(), theMonster, attackCost);
                }
            } else {
                oneAttackAnother(theMonster, myDungeon.getHero(), attackCost);
            }
            if (myDungeon.getHero().getHealth() <= 0) {
                log("Mission fail, your hero is killed by the monster.");
                isPlaying = false;
                break;
            } else if (theMonster.getHealth() <= 0) {
                log("You successfully kill the monster.");
                myDungeon.getHero().resetCurrentAttackSpeed();
                break;
            } else if (myDungeon.getHero().getCurrentAttackSpeed() <= 0 || theMonster.getCurrentAttackSpeed() <= 0) {
                myDungeon.getHero().addCurrentAttackSpeed(myDungeon.getHero().getMaxAttackSpeed());
                theMonster.addCurrentAttackSpeed(theMonster.getMaxAttackSpeed());
            }
        }
    }

    /**
     * oneAttackAnother() calculates damage.
     *
     * @param theAttacker The DungeonCharacter attacking.
     * @param theTarget   The DungeonCharacter being attacked.
     * @param theCost     The cost of doing attack
     * @see DungeonCharacter
     */
    private void oneAttackAnother(final DungeonCharacter theAttacker, final DungeonCharacter theTarget, final int theCost) {
        theAttacker.attack(theTarget, theCost);
        log(
            theAttacker.getLastDamageDone() > 0 && !theTarget.isLastAttackBlocked() ? String.format(
                "The %s %s successfully attacked the %s %s and did %d damage!",
                theAttacker.getClass().getSimpleName(), theAttacker.getName(), theTarget.getClass().getSimpleName(), theTarget.getName(), theAttacker.getLastDamageDone()
            ) : String.format(
                "The %s %s failed to do any damage to the %s %s",
                theAttacker.getClass().getSimpleName(), theAttacker.getName(), theTarget.getClass().getSimpleName(), theTarget.getName()
            )
        );
    }


}
