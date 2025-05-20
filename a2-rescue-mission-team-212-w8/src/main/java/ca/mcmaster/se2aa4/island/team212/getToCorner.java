package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

public class getToCorner implements Phase {// if drone spawns in the middle of an edge moves to the corner
    private final Drone drone;
    private JSONObject extraInfo;
    private boolean is_current_phase = true;
    private int check = 0;
    private int counter = 0;
    private int distance;
    private boolean onlyForSouth = false;
    public getToCorner(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        this.extraInfo = extraInfo;
        return is_current_phase;
    }

    @Override
    public String[] decision() {
        if (check == 0){// checks if already in corner
            check++;
            return new String[]{drone.getHeading(), "echoRight"};
        }
        if(extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE") && extraInfo.getInt("range") == 0){
            is_current_phase = false;
        }
        if(check ==1){
            distance = extraInfo.getInt("range")-2;
            check++;
            return new String[]{drone.getHeading(), "echoLeft"};
        }
        if(extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE") && extraInfo.getInt("range") == 0){
            is_current_phase = false;
            return new String[]{drone.getHeading(), "scan"};
        }
        if (check == 2){
            if(counter ==0){
                counter++;
                if (drone.getHeading().equals("S")){
                    distance = extraInfo.getInt("range")-2;
                    onlyForSouth = true;
                    return new String[]{drone.getHeading(), "changeHeadingLeft"};
                }
                return new String[]{drone.getHeading(), "changeHeadingRight"};
            }
            if (counter == 1){
                if (distance!=0){
                    distance--;
                    return new String[]{drone.getHeading(), "flyForwards"};
                }
                is_current_phase = false;
                if (onlyForSouth){
                    return new String[]{drone.getHeading(), "changeHeadingRight"};
                }
                return new String[]{drone.getHeading(), "changeHeadingLeft"};
            }
        }
        return new String[]{drone.getHeading(), "scan"};
    }
}
