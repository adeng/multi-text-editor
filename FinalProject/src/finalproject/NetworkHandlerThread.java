/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author alber
 */
public class NetworkHandlerThread extends NetworkHandler implements Runnable {    
    private boolean authenticated = false;
    private int pass;
    private boolean run = true;
    
    // Server constructor
    public NetworkHandlerThread(int port, int pass) throws IOException {
        super(port);
        this.pass = pass;
        
        sSock = new ServerSocket(port);
        sock = sSock.accept();
        initIO();
    }
    
    // Client constructor
    public NetworkHandlerThread(String ip, int port, int pass) throws IOException {
        super(ip, port);
        this.pass = pass;
        
        sock = new Socket(ip, port);
        initIO();
    }

    @Override
    public void run() {
    }
}
