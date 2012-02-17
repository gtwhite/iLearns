/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardadder;

import SerialPortComm.CardEvent;
import SerialPortComm.CardReader;
import SerialPortComm.CardReceivedListener;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import layout.TableLayout;
import mongo.Mong;

/**
 *
 * @author kel31710
 */
public class CardAdder extends JFrame implements CardReceivedListener{
    private Mong m;
    private DBCollection letters;
    private String letter;
    private String cardID;
    private JButton save_BTN = new JButton("Save");
    private JTextField letter_Edit = new JTextField();
    private JTextField cardID_Edit = new JTextField();
    
    
    public CardAdder() throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException{
        m = new Mong();
        m.connectMongo("Alden");
        letters = m.getCollection("Letters");
        
        CardReader cr = new CardReader(null);
        cr.addCardListener(this);
        
        cardID_Edit.setEditable(false);
        cardID_Edit.setColumns(10);
        letter_Edit.setColumns(5);
        
        save_BTN.addActionListener(new SaveButton());
        
        double[][] size = {
            {.5, TableLayout.FILL},
            {.5, TableLayout.FILL}
        };
        
        this.setLayout(new TableLayout(size));
        
        
        this.add(letter_Edit, "0, 0, c, c");
        this.add(cardID_Edit, "1, 0, c, c");
        this.add(save_BTN, "1, 1, c, c");
        
        
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public class SaveButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            letter = letter_Edit.getText();
            cardID = cardID_Edit.getText();
            
            BasicDBObject obj = new BasicDBObject();
            obj.put("Letter", letter);
            obj.put("CardID", cardID);
            
            letters.insert(obj);
        }
        
    }
    
    
    @Override
    public void CardReceived(CardEvent event) {
        cardID_Edit.setText(event.card());
    }
    
    
    public static void main(String args[]){
        try {
            new CardAdder();
        } catch (IOException ex) {
            Logger.getLogger(CardAdder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPortException ex) {
            Logger.getLogger(CardAdder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            Logger.getLogger(CardAdder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(CardAdder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
