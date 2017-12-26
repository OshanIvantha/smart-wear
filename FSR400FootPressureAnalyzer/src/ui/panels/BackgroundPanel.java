/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.panels;

import java.awt.Color;
import ui.elements.Shoe;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import ui.ColourPalatte;

/**
 *
 * @author Ivantha
 */
public class BackgroundPanel extends JPanel {

    private final int width = 380;
    private final int height = 600;

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

        Shoe.paint(g2d);

        int x0 = 60;
        int y0 = 0;
        int barWidth = 30;

        g2d.setColor(ColourPalatte.VERY_DARK_BLUE);
        g2d.fillRect(0, 0, x0 - 0, 600);
        g2d.fillRect(x0 + barWidth, 0, 20, 600);

        for (int i = 200; i >= 0; i--) {
            double hue = 200.0 / 360.0 * (1.0 - 1.0 / 200 * i);
            Color sensorColour = new Color(Color.HSBtoRGB((float) hue, (float) 1.0, (float) 0.95));
            g2d.setColor(new Color(sensorColour.getRed(), sensorColour.getGreen(), sensorColour.getBlue(), 200));
            g2d.fillRect(x0, y0 + (200 - i) * (600 / 200), barWidth, 600 / 200);
        }
    }
}
