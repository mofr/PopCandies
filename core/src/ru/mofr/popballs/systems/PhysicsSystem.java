package ru.mofr.popballs.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;
import ru.mofr.popballs.components.PhysicsBodyComponent;
import ru.mofr.popballs.components.PositionComponent;

import java.util.HashMap;
import java.util.Map;

@All({PhysicsBodyComponent.class, PositionComponent.class})
public class PhysicsSystem extends BaseEntitySystem {
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
        Circle circle = new Circle(physicsBodyComponent.radius);
        Body body = new Body();
        body.setMass(new Mass(new Vector2(0.0, 0.0), 60.0, 2.0));
        body.setMassType(MassType.NORMAL);
        body.addFixture(circle);
        body.translate(positionComponent.x, positionComponent.y);
        this.physicsWorld.addBody(body);
        bodyToEntityMap.put(body, entityId);
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
            positionComponent.x = body.getTransform().getTranslationX();
            positionComponent.y = body.getTransform().getTranslationY();
        }
    }
}
