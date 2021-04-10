package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mofr.popballs.components.*;

@All({PopComponent.class, PositionComponent.class})
public class PopSystem extends IteratingSystem {
    protected ComponentMapper<PositionComponent> mPositionComponent;

    @Override
    protected void process(int entityId) {
        PositionComponent positionComponent = mPositionComponent.get(entityId);
        double x = positionComponent.x;
        double y = positionComponent.y;
        world.delete(entityId);
        generateExplosion(x, y);
    }

    private void generateExplosion(double x, double y) {
        int entityId = world.create();
        PositionComponent positionComponent = world.edit(entityId).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        SpriteComponent spriteComponent = world.edit(entityId).create(SpriteComponent.class);
        spriteComponent.originX = 0.5f;
        spriteComponent.originY = 0.5f;
        AnimationComponent animationComponent = world.edit(entityId).create(AnimationComponent.class);
        animationComponent.animation = loadAnimation();
    }

    private Animation<TextureRegion> loadAnimation() {
        Texture texture = new Texture("explode1.png");
        int FRAME_COLS = 4;
        int FRAME_ROWS = 1;
        TextureRegion[][] tmp = TextureRegion.split(
                texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(0.025f, frames);
    }
}
