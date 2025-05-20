package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

public interface Phase {
    boolean isCurrentPhase(JSONObject extraInfo);
    String[] decision();
}
