package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

import java.util.Stack;

public class moveWest implements Phase {// if after moving to the corner the drone is facing west moves it to face east
    private final Drone drone;
    private boolean is_current_phase,doOnce;
    private JSONObject extraInfo;
    private Stack<String> moves = new Stack<String>();
    public String checkUpOrDown;

    public moveWest(Drone drone) {
        this.drone = drone;
        is_current_phase = true;
        doOnce = true;
        checkUpOrDown = "0";
    }

    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        this.extraInfo = extraInfo;
        return is_current_phase;
    }

    @Override
    public String[] decision() {
        if (!(drone.getHeading().equals("W")) && doOnce){
            is_current_phase = false;
            return new String[]{drone.getHeading(), "scan"};
        }
        if(doOnce){
            doOnce=false;
            return new String[]{drone.getHeading(), "echoForwards"};
        }
        if(checkUpOrDown.equals("check")){
            if(extraInfo.has("range") && (extraInfo.getInt("range")==0)){
                checkUpOrDown = "yes";
                moves.add("changeHeadingRight");
                moves.add("changeHeadingRight");
                moves.add("changeHeadingRight");
                moves.add("changeHeadingLeft");
            }
            else{
                checkUpOrDown = "yes";
                moves.add("changeHeadingLeft");
                moves.add("changeHeadingLeft");
                moves.add("changeHeadingLeft");
                moves.add("changeHeadingRight");
            }
        }
        if(extraInfo.has("range")&&checkUpOrDown.equals("0")) {
            for (int i = extraInfo.getInt("range") - 3; i > 0; i--) {
                moves.add("flyForwards");
            }
        }
        if(!moves.isEmpty()){
            return new String[]{drone.getHeading(), moves.pop()};
        }
        else{
            if (checkUpOrDown.equals("yes")){
                is_current_phase = false;
                return new String[]{drone.getHeading(), "scan"};
            }
            checkUpOrDown = "check";
            return new String[]{drone.getHeading(), "echoRight"};
        }
    }
}
