package org.test.byhiras.utils;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.test.byhiras.commons.Network;
import org.test.byhiras.commons.Route;
import org.test.byhiras.commons.Station;
import org.test.byhiras.commons.TubeLine;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
public class RouteFinderImpl implements RouteFinder {

    private static final Logger logger = LoggerFactory.getLogger(RouteFinderImpl.class);

    private static final int TRAVEL_TIME_BETWEEN_TWO_STOPS = 2;
    private static final int TRANSFER_TIME_BETWEEN_TWO_TUBE_LINES = 4;

    @Autowired
    private NetworkParser networkParser;

    private String location;
    private Network network;
    private List<Route> routes;

    public RouteFinderImpl(String location) {
        this.location = location;
    }

    @PostConstruct
    public void init() {
        network = networkParser.parseInputFile(location);
    }

    @Override
    public List<String> anyOptimalRoute(String originStation, String destinationStation) {
        routes = new ArrayList<>();

        Station start = network.getStation(originStation);
        Station stop = network.getStation(destinationStation);

        findAllRoutes(start, stop, 0, null, start.getName());

        if (routes.isEmpty()) {
            return null;
        }

        Collections.sort(routes, (a, b) -> a.getTime().compareTo(b.getTime()));

        logger.info("Possible routes:");
        for (Route route : routes) {
            logger.info(route.getTime() + " - " + Arrays.toString(route.getRoute().toArray()));
        }

        return routes.get(0).getRoute();
    }

    private void findAllRoutes(Station start, Station stop, Integer time, TubeLine currentLine, String route) {
        if (areNeighbours(start, stop)) {
            boolean canContinueOnSameTubeLine = getBestConnection(start, stop, currentLine).equals(currentLine);
            routes.add(
                    new Route(time + calculateTime(canContinueOnSameTubeLine),  route + "," + getBestConnection(start, stop, currentLine).getName() + ", " + stop.getName())
            );
        } else {
            start.getNeighbours().stream().filter(station -> !route.contains(station.getName())).forEach(station -> {
                TubeLine newLine = getBestConnection(start, station, currentLine);
                findAllRoutes(
                        station,
                        stop,
                        time + calculateTime(newLine.equals(currentLine)),
                        newLine,
                        route + "," + newLine.getName() + ", " + station.getName()
                );
            });
        }
    }

    private boolean areNeighbours(Station start, Station stop) {
        return (start.getNeighbours().contains(stop));
    }

    private Integer calculateTime(boolean canContinueOnSameTubeLine) {
        if (canContinueOnSameTubeLine) {
            return TRAVEL_TIME_BETWEEN_TWO_STOPS;
        } else {
            return TRANSFER_TIME_BETWEEN_TWO_TUBE_LINES + TRAVEL_TIME_BETWEEN_TWO_STOPS;
        }
    }

    private TubeLine getBestConnection(Station start, Station stop, TubeLine currentLine) {
        TubeLine result = null;
        for (TubeLine tubeLine : start.getTubeLines()) {
            if (stop.getTubeLines().contains(tubeLine)) {
                if (tubeLine.equals(currentLine)) {
                    return tubeLine;
                }
                result = tubeLine;
            }
        }
        return result;
    }

    @VisibleForTesting
    void setNetworkParser(NetworkParser networkParser) {
        this.networkParser = networkParser;
    }
}
