import java.awt.*;
import java.sql.PseudoColumnUsage;
import java.util.concurrent.CompletionService;

public class Particle extends Square{
    public Point push;
    private double force;
    private int opacity = 255;
    private double angle;

    private Color color = Color.red;
    public Particle(Game screen, Point position, double angle, double force) {
        super(screen, position, 3, 3);
        this.angle = angle;
        double pX = Math.cos(angle) * force;
        double pY = Math.sin(angle) * force;
        push = new Point(pX, pY);

        this.force = force;
        setColor(color);
    }

    @Override
    public void update() {
        if (opacity <= 0) {remove = true; return;}
        opacity = Math.max(opacity - 6, 0);
        setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity));

        double pX = Math.cos(angle) * force;
        double pY = Math.sin(angle) * force;
        push = new Point(pX, pY);

        position.setX(position.getX() + push.getX());
        position.setY(position.getY() + push.getY());
        force = force <= 0 ? 0 : force - 0.1;
    }
}
