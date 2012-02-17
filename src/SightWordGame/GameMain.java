/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SightWordGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author kel31710
 */
public class GameMain {
    private String swl =    "sightwordlist.xml";
    public GameMain(){
        try {
            GameModel model = new GameModel();
            GameView view = new GameView(model);
            GameController controller = new GameController(view, model);
            
            
            /*
            controller.cardEntered("0");
            
            try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            controller.cardEntered("1");
            try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            controller.cardEntered("0");
            
            try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            controller.cardEntered("2");
            try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            */
        } catch (IOException ex) {
            Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        new GameMain();
        
    }
}
