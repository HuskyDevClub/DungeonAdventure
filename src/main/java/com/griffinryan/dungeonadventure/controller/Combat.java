package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class Combat {

    private static final ArrayList<String> messageHistory = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String myDatabasePath = "jdbc:sqlite:save.sqlite";
    private static Dungeon myDungeon;
    private static boolean isPlaying;

    /**
     * main() is the executable method for the Combat class.
     * can be used to play the game in command prompt
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) throws IllegalAccessException, SQLException, IOException, ClassNotFoundException {
        System.out.println("new || load || delete:");
        // process the initialization phase according to user's choice
        switch (SCANNER.nextLine()) {
            case "load" -> {
                final HashMap<String, String[]> theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves(myDatabasePath);
                if (theNamesOfExistingSaves.size() > 0) {
                    theNamesOfExistingSaves.forEach((key, value) -> System.out.printf("%s - '%s' created at %s\n", key, value[0], value[1]));
                    while (true) {
                        // ask the user to select a save by entering the id
                        System.out.println("please enter save id:");
                        // don forget the id is string!
                        final String index = SCANNER.nextLine();
                        // ensure the id exists
                        if (theNamesOfExistingSaves.containsKey(index)) {
                            // load the progress (the myDungeon object to be specific) from the database with given id
                            myDungeon = DungeonSqliteInterface.load(myDatabasePath, index);
                            break;
                        }
                        // if id does not exist, the ask the player to try again
                        System.out.println("The id does not exist! Please try again.");
                    }
                } else {
                    System.out.println("There is no valid existing save to load from!");
                    newGame();
                }
            }
            case "delete" -> {
                HashMap<String, String[]> theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves(myDatabasePath);
                if (theNamesOfExistingSaves.size() > 0) {
                    while (true) {
                        theNamesOfExistingSaves.forEach((key, value) -> System.out.printf("%s - '%s' created at %s\n", key, value[0], value[1]));
                        // ask the user to select a save by entering the id
                        System.out.println("please enter save id:");
                        // don forget the id is string!
                        final String index = SCANNER.nextLine();
                        // ensure the id exists
                        if (theNamesOfExistingSaves.containsKey(index)) {
                            // load the progress (the myDungeon object to be specific) from the database with given id
                            DungeonSqliteInterface.delete(myDatabasePath, index);
                            theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves(myDatabasePath);
                            if (theNamesOfExistingSaves.size() == 0) {
                                newGame();
                                break;
                            }
                            System.out.println("Continue? (y/n):");
                            if (SCANNER.nextLine().equals("n")) {
                                newGame();
                                break;
                            }
                        }
                        // if id does not exist, the ask the player to try again
                        System.out.println("The id does not exist! Please try again.");
                    }
                } else {
                    System.out.println("There is no valid existing save to delete!");
                    newGame();
                }
            }
            default -> newGame();
        }
        start();
    }

    /**
     * start a new game.
     *
     * @see Dungeon
     */
    public static void newGame() throws IllegalAccessException, SQLException {
        // ask the player to choose hero by entering a number
        System.out.println("Please choose your hero:");
        System.out.println("1 - Priestess");
        System.out.println("2 - Thief");
        System.out.println("3 - Warrior");
        final int heroIndex = Integer.parseInt(SCANNER.nextLine());
        final Hero theHero;
        // ask the player to enter the name of the hero
        while (true) {
            System.out.println("Please enter a name for your hero:");
            final String theName = SCANNER.nextLine();
            // ensure that the name is not empty
            if (!theName.isEmpty()) {
                switch (heroIndex) {
                    case 1 -> theHero = HeroesFactory.spawn("Priestess", theName);
                    case 2 -> theHero = HeroesFactory.spawn("Thief", theName);
                    default -> theHero = HeroesFactory.spawn("Warrior", theName);
                }
                break;
            }
            System.out.println("The name cannot be empty, please try again!");
        }
        // generate a new dungeon
        myDungeon = new Dungeon(20, 20, theHero);
    }

    /**
     * start() begins the game state.
     *
     * @see Dungeon
     */
    public static void start() throws SQLException, IOException {
        isPlaying = true;
        while (isPlaying) {
            // print out current status
            log("Current room:");
            log(String.format("X: %d, Y: %d", myDungeon.getCurrentX(), myDungeon.getCurrentY()));
            log(myDungeon.getCurrentRoom().toString());
            log(myDungeon.getHero().toString());
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
            System.out.println("Please enter action:");
            final String theInput = SCANNER.nextLine();
            if (!theInput.startsWith("!")) {
                switch (theInput) {
                    case "up" -> move(Direction.UP);
                    case "down" -> move(Direction.DOWN);
                    case "left" -> move(Direction.LEFT);
                    case "right" -> move(Direction.RIGHT);
                    // picks up healing potions
                    case "php" -> {
                        if (myDungeon.getCurrentRoom().getNumberOfHealingPotions() > 0) {
                            log(String.format("%s picks up %d healing potions", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().getNumberOfHealingPotions()));
                            myDungeon.getHero().obtainHealingPotions(myDungeon.getCurrentRoom().pickUpHealingPotions());
                        } else {
                            log("There is no healing potion to pick up.");
                        }
                    }
                    // use healing potions
                    case "uhp" -> {
                        if (myDungeon.getHero().useHealingPotion()) {
                            log(String.format("%s used one healing potion and feel much better.", myDungeon.getHero().getName()));
                        } else {
                            log(String.format("%s does not have any healing potion.", myDungeon.getHero().getName()));
                        }
                    }
                    // picks up vision potions
                    case "pvp" -> {
                        if (myDungeon.getCurrentRoom().getNumberOfVisionPotions() > 0) {
                            log(String.format("%s picks up %d vision potions", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().getNumberOfVisionPotions()));
                            myDungeon.getHero().obtainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
                        } else {
                            log("There is no healing vision to pick up.");
                        }
                    }
                    // use vision potions
                    case "uvp" -> {
                        if (myDungeon.getHero().useVisionPotion()) {
                            log(String.format("%s used one vision potion and saw:", myDungeon.getHero().getName()));
                            log(myDungeon.getSurroundingRooms());

                        } else {
                            log(String.format("%s does not have any vision potion.", myDungeon.getHero().getName()));
                        }
                    }
                    // pick up pillar
                    case "pp" -> {
                        if (myDungeon.getCurrentRoom().hasPillar()) {
                            log(String.format("%s picks up pillar [%s]", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().pickUpPillar()));
                            myDungeon.getHero().obtainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
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
                    case "save" -> {
                        while (true) {
                            System.out.println("Enter a name for the save");
                            final String theSaveName = SCANNER.nextLine();
                            if (!theSaveName.isEmpty()) {
                                //save the current progress (the myDungeon object to be specific) into the database
                                System.out.printf("Progress has been saved with id = '%d'!\n", DungeonSqliteInterface.save(myDatabasePath, theSaveName, myDungeon));
                                break;
                            }
                            System.out.println("The name cannot be empty! Please try again.");
                        }
                    }
                    case "quit" -> isPlaying = false;
                }
            } else {
                DevelopmentTool.execute(theInput, myDungeon, myDungeon.getHero());
            }
        }
    }

    /**
     * now is only print message to the console, wait for the functionality to print stuff to GUI
     *
     * @param theMessage The message that needs to be print to the console or screen
     */
    private static void log(final String theMessage) {
        messageHistory.add(theMessage);
        System.out.println(theMessage);
    }


    /**
     * move() moves the player given a direction enum.
     *
     * @param theDirection Direction in which to move.
     * @see Direction
     */
    private static void move(final Direction theDirection) {
        // check whether the hero can move to certain direction, move if the hero can
        if (myDungeon.move(theDirection)) {
            log("Moved!");
            // if current room is a Pit, then subtract hp from the hero
            if (myDungeon.isCurrentRoomPit()) {
                final int theDamage = new Random().nextInt(1, 20);
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
        } else {
            log("Fail to move");
        }
    }


    /**
     * oneAttackAnother() calculates damage.
     *
     * @param theAttacker The DungeonCharacter attacking.
     * @param theTarget   The DungeonCharacter being attacked.
     * @see DungeonCharacter
     */
    private static void oneAttackAnother(final DungeonCharacter theAttacker, final DungeonCharacter theTarget) {
        theAttacker.attack(theTarget);
        log(
                theAttacker.getLastDamageDone() > 0 && !theTarget.isLastAttackBlocked()? String.format(
                        "The %s %s successfully attacked the %s %s and did %d damage!",
                        theAttacker.getClass().getSimpleName(), theAttacker.getName(), theTarget.getClass().getSimpleName(), theTarget.getName(), theAttacker.getLastDamageDone()
                ) : String.format(
                        "The %s %s fail to do any damage to the %s %s", theAttacker.getClass().getSimpleName(), theAttacker.getName(), theTarget.getClass().getSimpleName(), theTarget.getName()
                )
        );
    }

    /**
     * fightOne() is a method to handle fight calculations.
     *
     * @see Dungeon
     */
    public static void fightOne() {
        if (myDungeon.getCurrentRoom().getNumberOfMonsters() > 0) {
            final Monster theTarget = myDungeon.getCurrentRoom().removeMonster(0);
            while (true) {
                if (myDungeon.getHero().getHealth() <= 0) {
                    log("Mission fail, your hero is killed by the monster.");
                    isPlaying = false;
                    break;
                } else if (theTarget.getHealth() <= 0) {
                    log("You successfully kill the monster.");
                    break;
                } else {
                    fight(myDungeon.getHero(), theTarget);
                }
            }
        } else {
            log("There is no monsters in current room");
        }
    }

    /**
     * fight() is a method to handle fight calculations.
     *
     * @param theHero    Hero objects for player.
     * @param theMonster Monster objects for enemy.
     * @see DungeonCharacter
     */
    public static void fight(final Hero theHero, final Monster theMonster) {
        if (theHero.getAttackSpeed() >= theMonster.getAttackSpeed()) {
            for (int i = 0; i < theHero.getAttackSpeed() / theMonster.getAttackSpeed(); i++) {
                oneAttackAnother(theHero, theMonster);
            }
            oneAttackAnother(theMonster, theHero);
        } else {
            for (int i = 0; i < theMonster.getAttackSpeed() / theHero.getAttackSpeed(); i++) {
                oneAttackAnother(theMonster, theHero);
            }
            oneAttackAnother(theHero, theMonster);
        }
    }


}
