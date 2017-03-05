/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import main.Dashboard;

/**
 *
 * @author Ivantha
 */
public class Notification {
    
    public static void display(String message) {
        Dashboard.statusLabel.setText(message);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                Dashboard.statusLabel.setText("");
            }
        }, 5000);
    }
}
