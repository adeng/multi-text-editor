/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.NetworkHandler;
import helpers.Packet;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author alber
 */
public class MultiHostThread implements Runnable {
    NetworkHandler nh;
    public static boolean run = true;
    
    Queue<Packet> sync;

    public MultiHostThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
        sync = new ConcurrentLinkedQueue<Packet>();
        System.out.println("New connection!");
    }

    public boolean receiveAuth(String pass) throws IOException {
        boolean auth = (MainHostThread.pass.equals(pass));
        sendPacket(new Packet("auth", Boolean.toString(auth)));
        return auth;
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    public void sendPacket(Packet p) throws IOException {
        nh.getWriter().writeObject(p);
    }
    
    public String toString() {
        return nh.toString();
    }

    @Override
    public void run() {
        Packet info;
        String in;
        try {
            while(!nh.authenticated) {
                // Not currently authenticated; should run password loop
                info = (Packet) nh.getReader().readObject();

                if (info.getKey().equals("password")) {
                    boolean auth = receiveAuth(info.getValue());
                    if (auth) {
                        nh.authenticated = true;
                        break;
                    }
                }
            }
            
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
            
            nh.cleanUp();
        } catch (Exception ex) {
            System.out.println("Something went wrong :(");
            ex.printStackTrace();
        }
    }
}
