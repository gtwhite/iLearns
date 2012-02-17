/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SerialPortComm;

import gnu.io.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author kel31710
 */
public class OpenCommPort {
    public static final int TIMEOUTSECONDS =30;
    public static final int BAUD = 9600;
    protected Frame parent; //parent Frame for chooser
    protected DataInputStream is; //input stream
    protected PrintStream os; //output stream
    protected String rsp; //last readline
    protected boolean debug = true; //debug flag
    CommPortIdentifier thePortID; //chosen Port
    CommPort thePort; //the actual port
    
    public OpenCommPort(Frame f) throws IOException, NoSuchPortException, PortInUseException, 
        UnsupportedCommOperationException{
            SerialPortChooser chooser = new SerialPortChooser(null);
            String portName = null;
            do{
                chooser.setVisible(true);
                portName = chooser.getSelectedName();
                if(portName == null)
                    System.out.println("No Port Selected");
            }while(portName == null);
            thePortID = chooser.getPortIdentifier();

            //open the port
            System.out.println("Opening " + thePortID.getName());
            if(thePortID.getPortType() == CommPortIdentifier.PORT_SERIAL){
                thePort = thePortID.open("iLearn", TIMEOUTSECONDS * 1000);
                SerialPort myPort = (SerialPort) thePort;
                myPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            }else{
                throw new IllegalStateException("Wrong Port Type");
            }
            
            //get input and output streams
            try{
                is = new DataInputStream(thePort.getInputStream());
                
            }catch(IOException e){
                System.err.println("Cant open input stream for " + thePort.getName());
            }
            
            os = new PrintStream(thePort.getOutputStream(), true);
    }       
    
    protected void converse() throws IOException {
        System.out.println("Ready to read/write");
        //implemented later on
        
        //clean it up
        if(is != null)
            is.close();
        os.close();
    }
    
        
      
    
    
    
    
}
