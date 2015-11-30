/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.Packet;
import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alber
 */
public class ConnHostThread implements Runnable {
    public boolean run = true;
    
    private Queue<Packet> sync;
    
    public ConnHostThread() {
        sync = new ConcurrentLinkedQueue<Packet>();
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    @Override
    public void run() {
        while(run) {
            while(sync.size() > 0) {
                for(MultiHostThread mht : MainHostThread.sockets) {
                    mht.sendPacket(sync.remove());
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
