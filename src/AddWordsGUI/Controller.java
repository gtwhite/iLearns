/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AddWordsGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author kel31710
 */
public class Controller{

    private View view;
    private Model model;
    
    public Controller(View view, Model model){
        this.view = view;
        this.model = model;
        
        RecordButtonController rbc = new RecordButtonController();
        view.addAudioPlayListener(new PlayButtonController());
        view.addRecStartListener(rbc);
        view.addRecStopListener(rbc);
        view.addNewWordBtnListener(new NewWordController());
        view.addSaveBtntListener(new SaveButtonController());
        view.addDeleteWordBtnListener(new DeleteButtonController());
        populateTable(model.getAllWordsStrings());
        view.addTableSelectionListener(new TableSelectionListener());
        view.setWindowListener(new WindowController());
    }
    
    private void populateTable(List<String> words){
        if (words == null) return;
        Iterator it = words.iterator();
        while(it.hasNext()){
            view.addWordRow((String) it.next());
        }
        //model.setTableSize(view.getTableLength());
    }
    
    private class RecordButtonController implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(Statics.REC_START_CMD.equals(ae.getActionCommand())){
                model.recordStart();
                view.setStopRecord();
                //view.setRecordingText("Recording...");
            }else if(Statics.REC_STOP_CMD.equals(ae.getActionCommand())){
                model.recordStop();
                view.setStartRecord();
                //view.setRecordingText("Press to Start Recording");
            }
        }
    }
        
    private class PlayButtonController implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(Statics.PLAY_START_CMD.equals(ae.getActionCommand())){ 
                    model.playStart();
            }   
        }
    }
    
    private class TableSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
 
            if(e.getValueIsAdjusting() || lsm.isSelectionEmpty()) return;
                int index = lsm.getMinSelectionIndex();
                //model.setCurrentIdex(index);
                model.editWord(view.getWord(index));
                view.setWordText(model.getWordString());
                view.setWordTextEditable(false);
            }
        }
    
    private class NewWordController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
           // model.setCurrentIdex(Statics.NEW_ROW);
            view.setWordText("");
            view.setWordTextEditable(true);
            model.newWord();
        }
                
    }

    private class SaveButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //model.setCurrentWord(view.getWordText());
            model.setWordString(view.getWordText());
            if(model.saveWord())
                {
                    view.addWordRow(model.getWordString());
                }
            
        }

    }
    
    private class DeleteButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            model.deleteWord();
            view.deleteWord(model.getWordString());
        }

    }
    private class WindowController implements WindowListener {

        @Override
        public void windowOpened(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowClosing(WindowEvent we) {
            System.out.println(view.getContentPane().getSize().toString());
            model.stopDB();
            //model.saveDatabase();
        }

        @Override
        public void windowClosed(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowIconified(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeiconified(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowActivated(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void windowDeactivated(WindowEvent we) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}