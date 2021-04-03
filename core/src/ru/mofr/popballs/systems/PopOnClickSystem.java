package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import ru.mofr.popballs.components.*;
import ru.mofr.popballs.utils.Camera;

@All(ClickComponent.class)
public class PopOnClickSystem extends IteratingSystem {
    protected ComponentMapper<ClickComponent> mClickComponent;
    protected ComponentMapper<PoppableComponent> mPoppableComponent;
    protected PhysicsSystem physicsSystem;

    @Override
    protected void process(int entityId) {
        ClickComponent click = mClickComponent.get(entityId);
        Integer clickedEntity = physicsSystem.getAtPoint(click.x, click.y, 20);
        if (clickedEntity != null) {
            PoppableComponent poppableComponent = mPoppableComponent.get(clickedEntity);
            if (poppableComponent != null) {
                world.edit(clickedEntity).create(PopComponent.class);
            }
        }
    }
}
