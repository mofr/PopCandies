package ru.mofr.popballs.systems;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.mofr.popballs.components.PopComponent;
import ru.mofr.popballs.factories.CandyFactory;

import java.util.Random;

@All({PopComponent.class})
public class CreateCandyOnPopSystem extends IteratingSystem {
    private final Random random = new Random();

    @Override
    protected void process(int entityId) {
        float x = random.nextInt(200) - 100;
        float y = 1000 + random.nextInt(400);
        CandyFactory.create(world, x, y);
    }
}
