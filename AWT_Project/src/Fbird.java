import javax.swing.*;

public class Fbird{
    public static void main(String[] args){
        int bordwidth = 360;
        int bordlength = 640;
        JFrame f = new JFrame();
        f.setTitle("Flappy Bird");
        f.setSize(bordwidth,bordlength);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBird fl = new FlappyBird();
        f.add(fl);
        f.pack();
        f.requestFocus();
        f.setVisible(true);
    }
}