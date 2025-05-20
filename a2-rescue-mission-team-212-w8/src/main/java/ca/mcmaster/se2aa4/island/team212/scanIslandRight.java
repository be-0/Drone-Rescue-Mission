package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Stack;

public class scanIslandRight implements Phase {// do the reverse for other directions and scan while changing headings
    private final Drone drone;
    private JSONObject extraInfo;
    private boolean scan = true;
    private boolean echo = true;
    private boolean maybeNextPhase = false;
    private boolean traverseGap = false;
    private int tilesToBrave = 0;
    private boolean organize;
    private final Map map;
    private String intHeading = "0";
    private boolean checkBeside = false;
    private Stack<String> moves = new Stack<String>();

    public scanIslandRight(Drone drone, Map map) {//scans right to left
        this.map = map;
        this.drone = drone;
    }

    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {
        //gets new extraInfo and returns false if current phase is done and should move to next phase.
        this.extraInfo = extraInfo;
        return true;
    }

    @Override
    public String[] decision() {
        JSONArray biomesArray;
        if (scan) {
            scan = false;
            return new String[]{drone.getHeading(), "scan"};
        }

        if(extraInfo.has("creeks")){
            JSONArray creek = extraInfo.getJSONArray("creeks");
            if (!creek.isEmpty()){
                map.recordCreek(drone.get_x(),drone.get_y(),creek.getString(0) );
            }

        }
        if(extraInfo.has("sites")){
            JSONArray sites = extraInfo.getJSONArray("sites");
            if (!sites.isEmpty()){
                map.recordEmergencySite(drone.get_x(),drone.get_y());
            }
        }

        if (organize && extraInfo.has("found") && extraInfo.getString("found").equals("GROUND")) {
            tilesToBrave = extraInfo.getInt("range");
            traverseGap = true;
            organize = false;
            checkBeside = false;
        }

        if (maybeNextPhase) {
            if (extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE")) {
                return new String[]{drone.getHeading(), "stop"};
            }
            else {
                tilesToBrave = extraInfo.getInt("range");
                traverseGap = true;
                maybeNextPhase = false;
            }
            echo = true;
        }

        if (traverseGap) {
            scan = false;
            if (tilesToBrave != 0) {
                tilesToBrave--;
                return new String[]{drone.getHeading(), "flyForwards"};
            }
            traverseGap = false;
            scan = true;
            return new String[]{drone.getHeading(), "flyForwards"};

        } else {
            //if not only ocean keep flying forwards
            if (extraInfo.has("biomes")) {
                biomesArray = extraInfo.getJSONArray("biomes");
                if (!biomesArray.getString(0).equals("OCEAN") || biomesArray.length() > 1) {
                    scan = true;
                    return new String[]{drone.getHeading(), "flyForwards"};
                }
            }
            if (echo) {
                echo = false;
                scan = false;
                organize = true;
                return new String[]{drone.getHeading(), "echoForwards"};
            }
            else {
                if (intHeading.equals("0")){
                    this.intHeading = drone.getHeading();
                    organize = true;
                    checkBeside = true;
                    if(drone.getHeading().equals("N")){
                        moves.add("echoLeft");
                    }
                    else{
                        moves.add("echoRight");
                    }
                    return new String[]{drone.getHeading(), "echoForwards"};
                }

                if (checkBeside){
                    organize = false;
                    if(!moves.isEmpty()){
                        return new String[]{drone.getHeading(),moves.pop()};
                    }
                    if (extraInfo.has("found") && extraInfo.getString("found").equals("GROUND") && (extraInfo.getInt("range")<3)) {
                        if(drone.getHeading().equals("N")){
                            moves.add("echoLeft");
                        }
                        else{
                            moves.add("echoRight");
                        }
                        moves.add("flyForwards");
                        return new String[]{drone.getHeading(),moves.pop()};
                    }
                    else {
                        checkBeside = false;
                        return new String[]{drone.getHeading(), "echoForwards"};
                    }
                }

                switch (intHeading) {
                    case "N" -> {
                        if (!drone.getHeading().equals("S")) {
                            return changeHeading(intHeading);
                        }
                    }
                    case "S" -> {
                        if (!drone.getHeading().equals("N")) {
                            return changeHeading(intHeading);
                        }
                    }
                }
                intHeading = "0";
                maybeNextPhase = true;
                return new String[]{drone.getHeading(), "echoForwards"};
            }
        }
    }

    private String[] changeHeading(String intHeading) {
        switch (intHeading) {
            case ("N") -> {
                return new String[]{drone.getHeading(), "changeHeadingLeft"};
            }
            case ("S") -> {
                return new String[]{drone.getHeading(), "changeHeadingRight"};
            }
        }
        return new String[0];
    }

}