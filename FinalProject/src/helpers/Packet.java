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
    String key, value;
    
    public Packet(String key, String value) {
        this.key = key;
        this.value = value;
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
        key = stripped.substring(0, stripped.indexOf(":"));
        
        if( stripped.indexOf(":") + 1 == stripped.length() - 1 )
            value = "";
        else
            value = stripped.substring(stripped.indexOf(":") + 1, stripped.length());
    }
    
    public static boolean isPacket(String s) {
        return s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}';
    }
    
    public String getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString() {
        return "{" + key + ":" + value + "}" + '\u0000';
    }
}
