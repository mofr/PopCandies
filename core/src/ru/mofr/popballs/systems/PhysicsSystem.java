package ru.mofr.popballs.systems;

import com.artemis.BaseSystem;
import org.dyn4j.dynamics.Body;
import org.dyn4j.world.World;

public class PhysicsSystem extends BaseSystem {
    final private World<Body> physicsWorld;

    public PhysicsSystem() {
        this.physicsWorld = new World<>();
    }

    @Override
    protected void processSystem() {
        physicsWorld.update(this.world.getDelta());
    }
}
