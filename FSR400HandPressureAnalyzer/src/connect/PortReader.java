/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import common.SensorCache;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Dashboard;
import ui.Notification;

/**
 *
 * @author Ivantha
 */
public class PortReader implements SerialPortEventListener {

    private Enumeration ports;
    private CommPortIdentifier cpi;
    private SerialPort serialPort;
    private BufferedReader input;

    public void startReading() {
        cpi = null;
        ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            cpi = (CommPortIdentifier) ports.nextElement();

            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                this.testPort();
            }
        }
        
        if(Dashboard.isPortReaderNotStarted()){
            Notification.display("Device cannot be found");
        }
    }

    public void testPort() {
        try {
            serialPort = (SerialPort) cpi.open("FSR400PortReader", 2000);
        } catch (PortInUseException ex) {
            Logger.getLogger(PortReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(PortReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(PortReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < 100; i++) {
            try {
                String line = input.readLine();
                if (line.length() == 12 && line.charAt(0) == '[' && line.charAt(11) == ']' && line.charAt(5) == '-' && line.charAt(6) == '-') {
                    serialPort.addEventListener(this);
                    serialPort.notifyOnDataAvailable(true);
                    Dashboard.setPortReaderNotStarted(false);
                    Notification.display("Device connected successfully");
                    break;
                }
            } catch (IOException | TooManyListenersException ex) {
                Logger.getLogger(PortReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                SensorCache.setSensors(input.readLine());
            } catch (IOException ex) {
                Logger.getLogger(PortReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
