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

/**
 *
 * @author alber
 */
public abstract class Sendable {
    public NetworkHandler nh;
    public static boolean run = true;
    
    protected Queue<Packet> sync;
    
    public void sendPacket(Packet p) {
        nh.getWriter().println(p.toString());
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    public String toString() {
        return nh.toString();
    }
    
    // Client Thread
    public abstract void sendAuth(String pass) throws IOException;   
    public abstract void setEditor(EditorPage ep);
    
    // MultiHost Thread
    public abstract void receiveAuth();
    
    public abstract void run();
}
