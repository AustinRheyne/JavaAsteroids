

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

class Asteroids extends Game {
    Ship player;
    Point[] ship1 = new Point[]{new Point(0.0, 0.0), new Point(0.0, 20.0), new Point(20.0, 10.0)};
    ArrayList<Asteroid> obstructions = new ArrayList();
    ArrayList<Star> stars = new ArrayList();
    ArrayList<Bullet> bullets = new ArrayList();
    File[] asteroidFiles;


    public Asteroids() {
        super("Asteroids!", 800, 600);
        this.setFocusable(true);
        this.requestFocus();
        this.player = new Ship(this, this, this.ship1, 0.0);
        this.addKeyListener(this.player);

        asteroidFiles = new File("./Asteroids").listFiles();

        for(int i = 0; i < 5; ++i) {
            try {
                this.obstructions.add(this.createAsteroid());
            } catch (FileNotFoundException ignored) {}
        }

        for(int i = 0; i < 50; ++i) {
            this.stars.add(this.createStar());
        }

    }

    public void paint(Graphics brush) {
        brush.setColor(Color.black);
        brush.fillRect(0, 0, this.width, this.height);
        this.player.paint(brush);

        for (Asteroid obj : obstructions) {
            obj.paint(brush);
        }

        for (Star star : stars) {
            star.paint(brush);
        }

        for (Bullet bullet : bullets) {
            bullet.paint(brush);
        }

    }

    public static void main(String[] args) {
        Asteroids a = new Asteroids();
        a.repaint();
    }

    public Asteroid createAsteroid() throws FileNotFoundException {
        File chosenPrefabFile =  asteroidFiles[getRandomNumber(0, asteroidFiles.length-1)];
        Scanner reader = new Scanner(chosenPrefabFile);
        String data = reader.nextLine();
        String[] pnts = data.split("\\],\\[");
        Point[] result = new Point[pnts.length];
        for(int i = 0; i < result.length; i++) {
            String str = pnts[i];
            str = str.replace("[", "");
            str = str.replace("]", "");
            System.out.println(Arrays.toString(str.split(",")));
            double x = Double.parseDouble(str.split(",")[0]);
            double y = Double.parseDouble(str.split(",")[1]);
            result[i] = new Point(x, y);
        }
        return new Asteroid(this, result);
    }
    public Star createStar() {
        Star star = new Star(this, getRandomNumber(1, 10));
        star.setColor(Color.YELLOW);
        return star;
    }
    public void createBullet() {
        this.bullets.add(new Bullet(this, 10, this.player));
    }
    public static int getRandomNumber(int min, int max) {
        return (int)(Math.random() * (double)(max - min) + (double)min);
    }
}
