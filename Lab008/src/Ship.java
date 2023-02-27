import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.annotation.AnnotationFormatError;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.concurrent.PriorityBlockingQueue;

public class Ship extends Polygon implements KeyListener {
    private final double SPEED = 0.05;
    private final double TORQUE = 4;
    private boolean forward = false;
    private boolean backward = false;
    private boolean left = false;
    private boolean right = false;
    private Asteroids manager;
    private Point pull = new Point(0, 0);
    public Ship (Game screen, Asteroids manager, Point[] points, double rotation) {
        super(points, new Point(screen.getWidth()/2.0, screen.getHeight()/2.0), rotation, screen);
        this.manager = manager;
    }
    @Override
    public void update() {
        turn();
        move();
    }

    public Point getPull() {return pull;}

    public void move() {

        if (forward) {
            accelerate(SPEED);
            double angle = (rotation + 180) % 360.0;
            double pX = (Math.cos(angle) * 5) + position.getX();
            double pY = (Math.sin(angle) * 5) + position.getY();
            Point pos = new Point(position.getX(), position.getY());
            ((Asteroids)super.screen).createParticle(pos, getPull(), 1);
        }
        if (backward) { accelerate(-SPEED); }
        double newX = position.getX() + pull.getX();
        double newY = position.getY() + pull.getY();
        position.setX(newX);
        position.setY(newY);
    }

    public void turn() {
        if (left) {
            rotate((int)-TORQUE);
        }
        if (right) {
            rotate((int)TORQUE);
        }
    }

    public void accelerate (double acceleration) {
        pull.setX(pull.getX() + (acceleration * Math.cos(Math.toRadians(rotation))));
        pull.setY(pull.getY() + (acceleration * Math.sin(Math.toRadians(rotation))));
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            forward = true;
        }
        if (e.getKeyChar() == 's') {
            backward = true;
        }
        if (e.getKeyChar() == 'a') {
            left = true;
        }
        if(e.getKeyChar() == 'd') {
            right = true;
        }
        if(e.getKeyChar() == ' ') {
            manager.createBullet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            forward = false;
        }
        if (e.getKeyChar() == 's') {
            backward = false;
        }
        if (e.getKeyChar() == 'a') {
            left = false;
        }
        if(e.getKeyChar() == 'd') {
            right = false;
        }
    }
}
