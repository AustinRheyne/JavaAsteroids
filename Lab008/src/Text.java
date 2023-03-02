import java.awt.*;
import java.sql.Time;
import java.util.Spliterator;

public class Text {
    String value;
    int x;
    int y;
    public Color color = Color.WHITE;
    public boolean remove = false;

    private double duration = 0;

    private double lastTime = System.currentTimeMillis();
    private double totalTime = 0.0f;
    public Text(String value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public void setDuration(Double seconds) { this.duration = seconds; }
    public void setColor(Color color) { this.color = color; }

    public void paint(Graphics brush) {
        update();
        brush.setColor(color);
        Font font = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fm = brush.getFontMetrics(font);
        brush.drawString(value, x, y);
    }

    public void update() {
        if(duration > 0) {
            double currentTime = System.currentTimeMillis();
            totalTime += (currentTime - lastTime);
            lastTime = currentTime;
            if(totalTime/1000.0 >= duration) {
                remove = true;
            }
        }
    }


}
