package ru.mofr.popballs.systems;

import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.mofr.popballs.components.ClickComponent;
import ru.mofr.popballs.components.NewGameComponent;

@One({NewGameComponent.class, ClickComponent.class})
public class CleanUpSystem extends IteratingSystem {
    @Override
    protected void process(int entityId) {
        this.world.delete(entityId);
    }
}
