/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SightWordGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import layout.TableLayout;
import layout.TableLayoutConstants;

/**
 *
 * @author kel31710
 */
public class GameView extends JFrame {
    private JLabel correctLetters;
    private JPanel lastLetterBox;
    private JLabel lastLetter;
    private final JLabel wrong = new JLabel("Incorrect Guesses");
    private JLabel wrongLetters;
    private GameModel model;
    
    public GameView(GameModel model){
        this.model = model;
        MakeGUI();
    }

    private void MakeGUI() {
        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.setBounds(0, 0, screensize.width, screensize.height);
        
        correctLetters = new JLabel("");
        correctLetters.setFont(new Font("Times New Roman", Font.PLAIN, 72));
        wrong.setFont(new Font("Times New Roman", Font.PLAIN, 72));
        
        wrongLetters = new JLabel("");
        wrongLetters.setFont(new Font("Times New Roman", Font.PLAIN, 72));
        lastLetterBox = new JPanel();
        lastLetterBox.setPreferredSize(new Dimension(500,500));
        lastLetterBox.setBackground(Color.WHITE);
        lastLetter = new JLabel("");
        lastLetter.setSize(500, 200);     
        lastLetter.setFont(new Font("Times New Roman", Font.BOLD, 300));
        //lastLetterBox.add(lastLetter);
        
        
        int padding = 5;
        double fill = TableLayout.FILL;
        double p = TableLayout.PREFERRED;
        double[][] size = {
            {padding, fill, padding},
            {100, fill, .10}
        };
        
        this.setLayout(new TableLayout(size));
        
        this.add(correctLetters, "1, 0, c, b");
        this.add(lastLetter, "1, 1, c, c");
        this.add(lastLetterBox, "1, 1, c, c");
        this.add(wrong, "1, 2, l, c");
        this.add(wrongLetters, "1, 2, r, c");
        
        
        //this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    }
    
    public void setGameOver(){
        lastLetterBox.setVisible(false);
        lastLetter.setText("You WIN!");
    }
    
    public void setLastLetter(String letter, boolean correct){

        if(correct){
            lastLetterBox.setBackground(Color.GREEN);
            correctLetters.setText(correctLetters.getText().replaceFirst("_", letter));
            
        }else{
            lastLetterBox.setBackground(Color.RED);
            //wrongLetters.setText(wrongLetters.getText() + " " + letter);
            wrongLetters.setText(String.valueOf(model.getWrongGuesses()));
        }
        
        lastLetter.setText(letter);
    }
    
    public void intializeWord(int wordLength){
        lastLetterBox.setBackground(Color.WHITE);
        setCorrectLetters(wordLength);
        lastLetter.setText("");
        wrongLetters.setText(String.valueOf(model.getWrongGuesses()));
        
    }

    private void setCorrectLetters(int wordLength) {
        String s = "";
        for(int i =0; i < wordLength; i++){
            s += "_ ";
        }
        correctLetters.setText(s);
    }
    
    public void setWindowListener(WindowListener wl){
        this.addWindowListener(wl);
    }
    
}
