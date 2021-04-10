package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.components.SpriteComponent;
import ru.mofr.popballs.utils.Camera;

import java.util.Arrays;
import java.util.Comparator;

@All({SpriteComponent.class, PositionComponent.class})
public class RenderSystem extends BaseEntitySystem {
    private final SpriteBatch batch;
    protected ComponentMapper<SpriteComponent> mSpriteComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;
    private final Matrix4 transform = new Matrix4();

    public RenderSystem() {
        this.batch = new SpriteBatch();
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(0.85f, 0.85f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        ids = Arrays.stream(ids, 0, actives.size()).
                boxed().
                sorted(Comparator.comparingInt(entity -> mSpriteComponent.get(entity).order)).
                mapToInt(i -> i).
                toArray();

        batch.begin();
        for (int i = 0; i < actives.size(); i++) {
            int entity = ids[i];
            SpriteComponent spriteComponent = mSpriteComponent.get(entity);
            PositionComponent positionComponent = mPositionComponent.get(entity);
            double positionX = Camera.projectX(positionComponent.x);
            double positionY = Camera.projectY(positionComponent.y);
            TextureRegion textureRegion = spriteComponent.textureRegion;
            float width = textureRegion.getRegionWidth() * spriteComponent.scaleX;
            float height = textureRegion.getRegionHeight() * spriteComponent.scaleY;

            transform.idt();
            transform.translate((float)positionX, (float)positionY, 0);
            transform.rotate(Vector3.Z, (float)positionComponent.angle);
            transform.translate(
                    -width * spriteComponent.originX,
                    -height * spriteComponent.originY,
                    0
            );

            batch.setTransformMatrix(transform);
            batch.draw(textureRegion, 0, 0, width, height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
