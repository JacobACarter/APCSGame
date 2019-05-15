
package CharacterInfo;

import TileMap.CollisionBox;
import java.util.ArrayList;


public class EnemyTypeOneInfo extends CharacterInfo{
    ArrayList<String> idleFrames = new ArrayList<String>();  
    ArrayList<String> runFrames = new ArrayList<String>();
    ArrayList<String> jumpFrames = new ArrayList<String>();
    ArrayList<String> attackFrames = new ArrayList<String>();
    private double frame = 0;
    private double ms = 1;
    private CollisionBox idleCollisionBox = new CollisionBox(7,13,50,100);
    private CollisionBox attackHitBox = new CollisionBox(10,5,55,100);
    private CollisionBox idleHitBox = new CollisionBox(7,13, 50, 100);

    
    public EnemyTypeOneInfo(){
        
        idleFrames.add("/Characters/EnemyTypeOne/IdleFrame1.png");
        //idleFrames.add("/Characters/EnemyTypeOne/IdleFrame2.png");
        
        //runFrames.add("/Characters/Main/RunFrame1");
        //runFrames.add("/Character/Main/RunFrame2");
        
        //jumpFrames.add("/Character/Main/JumpFrame1");
        //jumpFrames.add("/Character/Main/JumpFrame2");
        
        //attackFrames.add("/Character/Main/AttackFrame1");
        //attackFrames.add("/Character/Main/AttackFrame2");
    }
    
    public ArrayList<String> getIdleFrames(){
        return idleFrames;
    }
            
    public ArrayList<String> getAttackFrames(){
        return attackFrames;
    }
        
    public ArrayList<String> getJumpFrames(){
        return jumpFrames;
    }
        
    public ArrayList<String> getRunFrames(){
        return runFrames;
    }
        
    public int getFrame(){
        return (int) frame;
    }
        
    public void addToFrame(double add){
        frame += add;
    }
        
    public double getMS(){
        return ms;
    }
        
    public CollisionBox getHurtBox(){
        return idleCollisionBox;
    }
        
    public CollisionBox getHitAttackBox(){
        return attackHitBox;
    }
        
    public CollisionBox getIdleHitBox(){
        return idleHitBox;
    }
      
      
    
    
      
    
}
