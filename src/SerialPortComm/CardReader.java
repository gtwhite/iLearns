/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SerialPortComm;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author Keith
 */
public class CardReader extends OpenCommPort implements SerialPortEventListener {
    //private String _card;
    private byte[] _cardArr = new byte[8];
    private List _listeners = new ArrayList();
    private int _byteCount = 0;
    private String _card;
    protected BufferedReader ifile;
    
    public CardReader(Frame f) throws IOException, NoSuchPortException, PortInUseException, 
            UnsupportedCommOperationException{
        super(f);
        this.converse();
    }
    
    public synchronized void receivedByte(byte b){
        _cardArr[_byteCount++] = b;
        if(_byteCount > 8){
            _card = _cardArr.toString();
            _fireCardEvent();
        }
    }
    
    public synchronized void receivedCard(String card){
        _card = card;
        _fireCardEvent();
    }

    public synchronized void addCardListener(CardReceivedListener l){
        _listeners.add( l );
    }
    
    public synchronized void removeCardListener(CardReceivedListener l){
        _listeners.remove(l);
    }
    
    private void _fireCardEvent() {
        CardEvent ce = new CardEvent(this, _card);
        Iterator listeners = _listeners.iterator();
        while(listeners.hasNext()){
           ((CardReceivedListener)listeners.next()).CardReceived(ce);
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        String line;
        try{
            line = ifile.readLine().trim();
            receivedCard(line);
            System.out.println(line);
            if(line == null){
                System.out.println("EOF on serialport");
            }
        }catch(IOException ex){
            System.err.println("IO Exception: " + ex);
        }
    }
    
    @Override
    protected void converse() throws IOException {
        if(!(thePort instanceof SerialPort)){
            System.err.println("Serial Port Needed");
            System.exit(1);
        }
        ((SerialPort)thePort).notifyOnDataAvailable(true);
        try{
            ((SerialPort)thePort).addEventListener(this);
        }catch(TooManyListenersException e){
            System.err.println("there are too many listeers for this " + e);
            System.exit(0);
        }
        
        ifile = new BufferedReader(new InputStreamReader(is));  
        //System.out.println(ifile.readLine());
    }
    
}
