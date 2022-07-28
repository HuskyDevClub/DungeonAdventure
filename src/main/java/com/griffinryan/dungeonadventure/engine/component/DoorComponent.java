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
 */
public class DoorComponent extends Component {

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
		double[] boundsArray = getHitBoxBoundaryArray(originPoint);

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
	 * @param thePoint The HitBox's center point.
	 *
	 * @return (x,y) bounds of the HitBox
	 * */
	private double[] getHitBoxBoundaryArray(Point2D thePoint){
		double[] result = new double[2];

		double x = thePoint.getX();
		double y = thePoint.getY();

		////////////////////////////////////////////
		// REturn the size in 2D array! duh!
		/*
		public static final Point2D[] doorSpawnPoints = new Point2D[] {
				new Point2D(5 * FXGL.getAppWidth() / 12, 0), // North
				new Point2D(5 * FXGL.getAppWidth() / 12, 5 * FXGL.getAppHeight() / 12), // East
				new Point2D(FXGL.getAppWidth() / 12, 5 * FXGL.getAppHeight() / 12), // West
				new Point2D(5 * FXGL.getAppWidth() / 12, 5*FXGL.getAppWidth()/6) // South
		};	*/




		result[0] = x;
		result[1] = y;
		return result;
	}

	public HitBox getHitBox() {
		return hitbox;
	}
}
