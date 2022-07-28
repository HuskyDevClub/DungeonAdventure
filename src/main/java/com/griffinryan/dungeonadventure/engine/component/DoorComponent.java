package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
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

	// private AnimatedTexture texture;
	// private AnimationChannel idleChannel;
	private Texture boundTexture;
	private SpawnData doorData;
	private int index;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent(SpawnData data) {
		double x = data.getX();
		double y = data.getY();

		this.doorData = data;
		this.boundTexture = FXGL.texture("brick.png", x, y); // north only
	}

	/**
	 * onAdded() sets properties upon instantiation of
	 * the Component object.
	 *
	 * @see Component
	 */
	@Override
	public void onAdded() {

		Point2D anchor = new Point2D(doorData.getX(), doorData.getY());
		entity.getTransformComponent().setAnchoredPosition(anchor);

		entity.getViewComponent().addChild(boundTexture);
		boundTexture.darker();
	}

	/**
	 * @return index[] of door choice based
	 * */
	public int[] getDoorIndexes(Point2D theDoorAnchor){
		int[] result_index = new int[2];

		double x = theDoorAnchor.getX();
		double y = theDoorAnchor.getY();

		//TODO now check and return the spot in bounds.

		

		return result_index;
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
	public double[] getDoorBoundaryBox(Point2D thePoint){
		double[] result = {5.0, 5.0};

		double x = doorData.getX();
		double y = doorData.getY();

		result[0] = x;
		result[1] = y;
		return result;
	}

	public SpawnData getDoorData() {
		return doorData;
	}

	public int getIndex() {
		return index;
	}
}
