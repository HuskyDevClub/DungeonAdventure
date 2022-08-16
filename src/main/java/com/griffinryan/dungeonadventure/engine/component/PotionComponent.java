package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class PotionComponent extends AbstractComponent {

    private final AnimatedTexture texture;
    private final Texture boundTexture;
    private final AnimationChannel idleChannel;
    private final int hpAmount = 100; // 100, 300, 500?

    /**
     * PotionComponent() is a constructor that takes different
     * AnimationChannel parameters to create an animated Entity.
     *
     * @see Component
     */
    public PotionComponent() {

        var bound = FXGL.texture("potion/potion.png");

        this.idleChannel = new AnimationChannel(FXGL.image("potion/lifepotion.png"),
            4, 17, 16, Duration.seconds(0.6), 0, 3);
        this.boundTexture = bound;

        this.texture = new AnimatedTexture(idleChannel);
    }

    /**
     * onAdded() sets properties upon instantiation of
     * the Component object.
     *
     * @see Component
     */
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(idleChannel);
        FXGL.getWorldProperties().setValue("potionHP", hpAmount);
    }

    /**
     * Retrieves the texture used for
     * boundary collision calculations.
     *
     * @see Texture
     */
    public Texture getBoundTexture() {
        return boundTexture;
    }
}
