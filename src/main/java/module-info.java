module com.griffinryan.dungeonadventure {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.griffinryan.dungeonadventure to javafx.fxml;
    exports com.griffinryan.dungeonadventure;
    exports com.griffinryan.dungeonadventure.basic;
    opens com.griffinryan.dungeonadventure.basic to javafx.fxml;
    exports com.griffinryan.dungeonadventure.heroes;
    opens com.griffinryan.dungeonadventure.heroes to javafx.fxml;
    exports com.griffinryan.dungeonadventure.monsters;
    opens com.griffinryan.dungeonadventure.monsters to javafx.fxml;
}
