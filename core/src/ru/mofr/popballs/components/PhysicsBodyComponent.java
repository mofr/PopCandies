package ru.mofr.popballs.components;

import com.artemis.Component;
import org.dyn4j.geometry.Vector2;

public class PhysicsBodyComponent extends Component {
    public static class Fixture {
        public Circle circle;
        public Polygon polygon;
    }
    public static class Circle {
        public float radius;
    }
    public static class Polygon {
        public Vector2[] vertices;
    }
    public Fixture[] fixtures;
    public boolean hasMass;
}
