/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.Packet;
import java.util.Queue;

/**
 *
 * @author Albert
 */
public class SendSyncThread implements Runnable {
    public boolean run = true;
    public Queue<Packet> sync;
    Sendable parent;
    
    public SendSyncThread(Queue<Packet> sync, Sendable parent) {
        this.sync = sync;
        this.parent = parent;
    }
    public void run() {
        while(run) {
            // Send all packets
            while(sync.size() > 0) {
                Packet p = sync.remove();
                System.out.println(p);
                parent.sendPacket(p);
            }
            
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
