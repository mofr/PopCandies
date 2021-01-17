package ru.mofr.popballs;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import org.dyn4j.dynamics.Body;
import ru.mofr.popballs.components.NewGameComponent;
import ru.mofr.popballs.systems.*;

public class Game extends ApplicationAdapter {
    World world;
    org.dyn4j.world.World<Body> physicsWorld;

    @Override
    public void create () {
        WorldConfiguration setup = new WorldConfigurationBuilder()
                .with(new InputSystem())
                .with(new NewGameSystem())
                .with(new CreateCandyOnClickSystem())
                .with(new RenderSystem())
                .with(new CleanUpSystem())
                .build();

        world = new World(setup);
        physicsWorld = new org.dyn4j.world.World<>();

        int newGame = world.create();
        world.edit(newGame).create(NewGameComponent.class);
    }

    @Override
    public void render () {
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.setDelta(deltaTime);
        world.process();
        physicsWorld.update(deltaTime);
    }
}
