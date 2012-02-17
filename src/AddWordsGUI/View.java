/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AddWordsGUI;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import layout.TableLayout;

/**
 *
 * @author kel31710
 */
public class View extends JFrame{
                
        private JButton startBtn;
        private JButton stopBtn;
        private JButton playBtn;
        private JButton saveBtn;
        private JButton newRowBtn;
        private JButton deleteWordBtn;
        private JLabel recordingLbl                 = new JLabel("Press Start To Record", SwingConstants.CENTER);
        private JLabel playAudioLbl                 = new JLabel("No Audio Available");
        private JTextArea editWordTxt               = new JTextArea();
        private JFileChooser jfc;
        private Model model;
        private DefaultTableModel wordTableModel    = new DefaultTableModel();
        private JTable wordTable;
        
        public View(Model model){
            this.model = model;
            MakeGUI();
        }
        
        private void MakeGUI() {
            startBtn = new JButton("Start Record");
            stopBtn = new JButton("Stop Record");
            playBtn = new JButton("Play Recording");
            saveBtn = new JButton("Save Word");
            newRowBtn = new JButton("New Word");
            deleteWordBtn = new JButton ("Delete Word");
            JLabel editWordLbl = new JLabel("Edit Word: ", SwingConstants.CENTER);
            
            wordTable = new JTable(wordTableModel);
            
            wordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTable.setPreferredScrollableViewportSize(new Dimension(100, 300));        
            JScrollPane jsp = new JScrollPane(wordTable);
            editWordTxt.setRows(1);
            editWordTxt.setColumns(10);
            this.setWordTextEditable(false);
            editWordTxt.setVisible(false);
            
            wordTableModel.addColumn("Words");
            
            startBtn.setActionCommand(Statics.REC_START_CMD);
            stopBtn.setActionCommand(Statics.REC_STOP_CMD);
            playBtn.setActionCommand(Statics.PLAY_START_CMD);
            saveBtn.setActionCommand(Statics.SAVE_WORD_CMD);
            newRowBtn.setActionCommand(Statics.ADD_WORD_CMD);
            deleteWordBtn.setActionCommand(Statics.DELETE_WORD_CMD);
            
            int border = 10;
            double f  = TableLayout.FILL;
            double p    = TableLayout.PREFERRED;
            double[][] size ={
                {border, f, f, border},
                {p, f, f, f, p}
            };
            
            this.setPreferredSize(new Dimension(490, 472));
            this.setResizable(false);
            
            this.setLayout(new TableLayout(size));
            stopBtn.setVisible(false);
            
            
            this.add(editWordLbl, "1, 1, c, c"); //row 1
            this.add(editWordTxt, "2, 1, l, c");
            
            //this.add(recordingLbl, "1, 2, l, c"); //row 2
            this.add(playBtn, "2, 2, l, c");    
            this.add(startBtn, "1, 2, r, c");
            this.add(stopBtn, "1, 2, r, c");
            
            this.add(saveBtn, "1, 3, l, c"); // row 4
            this.add(deleteWordBtn, "1, 3, r, c");
            this.add(newRowBtn, "2, 3, l, c");
            
            this.add(jsp, "1, 4, 2, 4");
            /*
            
            
            
            this.add(playAudioLbl, "1,3, t, t");
            this.add(jsp, "2, 3, c, c");
            
            this.add(newRowBtn, "2, 4, c, c");
            
            
             * 
             */
            
            //setUpFileChooser();
            
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        
        private void setUpFileChooser(){
            jfc = new JFileChooser(".");
            FileNameExtensionFilter wav = new FileNameExtensionFilter("WAV", ".wav");
            jfc.addChoosableFileFilter(wav);
            jfc.setFileFilter(wav);
        }
        
        public void setWindowListener(WindowListener wl){
            this.addWindowListener(wl);
        }
        
        public void addRecStartListener(ActionListener a){
           startBtn.addActionListener(a);
        }
        
        public void addSaveBtntListener(ActionListener a){
           saveBtn.addActionListener(a);
        }
        
        public void addNewWordBtnListener(ActionListener a){
           newRowBtn.addActionListener(a);
        }
        
        public void addRecStopListener(ActionListener a){
           stopBtn.addActionListener(a);
        }
        public void addAudioPlayListener(ActionListener a){
           playBtn.addActionListener(a);
        }
        
        public void addDeleteWordBtnListener(ActionListener a){
           deleteWordBtn.addActionListener(a);
        }
        
        public void addTableModelListener(TableModelListener tml){
            wordTableModel.addTableModelListener(tml);
        }
        
        public void addTableSelectionListener(ListSelectionListener tableSelectionListener) {
            wordTable.getSelectionModel().addListSelectionListener(tableSelectionListener);
        }

        public void addWordRow(String word){
            wordTableModel.addRow(new Object[]{word});
        }
        
        public String getWord(int index){
            return (String)wordTableModel.getValueAt(index, 0);
        }
        
        public void deleteWord(String word){
            //wordTableModel.removeRow(index);
            Vector w = new Vector<String>();
            w.add(word);
            int index = wordTableModel.getDataVector().indexOf(w);
            wordTableModel.removeRow(index);
        }
        
        public void setRecordingText(String text) {
            recordingLbl.setText(text);
        }
        
        public void setWordText(String text){
            editWordTxt.setText(text);
        }
        
        public void setWordTextEditable(boolean editable){
            editWordTxt.setEditable(editable);
            editWordTxt.setEnabled(editable);
            editWordTxt.setVisible(true);
        }
        
        public String getWordText(){
            return editWordTxt.getText();
        }
        
        public int getTableLength(){
            return wordTableModel.getRowCount();
        }
        
        public void setStartRecord(){
            stopBtn.setVisible(false);
            startBtn.setVisible(true);
        }
        
        public void setStopRecord(){
            stopBtn.setVisible(true);
            startBtn.setVisible(false);
        }


}
