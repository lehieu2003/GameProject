package com.hieu.userinterface;

import com.hieu.state.State;
import com.hieu.state.GameWorldState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    State gameState;
    private Thread gameThread;
    private boolean isRunning;
    private InputManager inputManager;

    public GamePanel() {
        gameState = new GameWorldState(this);
        inputManager = new InputManager(gameState);

    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    int a = 0;
    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;

        long period = 1000000000/80;

        while(isRunning){

            gameState.Update();
            gameState.Render();


            repaint();

            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try{

                    if(sleepTime > 0)
                            Thread.sleep(sleepTime/1000000);
                    else Thread.sleep(period/2000000);

            }catch(Exception e){}

            previousTime = System.nanoTime();
        }
    }

    public void paint(Graphics g){
        g.drawImage(gameState.getBufferedImage(), 0, 0, this);

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){ // callback method
        inputManager.setPressedButton(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.setReleasedButton(e.getKeyCode());
    }

    public void setState(State state) {
        gameState = state;
        inputManager.setState(state);
    }
}
