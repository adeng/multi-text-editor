/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import helpers.NetworkHandler;
import threads.*;
import java.awt.Dialog;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import threads.MainHostThread;

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
        getRootPane().setDefaultButton(actionButton);
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

            jLabel2.setVisible(false);
            passField.setVisible(false);
        }
    }
    
    private JDialog dialog;
        
    public void createDialog(String msg) {
        dialog = new JDialog();
        dialog.setTitle("Waiting");
        dialog.setContentPane(new JOptionPane(msg, 
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, 
                null, new Object[]{}, null));
        dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        
        dialog.setVisible(true);
    }
    
    public void closeDialog() {
        dialog.dispose();
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
        jLabel2 = new javax.swing.JLabel();
        ipField = new javax.swing.JTextField();
        actionButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        passField = new javax.swing.JTextField();

        jLabel1.setText("IP Address");

        jLabel2.setText("Password");

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

        passField.setText("Enter Password Here");
        passField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passFieldMouseClicked(evt);
            }
        });
        passField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFieldActionPerformed(evt);
            }
        });

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
                            .addComponent(jLabel2))
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
                    .addComponent(jLabel2)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Boolean testPassword = false;


    private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
        String ip = ipField.getText();

        if ("".equals(ip) || "".equals(portField.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid IP/port!");
            return;
        }

        int port = Integer.parseInt(portField.getText());
        String pass = passField.getText();
        
        Thread thread;

        try {
            actionButton.setText("Waiting for connection...");
            
            if( host ) {
                MainHostThread run = new MainHostThread(port, pass);
                thread = new Thread(run);
                thread.start();
                
                new Thread(new ConnHostThread()).start();
                
                this.setVisible(false);
                EditorPage ep = new EditorPage(true);
                run.setEditor(ep);
                ep.setVisible(true);
            } else {
                ClientThread run = new ClientThread(new NetworkHandler(ip, port));
                EditorPage ep = new EditorPage(false);
                run.setEditor(ep);
                thread = new Thread(run);
                thread.start();
                
                boolean asked = false;
                
                while(!run.nh.authenticated) {
                    if (asked) {
                        JOptionPane.showMessageDialog(null, "Incorrect password!");
                    }

                    asked = true;
                    pass = JOptionPane.showInputDialog(new JFrame(), "Please enter the password");
                    if (pass == null) {
                        JOptionPane.showMessageDialog(null, "Not authenticated!");
                        return;
                    }
                    run.sendAuth(pass);
                    Thread.sleep(500);
                }
                
                JOptionPane.showMessageDialog(null, "Connected!");
                this.setVisible(false);
                ep.setVisible(true);
            }
        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Host not found!");
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

    private void passFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passFieldActionPerformed

    private void passFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passFieldMouseClicked
        // TODO add your handling code here:
        passField.setText("");
    }//GEN-LAST:event_passFieldMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JTextField ipField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField passField;
    private javax.swing.JTextField portField;
    // End of variables declaration//GEN-END:variables
}
