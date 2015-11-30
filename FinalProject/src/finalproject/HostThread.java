/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author alber
 */
public class HostThread extends NetworkHandler implements Runnable {    
    private boolean authenticated = false;
    private boolean run = true;
    
    private Queue<Packet> syncQueue;
    
    // Server constructor
    public HostThread(int port, int pass) throws IOException {
        super(port);
        
        sSock = new ServerSocket(port);
        sock = sSock.accept();
        initIO();
    }

    @Override
    public void run() {
        while(run) {
            
        }
    }
}
