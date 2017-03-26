package org.test.byhiras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.test.byhiras.utils.RouteFinder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
@Component
public class Engine implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    @Autowired
    private RouteFinder routeFinder;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        sayHello();

        Scanner in = new Scanner(System.in);
        String line;

        while (!"quit".equals(line = in.nextLine())) {
            try {
                String start = line.split(",")[0];
                String stop = line.split(",")[1];
                logger.info("Optimal Route: " + Arrays.toString(routeFinder.anyOptimalRoute(start, stop).toArray()));
            } catch (Exception ex) {
                logger.error("Something went wrong!", ex);
                sayHello();
            }
        }
    }

    private void sayHello() {
        logger.info("Welcome to the Route Finder!");
        logger.info(" - type: station1,station2 to find route between the stations");
        logger.info(" - to exit type: quit");
    }
}
