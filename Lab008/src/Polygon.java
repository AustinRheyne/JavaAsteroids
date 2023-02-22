//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Polygon extends Shape {
    public double rotation;

    public Polygon(Point[] shape, Point position, double rotation, Game screen) {
        super(screen, position, shape);
        this.rotation = rotation;
        Point origin = new Point(shape[0].getX(), shape[0].getY());
        for (Point p : shape) {
            if (p.getX() < origin.getX()) {
                origin.setX(p.getX());
            }
            if (p.getY() < origin.getY()) {
                origin.setY(p.getY());
            }
        }

        // Then, we orient all of its points relative to the real origin.
        for (Point p : shape) {
            p.setX(p.getX() - origin.getX());
            p.setY(p.getY() - origin.getY());
        }

    }

    public Game getScreen() {
        return this.screen;
    }

    public void rotate(int degrees) {
        this.rotation = (this.rotation + (double)degrees) % 360.0;
    }

    private double findArea() {
        double sum = 0.0;
        int i = 0;

        for(int j = 1; i < this.shape.length; j = (j + 1) % this.shape.length) {
            sum += this.shape[i].getX() * this.shape[j].getY() - this.shape[j].getX() * this.shape[i].getY();
            ++i;
        }

        return Math.abs(sum / 2.0);
    }

    public Point findCenter() {
        Point sum = new Point(0.0, 0.0);
        int i = 0;

        for(int j = 1; i < this.shape.length; j = (j + 1) % this.shape.length) {
            sum.setX(sum.getX() + (this.shape[i].getX() + this.shape[j].getX()) * (this.shape[i].getX() * this.shape[j].getY() - this.shape[j].getX() * this.shape[i].getY()));
            sum.setY(sum.getY() + (this.shape[i].getY() + this.shape[j].getY()) * (this.shape[i].getX() * this.shape[j].getY() - this.shape[j].getX() * this.shape[i].getY()));
            ++i;
        }

        double area = this.findArea();
        return new Point(Math.abs(sum.getX() / (6.0 * area)), Math.abs(sum.getY() / (6.0 * area)));
    }

    public void update() {
    }
}
