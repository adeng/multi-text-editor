/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import gui.EditorPage;
import helpers.NetworkHandler;
import helpers.Packet;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Queue;

/**
 *
 * @author alber
 */
public abstract class Sendable {
    public EditorPage ep;
    public NetworkHandler nh;
    public static boolean run = true;
    
    protected Queue<Packet> sync;
    
    public void sendCharacter(char c, int position) {
        sync.add(new Packet("keystroke", position + ":" + c));
    }
    
    public void pasteCharacter(char c, int position) {
        sync.add(new Packet("paste", position + ":" + c));
    }
    
    public void sendPacket(Packet p) {
        System.out.println(p);
        nh.getWriter().println(p.toString());
    }
    
    public void setEditor(EditorPage ep) {
        this.ep = ep;
    }
    
    public void addPacket(Packet p) {
        sync.add(p);
    }
    
    public void processPacketData(Packet info) throws UnsupportedFlavorException, IOException{
        switch(info.getKey()) {
            // Initialization
            case "init":                            
                ep.setAllText(info.getValue());
                break;

            case "keystroke":
                String val = info.getValue();
                int pos = Integer.parseInt(val.substring(0, val.indexOf(":")));
                String s = val.substring(val.indexOf(":") + 1, val.length());
                if (s.equals("\b")) ep.deleteChar(s, pos);
                ep.insertChar(s, pos);
                break;
            
            case "paste":
                val = info.getValue();
                pos = Integer.parseInt(val.substring(0, val.indexOf(":")));
                Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();
                s= (String) c.getData(DataFlavor.imageFlavor);
                ep.paste(s, pos);
                break;
        }
    }
    
    public String toString() {
        return nh.toString();
    }
    
    // Client Thread
    public abstract void sendAuth(String pass) throws IOException;
    
    // MultiHost Thread
    public abstract void receiveAuth();
    
    public abstract void run();
}
