
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
    private ArrayList<CollisionBox> floors = new ArrayList<>();
    private int swordTimer;
    private final boolean left = false;
    private final boolean right = true;
      
    public FirstRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.png", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(new MainCharacterInfo());
            enemies.add(new Character(new EnemyTypeOneInfo()));
            floors.add(new CollisionBox(200, 100, 0, 280));
            floors.add(new CollisionBox(30, 100, 260, 285));
            floors.add(new CollisionBox(210, 100, 350,280));
           
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void init(){
        keyList.clear();
        bg.setPosition(0,0);
        mainC.setVector(0,0);
        mainC.setPosition(50, 100);
        mainC.setState(CharacterState.JUMP);
        enemies.get(0).setPosition(100, floors.get(0).getYPos()-enemies.get(0).getHurt().getHeight());
    }
    
    @Override
    public void update(){

        mainC.setVector(horizontalVectorC, verticalVectorC);
        mainC.update(enemies);
        for(Character e: enemies){
            enemyAI(e);
            e.update(mainC);
            if(e.getState() == CharacterState.HURT){
                e.setPosition(-10, 340);
                e.setState(CharacterState.IDLE);
            }
        }  
        
        if(mainC.getState() == CharacterState.HURT){
            mainC.setVector(0, 0);
            gsm.setState(4);
        }
        
        //if the character is currently attacking
        else if (mainC.getState() == CharacterState.HIT){
            swordTimer++;
            if (swordTimer >= 15){
                mainC.setHit(true);
                mainC.setState(CharacterState.IDLE);
            }
        }
        //if the character walks into the next room
        if (mainC.getXPos() > 560) {
            mainC.setVector(0, 0);
            gsm.setState(2);
        }
        if(mainC.getYPos()> 320){
            mainC.setVector(0, 0);
            gsm.setState(4);
        }
        
        //fake gravity thing
        mainC.setTouching(false);
        for(CollisionBox floor : floors){
            if(mainC.getHurt().checkFloor(floor)){
                if(mainC.getState() == CharacterState.JUMP){
                    mainC.setState(CharacterState.IDLE);
                }
                mainC.setPosition(mainC.getXPos(), floor.getYPos()-mainC.getHurt().getHeight());
                verticalVectorC = 0;
                mainC.setTouching(true);
            }
        }
        if(!mainC.getTouching()){
            verticalVectorC += 1;
            mainC.setState(CharacterState.JUMP);
        }
        
        for (Character bad : enemies){
            bad.setTouching(false);
            for(CollisionBox floor : floors){
                if(bad.getHurt().checkFloor(floor)){
                    if(bad.getState() == CharacterState.JUMP){
                        bad.setState(CharacterState.IDLE);
                    }
                    bad.setVector(0);
                    bad.setTouching(true);
                }
            }
            if(!bad.getTouching()){
                bad.setVector(10);
                bad.setState(CharacterState.JUMP);
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
            mainC.setDirection(left);
            if(mainC.getState() == CharacterState.IDLE){
                mainC.setState(CharacterState.RUN);
            }
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC = 3;
            directionVectorC = horizontalVectorC;
            mainC.setDirection(right);
            if(mainC.getState() == CharacterState.IDLE){
                mainC.setState(CharacterState.RUN);
            }
        }
        if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){
            mainC.setState(CharacterState.JUMP);
            verticalVectorC = -10;
        }
        else if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.HURT && mainC.getState() != CharacterState.JUMP){
            mainC.setState(CharacterState.HIT);
            mainC.setHit(false);
            swordTimer = 0;
        }
    }
    
    @Override
    public void keyReleased(int k){
        keyList.remove(new Integer(k));
        //character
        if (keyList.indexOf(KeyEvent.VK_A) == -1 && keyList.indexOf(KeyEvent.VK_D) == -1){
            if(mainC.getState() == CharacterState.RUN){
                mainC.setState(CharacterState.IDLE);
            }
            horizontalVectorC = 0;
            mainC.resetRunAnimation();        
        }
    }
    
    public void enemyAI(Character badGuy){
        badGuy.setVector((mainC.getXPos() - badGuy.getXPos()) / 100, badGuy.getVector());
    }
}
