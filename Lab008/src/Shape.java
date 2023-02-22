import java.awt.*;

public class Shape {
    protected Point position;
    protected Point[] shape;
    protected Game screen;
    private Color color = Color.WHITE;

    public Shape(Game screen, Point position, Point[] points) {
        this.screen = screen;
        this.position = position;
        this.shape = points;
    }
    public void setColor(Color color) { this.color = color; }
    public Color getColor() { return color; }

    /**
     * getPoints applies the rotation and offset to the shape of the polygon
     *
     * @return an array of points that represent the current rotation and offset of the polygon
     */
    public Point[] getPoints() {
        if(this instanceof Circle) {
            int radius = ((Circle)this).radius;
            int amount = radius * 4;
            Point[] points = new Point[amount];
            for (int i = 0; i < amount; i++) {
                double theta = Math.toRadians(360.0/amount);
                points[i] = new Point((position.getX() + radius) * Math.cos(theta), (position.getY() + radius) * Math.sin(theta));
            }
            return points;
        }
        double rotation = ((Polygon)this).rotation;
        int i = 0;
        Point center = ((Polygon)this).findCenter();
        Point[] points = new Point[shape.length];
        for (Point p : shape) {
            double x = ((p.getX()-center.getX()) * Math.cos(Math.toRadians(rotation)))
                    - ((p.getY()-center.getY()) * Math.sin(Math.toRadians(rotation)))
                    + center.getX()/2 + position.getX();
            double y = ((p.getX()-center.getX()) * Math.sin(Math.toRadians(rotation)))
                    + ((p.getY()-center.getY()) * Math.cos(Math.toRadians(rotation)))
                    + center.getY()/2 + position.getY();
            points[i++] = new Point(x,y);
        }
        return points;
    }

    /**
     * contains implements some magical math (i.e. the ray-casting algorithm)
     */
    public boolean contains(Point point) {
        Point[] points = shape;
        double crossingNumber = 0;
        for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) {
            if ((((points[i].getX() < point.getX()) && (point.getX() <= points[j].getX())) ||
                    ((points[j].getX() < point.getX()) && (point.getX() <= points[i].getX()))) &&
                    (point.getY() > points[i].getY() + (points[j].getY()-points[i].getY())/
                            (points[j].getX() - points[i].getX()) * (point.getX() - points[i].getX()))) {
                crossingNumber++;
            }
        }
        return crossingNumber % 2 == 1;
    }

    public void paint(Graphics brush) {
        update();
        wrapPosition();

        if(this instanceof Circle) {
            int radius = ((Circle)this).radius;
            brush.setColor(getColor());
            brush.fillOval((int)position.getX(), (int)position.getY(), radius, radius);
            return;
        }
        int[] x = new int[this.shape.length];
        int[] y = new int[this.shape.length];
        for (int i = 0; i < this.shape.length; i++) {
            x[i] = (int) this.shape[i].getX() + (int)position.getX();
            y[i] = (int) this.shape[i].getY() + (int)position.getY();
        }
        brush.setColor(getColor());
        brush.fillPolygon(x, y, shape.length);
    }

    public void update() { return; }

    private void wrapPosition() {
        if (position.getX() > screen.getWidth()) { position.setX(0); }
        if (position.getY() > screen.getHeight()) { position.setY(0); }
        if (position.getX() < 0) { position.setX(screen.getWidth()); }
        if (position.getY() < 0) { position.setY(screen.getHeight()); }
    }

    public boolean collides(Polygon target) {
        for (int i = 0; i < target.shape.length; i++) {
            if(contains(target.getPoints()[i])) {
                return true;
            }
        }
        return false;
    }

}
