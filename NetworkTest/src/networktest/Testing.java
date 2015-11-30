/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networktest;

import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class Testing {

    public static void show(String p) {
        System.out.println(p);
    }

    public static void main(String[] args) {
        String pass = JOptionPane.showInputDialog(null, "Please enter the password");
        show(pass);
    }
}
