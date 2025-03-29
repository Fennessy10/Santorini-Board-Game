import javax.swing.*;

public class PatrickMain {
    public static void main(String[] args) {
        System.out.println("print me!");

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Patrick Fennessy");

        window.setLocationRelativeTo(null); // window at center of screen
        window.setVisible(true);

    }
}
