package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

import static com.griffinryan.dungeonadventure.engine.Config.*;

/**
 *
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class DoorComponent extends Component {

	/**
	 * Collection of centered door spawn
	 * coordinates on the edge of the map.
	 * doorSpawnPoints[0] = North.
	 * doorSpawnPoints[1] = East.
	 * doorSpawnPoints[2] = West.
	 * doorSpawnPoints[3] = South.
	 */
	public static final Point2D[] doorSpawnPoints = new Point2D[] {
			new Point2D(5*VIEW_RESOLUTION_X/12, 0),
			new Point2D(11*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6),
			new Point2D(VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6),
			new Point2D(5*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6)
	};

	/**
	 * Collection of centered door spawn
	 * coordinates on the edge of the map.
	 * doorSpawnPoints[0] = North.
	 * doorSpawnPoints[1] = East.
	 * doorSpawnPoints[2] = West.
	 * doorSpawnPoints[3] = South.
	 */
	public static final double[] doorBoundaryBoxes = new double[] {
			5*VIEW_RESOLUTION_X/12, 0,
			11*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6,
			VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6,
			5*VIEW_RESOLUTION_X/12, 5*VIEW_RESOLUTION_Y/6
	};

	private Texture boundTexture;
	private HitBox hitbox;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent(SpawnData data) {
		double x = data.getX();
		double y = data.getY();
		Point2D originPoint = new Point2D(x, y);
		double[] boundsArray = getDoorBoundaryArray(originPoint);

		hitbox = new HitBox("doorHitBox",
				originPoint, BoundingShape.box(boundsArray[0], boundsArray[1]));

		/* TODO -> x and y scales depending on which point
		*   -> getDoorBoundaryArray() needs work for first!	*/
		boundTexture = FXGL.texture("brick.png", x, y);
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
	 * @param thePoint The door's direction and
	 *                     PropertyMap key.
	 *
	 * @return (0,0,0,0) if theDirection does not
	 * equal "doorN" "doorE" "doorW" or "doorS".
	 * */
	public double[] getDoorBoundaryArray(Point2D thePoint){
		double[] result = {5.0, 5.0};

		double x = doorData.getX();
		double y = doorData.getY();

		result[0] = x;
		result[1] = y;
		return result;
	}

}
