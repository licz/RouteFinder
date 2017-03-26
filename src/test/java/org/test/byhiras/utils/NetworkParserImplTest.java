package org.test.byhiras.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.byhiras.commons.Network;
import org.test.byhiras.commons.Station;
import org.test.byhiras.commons.TubeLine;

import java.util.Map;

/**
 * Created by leszek on 26/03/17.
 */
public class NetworkParserImplTest {
    
    private static final String [] ARRAY = new String [] {"Northern", "Oval", "Kennington", "Elephant & Castle"};
    private static final String NORTHERN = "Northern";

    private static Network NETWORK;

    private NetworkParserImpl networkParser = new NetworkParserImpl();

    @Before
    public void setUp() {
        NETWORK = new Network();
    }

    @Test
    public void shouldCreateNewTubeLineWhenNotInTheNetwork() {
        Assert.assertFalse(NETWORK.tubeLineExists(NORTHERN));

        TubeLine tubeLine = networkParser.processTubeLineName(NORTHERN, NETWORK);

        Assert.assertTrue(NETWORK.tubeLineExists(NORTHERN));
        Assert.assertEquals(tubeLine, NETWORK.getLine(NORTHERN));
    }

    @Test
    public void shouldUseAlreadyExistingTubeLine() {
        TubeLine tubeLine = networkParser.processTubeLineName(NORTHERN, NETWORK);

        TubeLine tubeLineInQuestion = networkParser.processTubeLineName(NORTHERN, NETWORK);

        Assert.assertEquals(tubeLine, tubeLineInQuestion);
    }

    @Test
    public void shouldCreateStationsForTubeLine() {
        TubeLine tubeLine = networkParser.processTubeLineName(NORTHERN, NETWORK);

        Map<String, Station> stations = networkParser.processStationNames(ARRAY, NETWORK, tubeLine);

        Assert.assertEquals(NETWORK.getLine(NORTHERN), NETWORK.getStation("Oval").getTubeLines().get(0));
        Assert.assertEquals(NETWORK.getLine(NORTHERN), NETWORK.getStation("Kennington").getTubeLines().get(0));
        Assert.assertEquals(NETWORK.getLine(NORTHERN), NETWORK.getStation("Elephant & Castle").getTubeLines().get(0));
        Assert.assertEquals(3, stations.size());
    }

    @Test
    public void shouldUseAlreadyExistingStation() {
        Station oval = new Station("Oval");
        NETWORK.addStation(oval);

        TubeLine tubeLine = networkParser.processTubeLineName(NORTHERN, NETWORK);
        Map<String, Station> stations = networkParser.processStationNames(ARRAY, NETWORK, tubeLine);

        Assert.assertEquals(oval, NETWORK.getStation("Oval"));
        Assert.assertEquals(oval, stations.get("Oval"));
    }

    @Test
    public void shouldSetNeighbours() {
        TubeLine tubeLine = networkParser.processTubeLineName(NORTHERN, NETWORK);
        networkParser.processStationNames(ARRAY, NETWORK, tubeLine);

        networkParser.setStationNeighbours(ARRAY, NETWORK);

        Assert.assertEquals(1, NETWORK.getStation("Oval").getNeighbours().size());
        Assert.assertEquals(NETWORK.getStation("Kennington"), NETWORK.getStation("Oval").getNeighbours().get(0));

        Assert.assertEquals(2, NETWORK.getStation("Kennington").getNeighbours().size());
        Assert.assertEquals(NETWORK.getStation("Oval"), NETWORK.getStation("Kennington").getNeighbours().get(0));
        Assert.assertEquals(NETWORK.getStation("Elephant & Castle"), NETWORK.getStation("Kennington").getNeighbours().get(1));

        Assert.assertEquals(1, NETWORK.getStation("Elephant & Castle").getNeighbours().size());
        Assert.assertEquals(NETWORK.getStation("Kennington"), NETWORK.getStation("Elephant & Castle").getNeighbours().get(0));
    }
}