/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SightWordGame;

import SerialPortComm.CardEvent;
import SerialPortComm.CardReader;
import SerialPortComm.CardReceivedListener;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kel31710
 */
public class GameController implements CardReceivedListener {
    
    private GameView view;
    private GameModel model;
    private boolean gameOver = false;
    private CardReader cr;
    
    public GameController(GameView view, GameModel model){
        this.view = view;
        this.model = model;
        
        try {
            cr = new CardReader(null);
            cr.addCardListener(this);
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPortException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        view.intializeWord(model.startGame().length());
        view.setWindowListener(new WindowController());
        
    }
    
    public void cardEntered(String id){
        System.out.println(id);
        if(model.letterCorrect(id)){
            view.setLastLetter(String.valueOf(model.getLetter()), true);
            gameOver = this.gameFlow();
        }else view.setLastLetter(String.valueOf(model.getLetter()), false);
    }
    
    public boolean gameFlow(){
        boolean over = false;
        if(model.wordComplete()){
            
            //model.removeCurrentWordFromList();
            
            if(model.gameComplete()){
                over = true;
                view.setGameOver();
            }else{
                System.out.println("word complete");
                view.intializeWord(model.setNewWord().length());
                model.playCurrentWordAudio();
            }
        }
        return over;
    }
    
    public boolean gameOver(){
        return gameOver;
    }
    
    @Override
    public void CardReceived(CardEvent event) {
        cardEntered(event.card());
    }
    
    
    private class WindowController implements WindowListener {

        @Override
        public void windowOpened(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowClosing(WindowEvent we) {
            System.out.println(view.getContentPane().getSize().toString());
            model.stopDB();
            //model.saveDatabase();
        }

        @Override
        public void windowClosed(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowIconified(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeiconified(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowActivated(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeactivated(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
