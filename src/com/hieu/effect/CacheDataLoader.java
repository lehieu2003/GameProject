package com.hieu.effect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.Hashtable;

public class CacheDataLoader { // theo kieu viet Design pattern singleton avoid creating many instance for cache data
    private static CacheDataLoader instance = null;
    private String frameFile = "data/frame.txt";
    private String animationFile = "data/animation.txt";
    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String,Animation> animations;

    public CacheDataLoader(){

    }
    public static CacheDataLoader getInstance(){
        // each time cal this function only get an instance
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

        FileReader fr = new FileReader(frameFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null){
            System.out.println("no data");
            throw new IOException();
        }else {
            // 2 lines de dua con tro ve dau file va doc file
            fr = new FileReader(frameFile);
            br = new BufferedReader(fr);

            while((line = br.readLine()).equals(" "));

            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++){
                FrameImage frameImage = new FrameImage();
                while ((line = br.readLine()).equals(""));
                frameImage.setName(line);

                while ((line = br.readLine()).equals(""));
                String[] str = line.split(" "); // (tach mang ngan cach boi " ") ta dc array gom 2 phan tu string
                String path = str[1];

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // (tach mang ngan cach boi " ") ta dc array gom 2 phan tu string
                int x = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // (tach mang ngan cach boi " ") ta dc array gom 2 phan tu string
                int y = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // (tach mang ngan cach boi " ") ta dc array gom 2 phan tu string
                int w = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // (tach mang ngan cach boi " ") ta dc array gom 2 phan tu string
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
