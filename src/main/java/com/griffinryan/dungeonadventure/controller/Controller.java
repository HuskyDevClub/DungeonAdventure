package com.griffinryan.dungeonadventure.controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Controller {

    private final BorderPane root;
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext context;

    /**
     * Controller() is a constructor method
     * to build the Controller object.
     *
     * @see Scene for more.
     */
    public Controller() {
        this.root = new BorderPane();
        this.scene = new Scene(root);
        this.canvas = new Canvas(600, 600);
        this.context = canvas.getGraphicsContext2D();

        root.setCenter(canvas);
    }


    /**
     * Getter method to get BorderPane used.
     *
     * @return BorderPane object that is root.
     */
    public BorderPane getRoot() {
        return root;
    }


    /**
     * Getter method to get Scene used.
     *
     * @return Scene object that is root.
     */
    public Scene getScene() {
        return scene;
    }
}
