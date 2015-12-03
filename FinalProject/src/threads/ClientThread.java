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
    EditorPage ep;
    
    private Queue<Packet> sync;
    
    // Server constructor
    public ClientThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
        
        sync = new ConcurrentLinkedQueue<Packet>();
    }
    
    public void setEditor(EditorPage ep) {
        this.ep = ep;
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    // Auth method for client
    public void sendAuth(String pass) throws IOException {
        sendPacket(new Packet("password", pass));
    }
    
    public void sendPacket(Packet p) throws IOException {
        nh.getWriter().writeObject(p);
    }

    @Override
    public void run() {
        Packet info;
        try {
            while(!nh.authenticated) {
                info = (Packet) nh.getReader().readObject();
                
                if (info.getKey().equals("auth")) {
                    if (Boolean.parseBoolean(info.getValue())) {
                        nh.authenticated = true;
                        break;
                    }
                }
            }
            
            // normal ops
            while(run) {
                // Send all packets
                while(sync.size() > 0) {
                    sendPacket(sync.remove());
                }
                
                // Receive all packets
                info = (Packet) nh.getReader().readObject();
                while(info != null) {
                    switch(info.getKey()) {
                        case "init":
                            ep.setAllText(info.getValue());
                            break;
                    }
                    info = (Packet) nh.getReader().readObject();
                }               
                
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            nh.cleanUp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
