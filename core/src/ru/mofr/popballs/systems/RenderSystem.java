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
    private final ShapeRenderer shapeRenderer;
    protected ComponentMapper<TextureComponent> mTextureComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;

    public RenderSystem() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(0.85f, 0.85f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0,0,0,0);
        shapeRenderer.line(0, 0, 100, 100);
        shapeRenderer.end();

        batch.begin();
        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        for (int i = 0, s = actives.size(); s > i; i++) {
            int id = ids[i];
            Texture texture = mTextureComponent.get(id).texture;
            int positionX = Camera.projectX(mPositionComponent.get(id).x);
            int positionY = Camera.projectY(mPositionComponent.get(id).y);
            positionX -= texture.getWidth() / 2;
            positionY -= texture.getHeight() / 2;
            batch.draw(texture, positionX, positionY);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
