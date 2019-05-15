/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;

import CharacterInfo.CharacterInfo;
import CharacterInfo.MainCharacterInfo;
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
    private ArrayList<BufferedImage> imagesRun = new ArrayList<>();
    private ArrayList<BufferedImage> imagesJump= new ArrayList<>();
    private ArrayList<BufferedImage> imagesAttack = new ArrayList<>();
    private double animationCounterIdle = 0;
    private double animationCounterRun = 0;
    private double animationCounterJump = 0;
    private double animationCounterAttack = 0;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private CollisionBox hurt;
    private CollisionBox hit;
    private CollisionBox attackHitBox;
    private State state;
    
    private double moveScale;
    
    public Character(CharacterInfo info){
        
        try{
            for(String file : info.getIdleFrames()){
                imagesIdle.add((ImageIO.read(getClass().getResourceAsStream(file))));
            }
            for(String file : info.getRunFrames()){
                imagesRun.add((ImageIO.read(getClass().getResourceAsStream(file))));
            }
            for(String file : info.getJumpFrames()){
                imagesJump.add((ImageIO.read(getClass().getResourceAsStream(file))));
            }
            for(String file : info.getAttackFrames()){
                imagesAttack.add((ImageIO.read(getClass().getResourceAsStream(file))));
            }
            moveScale = info.getMS();
            this.hurt = info.getHurtBox();
            this.hit = info.getIdleHitBox();
            this.attackHitBox = info.getHitAttackBox();
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
        hurt.setPos(x*moveScale,y*moveScale);
        hit.setPos(x*moveScale,y*moveScale);
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
    
    public void update(KnightBoss boss){
        
    }
    
    public void hitCollision(Character check){
        if(hurt.checkCollision(check.getHit())){
            state.setState(CharacterState.HURT);
        }
    }
    
    public void setHit(){
        this.hit = attackHitBox;
    }
    
    public void draw(Graphics2D g){
        if(getState() == CharacterState.HURT){
            g.drawImage(imagesIdle.get(0), (int)x, (int)y, null);
        }
        if(getState() == CharacterState.IDLE){
            g.drawImage(imagesIdle.get(((int)animationCounterIdle)%imagesIdle.size()), (int)x, (int)y, null);
            animationCounterIdle += (1.0/20.0);
        }
        if(getState() == CharacterState.RUN){
            g.drawImage(imagesRun.get(((int)animationCounterIdle)%imagesRun.size()), (int)x, (int)y, null);
            animationCounterIdle += (1.0/20.0);
        }
        if(getState() == CharacterState.HIT){
            g.drawImage(imagesAttack.get(((int)animationCounterIdle)%imagesAttack.size()), (int)x, (int)y, null);
            animationCounterIdle += (1.0/20.0);
        }
        
        else{
            g.drawImage(imagesIdle.get(0), (int)x, (int)y, null);
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
