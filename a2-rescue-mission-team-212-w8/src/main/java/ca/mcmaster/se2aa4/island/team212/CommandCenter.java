package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;


public class CommandCenter {
    private final Drone drone;
    private Map map;
    private final Phase[] phase;
    private int num;

    /*
    contructor: CommandCenter
    input: JSONObject
    output: n/a
    description: initialize CommandCenter
     */
    public CommandCenter (JSONObject info) {
        drone = new Drone(info);
        map = new Map();
        phase = new Phase[]{new getToCorner(drone), new getProperApproach(drone), new moveWest(drone), new FindingIsland(drone), new scanIslandLeft(drone,map),new reverseScan(drone), new scanIslandRight(drone,map)};
        num = 0;
    }

    /*
    name: implementDecision
    input: Action object, String
    output: JSONObject
    description: puts in decision depending on action given
     */
    public JSONObject implementDecision(String direction, Action action) {
        JSONObject decision = new JSONObject();// we stop the exploration immediately
        JSONObject parameters = new JSONObject();

        switch (action) {
            case echoForwards -> {
                decision.put("action", "echo");
                parameters.put("direction", direction);// change battery requirement when we figure it out :D
            }
            case echoLeft,echoRight ->{
                decision.put("action", "echo");
                System.out.println(drone.getRightOrLeft(action, direction));
                parameters.put("direction", drone.getRightOrLeft(action, direction));
            }
            case changeHeadingRight, changeHeadingLeft -> {//changes heading to the right
                decision.put("action", "heading");
                parameters.put("direction", drone.updateHeading(action, direction));//needs to change directions
            }
            case flyForwards -> {
                decision.put("action", "fly");
                drone.flyForwards();
            }
            case scan -> {
                decision.put("action", "scan");
            }
            case stop -> {
                decision.put("action", "stop");
            }
            default -> decision.put("action", "stop");
        }
        //drone.updateBattery(action);
        decision.put("parameters", parameters);
        return decision;
    }

    /*
    name: makeDecision
    input: n/a
    output: Action object
    description: tells drone what to do, creates series of actions for drone to do
     */
    public JSONObject makeDecision(JSONObject extraInfo) {
        if (drone.lowBattery()) {
            return implementDecision(drone.getHeading(), Action.stop);
        }
        else {
            if (!phase[num].isCurrentPhase(extraInfo)){
                num++;
            }
            String[] decision = phase[num].decision();
            return implementDecision(decision[0], Action.valueOf(decision[1]));
        }
    }

    public void updateDroneStatus(int cost){
        drone.updateBattery(cost);
    }
    public String getClosestCreek(){
        return map.calculateClosestCreek();
    }
    public int getRemainingBudget(){
        return drone.getBatteryLevel();
    }


}
