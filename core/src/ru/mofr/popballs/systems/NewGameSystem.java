package ru.mofr.popballs.systems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.mofr.popballs.components.NewGameComponent;
import ru.mofr.popballs.components.PhysicsBodyComponent;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.factories.CandyFactory;

import java.util.Random;

@All(NewGameComponent.class)
public class NewGameSystem extends IteratingSystem {
    private final Random random = new Random();

    @Override
    protected void process(int entityId) {
        for (int i = 0; i < 70; ++i) {
            float x = random.nextInt(200) - 100;
            float y = 1000 + random.nextInt(400);
            CandyFactory.create(world, x, y);
        }
        createWalls();
    }

    private void createWalls() {
        int walls = world.create();
        PositionComponent positionComponent = world.edit(walls).create(PositionComponent.class);
        positionComponent.x = 0;
        positionComponent.y = -100;
        PhysicsBodyComponent physicsBodyComponent = world.edit(walls).create(PhysicsBodyComponent.class);
        physicsBodyComponent.hasMass = false;
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[3];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[0].polygon.vertices = new Vector2[] {
                new Vector2(-458.07f, 388.32f),
                new Vector2(-498.01f, 378.49f),
                new Vector2(-319.51f, -450.41f),
                new Vector2(-279.78f, -397.73f),
        };
        physicsBodyComponent.fixtures[1] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[1].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[1].polygon.vertices = new Vector2[] {
                new Vector2(237.48f, -400.87f),
                new Vector2(-279.78f, -397.73f),
                new Vector2(-319.51f, -450.41f),
                new Vector2(283.46f, -447.98f),
        };
        physicsBodyComponent.fixtures[2] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[2].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[2].polygon.vertices = new Vector2[] {
                new Vector2(470.12f, 382.45f),
                new Vector2(430.16f, 392.18f),
                new Vector2(237.48f, -400.87f),
                new Vector2(283.46f, -447.98f),
        };
    }
}
