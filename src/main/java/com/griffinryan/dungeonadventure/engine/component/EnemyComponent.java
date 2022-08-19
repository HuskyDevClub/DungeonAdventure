package com.griffinryan.dungeonadventure.engine.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
import static com.almasb.fxgl.dsl.FXGL.texture;

/**
 * @author Griffin Ryan (glryan@uw.edu)
 */
public class EnemyComponent extends AbstractComponent {
    private final Entity player;
    private final LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private final Duration adjustDelay = Duration.seconds(1);
    private final AnimatedTexture texture;
    private final Texture boundTexture;
    private final AnimationChannel idleChannel;
    private final AnimationChannel walkChannel;
    private final AnimationChannel backChannel;
    private final int moveSpeed;
    private Point2D velocity = Point2D.ZERO;
    private int speed;
    private boolean y = true;
    private Entity self;

    /**
     * EnemyComponent() is a constructor that takes different
     * AnimationChannel parameters to create an animated Entity.
     *
     * @see AnimatedTexture
     * @see Component
     */
    public EnemyComponent(Entity player, int moveSpeed) {
        this.player = player;
        Texture bound = texture("spritesheet/dungeon/game/ogre_front.png", 30, 30).brighter();
        AnimationChannel idle = new AnimationChannel(FXGL.image("spritesheet/dungeon/game/ogre_idle_anim_f.png"),
            4, 32, 32, Duration.seconds(0.4), 0, 3);
        AnimationChannel walk = new AnimationChannel(FXGL.image("spritesheet/dungeon/game/ogre_run_anim_f.png"),
            4, 32, 32, Duration.seconds(0.4), 0, 3);
        AnimationChannel back = new AnimationChannel(FXGL.image("spritesheet/dungeon/game/ogre_run_anim_f.png"),
            4, 32, 32, Duration.seconds(0.4), 0, 3);

        this.idleChannel = idle;
        this.walkChannel = walk;
        this.backChannel = back;

        this.moveSpeed = moveSpeed;
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
        self = getEntity();
        self.setScaleUniform(2);
        getWorldProperties().setValue("enemyX", this.getEntity().getX());
        getWorldProperties().setValue("enemyY", this.getEntity().getY());
    }


    /**
     * onUpdate() sets properties upon updates of
     * the Component object.
     *
     * @param tpf Double value tpf.
     * @see Component
     */
    @Override
    public void onUpdate(double tpf) {
        move(tpf);
    }

    private void move(double tpf) {
        if (adjustDirectionTimer.elapsed(adjustDelay)) {
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();
        }
        self.translate(velocity);
    }

    private void adjustVelocity(double tpf) {
        Point2D directionToPlayer = player.getCenter()
            .subtract(self.getCenter())
            .normalize()
            .multiply(moveSpeed);

        velocity = velocity.add(directionToPlayer).multiply(tpf);
        if (directionToPlayer.getX() >= 0) {
            self.setScaleX(2);
        } else {
            self.setScaleX(-2);
        }
    }

    /**
     * moveRight() handles the
     * Entity object movement.
     *
     * @see Component
     */
    public void moveRight() {
        speed = 250;
        y = false;
        getEntity().setScaleX(1);
    }

    /**
     * moveLeft() handles the
     * Entity object movement.
     *
     * @see Component
     */
    public void moveLeft() {
        speed = -250;
        y = false;
        getEntity().setScaleX(-1);
    }

    /**
     * moveUp() handles the
     * Entity object movement.
     *
     * @see Component
     */
    public void moveUp() {
        speed = 250;
        y = true;
        getEntity().setScaleY(1);
    }

    /**
     * moveDown() handles the
     * Entity object movement.
     *
     * @see Component
     */
    public void moveDown() {
        speed = -250;
        y = true;
        getEntity().setScaleY(-1);
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
