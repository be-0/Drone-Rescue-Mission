package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Stack;

public class scanIslandLeft implements Phase {// do the reverse for other directions and scan while changing headings
    private final Drone drone;
    private boolean is_current_phase, scan, echo, maybeNextPhase, traverseGap, organize, checkBeside;
    private JSONObject extraInfo;
    private final Map map;
    private int tilesToBrave;
    private String intHeading;
    private Stack<String> moves;

    public scanIslandLeft(Drone drone, Map map) {
        this.map = map;
        this.drone = drone;
        is_current_phase = true;
        scan = true;
        echo = true;
        maybeNextPhase = false;
        traverseGap = false;
        tilesToBrave = 0;
        intHeading = "0";
        checkBeside = false;
        moves = new Stack<String>();
    }

    @Override
    public boolean isCurrentPhase(JSONObject extraInfo) {// starts scanning island from the left side every other tile
        //gets new extraInfo and returns false if current phase is done and should move to next phase.
        this.extraInfo = extraInfo;
        return is_current_phase;
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
            checkBeside = false;
            organize = false;
        }

        if (maybeNextPhase) {
            if (extraInfo.has("found") && extraInfo.getString("found").equals("OUT_OF_RANGE")) {
                is_current_phase = false;
                return new String[]{drone.getHeading(), "scan"};
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
            //echo
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
                        moves.add("echoRight");
                    }
                    else{
                        moves.add("echoLeft");
                    }
                    return new String[]{drone.getHeading(), "echoForwards"};
                }

                if (checkBeside){
                    organize = false;
                    if(!moves.isEmpty()){
                        return new String[]{drone.getHeading(),moves.pop()};
                    }
                    if(extraInfo.has("found") && extraInfo.getString("found").equals("GROUND") && (extraInfo.getInt("range")<3)) {
                        if(drone.getHeading().equals("N")){
                            moves.add("echoRight");
                        }
                        else{
                            moves.add("echoLeft");
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
                return new String[]{drone.getHeading(), "changeHeadingRight"};
            }
            case ("S") -> {
                return new String[]{drone.getHeading(), "changeHeadingLeft"};
            }
        }
        return new String[0];
    }
}