

package GameState;

import java.awt.*;
import TileMap.Background;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jacob
 */
public class GameOver extends GameState{
    
    private Background bg;
    private int currentChoice = 0;
    private String[] options = {"Start", "Quit"};
    private Color titleColor;
    private Font titleFont;
    private Font font;
    
    public GameOver(GameStateManager gsm){
        this.gsm = gsm;
        
        try{
            bg = new Background("/Background/menubg.gif", 1, 590,336);
            bg.setVector(-.1,0);
            
            titleColor = new Color(255, 255, 255);
            titleFont = new Font("Centruy Gothic", Font.PLAIN, 36);
            font = new Font("Arial", Font.PLAIN, 24);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void init(){}
    public void update(){
        bg.update();
    }
    public void draw(Graphics2D g){
        //draws background
        bg.draw(g);
        //draws title on top of background
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("GAME OVER", 200, 100);
        
        
    }
    
    private void select(){
        if(currentChoice == 0){
            //selected start
            gsm.setState(1);
            
        }
        if(currentChoice == 1){
            //selected quit
            
        }
    }
    //only can click enter up and down
    public void keyPressed(int k){
        if (k == KeyEvent.VK_ENTER){
            select();
        }
        if (k == KeyEvent.VK_UP){
           currentChoice--;
           if (currentChoice == -1){
               currentChoice =1;
           }
        }
        if (k == KeyEvent.VK_DOWN){
            currentChoice++;
            if (currentChoice == 2){
                currentChoice= 0;
            }
        }
    }
    public void keyReleased(int k){}
    
}
