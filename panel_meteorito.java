import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Graphics;
import java.util.Random;


public class panel_meteorito  extends JPanel {
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito moveMeteorito;
    public panel_meteorito () {
        randomMeteorito = new RandomMeteorito();
        moveMeteorito = new MoveMeteorito(this);

        setBackground(Color.BLACK);
        setLayout(null);

        moveMeteorito.start();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(randomMeteorito.randomImage, moveMeteorito.getX(), moveMeteorito.getY(),50,50,this);
        repaint();
    }
}

class RandomMeteorito extends JPanel {
    Image randomImage;
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
    public RandomMeteorito() {
        Random random = new Random();
        int index = random.nextInt(imageMeteorito.length);
        randomImage = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir")+File.separator+imageMeteorito[index]);
        if (randomImage != null) {
            setSize(randomImage.getWidth(null), randomImage.getHeight(null));
            setLocation(random.nextInt(800), -20);
        } else {
            System.err.println("Image not found: " + imageMeteorito[index]);
        }
        
        setOpaque(false);
    }
}
class MoveMeteorito extends Thread{
    private int x = 400;
    private int y = 300;
    private panel_meteorito panel;
    private Random random = new Random();
    private int randomdirection;
    MoveMeteorito(panel_meteorito panel){
        this.panel = panel;

        randomdirection = random.nextInt(8);
    }
    public void move(){
        if (randomdirection == 0) {//right
            x++;
        } else if (randomdirection == 1){//down
            y++;
        } else if(randomdirection == 2){//left
            x--;
        } else if(randomdirection == 3){//up
            y--;
        } else if (randomdirection == 4){//upright
            x++;
            y--;
        } else if(randomdirection == 5){//upleft
            x--;
            y--;
        } else if(randomdirection == 6){//downright
            x++;
            y++;
        } else if(randomdirection == 7){//downleft
            x--;
            y++;
        }

        if (x >= 800) {
            randomdirection = 2;
        } else if (y >= 600) {
            randomdirection = 3;
        } else if (x <= 0) {
            randomdirection = 0;
        } else if (y <= 0) {
            randomdirection = 1;
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
                Thread.sleep(1);
                move();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}