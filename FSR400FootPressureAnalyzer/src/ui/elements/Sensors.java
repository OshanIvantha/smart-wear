/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import common.SensorCache;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import ui.Point;

/**
 *
 * @author Ivantha
 */
public class Sensors {

    private static final HashMap<Integer, Point> SENSOR_POSITION = new HashMap<>();
    private static final int MAX_RADIUS = 17;

    private static Point avgPP = new Point(0, 0);

    static {
        SENSOR_POSITION.put(1, new Point(230 - MAX_RADIUS, 525 - MAX_RADIUS));
        SENSOR_POSITION.put(2, new Point(202 - MAX_RADIUS, 455 - MAX_RADIUS));
        SENSOR_POSITION.put(3, new Point(274 - MAX_RADIUS, 430 - MAX_RADIUS));
        SENSOR_POSITION.put(4, new Point(285 - MAX_RADIUS, 351 - MAX_RADIUS));
        SENSOR_POSITION.put(5, new Point(309 - MAX_RADIUS, 287 - MAX_RADIUS));
        SENSOR_POSITION.put(6, new Point(322 - MAX_RADIUS, 229 - MAX_RADIUS));
        SENSOR_POSITION.put(7, new Point(293 - MAX_RADIUS, 198 - MAX_RADIUS));
        SENSOR_POSITION.put(8, new Point(252 - MAX_RADIUS, 177 - MAX_RADIUS));
        SENSOR_POSITION.put(9, new Point(212 - MAX_RADIUS, 157 - MAX_RADIUS));
        SENSOR_POSITION.put(10, new Point(171 - MAX_RADIUS, 149 - MAX_RADIUS));
        SENSOR_POSITION.put(11, new Point(315 - MAX_RADIUS, 133 - MAX_RADIUS));
        SENSOR_POSITION.put(12, new Point(294 - MAX_RADIUS, 108 - MAX_RADIUS));
        SENSOR_POSITION.put(13, new Point(271 - MAX_RADIUS, 87 - MAX_RADIUS));
        SENSOR_POSITION.put(14, new Point(241 - MAX_RADIUS, 74 - MAX_RADIUS));
        SENSOR_POSITION.put(15, new Point(202 - MAX_RADIUS, 64 - MAX_RADIUS));
    }

    public static HashMap<Integer, Point> getSENSOR_POSITION() {
        return SENSOR_POSITION;
    }

    public static void setAvgPP(Point avgPP) {
        Sensors.avgPP = avgPP;
    }

    public static Point getAvgPP() {
        return avgPP;
    }

    public static int getMAX_RADIUS() {
        return MAX_RADIUS;
    }

    public static void paint(Graphics2D g2d) {
        HashMap<Integer, Integer> sensors = SensorCache.getSensors();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);

        int maxSensor = 1;
        int minSensor = 1;
        for (int i = 2; i < 16; i++) {
            if (sensors.get(i) > sensors.get(maxSensor)) {
                maxSensor = i;
            }
            if (sensors.get(i) < sensors.get(minSensor)) {
                minSensor = i;
            }
        }

        for (int i = 1; i < 16; i++) {
            int steps = MAX_RADIUS;
            int shadeThickness = (MAX_RADIUS / steps);
            int alpha = 255 / steps;
            
            //Display sensor colour in an absolute way
            //double hue = 200.0 / 360.0 * (1.0 - 1.0 / 1000.0 * sensors.get(i));
            
            //Display sensor colour in an relative way
            double hue = 200.0 / 360.0 * (1.0 - 1.0 / (sensors.get(maxSensor) - sensors.get(minSensor)) * sensors.get(i));
            
            Color sensorColour = new Color(Color.HSBtoRGB((float) hue, (float) 1.0, (float) 0.95));
            g2d.setColor(new Color(sensorColour.getRed(), sensorColour.getGreen(), sensorColour.getBlue(), alpha * 3));
            for (int j = steps; j > 0; j--) {
                g2d.fillOval(SENSOR_POSITION.get(i).getX() + MAX_RADIUS * (steps - j) / steps, SENSOR_POSITION.get(i).getY() + MAX_RADIUS * (steps - j) / steps,
                        shadeThickness * j * 2, shadeThickness * j * 2);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(i), SENSOR_POSITION.get(i).getX() + MAX_RADIUS, SENSOR_POSITION.get(i).getY() + MAX_RADIUS);
        }

        int r = 10;
        g2d.setColor(Color.BLACK);
        g2d.fillOval(avgPP.getX() - r, avgPP.getY() - r, r * 2, r * 2);
    }
}
