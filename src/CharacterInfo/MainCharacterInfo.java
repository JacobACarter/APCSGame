
package CharacterInfo;

import TileMap.CollisionBox;
import java.util.ArrayList;


public class MainCharacterInfo extends CharacterInfo{
    
    ArrayList<String> idleFrames = new ArrayList<String>();  
    ArrayList<String> runFrames = new ArrayList<String>();
    ArrayList<String> jumpFrames = new ArrayList<String>();
    ArrayList<String> attackFrames = new ArrayList<String>();
    private double ms = 1;
    private CollisionBox idleCollisionBox = new CollisionBox(7,13,50,100);
    
    private CollisionBox attackHitBoxR = new CollisionBox(10,5,55,100);
    private double xOffsetR = 5;
    private double yOffsetR = 0;
    
    private CollisionBox attackHitBoxL = new CollisionBox(10,5,40,100);
    private double xOffsetL = -10;
    private double yOffsetL = 0;
    private CollisionBox idleHitBox = new CollisionBox(0,0, 50, 100);
    
   

    
    public MainCharacterInfo(){
        
        idleFrames.add("/Characters/Main/IdleFrame1.png");
        idleFrames.add("/Characters/Main/IdleFrame2.png");
        
        runFrames.add("/Characters/Main/RunFrame1.png");
        runFrames.add("/Characters/Main/RunFrame2.png");
        
        jumpFrames.add("/Characters/Main/JumpFrame1.png");
        //jumpFrames.add("/Character/Main/JumpFrame2.png");
        
        attackFrames.add("/Characters/Main/AttackFrame1.png");
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
        
        public double getMS(){
            return ms;
        }
        
        public CollisionBox getHurtBox(){
            return idleCollisionBox;
        }
        
        public CollisionBox getHitAttackBoxR(){
            return attackHitBoxR;
        }
        
        public CollisionBox getHitAttackBoxL(){
            return attackHitBoxL;
        }
        
        public CollisionBox getIdleHitBox(){
            return idleHitBox;
        }
        
        public double getXOffsetR(){
            return xOffsetR;
        }
        
        public double getYOffsetR(){
            return yOffsetR;
        }
        
        public double getXOffsetL(){
            return xOffsetL;
        }
        
        public double getYOffsetL(){
            return yOffsetL;
        }
      
      
    
    
}
