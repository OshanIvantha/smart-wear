/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.HashMap;

/**
 *
 * @author Ivantha
 */
public class SensorCache {

    private static HashMap<Integer, Integer> sensorValue = new HashMap<>();

    static {
        for (int i = 1; i < 16; i++) {
            sensorValue.put(i, i);
        }
    }

    public static synchronized HashMap<Integer, Integer> getSensors() {
        return sensorValue;
    }

    public static synchronized void setSensors(String input) {
        if (input.length() == 12 && input.charAt(0) == '[' && input.charAt(11) == ']' && input.charAt(5) == '-' && input.charAt(6) == '-') {
            input = input.substring(1, 11);
            String[] s = input.split("--");

            sensorValue = new HashMap<>();
            sensorValue.put(1, Integer.parseInt(s[0]));
            sensorValue.put(2, Integer.parseInt(s[1]));

            for (int i = 3; i < 16; i++) {
                sensorValue.put(i, 0);
            }
        } else {
            System.out.println("Incorrect input  " + input);
        }
    }
}
