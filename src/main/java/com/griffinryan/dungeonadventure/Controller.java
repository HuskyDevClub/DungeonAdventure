package com.griffinryan.dungeonadventure;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

public class Controller {

	private BorderPane root;
	private Scene scene;
	private Canvas canvas;
	private GraphicsContext context;

	public Controller(){
		this.root = new BorderPane();
		this.scene = new Scene(root);
		this.canvas = new Canvas(600,600);
		this.context = canvas.getGraphicsContext2D();

		root.setCenter(canvas);
	}

	public BorderPane getRoot() {
		return root;
	}

	public Scene getScene() {
		return scene;
	}
}
