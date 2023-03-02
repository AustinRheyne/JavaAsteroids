import java.awt.*;

public class Bullet extends Circle{
    private Ship player;
    private double dx = 0.0;
    private double dy = 0.0;
    private Point inertia;
    private final double SPEED = 7;
    public Bullet(Game screen, int radius, Ship player) {
        super(screen, radius, new Point(player.position.getX(), player.position.getY()));
        this.player = player;
        this.NO_WRAP = true;

        // Use the rotation of the ship and the unit circle to decide the correct rate of change
        dx = Math.cos(Math.toRadians(player.rotation))*SPEED;
        dy = Math.sin(Math.toRadians(player.rotation))*SPEED;
        inertia = new Point(player.getPull().getX(), player.getPull().getY()); // Take the current ship velocity so that we adhere to
        // Newtons laws of motion

        this.setColor(Color.RED);
    }
    public void update() {
        Point pullP = new Point(-inertia.getX()+Asteroids.getRandomNumber(-2, 2), -inertia.getY()+Asteroids.getRandomNumber(-2, 2));
        ((Asteroids)screen).createParticle(new Point(position.getX(), position.getY()), pullP, 1).setColor(Color.red);
        position.setX(position.getX() + inertia.getX() + dx);
        position.setY(position.getY() + inertia.getY() + dy);
    }
    public void onEdge() {remove = true;}


}
