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
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class AuthHandlerThread extends NetworkHandler implements Callable<Boolean> {

    private boolean authenticated;
    private boolean run = true;
    private String pass;

    // Server constructor
    public AuthHandlerThread(int port, String pass) throws IOException {
        super(port);
        this.pass = pass;
    }

    // Client constructor
    public AuthHandlerThread(String ip, int port, String pass) throws IOException {
        super(ip, port);
        this.pass = pass;
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
    public Boolean call() throws Exception {
        System.out.println("Waiting");
        Packet info;
        while (run) {
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
                        if( auth ) {
                            cleanUp();
                            return true;
                        }
                    }
                } else {
                    sendAuth(pass);

                    // Wait until auth result
                    if (info.getKey().equals("auth")) {
                        if (Boolean.parseBoolean(info.getValue())) {
                            cleanUp();
                            return true;
                        }

                        // Kill thread
                        return false;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong :(");
                ex.printStackTrace();
            }
        }

        cleanUp();
        return false;
    }
}
