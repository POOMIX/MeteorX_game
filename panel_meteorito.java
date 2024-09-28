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
    private Random random = new Random();
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
            if (moveMeteorito[i] == null) continue; // Skip if null
            for (int j = i + 1; j < moveMeteorito.length; j++) {
                if (moveMeteorito[j] == null) continue; // Skip if null
                if (moveMeteorito[i].getHitBox().intersects(moveMeteorito[j].getHitBox())) {
                    resolveCollision(moveMeteorito[i], moveMeteorito[j]);
                }
            }
        }
    }

    private void resolveCollision(MoveMeteorito meteor1, MoveMeteorito meteor2) {
        Rectangle hitBox1 = meteor1.getHitBox();
        Rectangle hitBox2 = meteor2.getHitBox();
    
        // Calculate the overlap
        int overlapX = Math.min(hitBox1.x + hitBox1.width, hitBox2.x + hitBox2.width) - Math.max(hitBox1.x, hitBox2.x);
        int overlapY = Math.min(hitBox1.y + hitBox1.height, hitBox2.y + hitBox2.height) - Math.max(hitBox1.y, hitBox2.y);
    
        // Adjust positions to resolve the overlap
        if (overlapX < overlapY) {
            if (hitBox1.x < hitBox2.x) {
                meteor1.setX(meteor1.getX() - overlapX); // Move meteor1 to the left
                meteor2.setX(meteor2.getX() + overlapX); // Move meteor2 to the right
                meteor1.handleCollision(); // Reverse direction
                meteor2.handleCollision(); // Reverse direction
            } else {
                meteor1.setX(meteor1.getX() + overlapX); // Move meteor1 to the right
                meteor2.setX(meteor2.getX() - overlapX); // Move meteor2 to the left
                meteor1.handleCollision(); // Reverse direction
                meteor2.handleCollision(); // Reverse direction
            }
        } else {
            if (hitBox1.y < hitBox2.y) {
                meteor1.setY(meteor1.getY() - overlapY); // Move meteor1 up
                meteor2.setY(meteor2.getY() + overlapY); // Move meteor2 down
                meteor1.handleCollision(); // Reverse direction
                meteor2.handleCollision(); // Reverse direction
            } else {
                meteor1.setY(meteor1.getY() + overlapY); // Move meteor1 down
                meteor2.setY(meteor2.getY() - overlapY); // Move meteor2 up
                meteor1.handleCollision(); // Reverse direction
                meteor2.handleCollision(); // Reverse direction
            }
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
    private int x ;
    private int y;
    private panel_meteorito panel;
    private Random random = new Random();
    private int randomdirection;
    private int speed;
    MoveMeteorito(panel_meteorito panel) {
        this.panel = panel;
        // ตั้งค่า x และ y ให้อยู่ที่กลางจอ
        this.x = panel.getWidth() / 2 - 25; // 50 เป็นขนาดของอุกกาบาต
        this.y = panel.getHeight() / 2 - 25; // 50 เป็นขนาดของอุกกาบาต
        randomdirection = random.nextInt(8);
        speed = random.nextInt(10) + 1;
    }

    public void move() {
            if (randomdirection == 0) { // right
                x = x + 1;
            } 
            else if (randomdirection == 1) { // down
                y =  y + 1;
            } 
            else if (randomdirection == 2) { // left
                x =  x - 1;
            } 
            else if (randomdirection == 3) { // up
                y =  y - 1;
            } 
            else if (randomdirection == 4) { // upright
                x =  x+ 1;
                y =  y - 1;
            } 
            else if (randomdirection == 5) { // upleft
                x = x - 1;
                y =  y - 1;
            } 
            else if (randomdirection == 6) { // downright
                x = x + 1;
                y = y+ 1;
            } 
            else if (randomdirection == 7) { // downleft
                x = x - 1;
                y = y + 1;
            }

        // ตรวจสอบการชนขอบหน้าจอ
        if (x < 0) {
            x = 0; // ขอบซ้าย
            randomdirection = random.nextInt(8); // เปลี่ยนทิศทางไปทางขวา
            speed = random.nextInt(10) + 1;
        } else if (x > (panel.getWidth() - 50)) {
            x = panel.getWidth() - 50; // ขอบขวา
            randomdirection = random.nextInt(8); // เปลี่ยนทิศทางไปทางซ้าย
            speed = random.nextInt(10) + 1;
        }

        if (y < 0) {
            y = 0; // ขอบบน
            randomdirection = random.nextInt(8); // เปลี่ยนทิศทางไปทางลง
            speed = random.nextInt(10) + 1;
        } else if (y > (panel.getHeight() - 50)) {
            y = panel.getHeight() - 50; // ขอบล่าง
            randomdirection = random.nextInt(8); // เปลี่ยนทิศทางไปทางขึ้น
             speed = random.nextInt(10) + 1;
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
                Thread.sleep(speed); // เปลี่ยนความเร็วในการเคลื่อนที่
                move();
                System.out.println(speed);
                panel.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCollision() {
        // จัดการกับการชนกัน เช่น การหยุดการเคลื่อนที่
        randomdirection = random.nextInt(8); // เปลี่ยนทิศทางการเคลื่อนที่
        speed = random.nextInt(10) + 1;
        }
    }
}