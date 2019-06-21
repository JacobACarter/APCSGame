
package GameState;

import GameState.State.CharacterState;
import java.awt.Graphics2D;
import Mechanics.Background;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Mechanics.Character;
import Mechanics.CollisionBox;
import CharacterInfo.*;
import Main.Constants;



public class FirstRoom extends GameState{
    
    private Background bg;
    private GameStateManager gsm;
    double horizontalVectorC = 0;
    double verticalVectorC = 0;
    private Character mainC;
    private ArrayList<CollisionBox> floors = new ArrayList<>();
    private ArrayList<CollisionBox> envirs = new ArrayList<>();
    private ArrayList<CollisionBox> sclbls = new ArrayList<>();
    private int swordTimer;
    private final boolean left = false;
    private final boolean right = true;
      
    public FirstRoom(GameStateManager gsm){
        this.gsm = gsm;
        try{
            bg = new Background("/Background/First_Room.gif", 1, 1200, 801);
            bg.setVector(0,0);
            mainC = new Character(new MainCharacterInfo());
            floors.add(new CollisionBox(1920, 200, 0, 900));
            floors.add(new CollisionBox(150, 10, 515, 748));
            envirs.add(new CollisionBox(1920, 200, 0, 900));
            envirs.add(new CollisionBox(150, 200, 515, 748));
            sclbls.add(new CollisionBox(404, 618, 1340, 282));
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
        mainC.setPosition(200, 100);
        mainC.setState(CharacterState.JUMP);
    }
    
    @Override
    public void update(){
       
        
        //checks collision with enemies
        //checks if enemies are hit
        //sets the game to game over screen if the character is hurt
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
        if (mainC.getXPos() > 1920) {
            mainC.setVector(0, 0);
            gsm.setState(2);
        }
        //if the character falls off the map
        if(mainC.getYPos()> 1080){
            mainC.setVector(0, 0);
            gsm.setState(4);
        }
        
        //real gravity thing
        checkECollisions(mainC);
        
        //updates position according to key pressed
        mainC.setVector(horizontalVectorC, verticalVectorC);

        
        mainC.update();
    }
    
    public void updateGravity(Character cha){
        mainC.setTouching(false);
        for(CollisionBox floor: floors){
            if(mainC.getHurt().checkFloor(floor)){
                mainC.setPosition(mainC.getXPos(), floor.getYPos() - mainC.getHurt().getHeight());
                if(mainC.getState() ==  CharacterState.JUMP){
                    verticalVectorC = 0;
                    mainC.setState(CharacterState.IDLE);
                }
                mainC.setTouching(true);
            }
        }
        if(!mainC.getTouching() && mainC.getState() != CharacterState.CLIMB){
            mainC.setState(CharacterState.JUMP);
            verticalVectorC += Constants.gravity;
        }
    }
    
    public void checkECollisions(Character cha){
        
        cha.setTouching(false);
        for(CollisionBox envir : envirs){
            switch(mainC.getHurt().checkECollisions(envir)){
                case 1:
                    mainC.setPosition(cha.getXPos(), envir.getYPos() - cha.getHurt().getHeight());
                    if(cha.getState() ==  CharacterState.JUMP){
                        verticalVectorC = 0;
                        cha.setState(CharacterState.IDLE);
                    }
                    cha.setTouching(true);
                    System.out.println(1);
                    break;
                case 2:
                    cha.setPosition(envir.getXPos()-cha.getHurt().getWidth(), cha.getYPos());
                        if(horizontalVectorC >0){
                            horizontalVectorC = 0;
                        }
                        cha.setState(CharacterState.IDLE);
                    
                    System.out.println(2);
                    break;
                case 3:
                    cha.setPosition(envir.getXPos()+envir.getWidth(), cha.getYPos());
                    if(horizontalVectorC < 0){
                        horizontalVectorC = 0;
                    }
                    cha.setState(CharacterState.IDLE);
                    System.out.println(3);
                    break;
            }
        }
        if(!cha.getTouching()){
            System.out.println(0);
            cha.setState(CharacterState.JUMP);
            verticalVectorC += Constants.gravity;
        }
    }
    
    
    public boolean checkSCollisions(Character cha){
        
        cha.setTouching(false);
        for (CollisionBox wall : sclbls){
            if (cha.getHurt().checkCollision(wall))
                return true; 
        }
        return false;
    }
    
    @Override
    public void draw(Graphics2D g){
        //draws background
        bg.draw(g);
        //draws main character
        mainC.draw(g);
        //draws each enemy
    }
    private ArrayList<Integer> keyList = new ArrayList<>();
    @Override
    //an arraylist of keys is made so that multiple keys can be pressed at a time
    public void keyPressed(int k){
        
        if (keyList.indexOf(k) == -1){
            keyList.add(k);
        }
        if (k == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        //character
        if (!checkSCollisions(mainC)){
            horizontalVectorC = 0;
            if (keyList.indexOf(KeyEvent.VK_A) != -1){
                horizontalVectorC -= Constants.runSpeed;

                mainC.setDirection(left);
                if(mainC.getState() == CharacterState.IDLE){
                    mainC.setState(CharacterState.RUN);
                }
            }
            if (keyList.indexOf(KeyEvent.VK_D) != -1){
                horizontalVectorC += Constants.runSpeed;
                mainC.setDirection(right);
                if(mainC.getState() == CharacterState.IDLE){
                    mainC.setState(CharacterState.RUN);
                }
            }
            if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){           
                verticalVectorC = Constants.jumpVelocity;
            }
            else if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.HURT && mainC.getState() != CharacterState.JUMP){
                mainC.setState(CharacterState.HIT);
                mainC.setHit(false);
                swordTimer = 0;
            }
            
        }
        else{
            horizontalVectorC = 0;
            if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.CLIMB){
                mainC.setState(CharacterState.CLIMB);
            }
            if (mainC.getState() == CharacterState.CLIMB){
                verticalVectorC = 0;
                if (keyList.indexOf(KeyEvent.VK_W) != -1){
                    verticalVectorC = -Constants.climbSpeed;
                }
                if (keyList.indexOf(KeyEvent.VK_S) != -1){
                    verticalVectorC = Constants.climbSpeed;
                }
                if (keyList.indexOf(KeyEvent.VK_D) != -1){
                    horizontalVectorC = Constants.climbSpeed;
                }
                if (keyList.indexOf(KeyEvent.VK_A) != -1){
                    horizontalVectorC = -Constants.climbSpeed;
                }
                if (keyList.indexOf(KeyEvent.VK_SPACE) != -1){
                    mainC.setState(CharacterState.JUMP);
                }
               
            }
            else{
                if (keyList.indexOf(KeyEvent.VK_A) != -1){
                    horizontalVectorC -= Constants.runSpeed;

                    mainC.setDirection(left);
                    if(mainC.getState() == CharacterState.IDLE){
                        mainC.setState(CharacterState.RUN);
                    }
                }
                if (keyList.indexOf(KeyEvent.VK_D) != -1){
                    horizontalVectorC += Constants.runSpeed;
                    mainC.setDirection(right);
                    if(mainC.getState() == CharacterState.IDLE){
                        mainC.setState(CharacterState.RUN);
                    }
                }
                if ((k == KeyEvent.VK_W)&& (mainC.getState() != CharacterState.JUMP)){           
                    verticalVectorC = Constants.jumpVelocity;
                }
                else if (k == KeyEvent.VK_SPACE && mainC.getState() != CharacterState.HURT && mainC.getState() != CharacterState.JUMP){
                    mainC.setState(CharacterState.HIT);
                    mainC.setHit(false);
                    swordTimer = 0;
                }
            }
            
                
        }
    }
    
    @Override
    public void keyReleased(int k){
        //removes the key from the list of currently pressed keys
        keyList.remove(new Integer(k));
        //checks to see if character should be moving
        if (keyList.indexOf(KeyEvent.VK_A) == -1 && keyList.indexOf(KeyEvent.VK_D) == -1){
            if(mainC.getState() == CharacterState.RUN){
                mainC.setState(CharacterState.IDLE);
                
            }
            horizontalVectorC = 0;
            mainC.resetRunAnimation();        
        }
        horizontalVectorC = 0;
        if (keyList.indexOf(KeyEvent.VK_A) != -1){
            horizontalVectorC -= Constants.runSpeed;
 
            mainC.setDirection(left);
            if(mainC.getState() == CharacterState.IDLE){
                mainC.setState(CharacterState.RUN);
            }
        }
        if (keyList.indexOf(KeyEvent.VK_D) != -1){
            horizontalVectorC += Constants.runSpeed;
            mainC.setDirection(right);
            if(mainC.getState() == CharacterState.IDLE){
                mainC.setState(CharacterState.RUN);
            }
        }
    }
    //proportional control of the enemies movements to follow the main character
    public void enemyAI(Character badGuy){
        badGuy.setVector((mainC.getXPos() - badGuy.getXPos()) / 100, badGuy.getVector());
    }
}
