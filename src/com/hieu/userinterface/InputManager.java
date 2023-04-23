package com.hieu.userinterface;
import java.awt.event.KeyEvent;

public class InputManager  {
    // nhan phim
    public void processKeyPressed(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP:
                System.out.println("you press up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("you press down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("you press left");
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_ENTER:
                break;
            case KeyEvent.VK_A:
                break;
        }
    }
    // release phim
    public void processKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("you released up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("you released down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("you released left");
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_ENTER:
                break;
            case KeyEvent.VK_A:
                break;
        }
    }
}
