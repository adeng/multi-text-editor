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

    protected OutputStream os;
    protected PrintWriter pw;

    // Server constructor
    public NetworkHandler(int port) throws IOException {
        host = true;
        sSock = new ServerSocket(port);
        System.out.println("Hosting on: " + sSock.getInetAddress().getHostAddress() + ":" + port);
        sock = sSock.accept();
        System.out.println("Connected!");
        initIO();
    }

    // Client constructor
    public NetworkHandler(String ip, int port) throws IOException {
        host = false;
        System.out.println("Connecting to: " + ip + ":" + port);
        sock = new Socket(ip, port);
        System.out.println("Connected!");
        initIO();
    }
    
    public NetworkHandler(Socket s) throws IOException {
        host = false;
        sock = s;
        initIO();
    }

    public void initIO() throws IOException {
        // Input Stream
        is = sock.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);

        // Output Stream
        os = sock.getOutputStream();
        pw = new PrintWriter(os, true);
    }

    public void cleanUp() throws Exception {
        br.close();
        pw.close();
        sock.close();
        if (host) {
            sSock.close();
        }
    }

    public PrintWriter getWriter() {
        return pw;
    }

    public BufferedReader getReader() {
        return br;
    }

    public boolean isHost() {
        return host;
    }
}