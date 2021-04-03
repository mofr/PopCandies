package ru.mofr.popballs.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mofr.popballs.components.ClickComponent;
import ru.mofr.popballs.utils.Camera;

public class InputSystem extends BaseSystem {
    @Override
    protected void processSystem() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int click = this.world.create();
            ClickComponent clickComponent = this.world.edit(click).create(ClickComponent.class);
            clickComponent.x = Camera.unprojectX(Gdx.input.getX());
            clickComponent.y = Camera.unprojectY(Gdx.input.getY());
        }
    }
}
