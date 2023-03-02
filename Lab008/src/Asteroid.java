import java.awt.*;
import java.util.Random;

public class Asteroid extends Polygon{
    final double SPEED = Asteroids.getRandomNumber(1, 3);
    private double dx = 0.0f;
    private double dy = 0.0f;

    private double rot = 0.0f;
    private int size = 3;
    public Asteroid (Game screen, Point[] points, int size) {
        super(points, new Point(Asteroids.getRandomNumber(0, screen.getWidth()), Asteroids.getRandomNumber(0, screen.getHeight())), 0, screen);
        while (this.position.getX() > 300 && this.position.getX() < 500 && this.position.getY() > 200 && this.position.getY() < 400) {
            this.position.setX(Asteroids.getRandomNumber(0, screen.getWidth()));
            this.position.setY(Asteroids.getRandomNumber(0, screen.getHeight()));
        }

        dx = Math.random();
        dy = Math.random();
        rot = (Math.random() * 2) + 1;
        this.size = size;
    }
    public int getSize() {return size;}
    public void decrementSize() {size--;}
    @Override
    public void update() {
        position.setX(position.getX() + (SPEED*dx));
        position.setY(position.getY() + (SPEED*dy));
        rotate((int)rot);
    }


}
