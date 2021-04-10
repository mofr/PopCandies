package ru.mofr.popballs.factories;

import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mofr.popballs.components.PhysicsBodyComponent;
import ru.mofr.popballs.components.PoppableComponent;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.components.SpriteComponent;

public class CandyFactory {
    public static int create(World world, double x, double y) {
        Texture texture = new Texture("candy10.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        int entity = world.create();
        PositionComponent positionComponent = world.edit(entity).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        SpriteComponent spriteComponent = world.edit(entity).create(SpriteComponent.class);
        spriteComponent.textureRegion = new TextureRegion(texture);
        spriteComponent.originX = 0.5f;
        spriteComponent.originY = 0.5f;
        PhysicsBodyComponent physicsBodyComponent = world.edit(entity).create(PhysicsBodyComponent.class);
        physicsBodyComponent.hasMass = true;
        physicsBodyComponent.fixtures = new PhysicsBodyComponent.Fixture[1];
        physicsBodyComponent.fixtures[0] = new PhysicsBodyComponent.Fixture();
        physicsBodyComponent.fixtures[0].circle = new PhysicsBodyComponent.Circle();
        physicsBodyComponent.fixtures[0].circle.radius = 60;
        world.edit(entity).create(PoppableComponent.class);
        return entity;
    }
}
