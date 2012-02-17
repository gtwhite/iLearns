/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import ilearn.objects.SightWord;
import ilearn.objects.SightWord.Cue;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author kel31710
 */
public class Monga {
    private Process proc;
    private DB  db;
    private DBCollection coll;
    /**
     * @param args the command line arguments
     */
    
    public Monga() throws InterruptedException{
        try {
            this.startDB();
            /*
            BasicDBObject doc = new BasicDBObject();
            doc.put("hello", "world");
            coll.insert(doc);
            doc = new BasicDBObject();
            doc.put("hello", "child");
            doc.put("my", "world");
            coll.insert(doc);
            
            
            
            //DBObject myDoc = coll.findOne();
            
            DBCursor cur = coll.find();
            
            while(cur.hasNext()){
                System.out.println(cur.next());
            }
            
            System.out.println("**************************");
            
            BasicDBObject query = new BasicDBObject();
            query.put("hello", "world");
            
            cur = coll.find(query);
            */
            
            Cue cue = new Cue("test/path", Cue.CueType.AUDIO);
            System.out.println(Cue.CueType.AUDIO);
            SightWord sw = new SightWord("Hello", cue);
            
            coll.insert(sw.getDBObject());
            
            Cue cue2 = new Cue("test/path", Cue.CueType.AUDIO);           
            SightWord sw2 = new SightWord("Hello2", cue);
            coll.insert(sw2.getDBObject());
            
            
            BasicDBObject o = new BasicDBObject();
            o.put("MY", "World");
            o.put("Cue", "Suck it");
            coll.insert(o);
            
            //o = new BasicDBObject();
            
            BasicDBObject test = new BasicDBObject();
            
            
            System.out.println(coll.find(new BasicDBObject(), new BasicDBObject("Cue", 1)));//, new BasicDBObject("Cue", 1)).next().get("Cue"));
            /*DBObject obj = coll.findOne(o);
            obj.put("Word", "goodbye");
            
            coll.save(obj);
            
            DBCursor cur = coll.find();
            while(cur.hasNext()){
                System.out.println(cur.next());
            }
           // System.out.println(cur.next());
            */
            
            
            
            
            this.stopDB();
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Monga.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MongoException ex) {
                Logger.getLogger(Monga.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(Monga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void findSightWord(String sightWord){
            BasicDBObject query = new BasicDBObject();
            query.put("Word", sightWord);
            DBObject obj = coll.findOne(query);
            
        }
        public void startDB() throws IOException{
            ProcessBuilder pb = new ProcessBuilder("./bin/mongod","--dbpath", "./data/db");//, "--fork", "--logpath", "./data/log/mongodb.log", "--logappend");   //start mongo daemon
            //ProcessBuilder pb = new ProcessBuilder("open","/Applications/TextEdit.app");
            proc = pb.start();
            Mongo m = new Mongo();
            
            db = m.getDB("mydb");

            /*Set<String> colls = db.getCollectionNames();

            for (String s : colls) {
                System.out.println(s);
            }*/
            
            coll = db.getCollection("test");
            
            coll.drop();
            
            /*asicDBObject index = new BasicDBObject();
            index.put("Word", "1");
            coll.ensureIndex(index, "1", true);*/
        }
        
        public void stopDB() throws InterruptedException{            
            proc.destroy();
            System.out.println(proc.waitFor());
        }
    
    public static void main(String[] args) throws InterruptedException{
        //System.out.println(new java.io.File("").getCanonicalPath());
        new Monga();
    }
}
