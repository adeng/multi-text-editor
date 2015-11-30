/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

/**
 *
 * @author alber
 */
public class NetworkPage extends javax.swing.JFrame {

    private boolean host;
    private int pass;

    /**
     * Creates new form HostStart
     */
    public NetworkPage() {
        initComponents();
    }

    public NetworkPage(boolean host) {
        this();
        this.host = host;

        passField.setText("");

        if (host) {
            actionButton.setText("Host Session");

            try {
                InetAddress ip = InetAddress.getLocalHost();
                ipField.setText(ip.getHostAddress());
            } catch (Exception ex) {
                ipField.setText("Error! Something went wrong");
                ex.printStackTrace();
            }

            ipField.setEditable(false);
            //passField.setText(Integer.toString(pass));
            //passField.setEditable(false);
        } else {
            actionButton.setText("Connect to Session");
            ipField.setText("");
            portField.setText("");
            
            passLabel.setVisible(false);
            passField.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        passLabel = new javax.swing.JLabel();
        ipField = new javax.swing.JTextField();
        actionButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        passField = new javax.swing.JTextField();

        jLabel1.setText("IP Address");

        passLabel.setText("Password");

        ipField.setText("Click here to show your IP");
        ipField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ipFieldMouseClicked(evt);
            }
        });
        ipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipFieldActionPerformed(evt);
            }
        });

        actionButton.setText("Start");
        actionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Port");

        portField.setText("9286");

        passField.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(actionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(passLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ipField, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(portField)
                            .addComponent(passField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Boolean testPassword = false;


    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        NetworkHandlerThread nh;
        String ip = ipField.getText();
        int port = Integer.parseInt(portField.getText());
        String pass = passField.getText();

        try {
            Thread auth;
            AuthHandlerThread aht;
            
            if (host) {
                aht = new AuthHandlerThread(port, pass);
            } else {
                aht = new AuthHandlerThread(ip, port);
            }
            
            auth = new Thread(aht);            
            auth.start();
            
            do {
                if (!aht.authenticated && !host) {
                    pass = JOptionPane.showInputDialog(new JFrame(), "Please enter the password");
                    aht.sendAuth(pass);
                }
            } while (!aht.authenticated);

            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Connected!");

            EditorPage ep = new EditorPage();
            ep.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Something went wrong :(");
            ex.printStackTrace();
        }

        /*try {
            if( host ) {
                nh = new NetworkHandlerThread(Integer.parseInt(portField.getText()), 
                        Integer.parseInt(passField.getText()));
            } else {
                nh = new NetworkHandlerThread(ipField.getText(), 
                        Integer.parseInt(portField.getText()), 
                        Integer.parseInt(passField.getText()));
            }
            
            ep.setNetwork(nh);
        } catch ( Exception ex ) {
            System.out.println("Something went wrong :(");
            ex.printStackTrace();
        }*/

    }//GEN-LAST:event_actionButtonActionPerformed

    private void ipFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_ipFieldActionPerformed

    private void ipFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ipFieldMouseClicked
        // TODO add your handling code here:
        InetAddress addr;
        String ip = "";
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        ipField.setText(ip);
    }//GEN-LAST:event_ipFieldMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NetworkPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NetworkPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NetworkPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NetworkPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NetworkPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JTextField ipField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField passField;
    private javax.swing.JLabel passLabel;
    private javax.swing.JTextField portField;
    // End of variables declaration//GEN-END:variables
}
