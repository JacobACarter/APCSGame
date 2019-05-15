
package GameState;

import GameState.State.CharacterState;
import java.awt.Graphics2D;
import TileMap.Background;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Character;
import TileMap.CollisionBox;
import CharacterInfo.*;



public class FirstRoom extends GameState{
    
    private Background bg;
    private GameStateManager gsm;
    double horizontalVectorC = 0;
    double verticalVectorC = 0;
    double directionVectorC = 3;
    private Character mainC;
    private ArrayList<Character> enemies = new ArrayList<>();
    private CollisionBox floor;
    private int swordTimer;
    private final boolean left = false;
    private final boolean right = true;
      
    public FirstRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.jpg", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(new MainCharacterInfo());
            enemies.add(new Character(new EnemyTypeOneInfo()));
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
            if(e.getState() == CharacterState.HURT){
                e.setPosition(-10, -30);
                e.setState(CharacterState.IDLE);
            }
        }
        
        if(mainC.getState() == CharacterState.HURT){
            gsm.setState(2);
        }       
        //fake gravity thing
        if(!mainC.getHurt().checkFloor(floor)&&mainC.getState() == CharacterState.JUMP){
            verticalVectorC += 1;
        }
        //checks contact with the floor
        else if (mainC.getState() == CharacterState.JUMP){
            mainC.setPosition(mainC.getXPos(), floor.getYPos());
            mainC.setState(CharacterState.IDLE);
            verticalVectorC = 0;
        }
        //if the character is currently attacking
        if (mainC.getState() == CharacterState.HIT){
            swordTimer++;
            if (swordTimer >= 15){
                mainC.setHit();
                mainC.setState(CharacterState.IDLE);
            }
        }
        //if the character walks into the next room
        if (mainC.getXPos() > 560) {
            gsm.setState(2);
        }
        System.out.println(mainC.getXPos());
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
            mainC.setDirection(left);
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC = 3;
            directionVectorC = horizontalVectorC;
            mainC.setDirection(right);
        }
        if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){
            mainC.setState(CharacterState.JUMP);
            verticalVectorC = -10;
        }
        else if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.HURT && mainC.getState() != CharacterState.JUMP){
            mainC.setState(CharacterState.HIT);
            mainC.setHit();
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
