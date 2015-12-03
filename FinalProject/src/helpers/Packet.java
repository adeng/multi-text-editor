/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.Serializable;

/**
 *
 * @author alber
 */
public class Packet implements Serializable {
    String[] data;
    
    public Packet(String key, String value) {
        data[0] = key;
        data[1] = value;
    }
    
    public Packet(String key, int value) {
        this(key, Integer.toString(value));
    }
    
    public Packet(String key, double value) {
        this(key, Double.toString(value));
    }
    
    public Packet(String key, Packet value) {
        this(key, value.toString());
    }
    
    public Packet(String json) {
        String stripped = json.trim().substring(1, json.length() - 1);
        data[0] = stripped.substring(0, stripped.indexOf(":"));
        
        if( stripped.indexOf(":") + 1 == stripped.length() - 1 )
            data[1] = "";
        else
            data[1] = stripped.substring(stripped.indexOf(":") + 1, stripped.length());
    }
    
    public static boolean isPacket(String s) {
        return s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}';
    }
    
    public String getKey() {
        return data[0];
    }
    
    public String getValue() {
        return data[1];
    }
    
    public String toString() {
        return "{" + data[0] + ":" + data[1] + "}";
    }
}
