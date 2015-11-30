/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.NetworkHandler;
import helpers.Packet;
import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 *
 * @author alber
 */
public class ClientThread implements Runnable {
    public static NetworkHandler nh;
    private boolean authenticated = false;
    private boolean run = true;
    
    private Queue<Packet> sync;
    
    // Server constructor
    public ClientThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
        
        sync = new ConcurrentLinkedQueue<Packet>();
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    // Auth method for client
    public void sendAuth(String pass) throws IOException {
        sendPacket(new Packet("password", pass));
    }
    
    public void sendPacket(Packet p) {
        nh.getWriter().println(p.toString());
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
            
            // normal ops
            while(run) {
                while(sync.size() > 0) {
                    sendPacket(sync.remove());
                }
                
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
