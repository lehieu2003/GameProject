package com.hieu.effect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameImage {
    private String name;
    private BufferedImage image;
    public FrameImage(String name,BufferedImage image ){
        this.name = name;
        this.image = image;
    }
    // creat a copy constructor but different parameter passed in 
    public FrameImage(FrameImage frameImage){
        image = new BufferedImage(frameImage.getImageWidth(),frameImage.getImageHeight(),frameImage.getImage().getType());
        Graphics g = image.getGraphics();
        g.drawImage(frameImage.getImage(),0,0,null);
    }
    FrameImage(){
        image = null;
        name = null;
    }
    public void draw(Graphics2D graphics2D,int x, int y){
        graphics2D.drawImage(image,x-image.getWidth()/2, y-image.getHeight()/2,null);
    }
    public int getImageWidth(){
        return image.getWidth();
    }
    public int getImageHeight(){
        return image.getHeight();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
