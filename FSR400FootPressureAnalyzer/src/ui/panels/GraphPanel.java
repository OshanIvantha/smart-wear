/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.panels;

import common.SensorCache;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;

/**
 *
 * @author Ivantha
 */
public class GraphPanel extends JPanel {

    private final int width = 600;
    private final int height = 300;
    private final int padding = 25;
    private final int labelPadding = 25;
    private final int numberYDivisions = 10;
    private final int pointWidth = 1;
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color pointColor = new Color(100, 100, 100, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int numberDataPoints = 1000;

    private int sensor = 1;
    private final Queue<Integer> sensorHistory = new LinkedList<>();

    public GraphPanel() {
        for (int i = 0; i < numberDataPoints; i++) {
            sensorHistory.offer(0);
        }
    }

    public void setSensor(int sensor) {
        this.sensor = sensor;
        sensorHistory.clear();
        for (int i = 0; i < numberDataPoints; i++) {
            sensorHistory.offer(0);
        }
    }

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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Set graph scale
        double xScale = ((double)this.getWidth() - (2 * padding) - labelPadding) / (sensorHistory.size() - 1.0);
        double yScale = ((double)this.getHeight() - 2 * padding - labelPadding) / (1000.0);

        //Set coordinates of points
        sensorHistory.remove();
        sensorHistory.offer(SensorCache.getSensors().get(sensor));
        ArrayList<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < sensorHistory.size(); i++) {
            int xi = (int) ((i * xScale) + padding + labelPadding);
            int yi = (int) ((1000 - (Integer) sensorHistory.toArray()[i]) * yScale + padding);
            graphPoints.add(new Point(xi, yi));
        }

        //Draw white background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(padding + labelPadding, padding, this.getWidth()- (2 * padding) - labelPadding, this.getHeight() - (2 * padding) - labelPadding);
        g2d.setColor(Color.BLACK);

        //Create hatch marks and grid lines for y axis
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (this.getHeight() - (2 * padding) - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (sensorHistory.size() > 0) {
                g2d.setColor(gridColor);
                g2d.drawLine(padding + labelPadding + 1 + pointWidth, y0, this.getWidth() - padding, y1);
                g2d.setColor(Color.BLACK);
                String yLabel = ((int) ((1000 * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                 g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2d.drawLine(x0, y0, x1, y1);
        }
        
        //Create hatch marks and grid lines for x axis
        for (int i = 0; i < sensorHistory.size(); i++) {
            if (sensorHistory.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (sensorHistory.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                //if ((i % ((int) ((sensorHistory.size() / 20.0)) + 1)) == 0) {
                if ((i % 50) == 0) {
                    g2d.setColor(gridColor);
                    g2d.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2d.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2d.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2d.drawLine(x0, y0, x1, y1);
            }
        }
        
        //Create x and y axes
        g2d.drawLine(padding + labelPadding, this.getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2d.drawLine(padding + labelPadding, this.getHeight() - padding - labelPadding, this.getWidth() - padding, this.getHeight() - padding - labelPadding);
        
        //Draw lines
        Stroke oldStroke = g2d.getStroke();
        g2d.setColor(lineColor);
        g2d.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        //Draw points
        g2d.setStroke(oldStroke);
        g2d.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2d.fillOval(x, y, ovalW, ovalH);
        }
    }
}
