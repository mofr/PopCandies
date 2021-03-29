package ru.mofr.popballs;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import ru.mofr.popballs.components.NewGameComponent;
import ru.mofr.popballs.systems.*;

public class Game extends ApplicationAdapter {
    private World world;

    @Override
    public void create () {
        WorldConfiguration setup = new WorldConfigurationBuilder()
                .with(new InputSystem())
                .with(new NewGameSystem())
                .with(new CreateCandyOnClickSystem())
                .with(new PhysicsSystem())
                .with(new RenderSystem())
                .with(new PhysicsDebugRenderSystem())
                .with(new CleanUpSystem())
                .build();

        world = new World(setup);

        int newGame = world.create();
        world.edit(newGame).create(NewGameComponent.class);
    }

    @Override
    public void render () {
        world.setDelta(Gdx.graphics.getDeltaTime());
        world.process();
    }
}
