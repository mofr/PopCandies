package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.components.TextureComponent;
import ru.mofr.popballs.utils.Camera;

@All({TextureComponent.class, PositionComponent.class})
public class RenderSystem extends BaseEntitySystem {
    private final SpriteBatch batch;
    protected ComponentMapper<TextureComponent> mTextureComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;
    private final Matrix4 transform = new Matrix4();

    public RenderSystem() {
        this.batch = new SpriteBatch();
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(0.85f, 0.85f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glLineWidth(2);

        batch.begin();
        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        for (int i = 0, s = actives.size(); s > i; i++) {
            int id = ids[i];
            Texture texture = mTextureComponent.get(id).texture;
            PositionComponent positionComponent = mPositionComponent.get(id);
            double positionX = Camera.projectX(positionComponent.x);
            double positionY = Camera.projectY(positionComponent.y);

            transform.idt();
            transform.translate((float)positionX, (float)positionY, 0);
            transform.rotate(Vector3.Z, (float)positionComponent.angle);
            transform.translate(-texture.getWidth() / 2.0f, -texture.getHeight() / 2.0f, 0);

            batch.setTransformMatrix(transform);
            batch.draw(texture, 0, 0);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
