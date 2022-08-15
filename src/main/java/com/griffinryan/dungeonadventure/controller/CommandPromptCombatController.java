package com.griffinryan.dungeonadventure.controller;

import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class CommandPromptCombatController extends AbstractCombatController {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String INVALID_INPUT_MESSAGE = "Invalid input, please try again.";
    private static final HashMap<String, String> CMD_INFO = new HashMap<>() {{
        put("up", "going upward");
        put("down", "going downward");
        put("left", "going left");
        put("right", "going right");
        put("php", "picks up all healing potions in current room");
        put("uhp", "use 1 healing potion");
        put("pvp", "picks up all vision potions in current room");
        put("uvp", "use 1 vision potion");
        put("pp", "(try to) pick up the pillar located in this room");
        put("fight", "fight a monster inside the dungeon");
        put("history", "show all the messages");
        put("save", "save the current progress");
        put("quit", "quit the game immediately");
    }};

    /**
     * main() is the executable method for the Combat class.
     * can be used to play the game in command prompt
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) throws SQLException, IOException, ClassNotFoundException {
        new CommandPromptCombatController().start();
    }

    /**
     * print out all available commands and their functions
     */
    private static void showHelp() {
        for (String _cmd : CMD_INFO.keySet()) {
            System.out.printf("%s - %s\n", _cmd, CMD_INFO.get(_cmd));
        }
    }

    /**
     * ask the user from input and start a new game
     */
    public void start() throws SQLException, IOException, ClassNotFoundException {
        do {
            while (myDungeon == null) {
                System.out.println("Please choose: new || load || delete:");
                // process the initialization phase according to user's choice
                switch (SCANNER.nextLine()) {
                    case "new" -> this.newGame();
                    case "load" -> this.loadProgress();
                    case "delete" -> this.removeProgress();
                    default -> System.out.println(INVALID_INPUT_MESSAGE);
                }
            }
            this.play();
            // if myDungeon's value has been set to null, then it means quit
        } while (myDungeon != null);
    }

    /**
     * initialize for a complete new start
     *
     * @see Dungeon
     */
    private void newGame() {
        // ask the player to choose hero by entering a number
        int heroIndex;
        while (true) {
            System.out.println("Please choose your hero:");
            System.out.println("1 - Priestess");
            System.out.println("2 - Thief");
            System.out.println("3 - Warrior");
            try {
                heroIndex = Integer.parseInt(SCANNER.nextLine());
                if (1 <= heroIndex && heroIndex <= 3) {
                    break;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println(INVALID_INPUT_MESSAGE);
        }
        // ask the player to enter the name of the hero
        final Hero theHero;
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
        myDungeon = new Dungeon(theHero, 20, 20);
    }

    /**
     * load selected progress from existing saves
     */
    private void loadProgress() throws SQLException, IOException, ClassNotFoundException {
        final HashMap<String, String[]> theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves();
        if (theNamesOfExistingSaves.size() > 0) {
            theNamesOfExistingSaves.forEach((key, value) -> System.out.printf("%s - '%s' created at %s\n", key, value[0], value[1]));
            while (true) {
                // ask the user to select a save by entering the id
                System.out.println("Please enter save id (or entering '!back' for going back):");
                // don forget the id is string!
                final String index = SCANNER.nextLine();
                // if the user choose to go back
                if (index.equals("!back")) {
                    break;
                }
                // ensure the id exists
                else if (theNamesOfExistingSaves.containsKey(index)) {
                    // load the progress (the myDungeon object to be specific) from the database with given id
                    myDungeon = DungeonSqliteInterface.load(index);
                    break;
                }
                // if id does not exist, the ask the player to try again
                System.out.println(INVALID_INPUT_MESSAGE);
            }
        } else {
            System.out.println("There is no valid existing save to load from!");
        }
    }

    /**
     * remove selected progress from the existing saves
     *
     * @see Dungeon
     */
    private void removeProgress() throws SQLException {
        HashMap<String, String[]> theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves();
        if (theNamesOfExistingSaves.size() > 0) {
            while (true) {
                theNamesOfExistingSaves.forEach((key, value) -> System.out.printf("%s - '%s' created at %s\n", key, value[0], value[1]));
                // ask the user to select a save by entering the id
                System.out.println("Please enter save id (or entering '!back' for going back):");
                // don forget the id is string!
                final String index = SCANNER.nextLine();
                // if the user choose to go back
                if (index.equals("!back")) {
                    break;
                }
                // ensure the id exists
                else if (theNamesOfExistingSaves.containsKey(index)) {
                    // load the progress (the myDungeon object to be specific) from the database with given id
                    DungeonSqliteInterface.delete(index);
                    theNamesOfExistingSaves = DungeonSqliteInterface.getNamesOfExistingSaves();
                    if (theNamesOfExistingSaves.size() == 0) {
                        System.out.println("All saved has been deleted, and you will be sent back to main menu.");
                        break;
                    }
                    // ask whether the player wants to continue to delete progress(es) or going back
                    System.out.println("The progress has been deleted. Continue? (y/n):");
                    if (SCANNER.nextLine().equals("n")) {
                        break;
                    } else {
                        continue;
                    }
                }
                // if id does not exist, the ask the player to try again
                System.out.println("The id does not exist! Please try again.");
            }
        } else {
            System.out.println("There is no valid existing save to delete!");
        }
    }

    /**
     * play() begins the game state.
     *
     * @see Dungeon
     */
    private void play() throws SQLException, IOException {
        System.out.println("Your journey begins!");
        this.showCurrentStatus();
        // game main loop
        while (myDungeon != null) {
            // ask the player to input an action
            System.out.println("Please enter action (enter 'help' for more explanations):");
            final String theInput = SCANNER.nextLine();
            // process use choice
            if (!theInput.startsWith("!")) {
                switch (theInput) {
                    case "up" -> move(Direction.UP);
                    case "down" -> move(Direction.DOWN);
                    case "left" -> move(Direction.LEFT);
                    case "right" -> move(Direction.RIGHT);
                    case "php" -> picksUpAllHealingPotions();
                    case "uhp" -> useOneHealingPotions();
                    case "pvp" -> picksUpAllVisionPotions();
                    case "uvp" -> useOneVisionPotions();
                    // pick up pillar
                    case "pp" -> log(
                        myDungeon.getCurrentRoom().hasPillar()
                            ? String.format("%s picks up pillar [%s]", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().pickUpPillar())
                            : "There is no pillar to pick up."
                    );
                    case "fight" ->
                        fightMonster(RandomSingleton.nextInt(Integer.max(myDungeon.getCurrentRoom().getNumberOfMonsters() - 1, 1)));
                    case "history" -> this.showMessages();
                    case "save" -> this.saveProgress();
                    case "quit" -> myDungeon = null;
                    case "help" -> showHelp();
                    default -> System.out.println(INVALID_INPUT_MESSAGE);
                }
            } else {
                DevelopmentTool.execute(theInput, myDungeon, myDungeon.getHero());
            }
        }
    }

    /**
     * print out the current status
     */
    private void showCurrentStatus() {
        // print out the dungeon information - current room
        log("Current room:");
        this.log(getRoomOverview());
        log(String.format("X: %d, Y: %d", myDungeon.getCurrentX(), myDungeon.getCurrentY()));
        log(myDungeon.getCurrentRoom().toString());
        // print out the hero status
        log(myDungeon.getHero().toString());
        // list out all the Pillar(s) that player found (if any)
        final ArrayList<String> thePillarsFound = myDungeon.getPillarsFound();
        if (thePillarsFound.size() > 0) {
            log("Pillars Found:[");
            for (final String thePillar : thePillarsFound) {
                log(thePillar);
            }
            log("]");
        }
    }

    /**
     * get the overview of current room
     *
     * @return a string that contain the information
     */
    private String getRoomOverview() {
        final char[][] roomOverView = new char[3][3];
        for (final char[] row:roomOverView) {
            Arrays.fill(row, '*');
        }
        roomOverView[1][1] = this.myDungeon.getCurrentRoom().getFlag();
        if (this.myDungeon.canMove(Direction.UP)) {
            roomOverView[0][1] = '-';
        }
        if (this.myDungeon.canMove(Direction.DOWN)) {
            roomOverView[2][1] = '-';
        }
        if (this.myDungeon.canMove(Direction.LEFT)) {
            roomOverView[1][0] = '|';
        }
        if (this.myDungeon.canMove(Direction.RIGHT)) {
            roomOverView[1][2] = '|';
        }
        final StringBuilder theInfo = new StringBuilder();
        for (final char[] row:roomOverView) {
            theInfo.append(String.valueOf(row));
            theInfo.append('\n');
        }
        theInfo.deleteCharAt(theInfo.length()-1);
        return theInfo.toString();
    }

    /**
     * move() moves the player given a direction enum.
     *
     * @param theDirection Direction in which to move.
     * @return whether you successfully moved or not
     * @see Direction
     */
    @Override
    protected boolean move(Direction theDirection) {
        final boolean result = super.move(theDirection);
        if (result) {
            this.showCurrentStatus();
        }
        return result;
    }

    /**
     * now is only print message to the console, wait for the functionality to print stuff to GUI
     *
     * @param theMessage The message that needs to be print to the console or screen
     */
    @Override
    protected void log(String theMessage) {
        super.log(theMessage);
        System.out.println(theMessage);
    }

    /**
     * end the game
     */
    @Override
    protected void stop() {
        super.stop();
        System.out.println("Retry (y/n):");
        if (SCANNER.nextLine().equals("y")) {
            this.newGame();
        }
        System.out.println("Your journey begins!");
        this.showCurrentStatus();
    }

    /**
     * picks up all healing potions in current room
     */
    private void picksUpAllHealingPotions() {
        if (myDungeon.getCurrentRoom().getNumberOfHealingPotions() > 0) {
            log(String.format("%s picks up %d healing potions", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().getNumberOfHealingPotions()));
            myDungeon.getHero().obtainHealingPotions(myDungeon.getCurrentRoom().pickUpHealingPotions());
        } else {
            log("There is no healing potion to pick up.");
        }
    }

    /**
     * use 1 healing potion
     */
    private void useOneHealingPotions() {
        if (myDungeon.getHero().useOneHealingPotion()) {
            log(String.format("%s used one healing potion and feel much better.", myDungeon.getHero().getName()));
        } else {
            log(String.format("%s does not have any healing potion.", myDungeon.getHero().getName()));
        }
    }

    /**
     * picks up all vision potions in current room
     */
    private void picksUpAllVisionPotions() {
        if (myDungeon.getCurrentRoom().getNumberOfVisionPotions() > 0) {
            log(String.format("%s picks up %d vision potions", myDungeon.getHero().getName(), myDungeon.getCurrentRoom().getNumberOfVisionPotions()));
            myDungeon.getHero().obtainHealingPotions(myDungeon.getCurrentRoom().pickUpVisionPotions());
        } else {
            log("There is no healing vision to pick up.");
        }
    }

    /**
     * use 1 vision potion
     */
    private void useOneVisionPotions() {
        if (myDungeon.getHero().useOneVisionPotion()) {
            log(String.format("%s used one vision potion and saw:", myDungeon.getHero().getName()));
            log(myDungeon.getSurroundingRooms());
        } else {
            log(String.format("%s does not have any vision potion.", myDungeon.getHero().getName()));
        }
    }

    /**
     * save the current progress
     */
    private void saveProgress() throws SQLException, IOException {
        while (true) {
            System.out.println("Enter a name for the save");
            final String theSaveName = SCANNER.nextLine();
            if (!theSaveName.isEmpty()) {
                //save the current progress (the myDungeon object to be specific) into the database
                System.out.printf("Progress has been saved with id = '%s'!\n", DungeonSqliteInterface.save(theSaveName, myDungeon));
                break;
            }
            System.out.println("The name cannot be empty! Please try again.");
        }
    }
}
