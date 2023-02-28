import java.awt.*;

public class Text {
    String value;
    int x;
    int y;
    public Color color = Color.WHITE;
    public Text(String value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) { this.color = color; }

    public void paint(Graphics brush) {
        update();
        brush.setColor(color);
        Font font = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fm = brush.getFontMetrics(font);
        brush.drawString(value, x, y);
    }

    public void update() {}
}
