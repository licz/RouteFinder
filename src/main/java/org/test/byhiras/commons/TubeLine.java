package org.test.byhiras.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
public class TubeLine {
    private String name;
    private Map<String, Station> stations = new HashMap<>();

    public TubeLine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStations(Map<String, Station> stations) {
        this.stations.putAll(stations);
    }
}
