/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class WaitThread implements Runnable {
    
    private JDialog dialog;
    private boolean run = true;
        
    public void createDialog() {
        dialog = new JDialog();
        dialog.setTitle("Waiting");
        dialog.setContentPane(new JOptionPane("Waiting for connection", 
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, 
                null, new Object[]{}, null));
        dialog.setModal(true);
        
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        
        dialog.setVisible(true);
    }
    
    public void closeDialog() {
        run = false;
        dialog.dispose();
    }

    @Override
    public void run() {
        createDialog();
    }
    
}
