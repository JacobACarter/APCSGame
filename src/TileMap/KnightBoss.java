
//package TileMap;
//
//import GameState.State;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import javax.imageio.ImageIO;
//import GameState.State.CharacterState;
//
//public class KnightBoss extends Character{
//    
//    private int health;
//    private CollisionBox[] hitboxes;
//    private int[] hVectors;
//    private int[] vVectors;
//    private int[] x;
//    private int[] y;
//    private ArrayList<BufferedImage>[] attackImages;
//    
//    //constructor for boss with added hitboxes and frames
//    public KnightBoss(String[] s, double ms, CollisionBox hit, CollisionBox hurt, int health, CollisionBox[] hitboxes, String[][] f){
//        super(s, ms, hit, hurt);
//        this.health = health;
//        this.hitboxes = hitboxes;
//        hVectors = new int [hitboxes.length];
//        vVectors = new int[hitboxes.length];
//        attackImages = new ArrayList[hitboxes.length];
//        int count = 0;
//        for (ArrayList frames : attackImages){
//            try{
//            frames = new ArrayList<>();
//            for (String file : f[count])
//                frames.add((ImageIO.read(getClass().getResourceAsStream(file))));
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//    }   
//    
//    //updates the movement of each hitbox
//    public void setHitVector(int boxIndex, int dx, int dy){
//        hVectors[boxIndex] = dx;
//        vVectors[boxIndex] = dy;
//    }
//    
//    //updates each hitbox individually
//    public void updateBoss(){
//        for(int k = 0; k < hitboxes.length; k++){
//            x[k] += hVectors[k];
//            y[k] += vVectors[k];
//            hitboxes[k].updatePos(hVectors[k], vVectors[k]);
//        }
//        if (super.getState() == CharacterState.HIT){
//            health--;
//            super.setState(CharacterState.IDLE);
//        }
//    }
//    
//    //cycles the current attack out
//    public void deleteCycle(int boxIndex){
//        hVectors[boxIndex] = 0;
//        vVectors[boxIndex] = 0;
//        hitboxes[boxIndex].setPos(0, -100);
//    }
//    
//    //cycles an attack back in
//    public void spawnCycle(int boxIndex, int xPos, int yPos){
//        hitboxes[boxIndex].setPos(xPos, yPos);
//    }
//    
//    //deletes all hitboxes after boss is defeated
//    public void deleteTotal(){
//        for(int k = 0; k < hitboxes.length; k++){
//            hVectors[k] = 0;
//            vVectors[k] = 0;
//            hitboxes[k].setPos(0, -100);
//        }
//    }
//    
//    public void drawBoss(Graphics2D g){
//        for (int i = 0; i < hitboxes.length; i++){
//            g.drawImage(attackImages[i].get(0), (int)x[i], (int)y[i], null);
//        }
//    }
//}
//
