/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mechanics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Main.GamePanel;

    
public class Background {
    
    private BufferedImage image;
    
    private double x;
    private double y;
    private double dx;
    private double dy;
    private int picWidth;
    private int picHeight;
    
    private double moveScale;
    
    public Background(String s, double ms, int picWidth, int picHeight){
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
            this.picWidth = picWidth;
            this.picHeight = picHeight;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    //sets the postiions of the background on the screen
    public void setPosition(double x, double y){
        this.x = (x* moveScale) % picWidth;//modulus ensures the background does not continue down the screel
        this.y = (y* moveScale) % picHeight;
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
            g.drawImage(image, (int)x + picWidth, (int)y, null);
        }
        if(x > picWidth){
            g.drawImage(image, (int)x - picWidth, (int)y, null);
        }
    }
    
    
}
