/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Toolkit;
import javax.swing.JFrame;
/**
 *
 * @author Jacob
 */
public class Game {
        public static void main(String[] args){
            
            JFrame window = new JFrame("Game");
            window.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            window.setUndecorated(true);
            window.setContentPane(new GamePanel());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
        }
    
}
