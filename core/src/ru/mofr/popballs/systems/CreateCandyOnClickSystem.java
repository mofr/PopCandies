package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mofr.popballs.components.*;

@All(ClickComponent.class)
public class CreateCandyOnClickSystem extends IteratingSystem {
    private final Texture candyTexture = new Texture("candy9.png");
    protected ComponentMapper<ClickComponent> mClickComponent;

    public CreateCandyOnClickSystem() {
        candyTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    protected void process(int entityId) {
        ClickComponent click = mClickComponent.get(entityId);
        for (int i = 0; i < 3; ++i)
            createBall(click.x, click.y);
    }

    private void createBall(double x, double y) {
        int ball = world.create();
        PositionComponent positionComponent = world.edit(ball).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        SpriteComponent spriteComponent = world.edit(ball).create(SpriteComponent.class);
        spriteComponent.textureRegion = new TextureRegion(candyTexture);
        spriteComponent.originX = 0.5f;
        spriteComponent.originY = 0.5f;
        PhysicsBodyComponent physicsBodyComponent = world.edit(ball).create(PhysicsBodyComponent.class);
        physicsBodyComponent.hasMass = true;
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[1];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].circle = new PhysicsBodyComponent.Circle();
        physicsBodyComponent.fixtures[0].circle.radius = 60;
    }
}
