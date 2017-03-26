package org.test.byhiras.utils;

import java.util.List;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
public interface RouteFinder {
    List<String> anyOptimalRoute(String originStation, String destinationStation);
}