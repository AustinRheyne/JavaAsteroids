public class Star extends Circle{
    int SPEED;
    public Star(Game screen, int radius) {
        super(screen, radius, new Point(Asteroids.getRandomNumber(0, screen.getWidth()), Asteroids.getRandomNumber(0, screen.getHeight())));
        SPEED = radius/4;
    }

    @Override
    public void update() {
        position.setX(position.getX() + SPEED);
    }

    @Override
    public void onEdge() {
        position.setY(Asteroids.getRandomNumber(0, (screen.getHeight() - radius)));
    }
}
