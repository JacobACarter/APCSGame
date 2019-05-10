/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;

import GameState.State;
import GameState.State.CharacterState;
import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Jacob
 */
public class Character {
    private ArrayList<BufferedImage> imagesIdle = new ArrayList<>();
    private double animationCounterIdle = 0;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private CollisionBox hurt;
    private CollisionBox hit;
    private State state;
    
    private double moveScale;
    
    public Character(String[] s, double ms, CollisionBox hurt, CollisionBox hit){
        
        try{
            for(String file : s){
                imagesIdle.add((ImageIO.read(getClass().getResourceAsStream(file))));
                
            }
            moveScale = ms;
            this.hurt = hurt;
            this.hit = hit;
            state = new State();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public CharacterState getState(){
        return state.getCharacterState();
    }
    
    public void setState(CharacterState c){
        state.setState(c);
    }
    
    public CollisionBox getHit(){
        return hit;
    }
    
    public CollisionBox getHurt(){
        return hurt;
    }
    //sets the postiions of the background on the screen
    public void setPosition(double x, double y){
        this.x = (x* moveScale);
        this.y = (y* moveScale);
        hurt.setPos(x,y);
        hit.setPos(x,y);
    }
    
    public double getXPos(){
        return x;
    }
    
    public double getYPos(){
        return y;
    }
    //shows the change in the background as the character moves
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    
    public void update(){
        x += dx;
        y += dy;
        hurt.updatePos(dx, dy);
        hit.updatePos(dx, dy);
    }
    
    public void update(ArrayList<Character> enemies){
        x += dx;
        y += dy;
        hurt.updatePos(dx, dy);
        hit.updatePos(dx, dy);
        for(Character e : enemies){
            hitCollision(e);
        }
    }
    
    public void update(Character protag){
        x += dx;
        y += dy;
        hurt.updatePos(dx, dy);
        hit.updatePos(dx, dy);
        hitCollision(protag);
    }
    
    public void hitCollision(Character check){
        if(hurt.checkCollision(check.getHit())){
            state.setState(CharacterState.HIT);
        }
    }
    
    public void setHit(CollisionBox hit){
        this.hit = hit;
    }
    
    public void draw(Graphics2D g){
        if(getState() == CharacterState.HURT){
            g.drawImage(imagesIdle.get(0), (int)x, (int)y, null);
        }
        if(getState() == CharacterState.IDLE){
            g.drawImage(imagesIdle.get(((int)animationCounterIdle)%imagesIdle.size()), (int)x, (int)y, null);
            animationCounterIdle += (1.0/20.0);
        }
        else{
            g.drawImage(imagesIdle.get(1), (int)x, (int)y, null);
        }
        
    }
    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    } 
    
}
