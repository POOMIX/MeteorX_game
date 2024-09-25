
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        int amount_meteor = 1;
        //รับจำนวนอุกกาบาต
        if (args.length>0) {
            amount_meteor = Integer.parseInt(args[0]);
            System.out.println(amount_meteor);
        }
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(450, 100);

        panel_meteorito mypanel = new panel_meteorito();
        frame.add(mypanel);

        frame.setVisible(true);
    }
    
}