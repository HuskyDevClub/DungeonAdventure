package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Combat {

    private static final ArrayList<String> messageHistory = new ArrayList<>();
    private static Dungeon myDungeon;
    private static Hero myHero;
    private static boolean isPlaying;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        myHero = new Warrior("myHero");
        myDungeon = new Dungeon(10, 10);
        final Scanner theScanner = new Scanner(System.in);

        isPlaying = true;

        while (isPlaying) {

            // print out current status
            log("Current room:");
            log(String.format("X: %d, Y: %d", myDungeon.getCurrentX(), myDungeon.getCurrentY()));
            log(myDungeon.getCurrentRoom().toString());
            log(myHero.toString());
            // list out all the Pillar(s) that player found (if any)
            if (myDungeon.getNumOfPillarsFound() > 0) {
                log("Pillars Found:[");
                for (Pillar thePillar : myDungeon.getPillars()) {
                    if (thePillar.hasBeenFound()) {
                        log(thePillar.toString());
                    }
                }
                log("]");
            }
            log(myDungeon.toString());

            // ask the player to input an action
            System.out.println("PLease enter action:");

            switch (theScanner.next()) {
                case "up" -> move(Direction.UP);
                case "down" -> move(Direction.DOWN);
                case "left" -> move(Direction.LEFT);
                case "right" -> move(Direction.RIGHT);
                case "php" -> {
                    if (myDungeon.getCurrentRoom().getNumberOfHealingPotions() > 0) {
                        log(String.format("%s picks up %d healing potions", myHero.getMyName(), myDungeon.getCurrentRoom().getNumberOfHealingPotions()));
                        myHero.gainHealingPotions(myDungeon.getCurrentRoom().pickUpHealingPotions());
                    } else {
                        log("There is no healing potion to pick up.");
                    }
                }
                case "uhp" -> {
                    if (myHero.useHealingPotion()) {
                        log(String.format("%s used one healing potion and feel much better.", myHero.getMyName()));
                    } else {
                        log(String.format("%s does not have any healing potion.", myHero.getMyName()));
                    }
                }
                // picks up vision potions
                case "pvp" -> {
                    if (myDungeon.getCurrentRoom().getNumberOfVisionPotions() > 0) {
                        log(String.format("%s picks up %d vision potions", myHero.getMyName(), myDungeon.getCurrentRoom().getNumberOfVisionPotions()));
                        myHero.gainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
                    } else {
                        log("There is no healing vision to pick up.");
                    }
                }
                // pick up pillar
                case "pp" -> {
                    if (myDungeon.getCurrentRoom().hasPillar()) {
                        log(String.format("%s picks up pillar [%s]", myHero.getMyName(), myDungeon.getCurrentRoom().pickUpPillar()));
                        myHero.gainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
                    } else {
                        log("There is no pillar to pick up.");
                    }
                }
                case "fight" -> fightOne();
                case "quit" -> isPlaying = false;
            }
        }
    }

    /**
     * now is only print message to the console, wait for the functionality to print stuff to GUI
     *
     * @param theMessage the message that needs to be print to the console or screen
     */
    private static void log(String theMessage) {
        messageHistory.add(theMessage);
        System.out.println(theMessage);
    }

    private static void move(Direction theDirection) {
        // check whether the hero can move to certain direction, move if the hero can
        if (myDungeon.move(theDirection)) {
            log("Moved!");
            // if current room is a Pit, then subtract hp from the hero
            if (myDungeon.isCurrentRoomPit()) {
                final int theDamage = new Random().nextInt(1, 20);
                log(String.format("But since there is a pit in the room, you lost %d hit points", theDamage));
                myHero.injury(theDamage);
            }
            // if current room is the Exit, the player win
            else if (myDungeon.isCurrentRoomExit()) {
                if (myDungeon.areAllPillarsFound()) {
                    log("Mission succeed, your find the exit and escape.");
                    isPlaying = false;
                } else {
                    log("Your find the exit, but you cannot escape because you did not find all pillars.");
                }

            }
        } else {
            log("Fail to move");
        }
    }

    private static void oneAttackAnother(DungeonCharacter theAttacker, DungeonCharacter theTarget) {
        theAttacker.attack(theTarget);
        log(
                theAttacker.getMyLastDamageDone() > 0 ? String.format(
                        "The %s %s successfully attacked the %s %s and did %d damage!",
                        theAttacker.getClass().getSimpleName(), theAttacker.getMyName(), theTarget.getClass().getSimpleName(), theTarget.getMyName(), theAttacker.getMyLastDamageDone()
                ) : String.format(
                        "The %s %s fail to do any damage to the %s %s", theAttacker.getClass().getSimpleName(), theAttacker.getMyName(), theTarget.getClass().getSimpleName(), theTarget.getMyName()
                )
        );
    }

    public static void fightOne() {
        if (myDungeon.getCurrentRoom().getNumberOfMonsters() > 0) {
            var theTarget = myDungeon.getCurrentRoom().removeMonster(0);
            while (true) {
                if (myHero.getMyHealth() <= 0) {
                    log("Mission fail, your hero is killed by the monster.");
                    isPlaying = false;
                    break;
                } else if (theTarget.getMyHealth() <= 0) {
                    log("You successfully kill the monster.");
                    break;
                } else {
                    fight(myHero, theTarget);
                }
            }
        } else {
            log("There is no monsters in current room");
        }
    }

    public static void fight(Hero theHero, Monster theMonster) {
        if (theHero.getMyAttackSpeed() >= theMonster.getMyAttackSpeed()) {
            for (int i = 0; i < theHero.getMyAttackSpeed() / theMonster.getMyAttackSpeed(); i++) {
                oneAttackAnother(theHero, theMonster);
            }
            oneAttackAnother(theMonster, theHero);
        } else {
            for (int i = 0; i < theMonster.getMyAttackSpeed() / theHero.getMyAttackSpeed(); i++) {
                oneAttackAnother(theMonster, theHero);
            }
            oneAttackAnother(theHero, theMonster);
        }
    }
}
