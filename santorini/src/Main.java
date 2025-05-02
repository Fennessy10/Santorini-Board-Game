import com.santorini.GameController;
import com.santorini.GamePanelFrame;
import com.santorini.WelcomeFrame;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome");
            frame.setContentPane(new WelcomeFrame(frame).getPanel1());
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setSize(1000, 1000);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Center on screen
        });

    }
}