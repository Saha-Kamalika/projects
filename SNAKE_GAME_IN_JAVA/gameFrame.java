import javax.swing.JFrame;
public class gameFrame extends JFrame{
    gameFrame(){
        this.add(new gamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); //this function packs all the components we add to the frame.
        this.setVisible(true);
        this.setLocationRelativeTo(null); //the game window appears at the middle of your screen.
    }
}
