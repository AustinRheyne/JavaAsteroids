import java.awt.*;

public class Circle extends Shape{
    protected int radius;
    public Circle (Game screen, int radius, Point position) {
        super(screen, position, new Point[]{new Point(0, 0)});
        this.radius = radius;
        this.shape = getPoints(); // Now that it has been instantiated, make sure we update the obj
    }
    public double findArea() {
        return Math.PI * (radius*radius);
    }
    public void update() {}
}
