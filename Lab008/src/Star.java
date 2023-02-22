public class Star extends Circle{
    public Star(Game screen, int radius) {
        super(screen, radius, new Point(Asteroids.getRandomNumber(0, screen.getWidth()), Asteroids.getRandomNumber(0, screen.getHeight())));
    }


}
