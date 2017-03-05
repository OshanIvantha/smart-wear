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

    private static HashMap<Integer, Integer> leftSensorValues = new HashMap<>();
    private static HashMap<Integer, Integer> rightSensorValues = new HashMap<>();

    static {
        for (int i = 1; i < 19; i++) {
            leftSensorValues.put(i, 0);
            rightSensorValues.put(i, 0);
        }
    }

    public static HashMap<Integer, Integer> getLeftSensorValues() {
        return leftSensorValues;
    }

    public static HashMap<Integer, Integer> getRightSensorValues() {
        return rightSensorValues;
    }

    public static void setLeftSensorValues(HashMap<Integer, Integer> leftSensorValues) {
        SensorCache.leftSensorValues = leftSensorValues;
    }

    public static void setRightSensorValues(HashMap<Integer, Integer> rightSensorValues) {
        SensorCache.rightSensorValues = rightSensorValues;
    }

    public static void setSensors(String input) {
        if (input.length() == 12 && input.charAt(0) == '[' && input.charAt(11) == ']' && input.charAt(5) == '-' && input.charAt(6) == '-') {
            input = input.substring(1, 11);
            String[] s = input.split("--");

            leftSensorValues = new HashMap<>();
            leftSensorValues.put(1, Integer.parseInt(s[0]));
            leftSensorValues.put(2, Integer.parseInt(s[1]));

            for (int i = 3; i < 19; i++) {
                leftSensorValues.put(i, 0);
            }
        } else {
            System.out.println("Incorrect input  " + input);
        }
    }
}
