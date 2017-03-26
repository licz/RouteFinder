package org.test.byhiras.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
public class Station {
    private String name;
    private List<TubeLine> tubeLines;
    private List<Station> neighbours;

    public Station(String name) {
        this.name = name;
        tubeLines = new ArrayList<>();
        neighbours = new ArrayList<>();
    }

    public void addTubeLine(TubeLine tubeLine) {
        tubeLines.add(tubeLine);
    }

    public void addNeighbour(Station station) {
        neighbours.add(station);
    }

    public String getName() {
        return name;
    }

    public List<TubeLine> getTubeLines() {
        return tubeLines;
    }

    public List<Station> getNeighbours() {
        return neighbours;
    }
}
