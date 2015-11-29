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
    private int pass;

    // Server constructor
    public AuthHandlerThread(int port, int pass) throws IOException {
        super(port);
        this.pass = pass;
    }

    // Client constructor
    public AuthHandlerThread(String ip, int port, int pass) throws IOException {
        super(ip, port);
        this.pass = pass;
    }

    // Auth method for client
    public void sendAuth(int pass) throws IOException {
        oos.writeObject(new Packet("password", pass));
    }

    public boolean receiveAuth(int pass) throws IOException {
        boolean auth = (this.pass == pass);
        oos.writeObject(new Packet("auth", Boolean.toString(auth)));
        return auth;
    }

    @Override
    public Boolean call() throws Exception {
        Packet info;
        while (run) {
            try {
                // Not currently authenticated; should run password loop
                info = (Packet) ois.readObject();

                // Host should only end thread once authenticated
                // Client will end thread on response; design to try again later
                if (host) {
                    if (info.getKey().equals("password")) {
                        boolean auth = receiveAuth(Integer.parseInt(info.getValue()));
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
