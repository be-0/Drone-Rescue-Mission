package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Stack;

public class getProperApproach implements Phase {
    private final Drone drone;
    private boolean left,doOnce,is_current_phase, checkOnce, checkTwice;
    private JSONObject extraInfo;
    private Stack<String> moves = new Stack<String>();

    public getProperApproach(Drone drone) {
        this.drone = drone;
        doOnce = true;
        is_current_phase = true;
        checkOnce = true;
        checkTwice = true;
    }//ensures drone is facing east or west


    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        this.extraInfo = extraInfo;
        return is_current_phase;
    }

    @Override
    public String[] decision() {

        String heading = drone.getHeading();
        if(checkOnce){
            checkOnce = false;
            return new String[]{drone.getHeading(), "echoRight"};
        }
        if(checkTwice){
            checkTwice = false;
            if (drone.getHeading().equals("N")){
                left = extraInfo.getInt("range") != 0;
            }
            if (drone.getHeading().equals("S")){
                left = extraInfo.getInt("range") == 0;
            }
        }

        if (drone.getHeading().equals("E") || drone.getHeading().equals("W")){
            is_current_phase = false;
            return new String[]{drone.getHeading(), "scan"};
        }

        if(doOnce){
            doOnce=false;
            return new String[]{drone.getHeading(), "echoForwards"};
        }
        if(extraInfo.has("range")) {
            for (int i = extraInfo.getInt("range") - 1; i > 0; i--) {
                moves.add("flyForwards");
            }
        }

        if (moves.isEmpty()) {
            is_current_phase = false;
            switch (heading) {
                case "S" -> {
                    if (left) {
                        moves.add("changeHeadingLeft");
                    } else {
                        moves.add("changeHeadingRight");
                    }
                }
                case "N" -> {
                    if (left) {
                        moves.add("changeHeadingRight");
                    } else {
                        moves.add("changeHeadingLeft");
                    }
                }
            }
        }
        return new String[]{drone.getHeading(), moves.pop()};
    }
}
