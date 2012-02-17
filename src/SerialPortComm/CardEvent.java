/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SerialPortComm;

import java.util.EventObject;

/**
 *
 * @author Keith
 */
public class CardEvent extends EventObject {
    private String _card;
    
    public CardEvent(Object source, String card){
        super(source);
        _card = card;
    }
    
    public String card(){
        return _card;
    }
 
    
}
