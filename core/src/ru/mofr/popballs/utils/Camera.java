package ru.mofr.popballs.utils;

import com.badlogic.gdx.Gdx;

public class Camera {
    public static int projectX(int x) {
        return x + Gdx.graphics.getWidth() / 2;
    }

    public static int projectY(int y) {
        return y + Gdx.graphics.getHeight() / 2;
    }

    public static int unprojectX(int x) {
        return x - Gdx.graphics.getWidth() / 2;
    }

    public static int unprojectY(int y) {
        return Gdx.graphics.getHeight() - y - Gdx.graphics.getHeight() / 2;
    }
}
