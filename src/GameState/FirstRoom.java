
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
    double directionVectorC = 3;
    private Character mainC;
    private ArrayList<Character> enemies = new ArrayList<>();
    private String[] characterFrames = {"/Characters/Main/IdleFrame1.png", "/Characters/Main/IdleFrame2.png"};
    private String[] enemyTypeOneFrames = {"/Characters/enemyidle.png"};
    private CollisionBox floor;
    private int swordTimer;
    
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
        enemies.get(0).setPosition(100, floor.getYPos());
    }
    
    @Override
    public void update(){
        

        mainC.setVector(horizontalVectorC, verticalVectorC);
        mainC.update(enemies);
        for(Character e: enemies){
            enemyAI(e);
            e.update(mainC);
            if(e.getState() == CharacterState.HIT)
                e.setPosition(-10, -30);
                e.setState(CharacterState.IDLE);
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
        if (mainC.getState() == CharacterState.HURT){
            swordTimer++;
            if (swordTimer >= 30){
                mainC.setHit(new CollisionBox (0, 0, 50, 100));
                mainC.setState(CharacterState.IDLE);
            }
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
    
    public void enemyAI(Character badGuy){
        badGuy.setVector((mainC.getXPos() - badGuy.getXPos()) / 100, 0);
    }
}
