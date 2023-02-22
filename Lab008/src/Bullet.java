import java.awt.*;

public class Bullet extends Circle{
    private Ship player;
    public Bullet(Game screen, int radius, Ship player) {
        super(screen, radius, new Point(player.position.getX(), player.position.getY()));
        this.player = player;
        this.setColor(Color.RED);
    }



}
