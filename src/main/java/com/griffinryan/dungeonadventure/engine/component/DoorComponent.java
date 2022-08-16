package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;

/**
 * The DoorComponent object is a child class
 * of AdventureComponent.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class DoorComponent extends AbstractComponent {

    private final AnimatedTexture texture;
    private final Texture boundTexture;
    private final HitBox hitbox;
    private final double anchorX;
    private final double anchorY;
    private final AnimationChannel idleChannel;
    public String mapKey;

    /**
     * DoorComponent is a constructor that creates
     * an Entity object for Door.
     *
     * @see Component
     */
    public DoorComponent(String position) {

        /* Determine spawn point for Component and mapKey.	 */
        mapKey = setMapKey(position);

        /* set the corner of the component. */
        anchorX = setAnchorXLocation();
        anchorY = setAnchorYLocation();
        Point2D tempPoint = new Point2D(anchorX, anchorY);

        double[] widthLengthArray = getHitBoxBoundaryArray(tempPoint);

        if (Objects.equals(mapKey, "doorS")) {
            hitbox = new HitBox(
                new Point2D(0.0, -widthLengthArray[1]),
                BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));

        } else {
            hitbox = new HitBox(
                new Point2D(0.0, 0.0),
                BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));
        }

        /* After this point, reference only the hitbox for locations. */

        boundTexture = FXGL.texture("brick.png", hitbox.getWidth(), hitbox.getHeight());
        getWorldProperties().setValue(mapKey, false); /* Set doorNSEW to false.	*/

        this.idleChannel = new AnimationChannel(FXGL.image("potion/lifepotion.png"),
            4, 17, 16, Duration.seconds(0.6), 0, 3);
        this.texture = new AnimatedTexture(idleChannel);
    }

    /**
     * DoorComponent is a constructor that creates
     * an Entity object for Door.
     *
     * @see Component
     */
    public DoorComponent() {

        /* Determine spawn point for Component and mapKey.	 */
        mapKey = setMapKey();

        /* set the corner of the component. */
        anchorX = setAnchorXLocation();
        anchorY = setAnchorYLocation();
        Point2D tempPoint = new Point2D(anchorX, anchorY);

        double[] widthLengthArray = getHitBoxBoundaryArray(tempPoint);

        if (Objects.equals(mapKey, "doorS")) {
            hitbox = new HitBox(
                new Point2D(0.0, -widthLengthArray[1]),
                BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));

        } else {
            hitbox = new HitBox(
                new Point2D(0.0, 0.0),
                BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));
        }

        /* After this point, reference only the hitbox for locations. */

        boundTexture = FXGL.texture("brick.png", hitbox.getWidth(), hitbox.getHeight());
        getWorldProperties().setValue(mapKey, false); /* Set doorNSEW to false.	*/

        this.idleChannel = new AnimationChannel(FXGL.image("potion/lifepotion.png"),
            4, 17, 16, Duration.seconds(0.6), 0, 3);
        this.texture = new AnimatedTexture(idleChannel);
    }

    /**
     * onAdded() sets properties upon instantiation of
     * the Component object.
     *
     * @see Component
     */
    @Override
    public void onAdded() {

        /* Setting Point2D to corner of Entity. TODO: Maybe with HitBox reference? */
        Point2D anchor = new Point2D(this.getAnchorX(), this.getAnchorY());
        entity.getTransformComponent().setScaleOrigin(anchor);
        entity.getViewComponent().addChild(boundTexture);
        // entity.getViewComponent().addChild(texture);
        // texture.loopAnimationChannel(idleChannel);
    }

    /**
     * Retrieves the Component's
     * HitBox object.
     *
     * @return HitBox
     */
    public HitBox getHitBox() {
        return hitbox;
    }

    /**
     * Retrieves the Component's
     * anchorX coordinate.
     *
     * @return double
     */
    public double getAnchorX() {
        return anchorX;
    }

    /**
     * Retrieves the Component's
     * anchorY coordinate.
     *
     * @return double
     */
    public double getAnchorY() {
        return anchorY;
    }

    /**
     * Utility method to calculate Door collision
     * boundary on based on spawn point.
     * <p>
     * The size of the box is based on the spawn location!
     *
     * @param thePoint The HitBox's center point.
     * @return [width, length] bounds to be passed to HitBox.
     */
    public double[] getHitBoxBoundaryArray(Point2D thePoint) {
        return new double[]{500.0, 500.0};
    }

    /**
     * Sets the Component mapKey String value
     */
    private String setMapKey(String s) {
        String result = "";

        if (s.equalsIgnoreCase("north")) {
            return "doorN";
        } else if (s.equalsIgnoreCase("east")) {
            return "doorE";
        } else if (s.equalsIgnoreCase("south")) {
            return "doorS";
        } else if (s.equalsIgnoreCase("west")) {
            return "doorW";
        }

        return result;
    }

    /**
     * Sets the Component mapKey String value
     */
    private String setMapKey() {
        String result = "";

        for (int i = 0; i < getWorldProperties().toMap().size(); i++) {

            if (getWorldProperties().getBoolean("doorN")) {
                return "doorN";
            } else if (getWorldProperties().getBoolean("doorS")) {
                return "doorS";
            } else if (getWorldProperties().getBoolean("doorE")) {
                return "doorE";
            } else if (getWorldProperties().getBoolean("doorW")) {
                return "doorW";
            }
        }

        return result;
    }

    /**
     * Calculates the Component's bottom corner X coordinate
     * to be stored in a field double called anchor_x.
     * The fields are used in a (x, y) coordinate in the constructor
     * method for the Component to set the HitBox Object.
     * N: x = getAppWidth() / 2.0 - getAppWidth() / 32.0
     * S: x = getAppWidth() / 2.0 - getAppWidth() / 32.0
     * E: x = 15.0 * getAppWidth() / 16.0 + getAppWidth() / 64.0
     * W: x = 0 at 720p.
     *
     * @see HitBox
     */
    private double setAnchorXLocation() {

        return switch (this.mapKey) {
            case "doorN" -> 600.0;
            case "doorS" -> 600.0;
            case "doorE" -> 1220.0;
            case "doorW" -> 0.0;
            default -> 0;
        };
    }

    /**
     * Calculates the Component's bottom corner Y coordinate
     * to be stored in a field double called anchor_y.
     * The fields are used in a (x, y) coordinate in the constructor
     * method for the Component to set the HitBox Object.
     * N: y = 0 at 720p.
     * S: y = 660 - texture_size at 720p.
     * E: y = 360 - texture_size at 720p.
     * W: y = 360 - texture_size at 720p.
     *
     * @see HitBox
     */
    private double setAnchorYLocation() {

        return switch (this.mapKey) {
            case "doorN" -> 0.0;
            case "doorS" -> 660.0;
            case "doorE" -> 330.0;
            case "doorW" -> 330.0;
            default -> 0;
        };
    }
}
