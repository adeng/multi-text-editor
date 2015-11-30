/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.NetworkHandler;
import helpers.Packet;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class MainHostThread implements Runnable {    
    private boolean authenticated = false;
    public static boolean run = true;
    private ServerSocket ss;
    
    // Using package-private access level
    public static ArrayList<MultiHostThread> sockets;
    
    public static String pass;
    
    // Server constructor
    public MainHostThread(int port, String pass) throws IOException {
        ss = new ServerSocket(port);
        this.pass = pass;
    }

    @Override
    public void run() {
        while(run) {
            try {
                sockets.add(new MultiHostThread(new NetworkHandler(ss.accept())));
                Thread n = new Thread(sockets.get(sockets.size()-1));
                n.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}