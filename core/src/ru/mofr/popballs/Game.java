package ru.mofr.popballs;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.dyn4j.dynamics.Body;
import ru.mofr.popballs.components.NewGameComponent;
import ru.mofr.popballs.systems.*;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    World world;
    org.dyn4j.world.World<Body> physicsWorld;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        WorldConfiguration setup = new WorldConfigurationBuilder()
                .with(new InputSystem())
                .with(new NewGameSystem())
                .with(new CreateCandyOnClickSystem())
                .with(new RenderSystem(batch, shapeRenderer))
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

    @Override
    public void dispose () {
        batch.dispose();
    }
}
