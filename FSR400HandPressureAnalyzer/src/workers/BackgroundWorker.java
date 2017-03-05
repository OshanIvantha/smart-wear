/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import common.SensorCache;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.SwingWorker;
import ui.Point;
import ui.elements.Sensors;

/**
 *
 * @author Ivantha
 */
public class BackgroundWorker extends SwingWorker<Void, Void> {

    HashMap<String, Double> tempLeft;
    HashMap<String, Double> tempRight;

    HashMap<Integer, Integer> tempLeftValues = new HashMap<>();
    HashMap<Integer, Integer> tempRightValues = new HashMap<>();

    @Override
    protected Void doInBackground() throws Exception {
        while (!this.isCancelled()) {
            this.findAvgPPLeft();
            this.findAvdPPRight();

            for (int i = 1; i < 19; i++) {
                tempLeftValues.put(i, ThreadLocalRandom.current().nextInt(0, 1001));
                tempRightValues.put(i, ThreadLocalRandom.current().nextInt(0, 1001));
            }

            Thread.sleep(100);

            publish();
        }
        return null;
    }

    @Override
    protected void process(List<Void> p) {
        SensorCache.setLeftSensorValues(tempLeftValues);
        SensorCache.setRightSensorValues(tempRightValues);
    }

    public void findAvgPPLeft() {
        tempLeft = new HashMap<>();
        tempLeft.put("avgP", Double.parseDouble(SensorCache.getLeftSensorValues().get(1).toString()));
        tempLeft.put("avgPP", Double.parseDouble(SensorCache.getLeftSensorValues().get(1).toString()));
        tempLeft.put("avgPPx", Double.parseDouble(String.valueOf(Sensors.getLEFT_SENSOR_POSITION().get(1).getX() + Sensors.getMAX_RADIUS())));
        tempLeft.put("avgPPy", Double.parseDouble(String.valueOf(Sensors.getLEFT_SENSOR_POSITION().get(1).getY() + Sensors.getMAX_RADIUS())));

        for (int i = 2; i < 16; i++) {
            int currentP = SensorCache.getLeftSensorValues().get(i);

            tempLeft.put("avgPP", tempLeft.get("avgPP") + currentP / 2.0);
            tempLeft.put("avgPPx", ((tempLeft.get("avgPP") * tempLeft.get("avgPPx")) + (currentP * (Sensors.getLEFT_SENSOR_POSITION().get(i).getX()
                    + Sensors.getMAX_RADIUS()))) / (tempLeft.get("avgPP") + currentP));
            tempLeft.put("avgPPy", ((tempLeft.get("avgPP") * tempLeft.get("avgPPy")) + (currentP * (Sensors.getLEFT_SENSOR_POSITION().get(i).getY()
                    + Sensors.getMAX_RADIUS()))) / (tempLeft.get("avgPP") + currentP));
        }
        tempLeft.put("avgP", tempLeft.get("avgP") / 15);
        Sensors.setAvgPPLeft(new Point(tempLeft.get("avgPPx").intValue(), tempLeft.get("avgPPy").intValue()));
    }

    public void findAvdPPRight() {
        tempRight = new HashMap<>();
        tempRight.put("avgP", Double.parseDouble(SensorCache.getRightSensorValues().get(1).toString()));
        tempRight.put("avgPP", Double.parseDouble(SensorCache.getRightSensorValues().get(1).toString()));
        tempRight.put("avgPPx", Double.parseDouble(String.valueOf(Sensors.getRIGHT_SENSOR_POSITION().get(1).getX() + Sensors.getMAX_RADIUS())));
        tempRight.put("avgPPy", Double.parseDouble(String.valueOf(Sensors.getRIGHT_SENSOR_POSITION().get(1).getY() + Sensors.getMAX_RADIUS())));

        for (int i = 2; i < 16; i++) {
            int currentP = SensorCache.getLeftSensorValues().get(i);

            tempRight.put("avgPP", tempRight.get("avgPP") + currentP / 2.0);
            tempRight.put("avgPPx", ((tempRight.get("avgPP") * tempRight.get("avgPPx")) + (currentP * (Sensors.getRIGHT_SENSOR_POSITION().get(i).getX()
                    + Sensors.getMAX_RADIUS()))) / (tempRight.get("avgPP") + currentP));
            tempRight.put("avgPPy", ((tempRight.get("avgPP") * tempRight.get("avgPPy")) + (currentP * (Sensors.getRIGHT_SENSOR_POSITION().get(i).getY()
                    + Sensors.getMAX_RADIUS()))) / (tempRight.get("avgPP") + currentP));
        }
        tempRight.put("avgP", tempRight.get("avgP") / 15);
        Sensors.setAvgPPLeft(new Point(tempRight.get("avgPPx").intValue(), tempRight.get("avgPPy").intValue()));
    }
}
