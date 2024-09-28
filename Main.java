
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        int amount_meteor = 2;
        //รับจำนวนอุกกาบาต
        if (args.length>0) {
            try {
                amount_meteor = Integer.parseInt(args[0]);
                System.out.println(amount_meteor);
            } catch (Exception e) {
                System.out.println(e+"\tit wrong input");
            }   
        }
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(450, 100);

        panel_meteorito mypanel = new panel_meteorito(amount_meteor);
        frame.add(mypanel);

        frame.setVisible(true);
    }
    
}