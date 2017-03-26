package org.test.byhiras.commons;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leszek on 26/03/17.
 */
public class Route {
    private Integer time;
    private List<String> route;

    public Route(Integer time, String route) {
        this.time = time;
        this.route = Arrays.asList(route.split(","));
    }

    public Integer getTime() {
        return time;
    }

    public List<String> getRoute() {
        return route;
    }
}
