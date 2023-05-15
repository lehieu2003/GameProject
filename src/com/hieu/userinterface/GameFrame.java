package com.hieu.userinterface;

import com.hieu.effect.CacheDataLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame {
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;

    GamePanel gamePanel;
    public GameFrame(){

        super("Mega Man");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = this.getToolkit(); // use toolkit to get size of screen
        Dimension dimension = toolkit.getScreenSize();

        try {
            CacheDataLoader.getInstance().LoadData();
        }catch (IOException e){
            e.printStackTrace();
        }

        this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);

        gamePanel = new GamePanel();
        addKeyListener(gamePanel);
        add(gamePanel);

        this.addKeyListener(gamePanel);
    }
    public void startGame(){
        gamePanel.startGame();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame();
    }
}
