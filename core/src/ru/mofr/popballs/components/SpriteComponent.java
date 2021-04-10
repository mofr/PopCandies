package ru.mofr.popballs.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteComponent extends Component {
    public TextureRegion textureRegion;
    public float originX;
    public float originY;
    public float scaleX = 1;
    public float scaleY = 1;
    public int order;
}
