/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mechanics;


public class CollisionBox {

    double width;
    double height;
    double xPos;
    double yPos;
    double pXPos;
    double pYPos;
    
    public CollisionBox(double width, double height, double xPos, double yPos){
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public double getWidth(){
        return width;
    }
    
    public double getHeight(){
        return height;
    }
    
    public void updatePos(double dx, double dy){
        pXPos = xPos;
        pYPos = yPos;
        xPos += dx;
        yPos += dy;
    }
    
    public void setPos(double x, double y){
        pXPos = x;
        pYPos = y;
        xPos = x;
        yPos = y;
    }
    
    public double getXPos(){
        return xPos;
    }
    
    public double getYPos(){
        return yPos;
    }
    
    public double getPXPos(){
        return pXPos;
    }
    
    public double getPYPos(){
        return pYPos;
    }
    
    public boolean checkCollision(CollisionBox check){
        if (check.getWidth()+check.getXPos() < xPos+width && check.getXPos() +check.getWidth()>xPos){
            if((check.getYPos()>yPos && check.getYPos() < yPos+height) || (check.getYPos()+check.getHeight()>yPos && check.getYPos() < yPos+height)){
                return true;
            }
        }
        if (check.getXPos() < xPos+width && check.getXPos() +check.getWidth()>xPos){
            if((check.getYPos()>yPos && check.getYPos() < yPos+height) || (check.getYPos()+check.getHeight()>yPos && check.getYPos() < yPos+height)){
                return true;
            }
        }
        return false;
    }
    
    public boolean checkFloor(CollisionBox floor){
        if((xPos > floor.getXPos() && xPos < floor.getXPos() + floor.getWidth()) || (xPos+width > floor.getXPos() && xPos + width < floor.getXPos() + floor.getWidth())){
            if(yPos+height >= floor.getYPos()&& yPos+height < floor.getYPos()+floor.height){
                return true;
            }
        }
        return false;
    }
    //returns 0 if not colliding with anything
    //returns 1 if on a floor
    //retruns 2 if colliding with wall from the left
    //returns 3 fi colliding with wall from the right
    public int checkECollisions(CollisionBox check){
        if ((check.getWidth()+check.getXPos() <= xPos+width && check.getXPos() +check.getWidth()>=xPos) || (check.getXPos() <= xPos+width && check.getXPos() + check.getWidth() >= xPos)){
            if((check.getYPos()<=yPos+height && check.getYPos()+height >= yPos+height) || (check.getYPos()+check.getHeight()>=yPos && check.getYPos() <= yPos)){
                if(pYPos+height <= check.getYPos()){
                    return 1;
                }
                else if(pXPos+width <= check.getXPos()){
                    return 2;
                }
                else if (pXPos >= check.getXPos() + check.getWidth()){
                    return 3;
                }
            }
        }
            return 0;
        
    }
}
