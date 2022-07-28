package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.griffinryan.dungeonadventure.engine.Config.SPAWN_DISTANCE;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class DoorComponent extends Component {

	private Texture boundTexture;
	private HitBox hitbox;
	public String mapKey;
	private double anchor_x;
	private double anchor_y;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent() {

		/* Determine spawn point for Component and mapKey.	 */
		mapKey = findMapKey();

		anchor_x = findDoorXLocation();
		anchor_y = findDoorYlocation();
		Point2D tempPoint = new Point2D(anchor_x, anchor_y);
		double[] widthLengthArray = getHitBoxBoundaryArray(tempPoint);

		hitbox = new HitBox(mapKey,
				tempPoint, BoundingShape.box(widthLengthArray[0], widthLengthArray[1]));

		/* After this point, reference only the hitbox for locations. */

		boundTexture = FXGL.texture("brick.png", hitbox.getWidth(), hitbox.getHeight());
		getWorldProperties().setValue(mapKey, false); /* Set doorNSEW to false.	*/
	}

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

		/* Setting Point2D to center of Entity. */
		Point2D anchor = new Point2D(this.getAnchor_x(), this.getAnchor_y());
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

	/*
	private static final Point2D[] potionSpawnPoints = new Point2D[] {
			new Point2D(get, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
			new Point2D(FXGL.getAppWidth() - SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE),
			new Point2D(SPAWN_DISTANCE, FXGL.getAppHeight() - SPAWN_DISTANCE)
	}; */


	private double findDoorXLocation() {

		return switch (this.mapKey) {
			case "doorN" -> getAppWidth() / 2.0; // x = 640 at 720p.
			case "doorS" -> getAppWidth() / 2.0; // x = 640 at 720p.
			case "doorE" -> 15.0 * getAppWidth() / 16.0; // x = 1200 at 720p.
			case "doorW" -> getAppWidth() / 16.0; // x = 80 at 720p.
			default -> 0;
		};
	}

	private double findDoorYlocation() {

		return switch (this.mapKey) {
			case "doorN" -> getAppHeight() / 12.0; // y = 60 at 720p.
			case "doorS" -> 11 * getAppHeight() / 12.0; // y = 660 at 720p.
			case "doorE" -> getAppHeight() / 2.0; // y = 360 at 720p.
			case "doorW" -> getAppHeight() / 2.0; // y = 360 at 720p.
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

	public double getAnchor_x() {
		return anchor_x;
	}

	public double getAnchor_y() {
		return anchor_y;
	}
}
