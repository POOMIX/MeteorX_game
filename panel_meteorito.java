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
            moveMeteorito[i] = new MoveMeteorito(this);
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

    public void checkCollisions() {
        for (int i = 0; i < moveMeteorito.length; i++) {
            for (int j = i + 1; j < moveMeteorito.length; j++) {
                if (moveMeteorito[i].getHitBox().intersects(moveMeteorito[j].getHitBox())) {
                    resolveCollision(moveMeteorito[i], moveMeteorito[j]);
                }
            }
        }
    }

    private void resolveCollision(MoveMeteorito meteor1, MoveMeteorito meteor2) {
        // ปรับตำแหน่งของอุกกาบาตเมื่อชนกัน
        Rectangle hitBox1 = meteor1.getHitBox();
        Rectangle hitBox2 = meteor2.getHitBox();

        int overlapX = Math.min(hitBox1.x + hitBox1.width, hitBox2.x + hitBox2.width) - Math.max(hitBox1.x, hitBox2.x);
        int overlapY = Math.min(hitBox1.y + hitBox1.height, hitBox2.y + hitBox2.height) - Math.max(hitBox1.y, hitBox2.y);

        if (overlapX < overlapY) {
            if (hitBox1.x < hitBox2.x) {
                meteor1.setX(meteor1.getX() - overlapX); // เลื่อน meteor1 ไปทางซ้าย
            } else {
                meteor1.setX(meteor1.getX() + overlapX); // เลื่อน meteor1 ไปทางขวา
            }
        } else {
            if (hitBox1.y < hitBox2.y) {
                meteor1.setY(meteor1.getY() - overlapY); // เลื่อน meteor1 ขึ้น
            } else {
                meteor1.setY(meteor1.getY() + overlapY); // เลื่อน meteor1 ลง
            }
        }

        // เปลี่ยนทิศทาง
        meteor1.handleCollision();
        meteor2.handleCollision();
    }
}

class RandomMeteorito extends JPanel {
    Image[] randomImage;
    String[] imageMeteorito = {
        "images/1.png",
        "images/2.png",
        "images/3.png",
        "images/4.png",
        "images/5.png",
        "images/6.png",
        "images/7.png",
        "images/8.png",
        "images/9.png",
        "images/10.png"
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
    private panel_meteorito panel;
    private Random random = new Random();
    private int randomdirection;
    private int speed;
    MoveMeteorito(panel_meteorito panel) {
        this.panel = panel;
        randomdirection = random.nextInt(8);
        speed = random.nextInt(3) + 1;
    }

    public void move() {
        switch (randomdirection) {
            case 0: x += speed; break; // right
            case 1: y += speed; break; // down
            case 2: x -= speed; break; // left
            case 3: y -= speed; break; // up
            case 4: x += speed; y -= speed; break; // upright
            case 5: x -= speed; y -= speed; break; // upleft
            case 6: x += speed; y += speed; break; // downright
            case 7: x -= speed; y += speed; break; // downleft
        }

        // ตรวจสอบการชนขอบหน้าจอ
        if (x < 0) {
            x = 0; // ขอบซ้าย
            randomdirection = 0; // เปลี่ยนทิศทางไปทางขวา
        } else if (x > (panel.getWidth() - 50)) {
            x = panel.getWidth() - 50; // ขอบขวา
            randomdirection = 2; // เปลี่ยนทิศทางไปทางซ้าย
        }

        if (y < 0) {
            y = 0; // ขอบบน
            randomdirection = 1; // เปลี่ยนทิศทางไปทางลง
        } else if (y > (panel.getHeight() - 50)) {
            y = panel.getHeight() - 50; // ขอบล่าง
            randomdirection = 3; // เปลี่ยนทิศทางไปทางขึ้น
        }


        // ตรวจสอบการชนกัน
        panel.checkCollisions();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, 50, 50); // ขนาด Hit Box เท่ากับขนาดของอุกกาบาต
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10); // เปลี่ยนความเร็วในการเคลื่อนที่
                move();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCollision() {
        // จัดการกับการชนกัน เช่น การหยุดการเคลื่อนที่
        randomdirection = (randomdirection + 4) % 8; // เปลี่ยนทิศทางการเคลื่อนที่
    }
}
