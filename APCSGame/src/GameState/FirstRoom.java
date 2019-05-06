
package GameState;

import java.awt.Graphics2D;
import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Character;


public class FirstRoom extends GameState{
    
    private Background bg;
    private GameStateManager gsm;
    double horizontalVectorBg = 0;
    double verticalVectorBg = 0;
    double horizontalVectorC = 0;
    double verticalVectorC = 0;
    private Character mainC;
    private String[] characterFrames = {"/Characters/mainidle.png"};
    
    public FirstRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.jpg", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(characterFrames, 1, 100, 150);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void init(){
        bg.setPosition(0,0);
        mainC.setPosition(0, 0);
    }
    
    public void update(){
        bg.setVector(horizontalVectorBg, verticalVectorBg);
        bg.update(); 
        mainC.setVector(horizontalVectorC, verticalVectorC);
        mainC.update();
    }
    
    public void draw(Graphics2D g){
        bg.draw(g);
        mainC.draw(g);
    }
    private ArrayList<Integer> keyList = new ArrayList<Integer>();
    public void keyPressed(int k){
        if (keyList.indexOf(k) == -1){
            keyList.add(k);
        }
        //background
        if (keyList.indexOf(KeyEvent.VK_LEFT) != -1){
            horizontalVectorBg =1;
        }
        if (keyList.indexOf(KeyEvent.VK_RIGHT) != -1){
            horizontalVectorBg = -1;
        }
        if (keyList.indexOf(KeyEvent.VK_UP) != -1){
            verticalVectorBg = 1;
        }
        if (keyList.indexOf(KeyEvent.VK_DOWN) != -1){
            verticalVectorBg = -1;
        }
        //character
        if (keyList.indexOf(KeyEvent.VK_A) != -1){
            horizontalVectorC =-1;
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC = 1;
        }
        if (keyList.indexOf(KeyEvent.VK_W) != -1){
            verticalVectorC = -1;
        }
        if (keyList.indexOf(KeyEvent.VK_S) != -1){
            verticalVectorC = 1;
        }
    }
    
    public void keyReleased(int k){
        keyList.remove(new Integer(k));
        //background
        if (keyList.indexOf(KeyEvent.VK_LEFT) == -1 && keyList.indexOf(KeyEvent.VK_RIGHT) == -1){
            horizontalVectorBg = 0;
        }
        if (keyList.indexOf(KeyEvent.VK_UP) == -1 && keyList.indexOf(KeyEvent.VK_DOWN) == -1){
            verticalVectorBg = 0;
        }
        //character
        if (keyList.indexOf(KeyEvent.VK_A) == -1 && keyList.indexOf(KeyEvent.VK_D) == -1){
            horizontalVectorC = 0;
        }
        if (keyList.indexOf(KeyEvent.VK_W) == -1 && keyList.indexOf(KeyEvent.VK_S) == -1){
            verticalVectorC = 0;
        }
    }
}
