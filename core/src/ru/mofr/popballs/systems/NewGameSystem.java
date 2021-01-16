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
//        createSnake(0,0);
//        createApple((int)(Math.random() * 20 - 10), (int)(Math.random() * 20 - 10));
    }

//    private void createSnake(int x, int y) {
//        int snake = world.create();
//        world.edit(snake).create(SnakeComponent.class);
//        world.edit(snake).create(TurnComponent.class);
//        TailComponent tailComponent = world.edit(snake).create(TailComponent.class);
//        tailComponent.growth = 3;
//        tailComponent.tail = new LinkedList<>();
//        PositionComponent positionComponent = world.edit(snake).create(PositionComponent.class);
//        positionComponent.x = x;
//        positionComponent.y = y;
//        MovementComponent movementComponent = world.edit(snake).create(MovementComponent.class);
//        movementComponent.speed = 4;
//        movementComponent.direction = Direction.UP;
//        ColliderComponent colliderComponent = world.edit(snake).create(ColliderComponent.class);
//        colliderComponent.sizeX = 1;
//        colliderComponent.sizeY = 1;
//        world.edit(snake).create(TextureComponent.class).texture = pointTexture;
//    }

    private void createBall(int x, int y) {
        int ball = world.create();
        PositionComponent positionComponent = world.edit(ball).create(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;
        world.edit(ball).create(TextureComponent.class).texture = candyTexture;
    }
}
