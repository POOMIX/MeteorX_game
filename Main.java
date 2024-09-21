
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(450, 100);

        panel_meteorito mypanel = new panel_meteorito();
        frame.add(mypanel);

        frame.setVisible(true);
    }
    
}