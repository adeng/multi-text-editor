/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
    public boolean authenticated = false;

    protected InputStream is;
    protected InputStreamReader isr;
    protected BufferedReader br;
    protected ObjectInputStream ois;

    protected OutputStream os;
    protected PrintWriter pw;
    protected ObjectOutputStream oos;
    
    public static String ip;
    public static int port;

    // Client constructor
    public NetworkHandler(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        
        host = false;
        System.out.println("Connecting to: " + ip + ":" + port);
        sock = new Socket(ip, port);
        System.out.println("Connected!");
        initIO();
    }
    
    public NetworkHandler(Socket s) throws IOException {
        host = false;
        sock = s;
        
        ip = s.getInetAddress().getHostAddress();
        port = s.getLocalPort();
        initIO();
    }

    public void initIO() throws IOException {
        // Input Stream
        is = sock.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        ois = new ObjectInputStream(is);

        // Output Stream
        os = sock.getOutputStream();
        pw = new PrintWriter(os, true);
        oos = new ObjectOutputStream(oos);
    }

    public void cleanUp() throws Exception {
        br.close();
        pw.close();
        sock.close();
        if (host) {
            sSock.close();
        }
    }

    public ObjectOutputStream getWriter() {
        return oos;
    }

    public ObjectInputStream getReader() {
        return ois;
    }

    public boolean isHost() {
        return host;
    }
    
    public String toString() {
        return ip + ":" + port;
    }
}
