import javax.annotation.processing.SupportedOptions;
import java.awt.*;
import java.sql.PseudoColumnUsage;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;

public class Particle extends Square{
    public Point push;
    private double force;
    private int opacity = 255;
    private double angle;
    public Particle(Game screen, Point position, Point push, double force) {
        super(screen, position, 3, 3);
        this.NO_WRAP = true;
        this.push = new Point(push.getX() * -1, push.getY() * -1);
        this.force = force;
        setColor(getColor());

    }

    @Override
    public void update() {
        if (opacity <= 0) {remove = true; return;}
        opacity = Math.max(opacity - 6, 0);
        setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), opacity));
        position.setX(position.getX() + (push.getX()*force));
        position.setY(position.getY() + (push.getY()*force));
        force = force <= 0 ? 0 : force - 0.1;

    }
}
