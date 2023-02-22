//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

abstract class Game extends Canvas {
    protected boolean on;
    protected int width;
    protected int height;
    protected Image buffer;

    public Game(String name, int width, int height) {
        this.width = width;
        this.height = height;
        this.on = true;
        Frame frame = new Frame(name);
        frame.add(this);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.buffer = this.createImage(width, height);
    }

    public abstract void paint(Graphics brush);

    public void update(Graphics brush) {
        this.paint(this.buffer.getGraphics());
        brush.drawImage(this.buffer, 0, 0, this);
        if (this.on) {
            this.sleep(10);
            this.repaint();
        }

    }

    private void sleep(int time) {
        try {
            Thread.sleep((long)time);
        } catch (Exception exc) {
        }

    }
}
