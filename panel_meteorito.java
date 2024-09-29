import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
public class panel_meteorito extends JPanel {
    private int amount_meteor;
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito[] moveMeteorito;
    private Random random = new Random();

    private Image imageshow; // รูปภาพระเบิด
    private Timer timer;//ตั้งให้รูปหาย
    private int showX; // ตำแหน่งที่คลิก
    private int showY;
    private int startX = 350; // ตำแหน่งที่คลิก
    private int startY = 300;
    int count = 0;
    public panel_meteorito(int amount_meteor) {
        this.amount_meteor = amount_meteor;
        randomMeteorito = new RandomMeteorito(amount_meteor);
        moveMeteorito = new MoveMeteorito[amount_meteor];
        setBackground(Color.BLACK);
        setLayout(null);

        

        for (int i = 0; i < moveMeteorito.length; i++) {
            moveMeteorito[i] = new MoveMeteorito(this,startX,startY);
            moveMeteorito[i].start();
            
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int j = 0; j < moveMeteorito.length; j++) {
                    if (moveMeteorito[j] != null) {
                        int x = moveMeteorito[j].getX();
                        int y = moveMeteorito[j].getY();
                        if (e.getX() >= x && e.getX() <= x + 100 && e.getY() >= y && e.getY() <= y + 100) {
                            count++; 
                            if (count % 2 == 0) {
                                moveMeteorito[j] = null; 
                                showX = e.getX(); 
                                showY = e.getY();
                                imageshow = Toolkit.getDefaultToolkit().createImage("images\\bomb.gif");
                                timer = new Timer(1000, e1 -> {
                                    imageshow = null;
                                    repaint(); 
                                });
                                timer.start(); 
                                repaint(); 
                            }
                            System.out.println("Click count: " + count); 
                            break; 
                        }
                    }
                }
            }
        });
        
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < moveMeteorito.length; i++) {
            if(moveMeteorito[i] != null){ 
                g.drawImage(randomMeteorito.randomImage[i], moveMeteorito[i].getX(), moveMeteorito[i].getY(), 50, 50, this);
            }
        }
        if (imageshow != null) {
            g.drawImage(imageshow, showX, showY, 50, 50, this); // ขนาดของรูปใหม่ที่แสดง
        }
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
                meteor1.setX(meteor1.getX() - overlapX); // ขยับ meteor1 ไปซ้าย
                meteor2.setX(meteor2.getX() + overlapX); // ขยับ meteor2 ไปขวา
                meteor1.handleCollision(); // เปลี่ยนทาง
                meteor2.handleCollision(); // เปลี่ยนทาง
            } else {
                meteor1.setX(meteor1.getX() + overlapX); //  ขยับ meteor1 ไปขวา
                meteor2.setX(meteor2.getX() - overlapX); //  ขยับ meteor2 ไปซ้าย
                meteor1.handleCollision(); 
                meteor2.handleCollision(); 
            }
        } else {
            if (hitBox1.y < hitBox2.y) {
                meteor1.setY(meteor1.getY() - overlapY); //  ขยับ meteor1 ขึ้น
                meteor2.setY(meteor2.getY() + overlapY); //  ขยับ meteor2 ลง
                meteor1.handleCollision(); 
                meteor2.handleCollision(); 
            } else {
                meteor1.setY(meteor1.getY() + overlapY); //  ขยับ meteor1 ลง
                meteor2.setY(meteor2.getY() - overlapY); //  ขยับ meteor2 ขึ้น
                meteor1.handleCollision(); 
                meteor2.handleCollision(); 
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
    private int x = 0;
    private int y = 0;
    private panel_meteorito panel;
    private Random random = new Random();
    private int randomdirection;
    private int speed;  
    private int[] random257 = {2, 5, 7};// สุ่มทิศทางใหม่เป็นซ้าย, ซ้ายบน, หรือซ้ายล่าง
    private int[] random046 = {0, 4, 6};// สุ่มทิศทางใหม่เป็นขวา, ขวาบน, หรือขวาล่าง
    private int[] random345 = {3, 4, 5};// สุ่มทิศทางใหม่เป็นขึ้น, ขึ้นขวา, หรือขึ้นซ้าย
    private int[] random176 = {1, 7, 6};// สุ่มทิศทางใหม่เป็นลง, ลงซ้าย, หรือลงขวา

    MoveMeteorito(panel_meteorito panel, int startX, int startY) {
        this.panel = panel;
        this.x = startX; 
        this.y = startY;
        randomdirection = random.nextInt(8);  
        speed = random.nextInt(10) + 1;  
    }

    public void move() {
        if (randomdirection == 0) {  // ขวา
            x = x + 1;
        } else if (randomdirection == 1) {  // ลงงง
            y = y + 1;
        } else if (randomdirection == 2) {  // ซ้าย
            x = x - 1;
        } else if (randomdirection == 3) {  // ขึ้นบน
            y = y - 1;
        } else if (randomdirection == 4) {  // ขึ้นชวา
            x = x + 1;
            y = y - 1;
        } else if (randomdirection == 5) {  // ขึ้นซ้าย
            x = x - 1;
            y = y - 1;
        } else if (randomdirection == 6) {  // ลงขวา
            x = x + 1;
            y = y + 1;
        } else if (randomdirection == 7) {  //ลงซ้าย
            x = x - 1;
            y = y + 1;
        }

        // Handle boundary conditions
        if (x >= panel.getWidth() - 50) {
            if (randomdirection == 0 || randomdirection == 4 || randomdirection == 6) {
                randomdirection = random257[random.nextInt(random257.length)];  
                speed = random.nextInt(10) + 1;  
                //x = panel.getWidth()-50;
            }
        } else if (x <= 0) {
            if (randomdirection == 2 || randomdirection == 5 || randomdirection == 7) {
                randomdirection = random046[random.nextInt(random046.length)]; 
                speed = random.nextInt(10) + 1;  
                //x = 0;
            }
        }

        if (y >= panel.getHeight() - 50) {
            if (randomdirection == 1 || randomdirection == 7 || randomdirection == 6) {
                randomdirection = random345[random.nextInt(random345.length)];  
                speed = random.nextInt(10) + 1;  
                //y = panel.getHeight() - 50;
            }
        } else if (y <= 0) {
            if (randomdirection == 3 || randomdirection == 4 || randomdirection == 5) {
                randomdirection = random176[random.nextInt(random176.length)];  
                speed = random.nextInt(10) + 1;  
                //y = 0;
            }
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
        return new Rectangle(x, y, 50, 50);
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
                Thread.sleep(speed);
                move();
                panel.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCollision() {
        if (randomdirection == 0 || randomdirection == 4 || randomdirection == 6) {
            randomdirection = random257[random.nextInt(random257.length)];  
        } else if (randomdirection == 2 || randomdirection == 5 || randomdirection == 7) {
            randomdirection = random046[random.nextInt(random046.length)]; 
        }

        if (randomdirection == 1) {
            randomdirection = 3;  
        } else if (randomdirection == 3) {
            randomdirection = 1; 
        }
        
        speed = random.nextInt(10) + 1;  
    }
}
}