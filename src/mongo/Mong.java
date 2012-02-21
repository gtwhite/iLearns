/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kel31710
 */
public class Mong {
    Process proc;
    DB db;
    
    public Mong() throws IOException{
        startMongod();
        
    }
    
    private void startMongod() throws IOException{
        if(isWindows()){
            String dir = new File(".").getCanonicalPath();
            ProcessBuilder clean = new ProcessBuilder(dir + "\\bin\\mongod.exe","--repair", "--dbpath", dir+ "\\data\\db");
            proc = clean.start();
            try {
                proc.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mong.class.getName()).log(Level.SEVERE, null, ex);
            }
            ProcessBuilder pb = new ProcessBuilder(dir + "\\bin\\mongod.exe","--dbpath", dir+ "\\data\\db");//, "--fork", "--logpath", dir + "\\data\\log\\mongodb.log", "--logappend");   //start mongo daemon
            proc = pb.start();  
        }else{
            ProcessBuilder clean = new ProcessBuilder("./bin/mongod","--repair", "--dbpath", "./data/db");
            proc = clean.start();
            try {
                proc.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Mong.class.getName()).log(Level.SEVERE, null, ex);
            }
            ProcessBuilder pb = new ProcessBuilder("./bin/mongod","--dbpath", "./data/db");//, "--fork", "--logpath", "./data/log/mongodb.log", "--logappend");   //start mongo daemon
            proc = pb.start();
        }
    }
    
    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }
    
    public void stopMongod(){
        proc.destroy();
        
        System.out.println("Closed with Exit Code: "+ proc.exitValue());
    }
    
    public void connectMongo(String hostname, String DBname) throws UnknownHostException{
        Mongo m = new Mongo(hostname);
        connectMongo(m, DBname);
    }
    
    public void connectMongo(String DBname) throws UnknownHostException{
        Mongo m = new Mongo();
        connectMongo(m, DBname);
    }
    
    private void connectMongo(Mongo m, String DBname){
        db = m.getDB(DBname);
    }
    
    public DBCollection getCollection(String collectionName){
        return db.getCollection(collectionName);
    }
}
