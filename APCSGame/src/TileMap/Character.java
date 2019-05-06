/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jacob
 */
public class Character {
    private BufferedImage image;
    
    private double x;
    private double y;
    private double dx;
    private double dy;
    private int hurtWidth;
    private int hurtHeight;
    private int damageWidth;
    private int damageHeight;
    private State state;
    
    private double moveScale;
    
    public Character(String[] s, double ms, int hurtWidth, int hurtHeight){
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream(s[0]));
            moveScale = ms;
            this.hurtWidth = hurtWidth;
            this.hurtHeight = hurtHeight;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    //sets the postiions of the background on the screen
    public void setPosition(double x, double y){
        this.x = (x* moveScale);//modulus ensures the background does not continue down the screel
        this.y = (y* moveScale);
    }
    //shows the change in the background as the character moves
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    
    public void update(){
        x += dx;
        y += dy;
    }
    
    public void draw(Graphics2D g){
        
        g.drawImage(image, (int)x, (int)y, null);
        if(x <0){
            g.drawImage(image, 0, (int)y, null);
        }
        if(x > GamePanel.WIDTH){
            g.drawImage(image, GamePanel.WIDTH, (int)y, null);
        }
    }
    
}
