import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Graphics;
import java.util.Random;


public class panel_meteorito  extends JPanel {
    private RandomMeteorito randomMeteorito;
    public panel_meteorito () {
        randomMeteorito = new RandomMeteorito();
        setBackground(Color.BLACK);
        setLayout(null);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(randomMeteorito.randomImage, 0, 0,randomMeteorito.getNewWidth(),randomMeteorito.getNewHeight(),this);
        randomMeteorito.repaint();
    }
}

class RandomMeteorito extends JPanel {
    Image randomImage;
    private int newWidth = 50;
    private int newHeight = 50;
    public int getNewHeight() {
        return newHeight;
    }
    public int getNewWidth() {
        return newWidth;
    }
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