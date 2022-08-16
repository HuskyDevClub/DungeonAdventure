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

    @Override
    public String toString() {
        return text;
    }

    public List<Image> getSprites() {
        List<Image> sprites = new ArrayList<Image>();
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
