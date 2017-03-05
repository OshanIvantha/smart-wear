/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import ui.ColourPalatte;
import ui.elements.LeftHand;

/**
 *
 * @author Ivantha
 */
public class LeftBackgroundPanel extends JPanel {

    private final int width = 345;
    private final int height = 393;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(ColourPalatte.BRIGHT_WHITE);
        g2d.fillRect(0, 0, width, height);
        
        LeftHand.paint(g2d);
    }
}
