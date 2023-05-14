package com.hieu.effect;

import javax.imageio.ImageIO;

// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;

// import java.applet.Applet;
// import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.*;
// import java.net.MalformedURLException;
// import java.net.URL;
import java.util.Hashtable;

public class CacheDataLoader { // follow Design pattern singleton style to avoid creating many instance for cache data
    private static volatile CacheDataLoader instance = null; 
    private String frameFile = "data/frame.txt";
    private String animationFile = "data/animation.txt";
    private String physmapfile = "data/phys_map.txt";
    private String backgroundmapfile = "data/background_map.txt";
    // private String soundFile = "data/sounds.txt";

    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String,Animation> animations;
    // private Hashtable<String, Clip> sounds;

    private int[][] phys_map;
    private int[][] background_map;

    private CacheDataLoader(){ // constructor must be private 

    }
    public static CacheDataLoader getInstance(){
        // each time call this function only get an instance
        CacheDataLoader result = instance;
        if(result == null){
            synchronized(CacheDataLoader.class){
                result = instance; 
                if (result == null){
                    instance = result = new CacheDataLoader();
                }
            }
        }
        return result; 
    }
    public void LoadData() throws IOException{
        LoadFrame();
        LoadAnimation();
        LoadPhysMap();
        LoadBackgroundMap();
        // LoadSounds();

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
                int x;
                if (!str[1].isEmpty()) {
                    x = Integer.parseInt(str[1]);
                } else {
                    // handle empty string here
                    x = 0;
                }
                // int x = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array 
                int y;
                if (!str[1].isEmpty()) {
                    y = Integer.parseInt(str[1]);
                } else {
                    // handle empty string here
                    y = 0;
                }
                // int y = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array
                int w;
                if (!str[1].isEmpty()) {
                    w = Integer.parseInt(str[1]);
                } else {
                    // handle empty string here
                    w = 0;
                }
                // int w = Integer.parseInt(str[1]);

                while ((line = br.readLine()).equals(""));
                str = line.split(" "); // characters separated by " " will be added to the array
                int h;
                if (!str[1].isEmpty()) {
                    h = Integer.parseInt(str[1]);
                } else {
                    // handle empty string here
                    h = 0;
                }
                // int h = Integer.parseInt(str[1]);

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

    // public void LoadSounds() throws IOException{
    //     sounds = new Hashtable<String, Clip>();
        
    //     FileReader fr = new FileReader(soundFile);
    //     BufferedReader br = new BufferedReader(fr);
        
    //     String line = null;
        
    //     if(br.readLine()==null) { // no line = "" or something like that
    //         System.out.println("No data");
    //         throw new IOException();
    //     }
    //     else {
            
    //         fr = new FileReader(soundFile);
    //         br = new BufferedReader(fr);
            
    //         while((line = br.readLine()).equals(""));
            
    //         int n = Integer.parseInt(line);
            
    //         for(int i = 0;i < n; i ++){
                
    //             AudioClip audioClip = null;
    //             while((line = br.readLine()).equals(""));

    //             String[] str = line.split(" ");
    //             String name = str[0];
                
    //             String path = str[1];

    //             try {
    //                 audioClip =  Applet.newAudioClip(new URL("file","",str[1]));
 
    //              } catch (MalformedURLException ex) {}
                 
    //              instance.sounds.put(name, audioClip);
    //         }
            
    //     }
        
    //     br.close();
        
    // }
    // public Clip getSound(String name){
    //     return instance.sounds.get(name);
    // }
    public void LoadBackgroundMap() throws IOException{
        
        FileReader fr = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.background_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" |  ");
            for(int j = 0;j<numberOfColumns;j++)
                instance.background_map[i][j] = Integer.parseInt(str[j]);
        }
        
        for(int i = 0;i < numberOfRows;i++){
            
            for(int j = 0;j<numberOfColumns;j++)
                System.out.print(" "+instance.background_map[i][j]);
            
            System.out.println();
        }
        
        br.close();
        
    }

    
    public void LoadPhysMap() throws IOException{
        
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.phys_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" ");
            for(int j = 0;j<numberOfColumns;j++)
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
        }
        
        for(int i = 0;i < numberOfRows;i++){
            
            for(int j = 0;j<numberOfColumns;j++)
                System.out.print(" "+instance.phys_map[i][j]);
            
            System.out.println();
        }
        
        br.close();
        
    }
    public int[][] getPhysicalMap(){
        return instance.phys_map;
    }
    
    public int[][] getBackgroundMap(){
        return instance.background_map;
    }
}
