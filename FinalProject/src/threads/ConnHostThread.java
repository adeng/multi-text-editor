/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import gui.EditorPage;
import helpers.Packet;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alber
 */
public class ConnHostThread extends Sendable implements Runnable {
    public boolean run = true;
    
    public ConnHostThread() {
        sync = new ConcurrentLinkedQueue<Packet>();
    }
    
    @Override
    public void run() {
        while(run) {
            while(sync.size() > 0) {
                Packet p = sync.remove();
                System.out.println(p);
                for(MultiHostThread mht : MainHostThread.sockets) {
                    mht.sendPacket(p);
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
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
