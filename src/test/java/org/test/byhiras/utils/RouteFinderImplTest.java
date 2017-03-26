package org.test.byhiras.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.byhiras.commons.Network;
import org.test.byhiras.commons.Station;
import org.test.byhiras.commons.TubeLine;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by leszek on 26/03/17.
 */
public class RouteFinderImplTest {

    private RouteFinderImpl routeFinder;

    private NetworkParser mockNetworkParser = mock(NetworkParser.class);

    private Network network;

    @Before
    public void setUp() {
        routeFinder = new RouteFinderImpl(null);
        routeFinder.setNetworkParser(mockNetworkParser);

        network = new Network();

        TubeLine northern = new TubeLine("Northern");
        TubeLine bakerloo = new TubeLine("Bakerloo");

        network.addTubeLine(northern);
        network.addTubeLine(bakerloo);

        network.addStation(createStationOnLine("Oval", northern));
        network.addStation(createStationOnLine("Kennington", northern));
        network.addStation(createStationOnLine("Elephant & Castle", northern));
        network.addStation(createStationOnLine("Waterloo", northern));
        network.addStation(createStationOnLine("Embankment", northern));

        network.getStation("Oval").addNeighbour(network.getStation("Kennington"));
        network.getStation("Kennington").addNeighbour(network.getStation("Oval"));
        network.getStation("Kennington").addNeighbour(network.getStation("Elephant & Castle"));
        network.getStation("Elephant & Castle").addNeighbour(network.getStation("Kennington"));
        network.getStation("Waterloo").addNeighbour(network.getStation("Kennington"));
        network.getStation("Kennington").addNeighbour(network.getStation("Waterloo"));
        network.getStation("Waterloo").addNeighbour(network.getStation("Embankment"));
        network.getStation("Embankment").addNeighbour(network.getStation("Waterloo"));

        network.getStation("Embankment").addTubeLine(bakerloo);
        network.getStation("Waterloo").addTubeLine(bakerloo);

        when(mockNetworkParser.parseInputFile(anyString())).thenReturn(network);
    }

    private Station createStationOnLine(String name, TubeLine tubeLine) {
        Station station = new Station(name);
        station.addTubeLine(tubeLine);
        return station;
    }

    @Test
    public void shouldReturnTrueWhenStationsAreNeighbours() {
        Assert.assertTrue(routeFinder.areNeighbours(network.getStation("Oval"), network.getStation("Kennington")));
    }

    @Test
    public void shouldReturnFalseWhenStationsAreNotNeighbours() {
        Assert.assertFalse(routeFinder.areNeighbours(network.getStation("Oval"), network.getStation("Elephant & Castle")));
    }

    @Test
    public void shouldReturnCurrentConnectionWhenPossibleToContinueJourneyUsingSameLine() {
        Assert.assertEquals(
                network.getLine("Northern"),
                routeFinder.getBestConnection(network.getStation("Waterloo"), network.getStation("Embankment"), network.getLine("Northern"))
        );
    }

    @Test
    public void shouldReturnPossibleConnectionWhenImpossibleToContinueJourneyUsingSameLine() {
        Assert.assertEquals(
                network.getLine("Northern"),
                routeFinder.getBestConnection(network.getStation("Waterloo"), network.getStation("Kennington"), network.getLine("Bakerloo"))
        );
    }

    @Test
    public void shouldReturnAnyConnectionWhenStartingJourney() {
        Assert.assertTrue(
                routeFinder.getBestConnection(network.getStation("Waterloo"), network.getStation("Kennington"), null) != null
        );
    }
}