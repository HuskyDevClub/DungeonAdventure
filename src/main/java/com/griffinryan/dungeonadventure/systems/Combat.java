package com.griffinryan.dungeonadventure.systems;

import com.griffinryan.dungeonadventure.basic.DungeonCharacter;
import com.griffinryan.dungeonadventure.heroes.Hero;
import com.griffinryan.dungeonadventure.monsters.Monster;

import java.util.ArrayList;

public class Combat {

    private static final ArrayList<String> messageHistory = new ArrayList<>();

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
