/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SightWordGame;

import AddWordsGUI.Statics;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import mongo.Mong;
import sound.SimpleAudioPlayer;

/**
 *
 * @author kel31710
 */
public class GameModel {
    int currentPosition;
    String currentWordLetters;
    SimpleAudioPlayer player;
    char lastEntered;
    int wrongGuesses = 0;
    private Mong m;
    private DBCursor sightWords;
    private DBObject currentWord;
    private DBCollection letterDB;
    
    
    public GameModel() throws IOException{
        m = new Mong();
        m.connectMongo("Alden");
        DBCollection wordsCollection = m.getCollection("SightWords");
        sightWords = wordsCollection.find();
        letterDB = m.getCollection("Letters");
    }

    public String setNewWord(){
       currentWord = sightWords.next();
       currentWordLetters= (String) currentWord.get(Statics.WORD);
       currentPosition = 0;
       return currentWordLetters;
       //System.out.println("The Word is: " + currentWordLetters);
    }
    
    public boolean wordComplete(){
        return (currentPosition == (currentWordLetters.length()));
    }
    
    public boolean gameComplete(){
        return !sightWords.hasNext();
    }
    
    public void playCurrentWordAudio(){
        //only play if there is no player running and there is a valid audio file to play
        if((player == null  || !player.isAlive())){
                    player = new SimpleAudioPlayer((String) currentWord.get(Statics.CUE_PATH));
                    player.start();
        }
    }
    
    public String startGame(){
        this.setNewWord();
        this.playCurrentWordAudio();
        return currentWordLetters;
    }
    
    
    public char getLetter(){
        return lastEntered;
    }
    
    public boolean letterCorrect(String cardID){
        boolean correct = false;
        BasicDBObject toFind = new BasicDBObject();
        toFind.put("CardID", cardID);
        DBObject letterObj = letterDB.findOne(toFind);
        if(letterObj == null) return false;
        String letter = (String) letterObj.get("Letter");
        
        
        
        lastEntered = letter.toUpperCase().charAt(0);
        if(lastEntered == currentWordLetters.toUpperCase().charAt(currentPosition)){
            correct = true;
            currentPosition++;
        }else wrongGuesses++;
        
        return correct;
    }
    
    public int getWrongGuesses(){
        return wrongGuesses;
    }

    public void stopDB() {
        m.stopMongod();
    }
    
}
