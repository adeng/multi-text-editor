/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author alber
 */
public class NetworkHandler {
    protected ServerSocket sSock;
    protected Socket sock;
    
    protected boolean host;
    
    protected InputStream is;
    protected ObjectInputStream ois;
    
    protected OutputStream os;
    protected ObjectOutputStream oos;
      
    // Server constructor
    public NetworkHandler(int port) throws IOException {
        host = true;
        
        sSock = new ServerSocket(port);
        sock = sSock.accept();
        initIO();
    }
    
    // Client constructor
    public NetworkHandler(String ip, int port) throws IOException {
        host = false;
        
        sock = new Socket(ip, port);
        initIO();
    }
    
    public void initIO() throws IOException {
        // Input Stream
        is = sock.getInputStream();
        ois = new ObjectInputStream(is);
        
        // Output Stream
        os = sock.getOutputStream();
        oos = new ObjectOutputStream(os);
    }
    
    public void cleanUp() throws IOException {
        oos.close();
        ois.close();
    }
}
