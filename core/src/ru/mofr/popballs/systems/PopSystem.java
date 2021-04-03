package ru.mofr.popballs.systems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.mofr.popballs.components.PopComponent;

@All(PopComponent.class)
public class PopSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        world.delete(entityId);
    }
}
