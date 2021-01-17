package ru.mofr.popballs.utils;

import com.badlogic.gdx.Gdx;

public class Camera {
    public static double projectX(double x) {
        return x + Gdx.graphics.getWidth() / 2;
    }

    public static double projectY(double y) {
        return y + Gdx.graphics.getHeight() / 2;
    }

    public static double unprojectX(double x) {
        return x - Gdx.graphics.getWidth() / 2;
    }

    public static double unprojectY(double y) {
        return Gdx.graphics.getHeight() - y - Gdx.graphics.getHeight() / 2;
    }
}
