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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 *
 * @author Jacob
 */
public class Character {
    
    //holds all of the frames for each animation
    private ArrayList<BufferedImage> imagesIdle = new ArrayList<>();
    private ArrayList<BufferedImage> imagesRun = new ArrayList<>();
    private ArrayList<BufferedImage> imagesJump= new ArrayList<>();
    private ArrayList<BufferedImage> imagesAttack = new ArrayList<>();
    //counter to know which frame to print
    private double animationCounterIdle = 0;
    private double animationCounterRun = 0;
    private double animationCounterJump = 0;
    private double animationCounterAttack = 0;
    //basic coordinates of the character and how it moves
    private double x;
    private double y;
    private double dx;
    private double dy;
    //hit box and hurt box of the characters for collisions
    private CollisionBox hurt;
    private CollisionBox hit;
    
    private CollisionBox attackHitBoxR;
    private double xOffsetR;
    private double yOffsetR;
    
    private CollisionBox attackHitBoxL;
    private double xOffsetL;
    private double yOffsetL;
    private CollisionBox idleHitBox;
    //variables to know what the character is currently doing
    private State state;
    private boolean direction;
    private final boolean left = false;
    private final boolean right = true;
    
    private double moveScale;
    
    public Character(CharacterInfo info){
        
        //loading all the frames for animation from the characterinfo class
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
            
            //getting all other info from the character info class
            moveScale = info.getMS();
            this.hurt = info.getHurtBox();
            this.idleHitBox = info.getIdleHitBox();
            this.hit = this.idleHitBox;
            this.attackHitBoxR = info.getHitAttackBoxR();
            this.attackHitBoxL = info.getHitAttackBoxL();
            this.xOffsetL = info.getXOffsetL();
            this.yOffsetL = info.getYOffsetL();
            this.xOffsetR = info.getXOffsetR();
            this.yOffsetR = info.getYOffsetR();
            //instantiating the state and defaulting to idle
            state = new State();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
//********************Basic Access and Set methods*********************************************
    
    //resets for animation
    public void setDirection(boolean d){
        direction = d;
    }
    
    public void resetIdleAnimation(){
        animationCounterIdle = 0;
    }
    
    public void resetRunAnimation(){
        animationCounterRun = 0;
    }
    
    public void resetAttackAnimation(){
        animationCounterAttack= 0;
    }
    
    public void resetIdleJump(){
        animationCounterJump = 0;
    }
    
    //access what state the character is in
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
    //updates the position of the character as well as its hit/hurt boxes
    public void update(){
        x += dx;
        y += dy;
        hurt.updatePos(dx, dy);
        hit.updatePos(dx, dy);
    }
    //update but checks for collisions with enemie
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
    //sets the hit box to that of the characters attack
    public void setHit(boolean idle){
        if(idle){
            this.hit = idleHitBox;
        }
        else{
            if(direction == left){
                this.hit = attackHitBoxL;
                hit.setPos(x+xOffsetL, y+yOffsetL);
            }
            else{
                this.hit = attackHitBoxR;
                hit.setPos(x+xOffsetR, y+ yOffsetR);
            }
        }

      
    }
    //drawing the character
    public void draw(Graphics2D g){
        BufferedImage img;
        //checks state and decides which files to read from
        if(getState() == CharacterState.HURT){
            img = imagesIdle.get(0);
        }
        else if(getState() == CharacterState.IDLE){
            img = imagesIdle.get(((int)animationCounterIdle)%imagesIdle.size());
            animationCounterIdle += (1.0/20.0);
        }
        else if(getState() == CharacterState.RUN){
            img = imagesRun.get(((int)animationCounterRun)%imagesRun.size());
            animationCounterRun += (1.0/20.0);
        }
        else if(getState() == CharacterState.HIT){
            img = imagesAttack.get(((int)animationCounterAttack)%imagesAttack.size());
            animationCounterAttack += (1.0/20.0);
        }   
        else if(getState() == CharacterState.JUMP){
            img = imagesJump.get(((int)animationCounterJump)%imagesJump.size());
        }
        else{
            img = imagesIdle.get(0);
        }
        if(direction == left){
            g.drawImage(img, (int)x, (int)y, null);
        }
        else{
            AffineTransform tx = AffineTransform.getScaleInstance(-1,1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage reflect = op.filter(img, null);
            g.drawImage(reflect, (int)x, (int)y, null);     
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
