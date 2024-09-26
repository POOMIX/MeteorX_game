import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;
import javax.swing.JPanel;

public class panel_meteorito extends JPanel {
    private int amount_meteor;
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito[] moveMeteorito;

    public panel_meteorito(int amount_meteor) {
        this.amount_meteor = amount_meteor;
        randomMeteorito = new RandomMeteorito(amount_meteor);
        moveMeteorito = new MoveMeteorito[amount_meteor];
        setBackground(Color.BLACK);
        setLayout(null);

        for (int i = 0; i < moveMeteorito.length; i++) {
            moveMeteorito[i] = new MoveMeteorito(this, randomMeteorito.randomImage[i]);
            moveMeteorito[i].start();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < moveMeteorito.length; i++) {
            g.drawImage(randomMeteorito.randomImage[i], moveMeteorito[i].getX(), moveMeteorito[i].getY(), 50, 50, this);
        }
        repaint();
    }

    public MoveMeteorito[] getMoveMeteorito() {
        return moveMeteorito;
    }
}

class RandomMeteorito extends JPanel {
    Image randomImage[];
    String[] imageMeteorito = {
        "images/1.png", "images/2.png", "images/3.png", "images/4.png", "images/5.png",
        "images/6.png", "images/7.png", "images/8.png", "images/9.png", "images/10.png"
    };

    public RandomMeteorito(int amount_meteor) {
        Random random = new Random();
        randomImage = new Image[amount_meteor];
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
    private double vx; 
    private double vy;
    private panel_meteorito panel;
    private Image meteorImage;
    private Random random = new Random();

    MoveMeteorito(panel_meteorito panel, Image meteorImage) {
        this.panel = panel;
        this.meteorImage = meteorImage;
        vx = random.nextInt(3) + 1;  // ความเร็วในแกน X (สุ่ม)
        vy = random.nextInt(3) + 1;  // ความเร็วในแกน  (สุ่ม)
        // Randomize direction
        if (random.nextBoolean()) vx = -vx;
        if (random.nextBoolean()) vy = -vy;
    }

    public void move() {
        x += vx;
        y += vy;

         // การตรวจสอบขอบเขตของจอ
        if (x >= panel.getWidth() - 50 || x <= 0) {
            vx = -vx; // เด้งในแกน X
        }
        if (y >= panel.getHeight() - 50 || y <= 0) {
            vy = -vy; // เด้งในแกน 
        }
    }

    public void detectCollision(MoveMeteorito other) {
        int radius = 25; // รัศมีอุกกาบาต
        int dx = this.getX() - other.getX();
        int dy = this.getY() - other.getY();
        
        double distance = Math.sqrt((double)(dx * dx + dy * dy));
    
        if (distance < 2 * radius) {
            // Calculate new velocities based on collision
            double angle = Math.atan2(dy, dx);
            double speed1 = Math.sqrt(vx * vx + vy * vy);
            double speed2 = Math.sqrt(other.vx * other.vx + other.vy * other.vy);

            // Swap velocities based on the angle of collision
            double tempVx = vx;
            double tempVy = vy;
            vx = speed2 * Math.cos(angle);
            vy = speed2 * Math.sin(angle);
            other.vx = speed1 * Math.cos(angle + Math.PI);
            other.vy = speed1 * Math.sin(angle + Math.PI);
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
                Thread.sleep(10);
                move();
                for (int i = 0; i < panel.getMoveMeteorito().length; i++) {
                    if (panel.getMoveMeteorito()[i] != this) {
                        detectCollision(panel.getMoveMeteorito()[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
