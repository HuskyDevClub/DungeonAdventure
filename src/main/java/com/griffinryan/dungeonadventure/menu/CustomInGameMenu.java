package com.griffinryan.dungeonadventure.menu;

import com.almasb.fxgl.app.scene.FXGLDefaultMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.core.util.InputPredicates;
import com.almasb.fxgl.dsl.FXGL;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getDialogService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.localize;


public class CustomInGameMenu extends FXGLDefaultMenu {
    public CustomInGameMenu() {
        super(MenuType.GAME_MENU);

        // add save button
        MenuItem theSaveOption = new MenuItem("Save to Sqlite Database", () -> {
            Consumer<String> saveFileNameConsumer = theSaveName -> {
                if (!theSaveName.isEmpty()) {
                    //save the current progress (the myDungeon object to be specific) into the database
                    try {
                        System.out.printf(
                            "Progress has been saved with id = '%s'!\n",
                            DungeonSqliteInterface.save(theSaveName, FXGL.getWorldProperties().getObject("dungeonComponent_dungeon"))
                        );
                    } catch (SQLException | IOException e) {
                        System.out.println("Fail to creat save");
                        e.printStackTrace();
                    }
                }
            };
            getDialogService().showInputBoxWithCancel(localize("menu.enterSaveName"), InputPredicates.ALPHANUM, saveFileNameConsumer);
        });

        theSaveOption.setTranslateX(100);
        theSaveOption.setTranslateY(100);
        super.getContentRoot().getChildren().add(theSaveOption);
    }
}
