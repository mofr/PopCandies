package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import ru.mofr.popballs.components.ClickComponent;
import ru.mofr.popballs.components.NewGameComponent;
import ru.mofr.popballs.components.PositionComponent;
import ru.mofr.popballs.components.TextureComponent;

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
    }
}
