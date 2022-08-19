package com.griffinryan.dungeonadventure.menu;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public enum HeroType {
    THIEF("THIEF"),
    WARRIOR("WARRIOR"),
    PRIEST("PRIEST");

    private final String text;

    HeroType(String text) {
        this.text = text;
    }

    public static HeroType ofValue(String theType) {
        switch (theType.toLowerCase()) {
            case "thief" -> {
                return THIEF;
            }
            case "priestess" -> {
                return PRIEST;
            }
            case "warrior" -> {
                return WARRIOR;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }

    public List<Image> getSprites() {
        List<Image> sprites = new ArrayList<>();
        switch (text) {
            case "THIEF":
                for (int i = 0; i < 4; i++) {
                    sprites.add(FXGL.image("spritesheet/dungeon/menu/elf_m_idle_anim_f" + i + ".png", 128, 224));
                }
                break;
            case "WARRIOR":
                for (int i = 0; i < 4; i++) {
                    sprites.add(FXGL.image("spritesheet/dungeon/menu/knight_m_idle_anim_f" + i + ".png", 128, 224));
                }
                break;
            case "PRIEST":
                for (int i = 0; i < 4; i++) {
                    sprites.add(FXGL.image("spritesheet/dungeon/menu/wizzard_f_idle_anim_f" + i + ".png", 128, 224));
                }
                break;
        }

        return sprites;
    }


}
