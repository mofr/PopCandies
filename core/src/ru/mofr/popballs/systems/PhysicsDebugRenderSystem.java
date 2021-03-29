package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import ru.mofr.popballs.components.PhysicsBodyComponent;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.utils.Camera;

@All({PhysicsBodyComponent.class, PositionComponent.class})
public class PhysicsDebugRenderSystem extends BaseEntitySystem {
    private final ShapeRenderer shapeRenderer;
    protected ComponentMapper<PhysicsBodyComponent> mPhysicsBodyComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;
    private final Matrix4 transform = new Matrix4();
    private final Quaternion rotation = new Quaternion();
    private final Vector3 position = new Vector3();

    public PhysicsDebugRenderSystem() {
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glLineWidth(2);
        shapeRenderer.setColor(1,1,1,0);

        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        for (int i = 0, s = actives.size(); s > i; i++) {
            int id = ids[i];
            PhysicsBodyComponent physicsBodyComponent = mPhysicsBodyComponent.get(id);
            PositionComponent positionComponent = mPositionComponent.get(id);
            float positionX = (float)Camera.projectX(positionComponent.x);
            float positionY = (float)Camera.projectY(positionComponent.y);
            for (PhysicsBodyComponent.Fixture fixture : physicsBodyComponent.fixtures) {
                renderFixture(positionX, positionY, fixture);
            }
        }
    }

    private void renderFixture(float positionX, float positionY, PhysicsBodyComponent.Fixture fixture) {
        position.set(positionX, positionY, 0);
        transform.set(position, rotation);
        shapeRenderer.setTransformMatrix(transform);

        if (fixture.polygon != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (int i = 0; i < fixture.polygon.vertices.length - 1; i++) {
                shapeRenderer.line(fixture.polygon.vertices[i], fixture.polygon.vertices[i + 1]);
            }
            shapeRenderer.line(fixture.polygon.vertices[fixture.polygon.vertices.length - 1], fixture.polygon.vertices[0]);
            shapeRenderer.end();
        }
        else if (fixture.circle != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(0, 0, fixture.circle.radius, 32);
            shapeRenderer.end();
        }
    }
}
