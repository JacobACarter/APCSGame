
package GameState;

import GameState.State.CharacterState;
import java.awt.Graphics2D;
import TileMap.Background;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Character;
import TileMap.CollisionBox;
import TileMap.KnightBoss;

public class BossRoom extends GameState {
    
    private int animationCounter = 0;
    private Background bg;
    private GameStateManager gsm;
    double horizontalVectorC = 0;
    double verticalVectorC = 0;
    double directionVectorC = 3;
    private Character mainC;
    private KnightBoss boss;
    
    private String[] characterFrames = {"/Characters/Main/IdleFrame1.png", "/Characters/Main/IdleFrame2.png"};
    private CollisionBox floor;
    private int swordTimer;
    
    public BossRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.jpg", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(characterFrames, 1, new CollisionBox(6, 13,50, 250), new CollisionBox(0, 0, 50, 100));
            floor = new CollisionBox(600, 50, 0, 250);
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
        
    }
    
    @Override
    public void update(){
        

        mainC.setVector(horizontalVectorC, verticalVectorC);
        mainC.update();
        
        if(mainC.getState() == CharacterState.HIT){
            gsm.setState(3);
        }       
        
        if(!mainC.getHurt().checkFloor(floor)&&mainC.getState() == CharacterState.JUMP){
            verticalVectorC += 1;
        }
        else if (mainC.getState() == CharacterState.JUMP){
            mainC.setPosition(mainC.getXPos(), floor.getYPos());
            mainC.setState(CharacterState.IDLE);
            verticalVectorC = 0;
        }
        if (mainC.getState() == CharacterState.HURT){
            swordTimer++;
            if (swordTimer >= 15){
                mainC.setHit(new CollisionBox (0, 0, 50, 100));
                mainC.setState(CharacterState.IDLE);
            }
        }
        if (mainC.getXPos() > 1200) {
            gsm.setState(2);
        }
    }
    

    
    @Override
    public void draw(Graphics2D g){
        bg.draw(g);
        mainC.draw(g);
        
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
            directionVectorC = horizontalVectorC;
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC = 3;
            directionVectorC = horizontalVectorC;
        }
        if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){
            mainC.setState(CharacterState.JUMP);
            verticalVectorC = -10;
        }
        else if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.HURT && mainC.getState() != CharacterState.JUMP){
            mainC.setState(CharacterState.HURT);
            mainC.setHit(new CollisionBox(6, 13, mainC.getXPos() + directionVectorC + 3, mainC.getYPos()));
            swordTimer = 0;
        }
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
