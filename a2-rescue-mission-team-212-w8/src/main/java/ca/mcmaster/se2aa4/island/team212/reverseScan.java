package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

import java.util.Stack;

public class reverseScan implements Phase {
    private final Drone drone;
    private boolean is_current_phase = true;
    private JSONObject extraInfo;
    private Stack<String> moves = new Stack<String>();
    private String northOrSouth = "0";

    public reverseScan(Drone drone) {
        this.drone = drone;
    }//reverses the drone to scan the missing strips from the right side to left

    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        this.extraInfo = extraInfo;
        return is_current_phase;
    }

    @Override
    public String[] decision() {
        if (northOrSouth.equals("0")){
            if (drone.getHeading().equals("N")){
                northOrSouth = "North";
                return new String[]{drone.getHeading(), "echoForwards"};
            }
            else if(drone.getHeading().equals("S")){
                northOrSouth = "South";
                return new String[]{drone.getHeading(), "echoForwards"};
            }
        }
        if (extraInfo.has("found")) {
            for (int i = extraInfo.getInt("range") - 1; i > 0; i--) {
                moves.add("flyForwards");
            }
        }
        if (northOrSouth.equals("none") && moves.isEmpty()){
            if (extraInfo.has("found")) {
                for (int i = extraInfo.getInt("range") - 2; i > 0; i--) {
                    moves.add("flyForwards");
                }
            }
            else {
                is_current_phase = false;
                return new String[]{drone.getHeading(), "flyForwards"};
            }
        }
        if (northOrSouth.equals("North") && moves.isEmpty()) {
            northOrSouth=("none");
            moves.add("echoForwards");
            moves.add("changeHeadingLeft");
            moves.add("flyForwards");
            moves.add("changeHeadingLeft");
        }
        else if(northOrSouth.equals("South") && moves.isEmpty()) {
            northOrSouth=("none");
            moves.add("echoForwards");
            moves.add("changeHeadingRight");
            moves.add("flyForwards");
            moves.add("changeHeadingRight");
        }
        return new String[]{drone.getHeading(), moves.pop()};
        //return new String[]{drone.getHeading(), "stop"};
    }
}
