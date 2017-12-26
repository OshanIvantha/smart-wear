/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import common.SensorCache;
import java.util.HashMap;
import java.util.List;
import javax.swing.SwingWorker;
import main.Dashboard;
import ui.Point;
import ui.elements.Sensors;

/**
 *
 * @author Ivantha
 */
public class BackgroundWorker extends SwingWorker<Void, Void> {

    HashMap<String, Integer> temp1;
    HashMap<String, Double> temp2;

    @Override
    protected Void doInBackground() throws Exception {
        while (!this.isCancelled()) {
            temp1 = new HashMap<>();
            temp2 = new HashMap<>();
            temp1.put("maxP", SensorCache.getSensors().get(1));
            temp1.put("maxPP", 1);
            temp1.put("minP", SensorCache.getSensors().get(1));
            temp1.put("minPP", 1);
            temp2.put("avgP", Double.parseDouble(SensorCache.getSensors().get(1).toString()));
            temp2.put("avgPP", Double.parseDouble(SensorCache.getSensors().get(1).toString()));
            temp2.put("avgPPx", Double.parseDouble(String.valueOf(Sensors.getSENSOR_POSITION().get(1).getX() + Sensors.getMAX_RADIUS())));
            temp2.put("avgPPy", Double.parseDouble(String.valueOf(Sensors.getSENSOR_POSITION().get(1).getY() + Sensors.getMAX_RADIUS())));

            for (int i = 2; i < 16; i++) {
                int currentP = SensorCache.getSensors().get(i);
                if (currentP > temp1.get("maxP")) {
                    temp1.put("maxP", currentP);
                    temp1.put("maxPP", i);
                } else if (currentP < temp1.get("minP")) {
                    temp1.put("minP", currentP);
                    temp1.put("minPP", i);
                }

                temp2.put("avgPP", temp2.get("avgPP") + currentP / 2.0);
                temp2.put("avgPPx", ((temp2.get("avgPP") * temp2.get("avgPPx")) + (currentP * (Sensors.getSENSOR_POSITION().get(i).getX()
                        + Sensors.getMAX_RADIUS()))) / (temp2.get("avgPP") + currentP));
                temp2.put("avgPPy", ((temp2.get("avgPP") * temp2.get("avgPPy")) + (currentP * (Sensors.getSENSOR_POSITION().get(i).getY()
                        + Sensors.getMAX_RADIUS()))) / (temp2.get("avgPP") + currentP));
            }
            temp2.put("avgP", temp2.get("avgP") / 15);
            Sensors.setAvgPP(new Point(temp2.get("avgPPx").intValue(), temp2.get("avgPPy").intValue()));

            publish();
        }
        return null;
    }

    @Override
    protected void process(List<Void> p) {
        Dashboard.maxPTextField.setText(String.valueOf(temp1.get("maxP")));
        Dashboard.maxPPTextField.setText("Sensor " + String.valueOf(temp1.get("maxPP")));
        Dashboard.minPTextField.setText(String.valueOf(temp1.get("minP")));
        Dashboard.minPPTextField.setText("Sensor " + String.valueOf(temp1.get("minPP")));
        Dashboard.avgPTextField.setText(String.valueOf(temp2.get("avgP")));
        Dashboard.avgPPTextField.setText(Sensors.getAvgPP().getX() - 110 + ", " + Sensors.getAvgPP().getY());
    }
}
