package ca.mcmaster.se2aa4.island.team212;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//test does not work :(((((((((
public class CommandCenterTest { //makeDecision and implementDecision
    /*@org.junit.Test
    public void testImplementDecision () {
        JSONObject info = new JSONObject();
        JSONObject parameters = new JSONObject();
        var rcc = new CommandCenter (info);

        //see if normal commands work
        assertEquals("fly", rcc.implementDecision("N", Action.flyForwards).get("action"));
        assertEquals("echo", rcc.implementDecision("E", Action.echoForwards).get("action"));
        assertEquals("heading", rcc.implementDecision("N", Action.changeHeadingLeft).get("action"));
        assertEquals("scan", rcc.implementDecision("E", Action.scan).get("action"));

        //see if directions work
        parameters.put("direction", "W");
        assertEquals(parameters, rcc.implementDecision("E", Action.echoRight).get("parameters"));

        //see if directions is null
        assertNull(rcc.implementDecision("N", Action.flyForwards));
    }*/

    //test makeDecisions
    /*@org.junit.Test
    public void testMakeDecisions () {
        JSONObject info = new JSONObject();
        //JSONObject parameters = new JSONObject();
        var rcc = new CommandCenter (info);
        assertNull(rcc.makeDecision(info));
    }*/
}