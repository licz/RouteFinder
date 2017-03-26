package org.test.byhiras.utils;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.test.byhiras.commons.Network;
import org.test.byhiras.commons.Station;
import org.test.byhiras.commons.TubeLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leszek on 25/03/17.
 */
public class NetworkParserImpl implements NetworkParser {

    private static final Logger logger = LoggerFactory.getLogger(NetworkParserImpl.class);

    @Autowired
    private FileHelper fileHelper;

    @Override
    public Network parseInputFile(String location) {

        Network network = new Network();

        List<String> fileContent = fileHelper.readFromFile(location);

        for (String line : fileContent) {
            String [] array = line.split("\\^");

            logger.info("Processing Tube Line: " + array[0]);

            TubeLine tubeLine = processTubeLineName(array[0], network);
            Map<String, Station> stationsOnLine = processStationNames(array, network, tubeLine);
            tubeLine.addStations(stationsOnLine);
            setStationNeighbours(array, network);
        }

        return network;
    }

    @VisibleForTesting
    TubeLine processTubeLineName(String name, Network network) {
        TubeLine tubeLine;
        if (network.tubeLineExists(name)) {
            tubeLine = network.getLine(name);
        } else {
            tubeLine = new TubeLine(name);
            network.addTubeLine(tubeLine);
        }
        return tubeLine;
    }

    @VisibleForTesting
    Map<String, Station> processStationNames(String [] array, Network network, TubeLine tubeLine) {
        Map<String, Station> stationsOnLine = new HashMap<>();
        for (int i = 1; i < array.length; i++) {
            Station station;
            if (network.stationExists(array[i])) {
                station = network.getStation(array[i]);
            } else {
                station = new Station(array[i]);
                network.addStation(station);
            }
            stationsOnLine.put(array[i], station);
            station.addTubeLine(tubeLine);

        }
        return stationsOnLine;
    }

    @VisibleForTesting
    void setStationNeighbours(String[] array, Network network) {
        for (int i = 1; i < array.length; i++) {
            Station station = network.getStation(array[i]);

            if (i - 1 >= 1) {
                station.addNeighbour(network.getStation(array[i - 1]));
            }

            if (i + 1 < array.length) {
                station.addNeighbour(network.getStation(array[i + 1]));
            }
        }
    }
}
