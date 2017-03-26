package org.test.byhiras.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
public class Network {
    private Map<String, TubeLine> tubeLines;
    private Map<String, Station> stations;

    public Network() {
        tubeLines = new HashMap<>();
        stations = new HashMap<>();
    }

    public TubeLine getLine(String name) {
        if (!tubeLines.containsKey(name)) {
            throw new IllegalArgumentException("Tube Line does not exist: " + name);
        }

        return tubeLines.get(name);
    }

    public Station getStation(String name) {
        if (!stations.containsKey(name)) {
            throw new IllegalArgumentException("Station does not exist: " + name);
        }

        return stations.get(name);
    }

    public void addTubeLine(TubeLine tubeLine) {
        tubeLines.put(tubeLine.getName(), tubeLine);
    }

    public void addStation(Station station) {
        stations.put(station.getName(), station);
    }

    public boolean stationExists(String name) {
        return stations.containsKey(name);
    }

    public boolean tubeLineExists(String name) {
        return tubeLines.containsKey(name);
    }
}
