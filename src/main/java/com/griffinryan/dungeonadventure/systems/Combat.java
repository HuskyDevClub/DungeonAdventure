package com.griffinryan.dungeonadventure.systems;

import com.griffinryan.dungeonadventure.basic.DungeonCharacter;
import com.griffinryan.dungeonadventure.dungeon.Direction;
import com.griffinryan.dungeonadventure.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.heroes.Hero;
import com.griffinryan.dungeonadventure.heroes.Warrior;
import com.griffinryan.dungeonadventure.monsters.Monster;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Combat {

    private static final ArrayList<String> messageHistory = new ArrayList<>();

    public static void start() {
        Hero theHero = new Warrior("myHero");
        Dungeon theDungeon = new Dungeon(10, 10);
        final Scanner theScanner = new Scanner(System.in);

        boolean gameMainLoop = true;

        while (gameMainLoop) {
            System.out.println("PLease enter action:");
            boolean result = false;
            switch (theScanner.next()) {
                case "up" -> result = theDungeon.move(Direction.UP);
                case "down" -> result = theDungeon.move(Direction.DOWN);
                case "left" -> result = theDungeon.move(Direction.LEFT);
                case "right" -> result = theDungeon.move(Direction.RIGHT);
                case "php" -> {
                    if (theDungeon.getCurrentRoom().getNumberOfHealingPotions() > 0) {
                        log(String.format("%s picks up %d healing potions", theHero.getMyName(), theDungeon.getCurrentRoom().getNumberOfHealingPotions()));
                        theHero.gainHealingPotions(theDungeon.getCurrentRoom().pickUpHealingPotions());
                    } else {
                        log("There is no healing potion to pick up.");
                    }
                }
                case "uhp" -> {
                    if (theHero.useHealingPotion()) {
                        log(String.format("%s used one healing potion and feel much better.", theHero.getMyName()));
                    } else {
                        log(String.format("%s does not have any healing potion.", theHero.getMyName()));
                    }
                }
                case "pvp" -> {
                    if (theDungeon.getCurrentRoom().getNumberOfVisionPotions() > 0) {
                        log(String.format("%s picks up %d vision potions", theHero.getMyName(), theDungeon.getCurrentRoom().getNumberOfVisionPotions()));
                        theHero.gainHealingPotions(theDungeon.getCurrentRoom().pickUpVisionPotions());
                    } else {
                        log("There is no healing vision to pick up.");
                    }
                }
                case "fight" -> {
                    if (theDungeon.getCurrentRoom().getNumberOfMonsters() > 0) {
                        var theTarget = theDungeon.getCurrentRoom().removeMonster(0);
                        while (true) {
                            if (theHero.getMyHealth() <= 0) {
                                log("Mission fail, your hero is killed by the monster.");
                                gameMainLoop = false;
                                break;
                            } else if (theTarget.getMyHealth() <= 0) {
                                log("You successfully kill the monster.");
                                break;
                            } else {
                                fight(theHero, theTarget);
                            }
                        }
                    } else {
                        log("There is no monsters in current room");
                    }
                }
                case "quit" -> gameMainLoop = false;
            }
            if (!result) {
                log("fail to move");
            } else {
                log("Moved!");
                if (theDungeon.getCurrentRoom().hasPit()) {
                    final Random theRandom = new Random();
                    final int theDamage = theRandom.nextInt(1, 20);
                    log(String.format("But since there is a pit in the room, you lost %d hit points", theDamage));
                    theHero.injury(theDamage);
                }
            }

            log("Current room:");
            log(String.format("X: %d, Y: %d", theDungeon.getCurrentX(), theDungeon.getCurrentY()));
            log(theDungeon.getCurrentRoom().toString());
            log(theHero.toString());

            System.out.println(theDungeon);
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
