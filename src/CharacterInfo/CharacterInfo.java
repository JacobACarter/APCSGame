
package CharacterInfo;

import TileMap.CollisionBox;
import java.util.ArrayList;


public abstract class CharacterInfo {

        /*each one of these objects will be specific to a type of character,
        it will contain all info regarding the hitbox/hurtboxes and animation frames */
        private ArrayList<String> idleFrames;
        private ArrayList<String> attackFrames;
        private ArrayList<String> jumpFrames;
        private ArrayList<String> runFrames;
        private double ms = 1;
        private CollisionBox idleCollisionBox;
        private CollisionBox attackHitBox;
        private CollisionBox idleHitBox;
        private double xOffsetR;
        private double yOffsetR;
        private double xOffsetL;
        private double yOffsetL;
        
        public CharacterInfo(){
            
        }
        
        public CharacterInfo(ArrayList<String> idleFrames, ArrayList<String> runFrames, ArrayList<String> jumpFrames, ArrayList<String> attackFrames){
            this.idleFrames = idleFrames;
            this.runFrames = runFrames;
            this.jumpFrames = jumpFrames;
            this.attackFrames = attackFrames;
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
            return attackHitBox;
        }
        
        public CollisionBox getHitAttackBoxL(){
            return attackHitBox;
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
