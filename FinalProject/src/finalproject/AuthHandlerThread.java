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
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class AuthHandlerThread extends NetworkHandler implements Runnable {

    public boolean authenticated = false;
    private boolean run = true;
    private String pass;

    // Server constructor
    public AuthHandlerThread(int port, String pass) throws IOException {
        super(port);
        this.pass = pass;
    }

    // Client constructor
    public AuthHandlerThread(String ip, int port) throws IOException {
        super(ip, port);
    }

    // Auth method for client
    public void sendAuth(String pass) throws IOException {
        pw.println(new Packet("password", pass).toString());
    }

    public boolean receiveAuth(String pass) throws IOException {
        boolean auth = (this.pass.equals(pass));
        pw.println(new Packet("auth", Boolean.toString(auth)).toString());
        return auth;
    }

    @Override
    public void run() {
        Packet info;
        while (!authenticated) {
            try {

                // Not currently authenticated; should run password loop
                String in = br.readLine();
                System.out.println(in);
                info = new Packet(in);

                // Host should only end thread once authenticated
                // Client will end thread on response; design to try again later
                if (host) {
                    if (info.getKey().equals("password")) {
                        boolean auth = receiveAuth(info.getValue());
                        if (auth) {
                            cleanUp();
                            authenticated = true;
                            break;
                        }
                    }
                } else {
                    if (info.getKey().equals("auth")) {
                        if (Boolean.parseBoolean(info.getValue())) {
                            cleanUp();
                            authenticated = true;
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong :(");
                ex.printStackTrace();
            }
        }
        
        System.out.println("Authenticated! " + authenticated);

        try {
            cleanUp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
