/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AddWordsGUI;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mongo.Mong;
import sound.SimpleAudioPlayer;
import sound.SimpleAudioRecorder;

    
/**
 *
 * @author kel31710
 */
class Model {
    private SimpleAudioRecorder     recorder;
    private SimpleAudioPlayer       player;
    private DBCollection            coll;
    private DBObject                currentWord;
    private Mong                    m;
    private String                  wordString;
    private String                  wordPath;
    private String                  cueType;
    private boolean                 editWord;
    

    public Model() throws FileNotFoundException, IOException{
        m = new Mong();
        m.connectMongo("Alden");
        this.coll = m.getCollection("SightWords");
    }

    
    public void recordStart(){
        if(recorder == null || !recorder.isAlive()){    //dont record if a thread already exists
                recorder = new SimpleAudioRecorder(new File(Statics.TMP_FILE_NAME));
                recorder.start();
        }
    }
    
    public void recordStop(){
        if(recorder.isAlive()){     //stop recording only if it is currenly recording
            recorder.stopRecording();
            wordPath = Statics.TMP_FILE_NAME;
        }
        
    }
    
    public void playStart(){
        //only play if there is no player running and there is a valid audio file to play
        if((player == null  || !player.isAlive()) && audioFileValid()){
                    player = new SimpleAudioPlayer(wordPath);
                    player.start();
        }
    }
    
    private boolean audioFileValid(){
        boolean isValid = false;
        //add code here to check if it is a .wav
        if(wordPath != null &&
           wordPath.split("\\.")[1].toUpperCase().equals("WAV")) 
            isValid = true;
        
        return isValid;
        
    }
    
    public List<String> getAllWordsStrings(){
        DBCursor cur = coll.find();
        List<String> words = new ArrayList<String>();
        while(cur.hasNext()){
            words.add((String)cur.next().get(Statics.WORD));
        }
        return words;
    }
    
    public void setWordString(String word){
        this.wordString = word;
    }
    
    public String getWordString(){
        return wordString;
    }
    
    public void setWordPath(String path){
        this.wordPath = path;
    }
   
    
    public void setCueType(String cue){
        this.cueType = cue;
    }
    
    public boolean saveWord(){
        
        //DBObject obj = coll.findOne(new ObjectId(currentWord.get("_id").toString()));
        //if(obj == null) return false;
        if(wordString == "" || wordPath == "") return false;
        
        moveAudioFile();
        currentWord.put(Statics.WORD, wordString);
        currentWord.put(Statics.CUE_PATH, wordPath);
        currentWord.put(Statics.CUE_TYPE, cueType);
       
        coll.save(currentWord);
        return !editWord;
    }
    
    public void deleteWord(){
        coll.remove(currentWord);
        File f = new File(wordPath);
        if(f.exists()){
            f.delete();
        }
    }
    
    public boolean editWord(String word){
        currentWord = coll.findOne(new BasicDBObject(Statics.WORD, word));
        if (currentWord == null) return false;
        
        wordString = (String) currentWord.get(Statics.WORD);
        wordPath = (String) currentWord.get(Statics.CUE_PATH);
        cueType = (String) currentWord.get(Statics.CUE_TYPE);
        editWord = true;
        
        return true;
    }
    
    public void newWord(){
        currentWord = new BasicDBObject();
        wordString = "";
        wordPath = "";
        cueType = "AUDIO";
        editWord = false;
    }
    
    private void moveAudioFile() {
        /*
         * this needs to move the audio file
         * to a file with a more descriptive name
         * in a new folder perhaps "res/audio/currentWordString"
         * also update currentAudioFile
         */
        
        File tempAudio = new File(Statics.TMP_FILE_NAME);
        String newFilePath = Statics.AUDIO_DIR + wordString + ".wav";
        File newFile = new File(newFilePath);
        tempAudio.renameTo(newFile);
        wordPath = newFilePath;
    }
    
    public void stopDB(){
        m.stopMongod();
    }
    
    
}
