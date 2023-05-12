package com.hieu.userinterface;

import com.hieu.effect.Animation;
import com.hieu.effect.FrameImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private boolean isRunning;
    private InputManager inputManager;
    FrameImage frame1, frame2, frame3;
    Animation animation;
//    BufferedImage image;//  ( an object contains picture )
//    BufferedImage subImage;
    public GamePanel() {
        inputManager = new InputManager();
//        try {
//            image = ImageIO.read(new File("data/megasprite.png"));// the funtion to return picture from file
//            subImage = image.getSubimage(2,5,30,100);// return to 1 image theo y cua designer
//        }catch (Exception e){
//            e.printStackTrace(); // try catch de tra ve 1 ngoai le phong khi file megasprite ko ton tai no se tra ve 1 cai j do ( return an exception if file does not exist)
//        }
        try {
            BufferedImage image = ImageIO.read(new File("data/megasprite.png"));
            BufferedImage image1 = image.getSubimage(529,38,100,100);
            frame1 = new FrameImage("frame1",image1);

            BufferedImage image2 = image.getSubimage(616,38,100,100);
            frame2 = new FrameImage("frame2",image2);

            BufferedImage image3 = image.getSubimage(704,38,100,100);
            frame3 = new FrameImage("frame3",image3);

            animation = new Animation();
            animation.add(frame1,200*1000000);//milisecond (nhan 1trieu vi tinh theo nano s) ( nano s hon 1 tr lan so vs milis)
            animation.add(frame2,200*1000000);
            animation.add(frame3,200*1000000);
        }catch (Exception e){
            e.printStackTrace(); // try catch to return an exception if file megasprite dose not exist and it will return error statement
        }
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//        g.drawImage(subImage,10,10,this);
        Graphics2D graphics2D = (Graphics2D) g;
        animation.Update(System.nanoTime());
        animation.draw(100,130,graphics2D);
    }
    public void startGame(){
        if (thread == null){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public void run() {
        // frame per second
        long FPS = 80;
        long period = 1000*1000000/80;// nano second units ( 1 billions )
        long beginTime;
        // time to sleep = T - time ( update and render )
        long sleepTime;

        int a = 1; // test 

        beginTime = System.nanoTime(); // get System time ( real time ) and change to nano time
        while (isRunning){
//            System.out.println(" " + (a++));
            //Update game to limit the speed of game and avoid lag game
            //render game
            repaint();

            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime; // need to change to millisecond

            try{
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime/1000000);// change from nano to millisecond
                }else {
                    Thread.sleep(period/2000000);
                }
            }catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            } ;

            beginTime = System.nanoTime();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e){ // callback method
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }
}
