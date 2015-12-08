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
import javax.swing.text.BadLocationException;

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
    
    public void sendBackspace(int position) {
        sync.add(new Packet("backspace", position));
    }
    
    public void sendBackspace(int start, int end) {
        sync.add(new Packet("backspaceMultiple", start + ":" + end));
    }
    
    public void sendText(String s, int position) {
        sync.add(new Packet("paste", position + ":" + s));
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
    
    public void processPacketData(Packet info) throws Exception{
        String val = info.getValue();
        int pos;
        switch(info.getKey()) {
            // Initialization
            case "init":                            
                ep.setAllText(info.getValue());
                break;

            case "keystroke":
                pos = Integer.parseInt(val.substring(0, val.indexOf(":")));
                String s = val.substring(val.indexOf(":") + 1, val.length());
                ep.insertText(s, pos);
                break;
                
            case "backspace":
                ep.deleteChar(Integer.parseInt(val));
                break;
                
            case "backspaceMultiple":
                int start = Integer.parseInt(val.substring(0, val.indexOf(":")));
                int end = Integer.parseInt(val.substring(val.indexOf(":") + 1, val.length()));
                ep.deleteSelection(start, end);
                break;
            
            case "paste":
                pos = Integer.parseInt(val.substring(0, val.indexOf(":")));
                s = val.substring(val.indexOf(":") + 1, val.length());
                System.out.println("Position: " + pos);
                System.out.println("String: " + s);
                ep.insertText(s, pos);
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
