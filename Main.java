
import java.util.Scanner;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        try {
            int amount_meteor;
            Scanner scan = new Scanner(System.in);
            //รับจำนวนอุกกาบาต
            System.out.print("Enter the number of meteor = ");
            amount_meteor = scan.nextInt();
            JFrame frame = new JFrame();
            frame.setSize(800,600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(450, 100);
    
            panel_meteorito mypanel = new panel_meteorito(amount_meteor);
            frame.add(mypanel);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error = "+e);
        }
       
    }
    
}