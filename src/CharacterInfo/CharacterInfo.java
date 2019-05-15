
package CharacterInfo;

import TileMap.CollisionBox;
import java.util.ArrayList;


public abstract class CharacterInfo {

        private ArrayList<String> idleFrames;
        private ArrayList<String> attackFrames;
        private ArrayList<String> jumpFrames;
        private ArrayList<String> runFrames;
        private double frame = 0;
        private double ms = 1;
        private CollisionBox idleCollisionBox;
        private CollisionBox attackHitBox;
        private CollisionBox idleHitBox;
        
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
