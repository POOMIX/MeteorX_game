import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Graphics;
import java.util.Random;


public class panel_meteorito  extends JPanel {
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito[] moveMeteorito = new MoveMeteorito[5];;
    public panel_meteorito () {
        randomMeteorito = new RandomMeteorito();

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
            g.drawImage(randomMeteorito.randomImage[i], moveMeteorito[i].getX(), moveMeteorito[i].getY(),50,50,this);
            repaint();
        }
    }
}

class RandomMeteorito extends JPanel {
    Image randomImage[] = new Image[5];;
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
        for (int i = 0; i < randomImage.length; i++) {
            int index = random.nextInt(imageMeteorito.length);
            randomImage[i] = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir")+File.separator+imageMeteorito[index]);
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

        if (x >= panel.getWidth()-50) {
            if (randomdirection == 0) randomdirection = 2; // From right to left
            else if (randomdirection == 4) randomdirection = 5; // From upright to upleft
            else if (randomdirection == 6) randomdirection = 7; // From downright to downleft
        } else if (x <= 0) {
            if (randomdirection == 2) randomdirection = 0; // From left to right
            else if (randomdirection == 5) randomdirection = 4; // From upleft to upright
            else if (randomdirection == 7) randomdirection = 6; // From downleft to downright
        }
    
        if (y >= panel.getHeight()-50) {
            if (randomdirection == 1) randomdirection = 3; // From down to up
            else if (randomdirection == 6) randomdirection = 5; // From downright to upleft
            else if (randomdirection == 7) randomdirection = 4; // From downleft to upright
        } else if (y <= 0) {
            if (randomdirection == 3) randomdirection = 1; // From up to down
            else if (randomdirection == 4) randomdirection = 7; // From upright to downleft
            else if (randomdirection == 5) randomdirection = 6; // From upleft to downright
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
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}