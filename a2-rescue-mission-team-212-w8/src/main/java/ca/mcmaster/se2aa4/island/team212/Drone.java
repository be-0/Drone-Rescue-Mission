package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

public class Drone {

    private String heading;
    private int batteryLevel;
    private int x = 0;
    private int y = 0;

    /*
    contructor: Drone
    input: JSONObject
    output: n/a
    description: initialize drone
     */
    public Drone (JSONObject info) {
        batteryLevel = info.getInt("budget");
        heading = info.getString("heading");
    }

    public void flyForwards() {
        switch (heading) {
            case "N" -> {
                y++;
            }
            case "S" -> {
                y--;
            }
            case "E" -> {
                x++;
            }
            case "W" -> {
                x--;
            }
        }
    }

    /*
    name: updateBattery
    input: Action object
    output: void
    description: update battery level based on action
     */

    public void updateBattery(int cost) {
        batteryLevel -= cost;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public String updateHeading(Action action, String direction) {
        flyForwards();
        switch (action) {
            case changeHeadingRight -> {
                switch (direction) {
                    case "E" -> {
                        heading = "S";
                    }
                    case "S" -> {
                        heading = "W";
                    }
                    case "W" -> {
                        heading = "N";
                    }
                    case "N" -> {
                        heading = "E";
                    }
                }
            }
            case changeHeadingLeft -> {
                switch (direction) {
                    case "E" -> {
                        heading = "N";
                    }
                    case "S" -> {
                        heading = "E";
                    }
                    case "W" -> {
                        heading = "S";
                    }
                    case "N" -> {
                        heading = "W";
                    }
                }
            }
        }
        flyForwards();
        return heading;
    }

    /*
    name: checkBatteryLevel
    input: n/a
    output: boolean
    description: checks if drone battery level = 0
     */


    public String getHeading() {
        return heading;
    }

    public String getRightOrLeft(Action action, String direction) {
        String dir = "E";
        switch (action) {
            case echoRight -> {
                switch (direction) {
                    case "E" -> {
                        dir = "S";
                    }
                    case "S" -> {
                        dir = "W";
                    }
                    case "W" -> {
                        dir = "N";
                    }
                    case "N" -> {
                        dir = "E";
                    }
                }
            }
            case echoLeft -> {
                switch (direction) {
                    case "E" -> {
                        dir = "N";
                    }
                    case "S" -> {
                        dir = "E";
                    }
                    case "W" -> {
                        dir = "S";
                    }
                    case "N" -> {
                        dir = "W";
                    }
                }
            }
        }
        return dir;
    }


    public int get_x(){
        return this.x;
    }

    public int get_y(){
        return this.y;
    }

    public double distanceFromStartingPoint() {
        double distance = Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2));;
        return distance;
    }


    public boolean lowBattery() {
        double average_stop_cost = 0.14*distanceFromStartingPoint()+9.2;
        double max_stop_cost = average_stop_cost+5;
        double max_other_action_cost = 8;
        return batteryLevel <= max_stop_cost+max_other_action_cost;
    }

}
