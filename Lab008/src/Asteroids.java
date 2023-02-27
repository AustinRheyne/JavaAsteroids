

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Asteroids extends Game {
    Ship player;
    Point[] ship1 = new Point[]{new Point(0.0, 0.0), new Point(0.0, 20.0), new Point(20.0, 10.0)};
    ArrayList<Asteroid> obstructions = new ArrayList<>();
    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();
    File[] largeAsteroidFiles;
    File[] mediumAsteroidFiles;
    File[] smallAsteroidFiles;

    boolean gamePlaying = true;
    public Asteroids() {
        super("Asteroids!", 800, 600);
        this.setFocusable(true);
        this.requestFocus();
        player = new Ship(this, this, this.ship1, 0.0);
        player.setColor(Color.GRAY);
        this.addKeyListener(this.player);

        largeAsteroidFiles = new File("./LargeAsteroids").listFiles();
        mediumAsteroidFiles = new File("./MediumAsteroids").listFiles();
        smallAsteroidFiles = new File("./SmallAsteroids").listFiles();

        for(int i = 0; i < 4; ++i) {
            try {
                this.obstructions.add(this.createAsteroid());
            } catch (FileNotFoundException ignored) {}
        }

        for(int i = 0; i < 50; ++i) {
            this.stars.add(this.createStar());
        }

    }
    public void paint(Graphics brush) {
        if (gamePlaying) {
            brush.setColor(Color.black);
            brush.fillRect(0, 0, this.width, this.height);

            for (Star star : stars) {
                star.paint(brush);
            }

            this.player.paint(brush);

            for (Asteroid obj : obstructions) {
                obj.paint(brush);
                if (obj.collides(player)) {
                    gamePlaying = false;
                }
            }


            ArrayList<Bullet> toRemoveB = new ArrayList<>();
            ArrayList<Asteroid> toRemoveAst = new ArrayList<>();
            for (Bullet bullet : bullets) {
                bullet.paint(brush);
                if (bullet.remove) {
                    toRemoveB.add(bullet);
                } else { // All asteroids and bullets have been moved, check for collision
                    for (Asteroid obj : obstructions) {
                        if (bullet.collides(obj)) {
                            bullet.remove = true;
                            toRemoveAst.add(obj);
                        }
                    }
                }
            }
            bullets.removeAll(toRemoveB);
            for (Asteroid ast : toRemoveAst) {
                splitAsteroid(ast);
            }
            for (Particle particle : particles) {
                particle.paint(brush);
            }
        }
    }
    public static void main(String[] args) {
        Asteroids a = new Asteroids();
        a.repaint();
    }
    public Asteroid createAsteroid() throws FileNotFoundException, ArrayIndexOutOfBoundsException {

        File chosenPrefabFile =  largeAsteroidFiles[getRandomNumber(0, largeAsteroidFiles.length)];
        Scanner reader = new Scanner(chosenPrefabFile);
        String data = reader.nextLine();
        String[] pnts = data.split("\\],\\[");
        Point[] result = new Point[pnts.length];
        for(int i = 0; i < result.length; i++) {
            String str = pnts[i];
            str = str.replace("[", "");
            str = str.replace("]", "");
            double x = Double.parseDouble(str.split(",")[0]);
            double y = Double.parseDouble(str.split(",")[1]);
            result[i] = new Point(x, y);
        }

        //Point[] asteroid1 = new Point[]{new Point(0, 0), new Point(0, 20), new Point(10, 30), new Point(20, 20), new Point(20, 0)};
        return new Asteroid(this, result, 3);
    }
    public Asteroid createAsteroid(double oldX, double oldY, int size) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        File[] asteroidFiles = size == 3 ? largeAsteroidFiles : size == 2 ? mediumAsteroidFiles : smallAsteroidFiles;
        File chosenPrefabFile =  asteroidFiles[getRandomNumber(0, asteroidFiles.length-1)];
        Scanner reader = new Scanner(chosenPrefabFile);
        String data = reader.nextLine();
        String[] pnts = data.split("\\],\\[");
        Point[] result = new Point[pnts.length];
        for(int i = 0; i < result.length; i++) {
            String str = pnts[i];
            str = str.replace("[", "");
            str = str.replace("]", "");
            double x = Double.parseDouble(str.split(",")[0]);
            double y = Double.parseDouble(str.split(",")[1]);
            result[i] = new Point(x, y);
        }

        //Point[] asteroid1 = new Point[]{new Point(0, 0), new Point(0, 20), new Point(10, 30), new Point(20, 20), new Point(20, 0)};
        Asteroid ast = new Asteroid(this, result, size);
        ast.position.setX(oldX);
        ast.position.setY(oldY);
        return ast;
    }
    public Star createStar() {
        Star star = new Star(this, getRandomNumber(1, 10));
        star.setColor(Color.YELLOW);
        return star;
    }
    public void createBullet() {
        this.bullets.add(new Bullet(this, 9, this.player));
        new Thread(() -> {StdAudio.play("./Sounds/Shoot.wav");}).start();
    }
    public void createParticle(Point position, Point origin, double force) {
        particles.add(new Particle(this, position, origin, force));
    }
    public static int getRandomNumber(int min, int max) {
        return (int)(Math.random() * (double)(max - min) + (double)min);
    }
    public void splitAsteroid(Asteroid ast) {
        new Thread(() -> {StdAudio.play("./Sounds/Explosion.wav");}).start();
        ast.decrementSize();
        if(ast.getSize() <= 0) {
            obstructions.remove(ast);
            return;
        }
        double x = ast.position.getX();
        double y = ast.position.getY();
        try { obstructions.add(createAsteroid(x, y, ast.getSize())); obstructions.add(createAsteroid(x, y, ast.getSize())); } catch (Exception ignored) {}
        obstructions.remove(ast);
    }
}
