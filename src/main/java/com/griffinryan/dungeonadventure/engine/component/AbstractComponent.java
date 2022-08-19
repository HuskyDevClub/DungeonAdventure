package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * AbstractComponent is the parent object
 * extended by AdventureComponent objects.
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @see DoorComponent
 */
public abstract class AbstractComponent extends Component {

    public final String mapKey;
    private final Texture boundTexture;
    private final HitBox hitbox;
    private final double anchorX;
    private final double anchorY;

    /**
     * DoorComponent is a constructor that creates
     * an Entity object for Door.
     *
     * @see Component
     */
    protected AbstractComponent() {

        /* Determine spawn point for Component and mapKey.	 */
        mapKey = findMapKey();

        /* Sets the corner of the component. */
        anchorX = setAnchorXLocation();
        anchorY = setAnchorYLocation();
        Point2D tempPoint = new Point2D(anchorX, anchorY);

        double[] widthLengthArray = getHitBoxBoundaryArray(tempPoint);

        hitbox = new HitBox(mapKey,
            tempPoint, BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));

        /* After this point, reference only the hitbox for locations. */

        boundTexture = FXGL.texture("brick.png", hitbox.getWidth(), hitbox.getHeight());
        getWorldProperties().setValue(mapKey, false); /* Set doorNSEW to false.	*/
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
        entity.getTransformComponent().setAnchoredPosition(anchor);

        entity.getViewComponent().addChild(boundTexture);
        boundTexture.darker();
    }

    /**
     * Sets the Component textures
     * location each frame for parallax scrolling.
     *
     * @see Component
     */
    public void onUpdate(double tpf) {

        // Entity player = getGameScene().getGameWorld().getEntitiesByType(PLAYER).get(0);

        /* topLayer.setTranslateX(-BG_DISTANCE * TOP_SPEED * player.getX());
         * topLayer.setTranslateY(-BG_DISTANCE * TOP_SPEED * player.getY()); */
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
    private double[] getHitBoxBoundaryArray(Point2D thePoint) {
        double[] result = {0.0, 0.0};

        if (mapKey.equalsIgnoreCase("north")) {
            result[0] = getAppWidth() / 4.0;
            result[1] = getAppHeight() / 12.0;

            return result;
        } else if (mapKey.equalsIgnoreCase("south")) {
            result[0] = getAppWidth() / 6.0;
            result[1] = getAppHeight() / 3.0;

            return result;
        } else if (mapKey.equalsIgnoreCase("east")) {
            result[0] = getAppWidth() / 3.0;
            result[1] = getAppHeight() / 6.0;

            return result;
        } else if (mapKey.equalsIgnoreCase("west")) {
            result[0] = getAppWidth() / 4.0;
            result[1] = getAppHeight() / 6.0;

            return result;
        }

        return result;
    }


    /**
     * Sets the Component mapKey String value
     *
     * @return Finds and returns the map's Key
     * of the Entity's String.
     */
    private String findMapKey() {
        String result = "";

        for (int i = 0; i < getWorldProperties().toMap().size(); i++) {

            if (getWorldProperties().getBoolean("north")) {
                return "north";
            } else if (getWorldProperties().getBoolean("west")) {
                return "south";
            } else if (getWorldProperties().getBoolean("west")) {
                return "east";
            } else if (getWorldProperties().getBoolean("west")) {
                return "west";
            }

        }


        return result;
    }

    /**
     * Calculates the Component's bottom corner X coordinate
     * to be stored in a field double called anchor_x.
     * The fields are used in a (x, y) coordinate in the constructor
     * method for the Component to set the HitBox Object.
     * <p>
     * Sets the corner of the component
     * based on North, South, East, West
     * coordinates and 16:9 aspect ratio math.
     * <p>
     * AbstractComponent's texture_size = 30.
     * <p>
     * N: x = 600 - texture_size at 720p.
     * S: x = 600 - texture_size at 720p.
     * E: x = 1200 + texture_size at 720p. (x = 1220)
     * W: x = 0 at 720p.
     *
     * @see HitBox
     */
    private double setAnchorXLocation() {

        return switch (this.mapKey) {
            case "north", "south" -> getAppWidth() / 2.0 - getAppWidth() / 32.0;
            case "east" -> 15.0 * getAppWidth() / 16.0 + getAppWidth() / 64.0;
            case "west" -> 0.0;
            default -> 0;
        };
    }

    /**
     * Calculates the Component's bottom corner Y coordinate
     * to be stored in a field double called anchor_y.
     * The fields are used in a (x, y) coordinate in the constructor
     * method for the Component to set the HitBox Object.
     * <p>
     * Sets the corner of the component
     * based on North, South, East, West
     * coordinates and 16:9 aspect ratio math.
     * <p>
     * AbstractComponent's texture_size = 30.
     * <p>
     * N: y = 0 at 720p.
     * S: y = 660 - texture_size at 720p.
     * E: y = 360 - texture_size at 720p.
     * W: y = 360 - texture_size at 720p. (y = 330)
     *
     * @see HitBox
     */
    private double setAnchorYLocation() {

        return switch (this.mapKey) {
            case "north" -> 0.0;
            case "south" -> 11 * getAppHeight() / 12.0 - getAppHeight() / 54.0;
            case "east", "west" -> getAppHeight() / 2.0 - getAppHeight() / 12.0;
            default -> 0;
        };
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

}
