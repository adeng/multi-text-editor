/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import gui.EditorPage;
import helpers.NetworkHandler;
import helpers.Packet;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author alber
 */
public class MultiHostThread extends Sendable implements Runnable {

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

    @Override
    public void run() {
        Packet info;
        String in;
        try {
            while(!nh.authenticated) {
                // Not currently authenticated; should run password loop
                in = nh.getReader().readLine();
                System.out.println(in);
                info = new Packet(in);

                if (info.getKey().equals("password")) {
                    boolean auth = receiveAuth(info.getValue());
                    if (auth) {
                        nh.authenticated = true;
                        break;
                    }
                }
            }
            
            while(run) {
                // Send all packets
                while(sync.size() > 0) {
                    sendPacket(sync.remove());
                }
                
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
            System.out.println("Something went wrong :(");
            ex.printStackTrace();
        }
    }

    // Don't do anything
    @Override
    public void sendAuth(String pass) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receiveAuth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
