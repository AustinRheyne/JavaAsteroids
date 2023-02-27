public class Square extends Shape{
    double width;
    double height;
    public Square(Game screen, Point position, double width, double height) {
        super(screen, position, new Point[]{new Point(0, 0), new Point(width, 0), new Point(width, height), new Point(0, height)});
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {return;}
}
