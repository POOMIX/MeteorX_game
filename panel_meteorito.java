import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Graphics;
import java.util.Random;

public class panel_meteorito extends JPanel {
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito[] moveMeteorito = new MoveMeteorito[5];

    public panel_meteorito() {
        randomMeteorito = new RandomMeteorito();

        setBackground(Color.BLACK);
        setLayout(null);

        for (int i = 0; i < moveMeteorito.length; i++) {
            moveMeteorito[i] = new MoveMeteorito(this);
            moveMeteorito[i].start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < moveMeteorito.length; i++) {
            g.drawImage(randomMeteorito.randomImage[i], moveMeteorito[i].getX(), moveMeteorito[i].getY(), 50, 50, this);
        }
    }
}

class RandomMeteorito extends JPanel {
    Image randomImage[] = new Image[5];
    String[] imageMeteorito = {
        "images/1.png", "images/2.png", "images/3.png",
        "images/4.png", "images/5.png", "images/6.png",
        "images/7.png", "images/8.png", "images/9.png", "images/10.png"
    };

    public RandomMeteorito() {
        Random random = new Random();
        for (int i = 0; i < randomImage.length; i++) {
            int index = random.nextInt(imageMeteorito.length);
            randomImage[i] = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + imageMeteorito[index]);
        }

        setOpaque(false);
    }
}

class MoveMeteorito extends Thread {
    private int x = 400;
    private int y = 300;
    private panel_meteorito panel;
    private Random random = new Random();
    private int randomdirection;
    private int speed;  // Speed of the meteor
    private int[] random257 = {2, 5, 7};
    private int[] random046 = {0, 4, 6};
    private int[] random345 = {3, 4, 5};
    private int[] random176 = {1, 7, 6};

    MoveMeteorito(panel_meteorito panel) {
        this.panel = panel;
        randomdirection = random.nextInt(8);  // Random initial direction
        speed = random.nextInt(5) + 1;  // Random speed between 1 and 5
    }

    public void move() {
        if (randomdirection == 0) {  // right
            x = x + speed;
        } else if (randomdirection == 1) {  // down
            y = y + speed;
        } else if (randomdirection == 2) {  // left
            x = x - speed;
        } else if (randomdirection == 3) {  // up
            y = y - speed;
        } else if (randomdirection == 4) {  // upright
            x = x + speed;
            y = y - speed;
        } else if (randomdirection == 5) {  // upleft
            x = x - speed;
            y = y - speed;
        } else if (randomdirection == 6) {  // downright
            x = x + speed;
            y = y + speed;
        } else if (randomdirection == 7) {  // downleft
            x = x - speed;
            y = y + speed;
        }

        // Handle boundary conditions
        if (x >= panel.getWidth() - 50) {
            if (randomdirection == 0 || randomdirection == 4 || randomdirection == 6) {
                randomdirection = random257[random.nextInt(random257.length)];  // Choose from leftward directions
                speed = random.nextInt(5) + 1;
            }
        } else if (x <= 0) {
            if (randomdirection == 2 || randomdirection == 5 || randomdirection == 7) {
                randomdirection = random046[random.nextInt(random046.length)];  // Choose from rightward directions
                speed = random.nextInt(5) + 1;
            }
        }

        if (y >= panel.getHeight() - 50) {
            if (randomdirection == 1 || randomdirection == 7 || randomdirection == 6) {
                randomdirection = random345[random.nextInt(random345.length)];  // Choose upward directions
                speed = random.nextInt(5) + 1;
            }
        } else if (y <= 0) {
            if (randomdirection == 3 || randomdirection == 4 || randomdirection == 5) {
                randomdirection = random176[random.nextInt(random176.length)];  // Choose downward directions
                speed = random.nextInt(5) + 1;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);  // Control update rate
                move();
                panel.repaint();  // Repaint after moving
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
