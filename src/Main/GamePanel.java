/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GameState.GameStateManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    
    //dimensions for the game panel and scaling for screen
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int SCALE = 1;
    
    //game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    
    //image
    private BufferedImage image;
    private Graphics2D g;
    
    //game state manager
    private GameStateManager gsm;
    
    //audio file
    private String fileNameAudio = "/Audio.wav";
    private Clip clip;
    
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    
    private void init(){
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        
        gsm = new GameStateManager();
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileNameAudio));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        init();
        long start;
        long elapsed;
        long wait;
        clip.start();
        
        
        //basic game loop
        while(running){
            
            start = System.nanoTime();
            
            update();
            draw();
            drawToScreen();
            
            elapsed = System.nanoTime()-start;
            
            wait = targetTime - (elapsed/100000000);
            
            try{
                Thread.sleep(wait);     
            }
            catch(Exception e){
                e.printStackTrace();
                
            }
        }
        
        
    }
    
    private void update(){
        gsm.update();
    }
    private void draw(){
        gsm.draw(g);
    }
    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }
    
    public void keyTyped(KeyEvent key){
        
    }
    public void keyPressed(KeyEvent key){
        gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased(KeyEvent key){
        gsm.keyReleased(key.getKeyCode());
    }
}
