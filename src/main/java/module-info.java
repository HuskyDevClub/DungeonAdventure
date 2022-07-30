open module com.griffinryan.dungeonadventure {
	requires javafx.base;
    requires javafx.controls;
	// requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
    requires com.almasb.fxgl.all;
	requires org.xerial.sqlitejdbc;
	requires com.google.gson;

	exports com.griffinryan.dungeonadventure;
	exports com.griffinryan.dungeonadventure.engine;
	exports com.griffinryan.dungeonadventure.menu;
    /*
    opens com.griffinryan.dungeonadventure to javafx.fxml;
    exports com.griffinryan.dungeonadventure;

    opens com.griffinryan.dungeonadventure.entity to javafx.fxml;*/
}
