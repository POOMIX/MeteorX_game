import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;
import javax.swing.JPanel;


public class panel_meteorito  extends JPanel {
    private int amount_meteor;
    private RandomMeteorito randomMeteorito;
    private MoveMeteorito[] moveMeteorito;
    public panel_meteorito (int amount_meteor) {
        this.amount_meteor = amount_meteor;
        randomMeteorito = new RandomMeteorito(amount_meteor);
        moveMeteorito  = new MoveMeteorito[amount_meteor];
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
    Image randomImage[];
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
        randomImage  = new Image[amount_meteor];
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

        if (x >= 730) {
            randomdirection = 2;
        } else if (y >= 530) {
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
    public void Detection(){

    }
}