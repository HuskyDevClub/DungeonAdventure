package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * The DoorComponent object is a child class
 * of AdventureComponent.
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class DoorComponent extends Component {

	private Texture boundTexture;
	private HitBox hitbox;
	public String mapKey;
	private double anchorX;
	private double anchorY;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent() {

		/* Determine spawn point for Component and mapKey.	 */
		mapKey = findMapKey();

		/* set the corner of the component. */
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

	/*
	public DoorComponent(SpawnData data) {


		mapKey = findMapKey();

		double x = setDoorXLocation();
		double y = setDoorYlocation();
		Point2D tempPoint = new Point2D(x, y);
		double[] boundsArray = getHitBoxBoundaryArray(tempPoint);

		hitbox = new HitBox(mapKey,
				tempPoint, BoundingShape.box(boundsArray[0], boundsArray[1]));


		boundTexture = FXGL.texture("brick.png", hitbox.getWidth(), hitbox.getHeight());
		getWorldProperties().setValue(mapKey, false);
	}*/

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
	 * Utility method to calculate Door collision
	 * boundary on based on spawn point.
	 *
	 * The size of the box is based on the spawn location!
	 *
	 * @param thePoint The HitBox's center point.
	 *
	 * @return [width, length] bounds to be passed to HitBox.
	 * */
	private double[] getHitBoxBoundaryArray(Point2D thePoint){
		double[] result = {0.0, 0.0};

		if(mapKey.equalsIgnoreCase("doorN")) {
			result[0] = getAppWidth() / 8.0;
			result[1] = getAppHeight() / 24.0; // TODO: Check all this

			return result;
		} else if (mapKey.equalsIgnoreCase("doorS")) {
			result[0] = getAppWidth() / 12.0;
			result[1] = getAppHeight() / 6.0;

			return result;
		} else if (mapKey.equalsIgnoreCase("doorE")) {
			result[0] = getAppWidth() / 6.0;
			result[1] = getAppHeight() / 12.0;

			return result;
		} else if (mapKey.equalsIgnoreCase("doorW")) {
			result[0] = getAppWidth() / 6.0;
			result[1] = getAppHeight() / 12.0;

			return result;
		}

		return result;
	}

	/**
	 * Calculates the Component's bottom corner X coordinate
	 * to be stored in a field double called anchor_x.
	 * The fields are used in a (x, y) coordinate in the constructor
	 * method for the Component to set the HitBox Object.
	 * N: x = 600 - texture_size at 720p.
	 * 		getAppWidth() / 2.0 - getAppWidth() / 32.0
	 * S: x = 600 - texture_size at 720p.
	 * 		getAppWidth() / 2.0 - getAppWidth() / 32.0
	 * E: x = 1200 + texture_size at 720p. (x = 1220)
	 * 		15.0 * getAppWidth() / 16.0 + getAppWidth() / 64.0
	 * W: x = 0 at 720p.
	 * TODO:- Create a AdventureComp interface
	 * 		- that calculates the 16:9 math to
	 * 		- store in the propertyMap during load phase.
	 * @see HitBox
	 * */
	private double setAnchorXLocation() {
		/*		*/

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
	 * 		11 * getAppHeight() / 12.0 - getAppHeight() / 54.0
	 * E: y = 360 - texture_size at 720p.
	 * 		getAppHeight() / 2.0 - getAppHeight() / 12.0
	 * W: y = 360 - texture_size at 720p.
	 * 		getAppHeight() / 2.0 - getAppHeight() / 12.0
	 * TODO: Create a AdventureComp interface
	 * @see HitBox
	 * */
	private double setAnchorYLocation() {

		return switch (this.mapKey) {
			case "doorN" -> 0.0;
			case "doorS" -> 647.0;
			case "doorE" -> 330.0;
			case "doorW" -> 330.0;
			default -> 0;
		};
	}

	private String findMapKey() {
		String result = "";

		for (int i = 0; i < getWorldProperties().toMap().size(); i++) {

			if(getWorldProperties().getBoolean("doorN")){
				return "doorN";
			} else if(getWorldProperties().getBoolean("doorS")){
				return "doorS";
			} else if(getWorldProperties().getBoolean("doorE")){
				return "doorE";
			} else if(getWorldProperties().getBoolean("doorW")){
				return "doorW";
			}

		}


		return result;
	}

	public HitBox getHitBox() {
		return hitbox;
	}

	public double getAnchorX() {
		return anchorX;
	}

	public double getAnchorY() {
		return anchorY;
	}
}
