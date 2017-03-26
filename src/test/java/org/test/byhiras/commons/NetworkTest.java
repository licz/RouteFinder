package org.test.byhiras.commons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by leszek on 26/03/17.
 */
public class NetworkTest {

    private Network network = new Network();

    @Before
    public void setUp() {
        network = new Network();
    }

    @Test
    public void shouldAddTubeLine() {
        network.addTubeLine(new TubeLine("ASD"));
        Assert.assertTrue(network.tubeLineExists("ASD"));
    }

    @Test
    public void shouldAddStation() {
        network.addStation(new Station("ASD"));
        Assert.assertTrue(network.stationExists("ASD"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTubeLineDoesNotExist() {
        network.getLine("XXX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenStationDoesNotExist() {
        network.getStation("XXX");
    }
}