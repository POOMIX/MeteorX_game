import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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

    // เพิ่ม getter สำหรับ moveMeteorito[]
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
    private int vx = 1;
    private int vy = 1;
    private panel_meteorito panel;
    private Image meteorImage;
    private Random random = new Random();

    MoveMeteorito(panel_meteorito panel, Image meteorImage) {
        this.panel = panel;
        this.meteorImage = meteorImage;
        vx = random.nextInt(3) + 1; // ความเร็วในแกน X (สุ่ม)
        vy = random.nextInt(3) + 1; // ความเร็วในแกน Y (สุ่ม)
    }

    public void move() {
        // เคลื่อนที่วัตถุ
        x += vx;
        y += vy;

        // การตรวจสอบขอบเขตของจอ
        if (x >= panel.getWidth() - 50 || x <= 0) {
            vx = -vx; // เด้งในแกน X
        }
        if (y >= panel.getHeight() - 50 || y <= 0) {
            vy = -vy; // เด้งในแกน Y
        }
    }

    public void detectCollision(MoveMeteorito other) {
        Rectangle rect1 = new Rectangle(x, y, 50, 50);
        Rectangle rect2 = new Rectangle(other.getX(), other.getY(), 50, 50);

        if (rect1.intersects(rect2)) {
            // สลับทิศทางเมื่อชนกัน
            vx = -vx;
            vy = -vy;
            other.vx = -other.vx;
            other.vy = -other.vy;
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

