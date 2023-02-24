import java.awt.*;
import java.util.Random;

public class Asteroid extends Polygon{
    final double SPEED = Asteroids.getRandomNumber(1, 3);
    private double dx = 0.0f;
    private double dy = 0.0f;

    private int size = 3;
    public Asteroid (Game screen, Point[] points) {
        super(points, new Point(Asteroids.getRandomNumber(0, screen.getWidth()), Asteroids.getRandomNumber(0, screen.getHeight())), 0, screen);
        dx = Math.random();
        dy = Math.random();
    }

    @Override
    public void update() {
        position.setX(position.getX() + (SPEED*dx));
        position.setY(position.getY() + (SPEED*dy));
    }
}
