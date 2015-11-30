/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networktest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author alber
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Socket sock = new Socket("166.170.44.249", 5000);
        InputStream sis = sock.getInputStream();
        InputStreamReader isr = new InputStreamReader(sis);
        BufferedReader in = new BufferedReader(isr);
        System.out.println(in.readLine());
        in.close();
    }
    
}
