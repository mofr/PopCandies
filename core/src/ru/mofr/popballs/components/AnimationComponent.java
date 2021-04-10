package ru.mofr.popballs.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent extends Component {
    public Animation<TextureRegion> animation;
    public float time;
}
