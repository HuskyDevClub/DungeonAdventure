package com.griffinryan.dungeonadventure.controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

public class Controller {

    private final BorderPane root;
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext context;

    public Controller() {
        this.root = new BorderPane();
        this.scene = new Scene(root);
        this.canvas = new Canvas(600, 600);
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
