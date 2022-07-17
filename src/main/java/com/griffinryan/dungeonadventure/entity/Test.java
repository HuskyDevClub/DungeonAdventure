package com.griffinryan.dungeonadventure.entity;

public class Test {

    public static void main(String[] args) {

        var monster1 = new Ogre("m1");
        var w = new Warrior("w1");

        System.out.println(monster1.getMyHealth());
        System.out.println(w.getMyHealth());

        monster1.attack(w);

        System.out.println(monster1.getMyHealth());
        System.out.println(w.getMyHealth());
    }

}
