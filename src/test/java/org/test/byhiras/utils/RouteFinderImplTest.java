package org.test.byhiras.utils;

import org.junit.Before;
import org.junit.Test;
import org.test.byhiras.commons.Network;
import org.test.byhiras.commons.Station;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by leszek on 26/03/17.
 */
public class RouteFinderImplTest {

    private RouteFinderImpl routeFinder;

    private NetworkParser mockNetworkParser = mock(NetworkParser.class);

    @Before
    public void setUp() {
        routeFinder = new RouteFinderImpl(null);
        routeFinder.setNetworkParser(mockNetworkParser);

        Network network = new Network();

        network.addStation(new Station("Oval"));

        when(mockNetworkParser.parseInputFile(anyString())).thenReturn(network);
    }

    @Test
    public void shouldReturnTrueWhenStationsAreNeighbours() {

    }

    @Test
    public void shouldReturnFalseWhenStationsAreNotNeighbours() {

    }

    @Test
    public void shouldReturnCurrentConnectionWhenPossibleToContinueJourneyUsingSameLine() {

    }

    @Test
    public void shouldReturnPossibleConnectionWhenImpossibleToContinueJourneyUsingSameLine() {

    }
}