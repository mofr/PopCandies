package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import org.dyn4j.collision.Filter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.*;
import org.dyn4j.world.DetectFilter;
import org.dyn4j.world.World;
import org.dyn4j.world.result.ConvexDetectResult;
import ru.mofr.popballs.components.PhysicsBodyComponent;
import ru.mofr.popballs.components.PositionComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@All({PhysicsBodyComponent.class, PositionComponent.class})
public class PhysicsSystem extends BaseEntitySystem {
    final private static float SCALE = 200;
    final private World<Body> physicsWorld;
    final private Map<Body, Integer> bodyToEntityMap = new HashMap<>();
    final private Map<Integer, Body> entityToBodyMap = new HashMap<>();

    protected ComponentMapper<PhysicsBodyComponent> mPhysicsBodyComponent;
    protected ComponentMapper<PositionComponent> mPositionComponent;

    public PhysicsSystem() {
        this.physicsWorld = new World<>();
        this.physicsWorld.setGravity(0, -9.8);
    }

    @Override
    protected void inserted(int entityId) {
        PhysicsBodyComponent physicsBodyComponent = mPhysicsBodyComponent.get(entityId);
        PositionComponent positionComponent = mPositionComponent.get(entityId);

        Body body = new Body();
        body.translate(positionComponent.x / SCALE, positionComponent.y / SCALE);

        if (physicsBodyComponent.hasMass) {
            body.setMass(new Mass(new Vector2(0.0, 0.0), 60.0, 2.0));
            body.setMassType(MassType.NORMAL);
        }

        for (PhysicsBodyComponent.Fixture fixture : physicsBodyComponent.fixtures) {
            Convex convex = null;
            if (fixture.circle != null) {
                convex = new Circle(fixture.circle.radius / SCALE);
            }
            else if (fixture.polygon != null) {
                Vector2[] vertices = new Vector2[fixture.polygon.vertices.length];
                for (int i = 0; i < fixture.polygon.vertices.length; i++) {
                    vertices[i] = new Vector2(fixture.polygon.vertices[i].x / SCALE, fixture.polygon.vertices[i].y / SCALE);
                }
                convex = new Polygon(vertices);
            }

            if (convex != null) {
                body.addFixture(convex);
            }
        }

        this.physicsWorld.addBody(body);
        bodyToEntityMap.put(body, entityId);
        entityToBodyMap.put(entityId, body);
    }

    @Override
    protected void removed(int entityId) {
        Body body = entityToBodyMap.get(entityId);
        entityToBodyMap.remove(entityId);
        bodyToEntityMap.remove(body);
        physicsWorld.removeBody(body);
    }

    @Override
    protected void processSystem() {
        physicsWorld.update(this.world.getDelta());

        for (int i = 0; i < physicsWorld.getBodyCount(); ++i) {
            Body body = physicsWorld.getBody(i);
            int entity = bodyToEntityMap.get(body);
            PositionComponent positionComponent = mPositionComponent.get(entity);
            positionComponent.x = body.getTransform().getTranslationX() * SCALE;
            positionComponent.y = body.getTransform().getTranslationY() * SCALE;
            positionComponent.angle = body.getTransform().getRotationAngle() * 57.2958;
        }
    }

    public Integer getAtPoint(double x, double y, double detectRadius) {
        x /= SCALE;
        y /= SCALE;
        DetectFilter<Body, BodyFixture> detectFilter = new DetectFilter<>(false, false, Filter.DEFAULT_FILTER);
        Convex convex = new Circle(detectRadius / SCALE);
        Transform transform = new Transform();
        transform.translate(x, y);
        List<ConvexDetectResult<Body, BodyFixture>> results = physicsWorld.detect(convex, transform, detectFilter);
        double maxDepth = 0;
        Body closestBody = null;
        for (ConvexDetectResult<Body, BodyFixture> result : results) {
            if (result.getBody() != null && result.getPenetration().getDepth() > maxDepth) {
                closestBody = result.getBody();
                maxDepth = result.getPenetration().getDepth();
            }
        }
        return bodyToEntityMap.get(closestBody);
    }
}
