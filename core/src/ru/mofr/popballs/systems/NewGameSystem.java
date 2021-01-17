package ru.mofr.popballs.systems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import ru.mofr.popballs.components.*;

@All(NewGameComponent.class)
public class NewGameSystem extends IteratingSystem {
    private final Texture candyTexture = new Texture("candy1.png");

    @Override
    protected void process(int entityId) {
        createBall(0, 0);
        createBall(100, 100);
        createBall(-200, -250);
    }

    private void createBall(int x, int y) {
        int ball = world.create();
        PositionComponent positionComponent = world.edit(ball).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        world.edit(ball).create(TextureComponent.class).texture = candyTexture;
        PhysicsBodyComponent physicsBodyComponent = world.edit(ball).create(PhysicsBodyComponent.class);
        physicsBodyComponent.radius = 40;
    }
}
