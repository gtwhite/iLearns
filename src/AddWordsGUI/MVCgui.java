/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AddWordsGUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author kel31710
 */
public class MVCgui {
    private String swl = "sightwordlist.xml";
    public MVCgui(){
        try {
            Model model = new Model();
            View view = new View(model);
            Controller controller = new Controller(view, model);
        } catch (IOException ex) {
            Logger.getLogger(MVCgui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        new MVCgui();
    }
}
