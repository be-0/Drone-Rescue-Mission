package ca.mcmaster.se2aa4.island.team212;

public class Map {
    private int emergency_site_x;
    private int emergency_site_y;
    private boolean found_emergency_site = false;
    private final int[] creek_x = new int[20];
    private final int[] creek_y = new int[20];
    private int number_of_creeks = 0;
    private final String[] creek_ids = new String[20];
    public void recordEmergencySite(int x, int y){
        emergency_site_x = x;
        emergency_site_y = y;
        found_emergency_site = true;
    }
    public void recordCreek(int x, int y, String id){
        creek_x[number_of_creeks] = x;
        creek_y[number_of_creeks] = y;
        creek_ids[number_of_creeks] = id;
        number_of_creeks++;
    }
    public String calculateClosestCreek(){
        String closest_creek_id = "";
        double distance;
        double shortest_distance = 1000000000;
        if (found_emergency_site) {
            if (foundCreeks()) {
                for (int i = 0; i < number_of_creeks; i++) {
                    distance = Math.sqrt(Math.pow(emergency_site_x - creek_x[i], 2) + Math.pow(emergency_site_y - creek_y[i], 2));
                    if (distance < shortest_distance) {
                        shortest_distance = distance;
                        closest_creek_id = creek_ids[i];
                    }
                }
            }
            else {
                closest_creek_id = "No creeks found";
            }
        }
        else {
            if (foundCreeks()){
                closest_creek_id = newestCreekID();
            } else {
                closest_creek_id = "No creeks found 1";
            }
        }
        return closest_creek_id;
    }
    public boolean foundCreeks(){
        return number_of_creeks > 0;
    }

    public String newestCreekID(){
        return creek_ids[number_of_creeks-1];
    }


}
