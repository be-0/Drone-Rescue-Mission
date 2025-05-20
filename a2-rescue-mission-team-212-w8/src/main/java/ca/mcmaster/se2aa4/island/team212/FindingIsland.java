package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

public class FindingIsland implements Phase{//locates the start of the island
    private final Drone drone;
    private JSONObject extraInfo;
    private boolean is_current_phase = true;
    private int counter = 0;
    private String direction = "start";
    private boolean needScanForwards = true;
    private boolean needScanRight = true;
    private boolean needScanLeft = true;

    public FindingIsland(Drone drone) {
        this.drone = drone;
    }
    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        //gets new extraInfo and returns false if current phase is done and should move to next phase.
        this.extraInfo = extraInfo;
        return is_current_phase;
    }

    @Override
    public String[] decision() {
        if (this.direction.equals("forwards")){
            if (extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE")){
                this.needScanForwards = false;
            }
        }
        if (this.direction.equals("right")){
            if (extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE") && extraInfo.getInt("range") == 0){
                this.needScanRight = false;
            }
        }
        if (this.direction.equals("left")){
            if (extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE") && extraInfo.getInt("range") == 0){
                this.needScanLeft = false;
            }
        }
        while (true) {
            if (extraInfo!=null && extraInfo.has("found") && extraInfo.getString("found").equals("GROUND")) {
                this.is_current_phase = false;
                switch (direction) {
                    case "right" -> {
                        return new String[]{drone.getHeading(), "changeHeadingRight"};
                    }
                    case "left" -> {
                        return new String[]{drone.getHeading(), "changeHeadingLeft"};
                    }
                    case "forwards" -> {;
                        return new String[]{drone.getHeading(), "flyForwards"};
                    }
                }
            }
            else if (counter == 0) {
                counter++;
                direction = "forwards";
                if (needScanForwards) {
                    return new String[]{drone.getHeading(), "echoForwards"};
                }
            } else if (counter == 1) {
                counter++;
                direction = "right";
                if (needScanRight) {
                    return new String[]{drone.getHeading(), "echoRight"};
                }
            } else if (counter == 2) {
                counter++;
                direction = "left";
                if (needScanLeft) {
                    return new String[]{drone.getHeading(), "echoLeft"};
                }
            } else if (counter == 3) {
                counter = 0;
                return new String[]{drone.getHeading(), "flyForwards"};
            }
        }
    }
}
