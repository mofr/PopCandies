package ru.mofr.popballs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.mofr.popballs.components.AnimationComponent;
import ru.mofr.popballs.components.SpriteComponent;

@All({SpriteComponent.class, AnimationComponent.class})
public class AnimationSystem extends IteratingSystem {
    protected ComponentMapper<SpriteComponent> mSpriteComponent;
    protected ComponentMapper<AnimationComponent> mAnimationComponent;

    @Override
    protected void process(int entityId) {
        AnimationComponent animationComponent = mAnimationComponent.get(entityId);
        SpriteComponent spriteComponent = mSpriteComponent.get(entityId);

        spriteComponent.textureRegion = animationComponent.animation.getKeyFrame(animationComponent.time);
        animationComponent.time += world.getDelta();
    }
}
