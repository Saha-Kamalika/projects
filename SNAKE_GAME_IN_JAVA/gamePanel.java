import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener{
    Toolkit tk;
    static final int screenWidth=  1000;
    static final int screenHeight= 1000;
    static final int objectSize= 50;
    static final int objects=(screenWidth*screenHeight)/objectSize;  //calculates the no of objects that can be accommodated
    static final int delay=75;
    final int x[]=new int[objects]; //holds x coordinates of the snake body
    final int y[]=new int[objects]; ////holds y coordinates of the snake body
    int bodyparts=6;
    int foodCount;   //counts the no of apples eaten
    int foodX;  //stores the x coordinate of the apples...changes randomly
    int foodY; //stores the y coordinate of the apples
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;
    gamePanel(){
        tk = Toolkit.getDefaultToolkit();
        random=new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }
    public void startGame(){
        newFood(); //creates new apple on the screen
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            //this.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            /*for (int i = 0; i < screenHeight / objectSize; i++) {   //creates a matrix grid for better visualization
                g.drawLine(i * objectSize, 0, i * objectSize, screenHeight);
                g.drawLine(0, i * objectSize, screenWidth, i * objectSize);
            }*/
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillOval(foodX, foodY, objectSize, objectSize); //apples occupies only a single grid space

            for (int i = 0; i < bodyparts; i++) {
                //draws the snake
                if (i == 0) {
                    g.setColor(Color.white);
                    g.fillRect(x[i], y[i], objectSize, objectSize);
                } else {
                    //g.setColor(Color.darkGray);
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], objectSize, objectSize);
                }
            }
            // Shows the score at the top of the screen
            g.setColor(Color.red);
            g.setFont(new Font("Uni Sans", Font.BOLD,40));
            FontMetrics m=getFontMetrics(g.getFont()); //lines text at the center
            g.drawString("Your Score: "+foodCount, (screenWidth - m.stringWidth("Your Score: "+foodCount))/2,g.getFont().getSize());
        }
        else gameOver(g);
    }
    public void newFood(){  //generates new coordinate of the new apple
        foodX=random.nextInt(screenWidth/objectSize)*objectSize;
        foodY=random.nextInt(screenHeight/objectSize)*objectSize;
    }
    public void move(){
        for(int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-objectSize;
                break;
            case 'D':
                y[0]=y[0]+objectSize;
                break;
            case 'L':
                x[0]=x[0]-objectSize;
                break;
            case 'R':
                x[0]=x[0]+objectSize;
                break;
        }
    }
    public void checkFood(){
        if((x[0]==foodX) && (y[0]==foodY)){
            tk.beep();
            bodyparts++;
            foodCount++;
            newFood();
        }
    }
    public void collisions(){
        for(int i=bodyparts;i>0;i--){  //if head collides with body
           if((x[0]==x[i]) && (y[0]==y[i])){
               running=false; //gameOver
           }
        }
        if(x[0]<0)// when head touches left border of the window
            running=false;
        if(x[0]>screenWidth)// when head touches right border of the window
            running=false;
        if(y[0]<0)// when head touches top border of the window
            running=false;
        if(y[0]>screenHeight)// when head touches bottom border of the window
            running=false;
        if(!running)
            timer.stop();
    }
    public void gameOver(Graphics g){
        this.setBackground(Color.black);
        g.setColor(Color.red);
        g.setFont(new Font("Uni Sans", Font.BOLD,40));
        FontMetrics m1=getFontMetrics(g.getFont()); //lines text at the center
        g.drawString("Your Score: "+foodCount, (screenWidth - m1.stringWidth("Your Score: "+foodCount))/2,g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,100));
        FontMetrics m2=getFontMetrics(g.getFont()); //lines text at the center
        g.drawString("Game Over", (screenWidth - m2.stringWidth("Game Over"))/2,screenHeight/2);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkFood();
            collisions();
        }
        repaint();
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
