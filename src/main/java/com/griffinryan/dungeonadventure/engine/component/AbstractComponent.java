package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;

/**
 * AbstractComponent is the parent object
 * extended by AdventureComponent objects.
 *
 * @see DoorComponent
 * @author Griffin Ryan (glryan@uw.edu)
 */
public abstract class AbstractComponent extends Component {

	private Texture boundTexture;
	private HitBox hitbox;
	public String mapKey;
	private double anchorX;
	private double anchorY;

	//public protected const.

}
