package ru.mofr.popballs.systems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import org.dyn4j.geometry.Vector2;
import ru.mofr.popballs.components.*;

@All(NewGameComponent.class)
public class NewGameSystem extends IteratingSystem {
    private final Texture candyTexture = new Texture("candy1.png");

    @Override
    protected void process(int entityId) {
        createBall(0, 0);
        createBall(100, 100);
        createBall(-200, -250);
        createWalls();
    }

    private void createBall(int x, int y) {
        int ball = world.create();
        PositionComponent positionComponent = world.edit(ball).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        world.edit(ball).create(TextureComponent.class).texture = candyTexture;
        PhysicsBodyComponent physicsBodyComponent = world.edit(ball).create(PhysicsBodyComponent.class);
        physicsBodyComponent.hasMass = true;
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[1];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].circle = new PhysicsBodyComponent.Circle();
        physicsBodyComponent.fixtures[0].circle.radius = 40;
    }

    private void createWalls() {
        int walls = world.create();
        PositionComponent positionComponent = world.edit(walls).create(PositionComponent.class);
        positionComponent.x = 0;
        positionComponent.y = 0;
        PhysicsBodyComponent physicsBodyComponent = world.edit(walls).create(PhysicsBodyComponent.class);
        physicsBodyComponent.hasMass = false;
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[3];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[0].polygon.vertices = new Vector2[] {
                new Vector2(-45.807, 38.832),
                new Vector2(-49.801, 37.849),
                new Vector2(-31.951, -45.041),
                new Vector2(-27.978, -39.773),
        };
        physicsBodyComponent.fixtures[1] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[1].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[1].polygon.vertices = new Vector2[] {
                new Vector2(23.748, -40.087),
                new Vector2(-27.978, -39.773),
                new Vector2(-31.951, -45.041),
                new Vector2(28.346, -44.798),
        };
        physicsBodyComponent.fixtures[2] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[2].polygon = new PhysicsBodyComponent.Polygon();
        physicsBodyComponent.fixtures[2].polygon.vertices = new Vector2[] {
                new Vector2(47.012, 38.245),
                new Vector2(43.016, 39.218),
                new Vector2(23.748, -40.087),
                new Vector2(28.346, -44.798),
        };
    }
}
