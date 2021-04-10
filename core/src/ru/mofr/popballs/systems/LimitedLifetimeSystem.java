package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mofr.popballs.components.*;

@All({LimitedLifetimeComponent.class})
public class LimitedLifetimeSystem extends IteratingSystem {
    protected ComponentMapper<LimitedLifetimeComponent> mLimitedLifetimeComponent;

    @Override
    protected void process(int entityId) {
        LimitedLifetimeComponent limitedLifetimeComponent = mLimitedLifetimeComponent.get(entityId);
        limitedLifetimeComponent.lifetime -= world.getDelta();
        if (limitedLifetimeComponent.lifetime <= 0) {
            world.delete(entityId);
        }
    }
}
