package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public final class Combat {

    private static final ArrayList<String> messageHistory = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static Dungeon myDungeon;
    private static Hero myHero;
    private static boolean isPlaying;

    public static void main(final String[] args) {
        reset();
        start();
    }

    public static void reset() {
        // ask the player to choose hero by entering a number
        System.out.println("Please choose your hero:");
        System.out.println("1 - Priestess");
        System.out.println("2 - Thief");
        System.out.println("3 - Warrior");
        final int heroIndex = SCANNER.nextInt();
        // ask the player to enter the name of the hero
        while (true) {
            System.out.println("Please enter a name for your hero:");
            final String theName = SCANNER.nextLine();
            // ensure that the name is not empty
            if (theName.length() > 0) {
                switch (heroIndex) {
                    case 1 -> myHero = new Priestess(theName);
                    case 2 -> myHero = new Thief(theName);
                    default -> myHero = new Warrior(theName);
                }
                break;
            }
            System.out.println("The name cannot be empty, please try again!");
        }
        // generate a new dungeon
        myDungeon = new Dungeon(10, 10);
    }

    public static void start() {
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
                for (final Pillar thePillar : myDungeon.getPillars()) {
                    if (thePillar.hasBeenFound()) {
                        log(thePillar.toString());
                    }
                }
                log("]");
            }
            // ask the player to input an action
            System.out.println("PLease enter action:");
            final String theInput = SCANNER.next();
            if (!theInput.startsWith("!")) {
                switch (theInput) {
                    case "up" -> move(Direction.UP);
                    case "down" -> move(Direction.DOWN);
                    case "left" -> move(Direction.LEFT);
                    case "right" -> move(Direction.RIGHT);
                    // picks up healing potions
                    case "php" -> {
                        if (myDungeon.getCurrentRoom().getNumberOfHealingPotions() > 0) {
                            log(String.format("%s picks up %d healing potions", myHero.getMyName(), myDungeon.getCurrentRoom().getNumberOfHealingPotions()));
                            myHero.obtainHealingPotions(myDungeon.getCurrentRoom().pickUpHealingPotions());
                        } else {
                            log("There is no healing potion to pick up.");
                        }
                    }
                    // use healing potions
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
                            myHero.obtainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
                        } else {
                            log("There is no healing vision to pick up.");
                        }
                    }
                    // use vision potions
                    case "uvp" -> {
                        if (myHero.useVisionPotion()) {
                            log(String.format("%s used one vision potion and saw:", myHero.getMyName()));
                            log(myDungeon.getSurroundingRooms());

                        } else {
                            log(String.format("%s does not have any vision potion.", myHero.getMyName()));
                        }
                    }
                    // pick up pillar
                    case "pp" -> {
                        if (myDungeon.getCurrentRoom().hasPillar()) {
                            log(String.format("%s picks up pillar [%s]", myHero.getMyName(), myDungeon.getCurrentRoom().pickUpPillar()));
                            myHero.obtainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
                        } else {
                            log("There is no pillar to pick up.");
                        }
                    }
                    case "fight" -> fightOne();
                    case "history" -> {
                        for (final String theMsg : messageHistory) {
                            System.out.println(theMsg);
                        }
                    }
                    case "quit" -> isPlaying = false;
                }
            } else {
                DevelopmentTool.execute(theInput, myDungeon, myHero);
            }
        }
    }

    /**
     * now is only print message to the console, wait for the functionality to print stuff to GUI
     *
     * @param theMessage the message that needs to be print to the console or screen
     */
    private static void log(final String theMessage) {
        messageHistory.add(theMessage);
        System.out.println(theMessage);
    }

    private static void move(final Direction theDirection) {
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
                    log("Mission succeed, your find the exit and escape with all the pillars.");
                    isPlaying = false;
                } else {
                    log("Your find the exit, but you cannot escape because you did not find all the pillars.");
                }

            }
        } else {
            log("Fail to move");
        }
    }

    private static void oneAttackAnother(final DungeonCharacter theAttacker, final DungeonCharacter theTarget) {
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
            final Monster theTarget = myDungeon.getCurrentRoom().removeMonster(0);
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

    public static void fight(final Hero theHero, final Monster theMonster) {
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
