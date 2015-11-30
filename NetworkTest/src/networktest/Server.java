/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networktest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author alber
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSock = new ServerSocket(5000);
        Socket sock = serverSock.accept();
        OutputStream sos = sock.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(sos);
        BufferedWriter out = new BufferedWriter(osw);
        out.write("hello");
        out.close();
    }
    
}
