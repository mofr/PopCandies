package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import ru.mofr.popballs.components.*;

@All(ClickComponent.class)
public class CreateCandyOnClickSystem extends IteratingSystem {
    private final Texture candyTexture = new Texture("candy1.png");
    protected ComponentMapper<ClickComponent> mClickComponent;

    @Override
    protected void process(int entityId) {
        ClickComponent click = mClickComponent.get(entityId);
        createBall(click.x, click.y);
    }

    private void createBall(int x, int y) {
        int ball = world.create();
        PositionComponent positionComponent = world.edit(ball).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        world.edit(ball).create(TextureComponent.class).texture = candyTexture;
        PhysicsBodyComponent physicsBodyComponent = world.edit(ball).create(PhysicsBodyComponent.class);
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[1];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].circle = new PhysicsBodyComponent.Circle();
        physicsBodyComponent.fixtures[0].circle.radius = 40;
    }
}
