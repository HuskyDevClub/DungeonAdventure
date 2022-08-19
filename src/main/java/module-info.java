open module com.griffinryan.dungeonadventure {
    requires javafx.base;
    requires javafx.graphics;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires com.google.gson;
    requires org.junit.jupiter.api;

    exports com.griffinryan.dungeonadventure;
    exports com.griffinryan.dungeonadventure.engine;
    exports com.griffinryan.dungeonadventure.menu;
    /*
    opens com.griffinryan.dungeonadventure to javafx.fxml;
    exports com.griffinryan.dungeonadventure;

    opens com.griffinryan.dungeonadventure.entity to javafx.fxml;*/
}
