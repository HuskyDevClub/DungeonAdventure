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

	// private AnimatedTexture texture;
	// private AnimationChannel idleChannel;
	private Texture boundTexture;
	private SpawnData spawnPoint;

	/**
	 * DoorComponent is a constructor that creates
	 * an Entity object for Door.
	 * @see Component
	 * */
	public DoorComponent(SpawnData data) {
		data.getX();
		double x = data.getX();
		double y = data.getY();

		this.spawnPoint = data;
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

		Point2D anchor = new Point2D(spawnPoint.getX(), spawnPoint.getY());
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
	public double[] getDoorBoundaryBox(Point2D thePoint){
		double[] result = {5.0, 5.0};

		double x = spawnPoint.getX();
		double y = spawnPoint.getY();

		result[0] = x;
		result[1] = y;
		return result;
	}
}
