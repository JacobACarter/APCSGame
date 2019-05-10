
package GameState;

import GameState.State.CharacterState;
import java.awt.Graphics2D;
import TileMap.Background;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Character;
import TileMap.CollisionBox;


public class FirstRoom extends GameState{
    
    private int animationCounter = 0;
    private Background bg;
    private GameStateManager gsm;
    double horizontalVectorC = 0;
    double verticalVectorC = 0;
    private Character mainC;
    private ArrayList<Character> enemies = new ArrayList<>();
    private String[] characterFrames = {"/Characters/Main/IdleFrame1.png", "/Characters/Main/IdleFrame2.png"};
    private String[] enemyTypeOneFrames = {"/Characters/enemyidle.png"};
    private CollisionBox floor;
    
    public FirstRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.jpg", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(characterFrames, 1, new CollisionBox(6, 13,50,100), new CollisionBox(0, 0, 50, 100));
            enemies.add(new Character(enemyTypeOneFrames, 1, new CollisionBox(20,20,100,100), new CollisionBox(6,14,100,100)));
            floor = new CollisionBox(600,100, 0,150);
           
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void init(){
        bg.setPosition(0,0);
        mainC.setPosition(50, 100);
        mainC.setState(CharacterState.JUMP);
        enemies.get(0).setPosition(100,100);
    }
    
    @Override
    public void update(){
        

        mainC.setVector(horizontalVectorC, verticalVectorC);
        mainC.update(enemies);
        for(Character e: enemies){
            e.update();
        }
        if(mainC.getState() == CharacterState.HIT){
            gsm.setState(2);
        }
        
        if(!mainC.getHurt().checkFloor(floor)&&mainC.getState() == CharacterState.JUMP){
            verticalVectorC += 1;
        }
        else if (mainC.getState() == CharacterState.JUMP){
            mainC.setPosition(mainC.getXPos(), floor.getYPos());
            mainC.setState(CharacterState.IDLE);
            verticalVectorC = 0;
        }
    }
    

    
    @Override
    public void draw(Graphics2D g){
        bg.draw(g);
        mainC.draw(g);
        for(Character e: enemies){
            e.draw(g);
        }
    }
    private ArrayList<Integer> keyList = new ArrayList<>();
    @Override
    public void keyPressed(int k){
        if (keyList.indexOf(k) == -1){
            keyList.add(k);
        }
        //character
        if (keyList.indexOf(KeyEvent.VK_A) != -1){
            horizontalVectorC =-3;
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC = 3;
        }
        if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){
            mainC.setState(CharacterState.JUMP);
            verticalVectorC = -10;
        }
//        if (keyList.indexOf(KeyEvent.VK_S) != -1){
//            verticalVectorC = 1;
//        }
    }
    
    @Override
    public void keyReleased(int k){
        keyList.remove(new Integer(k));
        //character
        if (keyList.indexOf(KeyEvent.VK_A) == -1 && keyList.indexOf(KeyEvent.VK_D) == -1){
            horizontalVectorC = 0;
        }
    }
}
