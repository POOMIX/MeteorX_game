import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.util.Random;


public class panel_meteorito  extends JPanel {
    public panel_meteorito () {
        setSize(20, 20);
        setBackground(Color.BLACK);
    }
}

class RandomMeteorito extends JPanel {
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
    
    Image randomImage;

    public RandomMeteorito() {
        Random random = new Random();
        int index = random.nextInt(imageMeteorito.length);
        randomImage = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(imageMeteorito[index]));

        if (randomImage != null) {
            setSize(randomImage.getWidth(null), randomImage.getHeight(null));
            setLocation(random.nextInt(800), -20);
        } else {
            System.err.println("Image not found: " + imageMeteorito[index]);
        }
        
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (randomImage != null) {
            g.drawImage(randomImage, 0, 0, this);
        }
    }
}