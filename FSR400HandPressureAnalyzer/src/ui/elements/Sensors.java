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

    private static final HashMap<Integer, Point> LEFT_SENSOR_POSITION = new HashMap<>();
    private static final HashMap<Integer, Point> RIGHT_SENSOR_POSITION = new HashMap<>();
    private static final int MAX_RADIUS = 17;

    private static Point avgPPLeft = new Point(0, 0);
    private static Point avgPPRight = new Point(0, 0);

    static {
        LEFT_SENSOR_POSITION.put(1, new Point(60 - MAX_RADIUS, 225 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(2, new Point(95 - MAX_RADIUS, 267 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(3, new Point(125 - MAX_RADIUS, 68 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(4, new Point(135 - MAX_RADIUS, 111 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(5, new Point(149 - MAX_RADIUS, 175 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(6, new Point(186 - MAX_RADIUS, 33 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(7, new Point(190 - MAX_RADIUS, 93 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(8, new Point(195 - MAX_RADIUS, 167 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(9, new Point(238 - MAX_RADIUS, 55 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(10, new Point(235 - MAX_RADIUS, 110 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(11, new Point(235 - MAX_RADIUS, 170 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(12, new Point(291 - MAX_RADIUS, 107 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(13, new Point(284 - MAX_RADIUS, 146 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(14, new Point(278 - MAX_RADIUS, 187 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(15, new Point(182 - MAX_RADIUS, 226 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(16, new Point(250 - MAX_RADIUS, 250 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(17, new Point(248 - MAX_RADIUS, 324 - MAX_RADIUS));
        LEFT_SENSOR_POSITION.put(18, new Point(162 - MAX_RADIUS, 318 - MAX_RADIUS));
        
        for(int i = 1; i < 19; i++){
            RIGHT_SENSOR_POSITION.put(i, new Point(360 - (MAX_RADIUS * 2) - LEFT_SENSOR_POSITION.get(i).getX(),LEFT_SENSOR_POSITION.get(i).getY()));
        }
    }

    public static HashMap<Integer, Point> getLEFT_SENSOR_POSITION() {
        return LEFT_SENSOR_POSITION;
    }

    public static HashMap<Integer, Point> getRIGHT_SENSOR_POSITION() {
        return RIGHT_SENSOR_POSITION;
    }

    public static void setAvgPPLeft(Point avgPPLeft) {
        Sensors.avgPPLeft = avgPPLeft;
    }

    public static Point getAvgPPLeft() {
        return avgPPLeft;
    }

    public static Point getAvgPPRight() {
        return avgPPRight;
    }

    public static void setAvgPPRight(Point avgPPRight) {
        Sensors.avgPPRight = avgPPRight;
    }

    public static int getMAX_RADIUS() {
        return MAX_RADIUS;
    }

    public static void paint(Graphics2D g2d, boolean isLeft) {
        HashMap<Integer,Integer> sensors;
        if(isLeft){
            sensors = SensorCache.getLeftSensorValues();
        }else{
            sensors = SensorCache.getRightSensorValues();
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);

        int maxSensor = 1;
        int minSensor = 1;
        for (int i = 2; i < 19; i++) {
            if (sensors.get(i) > sensors.get(maxSensor)) {
                maxSensor = i;
            }
            if (sensors.get(i) < sensors.get(minSensor)) {
                minSensor = i;
            }
        }

        for (int i = 1; i < 19; i++) {
            int steps = MAX_RADIUS;
            int shadeThickness = (MAX_RADIUS / steps);
            int alpha = 255 / steps;

//            Display sensor colour in an absolute way
            double hue = 200.0 / 360.0 * (1.0 - 1.0 / 1000.0 * sensors.get(i));
//            Display sensor colour in an relative way
//            double hue = 200.0 / 360.0 * (1.0 - 1.0 / (sensors.get(maxSensor) - sensors.get(minSensor)) * sensors.get(i));

            Color sensorColour = new Color(Color.HSBtoRGB((float) hue, (float) 1.0, (float) 0.95));
            g2d.setColor(new Color(sensorColour.getRed(), sensorColour.getGreen(), sensorColour.getBlue(), alpha * 3));
            if (isLeft) {
                for (int j = steps; j > 0; j--) {
                    g2d.fillOval(LEFT_SENSOR_POSITION.get(i).getX() + MAX_RADIUS * (steps - j) / steps, LEFT_SENSOR_POSITION.get(i).getY() + MAX_RADIUS * (steps - j) / steps,
                            shadeThickness * j * 2, shadeThickness * j * 2);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(i), LEFT_SENSOR_POSITION.get(i).getX() + MAX_RADIUS, LEFT_SENSOR_POSITION.get(i).getY() + MAX_RADIUS);
            }
            else{
                for (int j = steps; j > 0; j--) {
                    g2d.fillOval(RIGHT_SENSOR_POSITION.get(i).getX() + MAX_RADIUS * (steps - j) / steps, RIGHT_SENSOR_POSITION.get(i).getY() + MAX_RADIUS * (steps - j) / steps,
                            shadeThickness * j * 2, shadeThickness * j * 2);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(i), RIGHT_SENSOR_POSITION.get(i).getX() + MAX_RADIUS, RIGHT_SENSOR_POSITION.get(i).getY() + MAX_RADIUS);
            }
        }

        int r = 10;
        g2d.setColor(Color.BLACK);
        g2d.fillOval(avgPPLeft.getX() - r, avgPPLeft.getY() - r, r * 2, r * 2);
    }
}
