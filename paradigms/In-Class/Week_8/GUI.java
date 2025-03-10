import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Frame Tab");
        frame.setSize(300, 100);
        frame.setVisible(true);

        frame.getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton button = new JButton("Swing Button");
        frame.getContentPane().add(button);
        frame.validate();
    }
}
