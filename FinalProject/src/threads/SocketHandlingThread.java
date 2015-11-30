/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.net.ServerSocket;

/**
 *
 * @author alber
 */
public class SocketHandlingThread implements Runnable {
    
    private ServerSocket ss;
    private boolean run = true;
    
    public SocketHandlingThread(ServerSocket ss) {
        this.ss = ss;
    }
    
    @Override
    public void run() {
        while(run) {
            
        }
    }
    
}
