package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class DroneTest {
    @org.junit.Test
    public void testFlyForwards () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);

        drone.flyForwards();

        assertEquals(1, drone.get_x());
    }

    @org.junit.Test
    public void testUpdateBattery () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);

        drone.updateBattery(5);
        assertEquals(95, drone.getBatteryLevel());
    }

    @org.junit.Test
    public void testGetters () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);

        //getBattery
        assertEquals(100, drone.getBatteryLevel());
        //getHeading
        assertEquals("E", drone.getHeading());
        //get x
        assertEquals(0, drone.get_x());
        //get y
        assertEquals(0, drone.get_y());
    }

    @org.junit.Test
    public void testUpdateHeading () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);

        drone.updateHeading(Action.changeHeadingLeft, "E");
        assertEquals("N", drone.getHeading());
    }

    @org.junit.Test
    public void testDistanceFromStartingPoint () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);
        drone.flyForwards();

        assertEquals(1.0, drone.distanceFromStartingPoint());
    }

    @org.junit.Test
    public void testLowBattery () {
        JSONObject info = new JSONObject();
        info.put("budget", 100);
        info.put("heading", "E");
        var drone = new Drone(info);
        drone.flyForwards();

        assertFalse(drone.lowBattery());
    }

}