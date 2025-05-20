package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class FindingIslandTest {
    @org.junit.Test
    public void testIsCurrentPhase () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        Drone drone = new Drone(info);

        FindingIsland findIsland = new FindingIsland(drone);
        assertTrue(findIsland.isCurrentPhase(info));
    }

    @org.junit.Test
    public void testDecision () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        Drone drone = new Drone(info);

        FindingIsland findIsland = new FindingIsland(drone);
        assertTrue(findIsland.isCurrentPhase(info));
    }

}