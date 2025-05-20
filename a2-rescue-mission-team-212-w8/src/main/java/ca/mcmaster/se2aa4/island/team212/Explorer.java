package ca.mcmaster.se2aa4.island.team212;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private CommandCenter commandCenter;
    private JSONObject extraInfo = new JSONObject();

    @Override
    public void initialize(String s) {
        //generates rand battery and starting position, and is always in an edge position
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s))); //<- battery and heading depends on map ig
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading"); //<- gets direction drone is facing? (or position)
        Integer batteryLevel = info.getInt("budget"); //<- get budget
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
        commandCenter = new CommandCenter(info);
    }
    @Override
    public String takeDecision() {
        JSONObject decision = commandCenter.makeDecision(extraInfo);
        logger.info("** Decision: {}",decision.toString()); //<- {"action":"echo","parameters":{"direction":"E"}} for now
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));

        logger.info("The cost of the action was {}", response.getInt("cost"));
        commandCenter.updateDroneStatus(response.getInt("cost"));

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");
        this.extraInfo = extraInfo;

        logger.info("Additional information received: {}", extraInfo);
    }
    @Override
    public String deliverFinalReport() {
        String log = "Send rescue boat to the creek with ID: " + commandCenter.getClosestCreek();
        String result = commandCenter.getClosestCreek();
        logger.info(log);
        logger.info("Remaining battery: " + commandCenter.getRemainingBudget());
        return result;
    }
}
