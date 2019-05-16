
package GameState;

import GameState.State.CharacterState;
import java.awt.Graphics2D;
import TileMap.Background;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Character;
import TileMap.CollisionBox;
import CharacterInfo.*;



public class SecondRoom extends GameState{
    
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
      
    public SecondRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/firstroom.png", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(new MainCharacterInfo());
            enemies.add(new Character(new EnemyTypeOneInfo()));
            enemies.add(new Character(new EnemyTypeOneInfo()));
            enemies.add(new Character(new EnemyTypeOneInfo()));
            floors.add(new CollisionBox(100, 300, 0, 100));
            floors.add(new CollisionBox(40, 200, 140, 210));
            floors.add(new CollisionBox(40, 100, 220, 280));
            floors.add(new CollisionBox(60, 100, 300, 260));
            floors.add(new CollisionBox(200, 200, 410, 270));
           
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
        enemies.get(0).setPosition(100, floors.get(0).getYPos()-enemies.get(0).getHurt().getHeight());
        enemies.get(1).setPosition(190, floors.get(2).getYPos()-enemies.get(0).getHurt().getHeight());
        enemies.get(2).setPosition(420, floors.get(4).getYPos()-enemies.get(0).getHurt().getHeight());
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
            gsm.setState(3);
        }
        if(mainC.getYPos()> 320){
            mainC.setVector(0, 0);
            gsm.setState(4);
        }
        if (mainC.getXPos() < 0) {
            mainC.setVector(0, 0);
            gsm.setState(1);
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
                    bad.update(mainC);
                }
            }
            if(!bad.getTouching()){
                bad.setVector((-(mainC.getXPos() - bad.getXPos()) / 100) + 0.5, 0);
                bad.setState(CharacterState.JUMP);
                bad.update(mainC);
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
