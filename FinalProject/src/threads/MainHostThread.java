/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import gui.EditorPage;
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
    public static int port;
    public static boolean run = true;
    private ServerSocket ss;
    EditorPage ep;
    
    public static ArrayList<MultiHostThread> sockets;
    
    public static String pass;
    
    // Server constructor
    public MainHostThread(int port, String pass) throws IOException {
        sockets = new ArrayList<MultiHostThread>();
        ss = new ServerSocket(port);
        this.port = port;
        this.pass = pass;
        this.ep = ep;
    }
    
    public void setEditor(EditorPage ep) {
        this.ep = ep;
    }
    
    public Packet welcomePacket() {
        return new Packet("init", ep.getAllText());
    }

    @Override
    public void run() {
        while(run) {
            try {
                Socket s = ss.accept();
                NetworkHandler nh = new NetworkHandler(s);
                MultiHostThread mht = new MultiHostThread(nh);
                mht.addPacket(welcomePacket());
                sockets.add(mht);
                Thread n = new Thread(sockets.get(sockets.size()-1));
                n.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}