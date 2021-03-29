package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.components.TextureComponent;
import ru.mofr.popballs.utils.Camera;

@All({TextureComponent.class, PositionComponent.class})
public class RenderSystem extends BaseEntitySystem {
    private final SpriteBatch batch;
    protected ComponentMapper<TextureComponent> mTextureComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;

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
            double positionX = Camera.projectX(mPositionComponent.get(id).x);
            double positionY = Camera.projectY(mPositionComponent.get(id).y);
            positionX -= texture.getWidth() / 2.0;
            positionY -= texture.getHeight() / 2.0;
            batch.draw(texture, (float)positionX, (float)positionY);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
