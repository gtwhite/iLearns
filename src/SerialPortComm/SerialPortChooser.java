/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SerialPortComm;

/**
 *
 * @author kel31710
 */
import gnu.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SerialPortChooser extends JDialog implements ItemListener{
    protected HashMap map = new HashMap(); //map for ports
    protected String selectedPortName;
    protected CommPortIdentifier selectedPortIdentifier;
    protected JComboBox serialPortChoices; //available choices
    protected JLabel selectedChoice; //label for choice
    protected final int PAD =5; //padding
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        //get the name
        selectedPortName = (String)((JComboBox)e.getSource()).getSelectedItem();
        selectedPortIdentifier = (CommPortIdentifier)map.get(selectedPortName);
        selectedChoice.setText(selectedPortName);
    }
    
    public String getSelectedName(){
        return selectedPortName;
    }
    
    public CommPortIdentifier getPortIdentifier(){
        return selectedPortIdentifier;
    }

    public SerialPortChooser(JFrame parent){
        super(parent, "Choose a Port", true);
        makeGUI();
        populate();
        finishGUI();
    }

    private void makeGUI() {
        Container cp = getContentPane();
        JPanel itemPane = new JPanel();
        cp.add(BorderLayout.CENTER, itemPane);
        itemPane.setLayout(new GridLayout(0,2,PAD,PAD));
        
        itemPane.add(new JLabel("Seriel Ports",JLabel.RIGHT));
        serialPortChoices = new JComboBox();
        itemPane.add(serialPortChoices);
        
        itemPane.add(new JLabel("Your Choice: ", JLabel.RIGHT));
        itemPane.add(selectedChoice = new JLabel());
        
        JButton okButton;
        cp.add(BorderLayout.SOUTH, okButton = new JButton("OK"));
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SerialPortChooser.this.dispose();
            }
        });
        
    }
    
    private void populate() {
        Enumeration pList = CommPortIdentifier.getPortIdentifiers(); //get a list of ports
        while (pList.hasMoreElements()){
            CommPortIdentifier cpi = (CommPortIdentifier)pList.nextElement();
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL && cpi.getName().contains("tty"))
                serialPortChoices.addItem(cpi.getName());
                map.put(cpi.getName(), cpi);
            System.out.println(cpi.getName()+ " is " + cpi.getPortType());
        }
        selectedPortName = (String)serialPortChoices.getItemAt(0);
        selectedPortIdentifier = (CommPortIdentifier)map.get(selectedPortName);
        selectedChoice.setText(selectedPortName);
    }
    
    private void finishGUI() {
        serialPortChoices.addItemListener(this);
        pack();
        //addWindowListener(new WindowCloser(this, true));
    }

}
