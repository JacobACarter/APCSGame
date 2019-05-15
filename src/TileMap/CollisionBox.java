/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;


public class CollisionBox {

    double width;
    double height;
    double xPos;
    double yPos;
    
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
        xPos += dx;
        yPos += dy;
    }
    
    public void setPos(double x, double y){
        xPos = x;
        yPos = y;
    }
    
    public double getXPos(){
        return xPos;
    }
    
    public double getYPos(){
        return yPos;
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
        if(((xPos>floor.getXPos() && xPos<floor.getXPos()+floor.getWidth())|| (xPos+width>floor.getXPos() && xPos+width<floor.getXPos()+floor.getWidth())) && yPos+height > floor.getYPos()){
            return true;
        }
        if(((xPos>floor.getXPos() && xPos<floor.getXPos()+floor.getWidth())|| (xPos+width>floor.getXPos() && xPos+width<floor.getXPos()+floor.getWidth())) && yPos+height == floor.getYPos()){
            return true;
        }
        return false;
    }
}
