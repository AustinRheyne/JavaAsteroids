

import com.sun.java.accessibility.util.TopLevelWindowListener;

import javax.swing.text.Position;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

class Asteroids extends Game {
    Ship player;
    ArrayList<Asteroid> obstructions = new ArrayList<>();
    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Text> texts = new ArrayList<>();
    File[] largeAsteroidFiles;
    File[] mediumAsteroidFiles;
    File[] smallAsteroidFiles;
    boolean gamePlaying = true;
    PointsSystem points = new PointsSystem(0, 700, 40);
    Text gameOver;

    int level = 1;
    public Asteroids() {
        super("Asteroids!", 800, 600);
        this.setFocusable(true);
        this.requestFocus();
        Point[] ship1 = new Point[]{new Point(0.0, -10.0), new Point(0.0, 10.0), new Point(25.0, 0.0)};
        player = new Ship(this, this, ship1, 0.0);
        player.setColor(Color.GRAY);
        points.setColor(Color.orange);
        this.addKeyListener(this.player);

        largeAsteroidFiles = new File("./LargeAsteroids").listFiles();
        mediumAsteroidFiles = new File("./MediumAsteroids").listFiles();
        smallAsteroidFiles = new File("./SmallAsteroids").listFiles();

        for(int i = 0; i < 2; ++i) {
            try {
                this.obstructions.add(this.createAsteroid());
            } catch (FileNotFoundException ignored) {}
        }
        for(int i = 0; i < 50; ++i) {
            this.stars.add(this.createStar());
        }

        gameOver = new Text("GAME OVER!", 375, 300);
        gameOver.setColor(Color.RED);
    }
    public void paint(Graphics brush) {
        if (gamePlaying) {
            brush.setColor(Color.black);
            brush.fillRect(0, 0, this.width, this.height);

            paintStars(brush);
            paintParticles(brush);
            this.player.paint(brush);
            paintAsteroids(brush);
            paintBullets(brush);
            paintText(brush);
            points.paint(brush);

            if(obstructions.size() == 0) {
                nextLevel();

                Text nextLevelText = new Text("LEVEL UP!", 375, 300);
                nextLevelText.setDuration(2.0);
                nextLevelText.setColor(Color.GREEN);
                texts.add(nextLevelText);

            }
        } else {
            gameOver.paint(brush);
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
        this.player.accelerate(-2.5);
    }
    public Particle createParticle(Point position, Point push, double force) {
        Particle p = new Particle(this, position, push, force);
        particles.add(p);
        return p;
    }
    private void paintParticles(Graphics brush) {
        ArrayList<Particle> toRemoveP = new ArrayList<>();
        for (Particle particle : particles) {
            particle.paint(brush);
            if (particle.remove) {toRemoveP.add(particle);}
        }
        particles.removeAll(toRemoveP);
    }
    private void paintStars(Graphics brush) {
        for (Star star : stars) {
            star.paint(brush);
        }
    }
    private void paintAsteroids(Graphics brush) {
        for (Asteroid obj : obstructions) {
            obj.paint(brush);
            if (obj.collides(player)) {
                gamePlaying = false;
            }
        }
    }
    private void paintBullets(Graphics brush) {

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
    }
    private void paintText(Graphics brush) {
        ArrayList<Text> toRemove = new ArrayList<>();
        for(Text txt : texts) {
            txt.paint(brush);
            if (txt.remove) {
                toRemove.add(txt);
            }
        }
        texts.removeAll(toRemove);
    }
    private void nextLevel() {
        level++;
        for(int i = 0; i < 2*level; ++i) {
            try {
                this.obstructions.add(this.createAsteroid());
            } catch (FileNotFoundException ignored) {}
        }
    }
    public static int getRandomNumber(int min, int max) {
        return (int)(Math.random() * (double)(max - min) + (double)min);
    }
    public void splitAsteroid(Asteroid ast) {
        // Update our points system to reflect some damage done
        points.points += (3 - (ast.getSize()-1)) + (level-1);

        for (int i = 0; i < 180; i++) {
            int theta = i % 180;
            double x = Math.cos(theta);
            double y = Math.sin(theta);
            Particle p = createParticle(new Point(ast.findCenter().getX() + ast.position.getX(), ast.findCenter().getY() + ast.position.getY()),
                           new Point(x, y), Math.random() * (ast.getSize() + 1));
            Color c  = getRandomNumber(0, 2) == 0 ? Color.WHITE : Color.GRAY;
            p.setColor(c);
        }

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
