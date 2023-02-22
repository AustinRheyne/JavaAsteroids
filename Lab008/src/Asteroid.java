import java.awt.*;

public class Asteroid extends Polygon{
    public Asteroid (Game screen, Point[] points) {
        super(points, new Point(Asteroids.getRandomNumber(0, screen.getWidth()), Asteroids.getRandomNumber(0, screen.getHeight())), 0, screen);
    }
}
