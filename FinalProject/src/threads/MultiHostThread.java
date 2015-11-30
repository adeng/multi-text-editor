/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import helpers.NetworkHandler;
import helpers.Packet;
import java.io.IOException;

/**
 *
 * @author alber
 */
public class MultiHostThread implements Runnable {
    NetworkHandler nh;
    public static boolean run = true;

    public MultiHostThread(NetworkHandler nh) throws IOException {
        this.nh = nh;
    }

    public boolean receiveAuth(String pass) throws IOException {
        boolean auth = (MainHostThread.pass.equals(pass));
        sendPacket(new Packet("auth", Boolean.toString(auth)));
        return auth;
    }
    
    public void sendPacket(Packet p) {
        nh.getWriter().println(p.toString());
    }

    @Override
    public void run() {
        Packet info;
        String in;
        try {
            while(!nh.authenticated) {
                // Not currently authenticated; should run password loop
                in = nh.getReader().readLine();
                System.out.println(in);
                info = new Packet(in);

                if (info.getKey().equals("password")) {
                    boolean auth = receiveAuth(info.getValue());
                    if (auth) {
                        nh.cleanUp();
                        nh.authenticated = true;
                        break;
                    }
                }
            }
            // listen for other stuff
        } catch (Exception ex) {
            System.out.println("Something went wrong :(");
            ex.printStackTrace();
        }
    }
}
