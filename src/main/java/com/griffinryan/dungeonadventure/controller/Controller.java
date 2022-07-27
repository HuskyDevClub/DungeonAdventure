package com.griffinryan.dungeonadventure.controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class Controller {

    private final BorderPane root;
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext context;

    /**
     * 
     */
    public Controller() {
        this.root = new BorderPane();
        this.scene = new Scene(root);
        this.canvas = new Canvas(600, 600);
        this.context = canvas.getGraphicsContext2D();

        root.setCenter(canvas);
    }

    
    /** 
     * @return BorderPane
     */
    public BorderPane getRoot() {
        return root;
    }

    
    /** 
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }
}
