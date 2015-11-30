/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import finalproject.NetworkHandler;
import finalproject.Packet;
import java.io.*;
import java.net.Socket;
import java.util.Queue;


/**
 *
 * @author alber
 */
public class ClientThread implements Runnable {
    public NetworkHandler nh;
    private boolean authenticated = false;
    private boolean run = true;
    
    private Queue<Packet> syncQueue;
    
    // Server constructor
    public ClientThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
    }
    
    // Auth method for client
    public void sendAuth(String pass) throws IOException {
        nh.getWriter().println(new Packet("password", pass).toString());
    }

    @Override
    public void run() {
        Packet info;
        String in;
        try {
            while(!nh.authenticated) {
                in = nh.getReader().readLine();
                System.out.println(in);
                info = new Packet(in);
                
                if (info.getKey().equals("auth")) {
                    if (Boolean.parseBoolean(info.getValue())) {
                        nh.cleanUp();
                        nh.authenticated = true;
                        break;
                    }
                }
            }
            
            while(run) {
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
