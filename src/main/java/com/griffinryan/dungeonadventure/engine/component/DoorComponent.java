package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class DoorComponent extends Component {

	private Texture boundTexture;
	private HitBox hitbox;
	public String mapKey;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent(SpawnData data) {

		/* Determine spawn point for Component and mapKey.	 */
		mapKey = findMapKey();

		double x = findDoorXLocation();
		double y = findDoorYlocation();
		Point2D tempPoint = new Point2D(x, y);
		double[] boundsArray = getHitBoxBoundaryArray(tempPoint);

		hitbox = new HitBox(mapKey,
				tempPoint, BoundingShape.box(boundsArray[0], boundsArray[1]));

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
		Point2D anchor = hitbox.getCenterWorld();

		entity.getTransformComponent().setAnchoredPosition(anchor);

		entity.getViewComponent().addChild(boundTexture);
		boundTexture.darker();
	}

	/**
	 * Utility method to calculate Door collision
	 * boundary on the direction it is facing.
	 *
	 * Use the returned array for BoundingBox
	 * parameters!
	 *
	 * @param thePoint The HitBox's center point.
	 *
	 * @return (x,y) bounds of the HitBox
	 * */
	private double[] getHitBoxBoundaryArray(Point2D thePoint){
		double[] result = {0.0, 0.0};

		if(mapKey.equalsIgnoreCase("doorN")) {
			result[0] = getAppWidth() / 12.0;
			result[1] = getAppHeight() / 6.0;

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

	private double findDoorXLocation() {

		return switch (this.mapKey) {
			case "doorN" -> 5 * getAppWidth() / 2;
			case "doorS" -> 5 * getAppWidth() / 12.0;
			case "doorE" -> 11 * getAppWidth() / 12.0;
			case "doorW" -> 0;
			default -> 0;
		};
	}

	private double findDoorYlocation() {

		return switch (this.mapKey) {
			case "doorN" -> 0;
			case "doorS" -> 11 * getAppWidth() / 12.0;
			case "doorE" -> 5 * getAppHeight() / 6.0;
			case "doorW" -> 5 * getAppHeight() / 6.0;
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
}
