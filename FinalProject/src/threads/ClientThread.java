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
public class ClientThread extends Sendable implements Runnable {        
    // Server constructor
    public ClientThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
        
        sync = new ConcurrentLinkedQueue<Packet>();
    }
    
    // Auth method for client
    public void sendAuth(String pass) throws IOException {
        sendPacket(new Packet("password", pass));
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
                        nh.authenticated = true;
                        break;
                    }
                }
            }
           
            new Thread(new SendSyncThread(sync, this)).start();
            
            // normal ops
            while(run) {                
                // Receive all packets
                in = nh.getReader().readLine();
                while(in != null) {
                    info = new Packet(in);
                    processPacketData(info);
                    in = nh.getReader().readLine();
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

    // Don't do anything
    @Override
    public void receiveAuth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
