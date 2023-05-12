package com.hieu.effect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

public class CacheDataLoader { // follow Design pattern singleton style to avoid creating many instance for cache data
    private static CacheDataLoader instance = null;
    private String frameFile = "data/frame.txt";
    private String animationFile = "data/animation.txt";
    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String,Animation> animations;

    public CacheDataLoader(){

    }
    public static CacheDataLoader getInstance(){
        // each time call this function only get an instance
        if (instance == null){
            instance = new CacheDataLoader();
        }
        return instance;
    }
    public void LoadData() throws IOException{
        LoadFrame();
        LoadAnimation();
    }
    public void LoadFrame() throws IOException{
        frameImages = new Hashtable<String,FrameImage>();

        FileReader fr = new FileReader(frameFile); // after read file it will return a thread data
        BufferedReader br = new BufferedReader(fr);// then we base on data thread to read it

        String line = null;

        if (br.readLine() == null){
            System.out.println("no data");
            throw new IOException();
        }else {
            // 2 lines to move pointer to the head file and read file
            fr = new FileReader(frameFile);
            br = new BufferedReader(fr);

            while((line = br.readLine()).equals(" "));

            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++){
                FrameImage frameImage = new FrameImage();
                while ((line = br.readLine()).equals(""));
                frameImage.setName(line);

                while ((line = br.readLine()).equals(""));
                String[] str = line.split(" "); // characters separated by " " will be added to the array
                String path = str[1];

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array
                int x = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array 
                int y = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array
                int w = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array
                int h = Integer.parseInt(str[1]);

                BufferedImage imageData = ImageIO.read(new File(path));
                BufferedImage image = imageData.getSubimage(x,y,w,h);
                frameImage.setImage(image);

                instance.frameImages.put(frameImage.getName(),frameImage);
            }
        }
        br.close();
    }

    public FrameImage getFrameImage(String name){
        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }
    public void LoadAnimation() throws IOException{
        animations =  new Hashtable<String,Animation>();

        FileReader fr = new FileReader(animationFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null){
            System.out.println("no data");
            throw new IOException();
        }
        else {
            fr = new FileReader(animationFile);
            br = new BufferedReader(fr);

            while ((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++){
                Animation animation = new Animation();

                while ((line = br.readLine()).equals(""));
                animation.setName(line);

                while ((line = br.readLine()).equals(""));
                String[] str = line.split(" ");

                for (int j = 0; j < str.length; j += 2){
                    animation.add(getFrameImage(str[j]),Double.parseDouble(str[j+1]));
                }
                instance.animations.put(animation.getName(),animation);
            }
        }
    }
    public Animation getAnimation(String name){
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }
}
