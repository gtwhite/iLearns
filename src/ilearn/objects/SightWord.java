/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ilearn.objects;

import com.mongodb.BasicDBObject;

/**
 *
 * @author kel31710
 */
public class SightWord{

    private String word = "";
    private Cue cue;
        
    public SightWord(String word, Cue cue){
        this.word = word;
        this.cue = cue;
    }
    
    public String getWord(){
        return this.word;
    }
    
    public String getCuePath(){
        return cue.getPath();
    }
    
    public Cue.CueType getCueType(){
        return cue.getCue();
    }
    
    public BasicDBObject getDBObject(){
        BasicDBObject dbob = new BasicDBObject();
        dbob.put("Word", this.word);
        dbob.put("Cue", this.cue.getDBObject());
        return dbob;
    }
    
    
    
    public static class Cue{
        private String path;
        private CueType cuetype;

        public enum CueType{
            PICTURE, VIDEO, AUDIO, TEXT_TO_SPEACH
        }

        public Cue(String path, CueType cuetype) {
            this.path = path;
            this.cuetype = cuetype;
        }

        private CueType getCue(){
            return this.cuetype;
        }

        private String getPath(){
            return this.path;
        }

        private BasicDBObject getDBObject(){
           BasicDBObject dbob = new BasicDBObject();
           dbob.put("Path", path);
           dbob.put("CueType", cuetype.toString());

           return dbob;
        }
    }
}
