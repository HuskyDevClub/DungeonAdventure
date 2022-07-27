package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;

public class PotionComponent extends Component {

	private AnimatedTexture texture;
	private Texture boundTexture;
	private AnimationChannel idleChannel;
	private int hpAmount; // 100, 300, 500?

	public PotionComponent(AnimationChannel idle, Texture bound){
		this.idleChannel = idle;
		this.boundTexture = bound;

		this.texture = new AnimatedTexture(idleChannel);
	}

	@Override
	public void onAdded(){
		entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(idleChannel);
	}
}