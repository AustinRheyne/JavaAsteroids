public class PointsSystem extends Text{
    public int points = 0;

    public PointsSystem(int points, int x, int y) {
        super("Points: 0", x, y);
        this.points = points;
    }

    @Override
    public void update() {
        super.value = "Points: " + points;
    }
}
