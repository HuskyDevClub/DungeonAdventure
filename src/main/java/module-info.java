module com.griffinryan.dungeonadventure {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.griffinryan.dungeonadventure to javafx.fxml;
	exports com.griffinryan.dungeonadventure;
}
