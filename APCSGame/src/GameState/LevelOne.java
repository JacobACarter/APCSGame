/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Main.GamePanel;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Jacob
 */
public class LevelOne extends GameState{
    
    private TileMap tileMap;
    
    public LevelOne(GameStateManager gsm){
        this.gsm = gsm;
    }
    
    public void init(){
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/testTiles.png");
        tileMap.loadMap("/Maps/testTilesMap.txt");
        tileMap.setPosition(0,0);
    }
    
    
    public void update(){}
    
    public void draw(Graphics2D g){
        //clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0,0,GamePanel.WIDTH, GamePanel.HEIGHT);
        
        //draw tilemap
        tileMap.draw(g);
    
    }
    
    public void keyPressed(int k){}
    
    public void keyReleased(int k){}
}
