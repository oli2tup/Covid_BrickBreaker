package Assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CE203_1800579_Ass2 extends JPanel implements KeyListener {

    ArrayList<Block> blocks;        // Storing out blocks
    Thread thread;                  // stopping and starting
    Block paddle ;                  // the moving paddle at rhe bottom
    Ball ball ;                     // the 'ball' breaking the blocks
    int score;                      // keeping track of players score

    Score theScore;                 // score panel

    // for printing the facts
    CovidFacts facts;
    int it = 0;                     // this will increment in update(when a block is destroyed).The draw method in
                                    // CovidFacts will draw the string at the "it" index of ArrayList factsString
                                    // storing the different facts, so everytime a block is destroyed the next fact is shown

    // Covid Facts
    String a = "The virus that causes COVID-19 most commonly spreads between \n" +
            "people who are in close contact with one another (within about\n" +
            "6 feet, or 2 arm lengths).";
    String a1 = "It spreads through respiratory droplets or small particles,\n" +
            "such as those in aerosols, produced when an infected person coughs,\n" +
            "sneezes, sings, talks, or breathes.";
    String a2 ="A novel coronavirus is a new coronavirus that has not been previously\n" +
            "identified.";
    String a3 ="On February 11, 2020 the World Health Organization announced\n" +
            "an official name for the disease that is causing the 2019 novel \n" +
            "coronavirus outbreak, first identified in Wuhan China.";
    String a4 ="It is not yet known whether weather and temperature affect\n" +
            "the spread of COVID-19.";
    String a5 = "Community spread means people have been infected with the \n" +
            "virus in an area, including some who are not sure how or where\n" +
            "they became infected.";
    String a6 ="The main symptoms of coronavirus are: a high temperature, a new,\n" +
            "continuous cough, a loss or change to your sense of smell or taste";
    String a7 ="If you have any of the main symptoms of coronavirus: Get a test.\n" +
            "You and anyone you live with should stay at home.";
    String a8 ="If you have received a letter inviting you to book a coronavirus\n" +
            "vaccination, you can book it. Please do not try to book a vaccination\n" +
            "if you have not received a letter.";
    //storing the strings
    ArrayList<String> factsString = new ArrayList<>(Arrays.asList(a, a1, a2, a3, a4, a5, a6, a7, a8));

    // initial drawing of the blocks
    // initiate the blocks array and all items shown on the screen, will be called at the beginning and when
    // the ball falls off the screen
    void reset(){
        blocks = new ArrayList<>();
        paddle = new Block(175, 480, 150, 10, Color.BLUE);
        ball = new Ball(237, 435, 15, 15, "covid.png");
        theScore = new Score(score);                  // setting the score object

        // creating new different coloured blocks and adding them to the arraylist
        for (int i = 0; i < 8; i++)
            blocks.add(new Block((i * 60 + 2), 0, 60, 25, Color.BLUE));
        for (int i = 0; i < 8; i++)
            blocks.add(new Block((i * 60 + 2), 25, 60, 25, Color.PINK));
        for (int i = 0; i < 8; i++)
            blocks.add(new Block((i * 60 + 2), 50, 60, 25, Color.YELLOW));
        for (int i = 0; i < 8; i++)
            blocks.add(new Block((i * 60 + 2), 75, 60, 25, Color.GREEN));

        addKeyListener(this);
        setFocusable(true);
    }

    // constructor, calling reset()
    CE203_1800579_Ass2(){
        reset();
    }

    // drawing everything the user will see
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Block b : blocks){             // drawing the blocks
            b.draw(g, this);
        }
        paddle.draw(g, this);           // drawing the paddle
        ball.drawImage(g,this);         // drawing the ball

        theScore = new Score(score);
        theScore.drawString(g,this);    // drawing the score

        // drawing the Covid facts
        facts = new CovidFacts(factsString.get(it));
        facts.draw(g, this);
    }

    // updates what the user see's
    public void update() {
        ball.x += ball.movX;

        // moving the ball side to side
        if(ball.x > (getWidth() - 25) || ball.x < 0) {
            ball.movX *= -1;
        }
        // moving the ball up and down
        if(ball.y < 0 || ball.intersects(paddle)) {
            ball.movY *= -1;
        }

        ball.y += ball.movY;

        // if the ball out out of frame, reset the game and null the thread
        if(ball.y > getHeight()){
            try {
                thread.sleep(5 * 1000);
            } catch (InterruptedException ie) {
                thread.currentThread().interrupt();
            }
            thread = null;
            // System.out.println(score); // testing the score
            reset();
        }

        // if the blocks intersects with the paddle and a block
        // increment the score and destroy block
        blocks.forEach(block -> {
            if(ball.intersects(block) && !block.destroyed) {
                score += 5;
                ball.movY *=-1;
                block.destroyed = true;
                // making sure it doesn't go pas the length of factsString
                if(it!=9) {
                    it++;
                    if (it == 8)
                        it = 0;
                }
            }
        });
        // update what the user see's, repaint
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // listener, actions performed when a key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        // for the enter Key
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            thread = new Thread(() -> {
                while (true) {
                    update();               // calling the update method
                    //System.out.println("running"); // testing the key press
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException a) {
                        a.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        // moving to the right
        // stop the paddle from moving out of view
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x<(getWidth() - paddle.width)){
            update();
            paddle.x += 15;
        }
        //moving to the left
        // stop the paddle from moving out of view
        if(e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0){
            update();
            paddle.x -= 15;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    static class Block extends Rectangle {

        boolean destroyed;  // check if the block is destroyed
        Color cl;           // color of the blocks

        int movX, movY;

        // blocks, taking the position x,y ,the height, width and color
        // setting the values for the blocks
        Block(int x, int y, int w, int h, Color cl) {
            this.x = x;
            this.y = y;

            movX = 3;
            movY = 3;

            this.width = w;
            this.height = h;

            this.cl = cl;
        }

        //draw the block if not destroyed before drawing
        public void draw(Graphics g, Component c) {
            if (!destroyed) {
                g.setColor(cl);
                g.fillRect(x, y, width, height);
            }
        }
    }

    static class Score {

        int score;

        Score(int s){
            this.score = s;
        }

        // drawing the Score in the bottom right corner of the frame
        public void drawString(Graphics g, Component c){
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Score: "+score, 400, 550);
        }
    }

    static class CovidFacts {

        String facts;
        int y = 200;

        CovidFacts(String k) {
            this.facts = k;
        }

        // drawing the facts and splitting on the new line and printing the next line right below the previous
        public void draw(Graphics g, Component c) {
            for (String line : facts.split("\n")) {
                g.setColor(Color.red);
                g.setFont(new Font("serif", Font.BOLD, 15));
                g.drawString(line, 20, y += g.getFontMetrics().getHeight());
            }
        }
    }

    static class Ball extends Rectangle {

        int movX, movY;
        Image pic;

        // ball constructor taking the coordinates, size and string which is the file name
        Ball(int x, int y, int w, int h, String s) {
            this.x = x;
            this.y = y;

            movX = 3;
            movY = 3;

            this.width = w;
            this.height = h;

            try {
                pic = ImageIO.read(new File("src/Assignment2/" + s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //draw the Ball
        public void drawImage(Graphics g, Component c) {
            g.drawImage(pic, x, y, width, height, c);
        }
    }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Covid");

            CE203_1800579_Ass2 panel = new CE203_1800579_Ass2();

            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(500, 600);
            frame.setResizable(false);
        }
}
