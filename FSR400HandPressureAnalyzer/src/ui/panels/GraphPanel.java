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
import java.util.HashMap;
import java.util.Queue;
import javax.swing.JPanel;
import ui.ColourPalatte;

/**
 *
 * @author Ivantha
 */
public class GraphPanel extends JPanel {

    private final int width = 1000;
    private final int height = 250;
    private final int padding = 15;
    private final int labelPadding = 25;
    private final int numberYDivisions = 10;
    private final int pointWidth = 3;

    private final Color pointColor = new Color(100, 100, 100, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int numberDataPoints = 120;
    private boolean isLeftGraphPanel = true;

    private final HashMap<Integer, Queue<Integer>> sensors = new HashMap<>();

    double xScale;
    double yScale;

    public HashMap<Integer, Queue<Integer>> getSensors() {
        return sensors;
    }

    public void setIsLeftGraphPanel(boolean isLeftGraphPanel) {
        this.isLeftGraphPanel = isLeftGraphPanel;
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
        xScale = ((double) this.getWidth() - (2 * padding) - labelPadding) / (numberDataPoints - 1.0);
        yScale = ((double) this.getHeight() - 2 * padding - labelPadding) / (1000.0);

        //Draw background
        g2d.setColor(ColourPalatte.VERY_DARK_BLUE);
        g2d.fillRect(0, 0, width, height);

        //Draw white background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(padding + labelPadding, padding, this.getWidth() - (2 * padding) - labelPadding, this.getHeight() - (2 * padding) - labelPadding);
        g2d.setColor(Color.BLACK);

        //Create hatch marks and grid lines for y axis
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (this.getHeight() - (2 * padding) - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (numberDataPoints > 0) {
                g2d.setColor(gridColor);
                g2d.drawLine(padding + labelPadding + 1 + pointWidth, y0, this.getWidth() - padding, y1);
                g2d.setColor(Color.WHITE);
                String yLabel = ((int) ((1000 * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2d.drawLine(x0, y0, x1, y1);
        }

        //Create hatch marks and grid lines for x axis
        for (int i = 0; i < numberDataPoints; i++) {
            if (numberDataPoints > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numberDataPoints - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                //if ((i % ((int) ((sensorHistory.size() / 20.0)) + 1)) == 0) {
                if ((i % 10.0) == 0) {
                    g2d.setColor(gridColor);
                    g2d.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2d.setColor(Color.WHITE);
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

        for (int sensor = 1; sensor < 19; sensor++) {
            if (sensors.containsKey(sensor)) {
                //Set coordinates of points
                if (sensors.get(sensor).size() == numberDataPoints) {
                    sensors.get(sensor).remove();
                }

                if (isLeftGraphPanel) {
                    sensors.get(sensor).offer(SensorCache.getLeftSensorValues().get(sensor));
                } else {
                    sensors.get(sensor).offer(SensorCache.getRightSensorValues().get(sensor));
                }

                ArrayList<Point> graphPoints = new ArrayList<>();
                for (int i = 0; i < sensors.get(sensor).size(); i++) {
                    //int xi = (int) ((i * xScale) + padding + labelPadding);
                    int xi = (int) (((numberDataPoints - i) * xScale) + padding + labelPadding);
                    int yi = (int) ((1000 - (Integer) sensors.get(sensor).toArray()[i]) * yScale + padding);
                    graphPoints.add(new Point(xi, yi));
                }

                //Draw lines
                Color lineColourHSV = new Color(Color.HSBtoRGB((float) (sensor * 20.0 / 360.0), (float) 1.0, (float) 0.95));
                Color lineColor = new Color(lineColourHSV.getRed(), lineColourHSV.getGreen(), lineColourHSV.getBlue(), 180);
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

        //Draw resultant
        if (sensors.containsKey(19)) {
            //Scale for resultant
            xScale = ((double) this.getWidth() - (2 * padding) - labelPadding) / (numberDataPoints - 1.0);
            yScale = ((double) this.getHeight() - 2 * padding - labelPadding) / (18000.0);

            //Set coordinates of points
            if (sensors.get(19).size() == numberDataPoints) {
                sensors.get(19).remove();
            }

            Integer sum = 0;
            if (isLeftGraphPanel) {
                for (Integer i : SensorCache.getLeftSensorValues().values()) {
                    sum += i;
                }
            } else {
                for (Integer i : SensorCache.getRightSensorValues().values()) {
                    sum += i;
                }
            }
            System.out.println(sum);
            sensors.get(19).offer(sum);

            ArrayList<Point> graphPoints = new ArrayList<>();
            for (int i = 0; i < sensors.get(19).size(); i++) {
                //int xi = (int) ((i * xScale) + padding + labelPadding);
                int xi = (int) (((numberDataPoints - i) * xScale) + padding + labelPadding);
                int yi = (int) ((18000 - (Integer) sensors.get(19).toArray()[i]) * yScale + padding);
                graphPoints.add(new Point(xi, yi));
            }

            //Draw lines
            Stroke oldStroke = g2d.getStroke();
            g2d.setColor(new Color(0, 0, 0, 255));
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
            g2d.setColor(new Color(0, 0, 0, 255));
            for (int i = 0; i < graphPoints.size(); i++) {
                int x = graphPoints.get(i).x - pointWidth / 2;
                int y = graphPoints.get(i).y - pointWidth / 2;
                int ovalW = pointWidth;
                int ovalH = pointWidth;
                g2d.fillOval(x, y, ovalW, ovalH);
            }
        }
    }
}
