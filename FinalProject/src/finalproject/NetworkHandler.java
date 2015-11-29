/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author alber
 */
public class NetworkHandler {
    
    private ServerSocket sSock;
    private Socket sock;
    private boolean auth = false;
    private int pass;
    
    // Server constructor
    public NetworkHandler(int port, int pass) throws IOException {
        this.pass = pass;
        
        sSock = new ServerSocket(port);
        sock = sSock.accept();
    }
    
    // Client constructor
    public NetworkHandler(String ip, int port, int pass) throws IOException {
        this.pass = pass;
        
        sock = new Socket(ip, port);
    }
    
    
    // Auth method for client
    public void sendAuth(int pass) throws IOException {
        // Input Stream
        InputStream is = sock.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
        // Output Stream
        OutputStream os = sock.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(pass);
        bw.close();
        
    }
    
    public void receiveAuth() throws IOException {
        InputStream is = sock.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        
    }
}
