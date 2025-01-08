import java.awt.*;
import java.awt.event.*;
import java.nio.channels.Pipe;
import java.util.*;

import javax.management.timer.Timer;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener,KeyListener{

    private static final ActionEvent ActionEvent = null;
    int bordwidth = 360;
    int bordlength = 640;
    int birdx = bordwidth/8;
    int birdy = bordlength/2;
    int pipex = bordwidth;
    int pipey = 0;
    int pipewidth = 64;
    int pipelength = 512;
    Image back;
    Image bird;
    Image toppipe;
    Image bottom;
    javax.swing.Timer gameloop;
    javax.swing.Timer pipetimer;
    ArrayList<pipe> pipes;
    Random random = new Random();
    boolean gameover = false;
    double score;
    class pipe{
        int x = pipex;
        int y = pipey;
        int weidth = pipewidth;
        int length = pipelength;
        Image img;
        boolean passed = false;
        pipe(Image img){
            this.img = img;
        }
    }
    FlappyBird(){
        setPreferredSize(new Dimension(bordwidth,bordlength));
        setBackground(Color.GREEN);
        setFocusable(true);
        addKeyListener(this);
        back = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        bird = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottom = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        gameloop = new javax.swing.Timer(1000/60, this);
        gameloop.start();
        pipes = new ArrayList<pipe>();
        pipetimer = new javax.swing.Timer(1500,new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                placepipe();
            }
        });
        pipetimer.start();
    }
    public void placepipe(){
        pipe p = new pipe(toppipe);
        pipe q = new pipe(bottom);
        pipes.add(q);
        pipes.add(p);
        p.y = (int)(p.y-p.length/4-Math.random()*(p.length/2));
        q.y= p.y + 650;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }  
    public class Bird {
        int x = birdx;
        int y = birdy;
        int weidth = 34;
        int height = 24;
        
    }
    public void draw(Graphics g){
        g.drawImage(back, 0, 0, bordwidth,bordlength,null);
        g.drawImage(bird, b.x, b.y, 34, 24, null);
        for(int i=0;i<pipes.size();i++){
            pipe pp= pipes.get(i);
            g.drawImage(pp.img,pp.x, pp.y, pp.weidth, pp.length, null);
        }
        if(gameover){
            g.setColor(Color.RED);
            g.setFont(new Font("Wide Latin",Font.PLAIN,20));
            g.drawString("GAME OVER", 70, bordlength/2);
            g.setFont(new Font("Wide Latin",Font.PLAIN,15));
            g.drawString("Score : "+String.valueOf((int)score), 128, 350);
            g.setColor(Color.black);
            g.setFont(new Font("Arial Narrow",Font.PLAIN,20));
            g.drawString("Press Space to Restart", 90, 450);
        }
        else{
            g.setColor(Color.RED);
            g.setFont(new Font("Cooper Black",Font.PLAIN,20));
            g.drawString("Score : "+String.valueOf((int)score), 10, 40);
        }
    }
    public boolean collision(Bird a,pipe b){
        return a.x < b.x + b.weidth &&
               a.x + a.weidth > b.x &&
               a.y < b.y + b.length &&
               a.y + a.height > b.y;
    }
    Bird b = new Bird();
    int x = b.y;
    public void move(){
        b.y = x;
        b.y = Math.max(b.y, 0);
        for(int i=0;i<pipes.size();i++){
            pipe pp= pipes.get(i);
            pp.x -= 4;
            if (collision(b, pp)){
                    gameover = true;
            }
            if(!pp.passed && b.x > pp.x + pp.weidth){
                pp.passed = true;
                score += 0.5;
            }
        }
        if(b.y > bordlength){
            gameover = true;
        }
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){
            x=b.y-30;
        }
        if(e.getKeyCode()== KeyEvent.VK_DOWN){
            x = b.y + 30;
        }
        if(e.getKeyCode()== KeyEvent.VK_SPACE){
            if(gameover){
                b.y = birdy;
                pipes.clear();
                score = 0;
                gameover = false;
                gameloop.start();
                pipetimer.start();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover==true){
            gameloop.stop();
            pipetimer.stop();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}