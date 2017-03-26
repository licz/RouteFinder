package org.test.byhiras.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.test.byhiras.utils.FileHelperImpl;
import org.test.byhiras.utils.NetworkParserImpl;
import org.test.byhiras.utils.RouteFinderImpl;

/**
 * Created by leszek.sosnowski on 24/03/2017.
 */
@Configuration
@PropertySource("classpath:route-finder.properties")
public class Config {

    @Value("${route.finder.network.filename}")
    private String filename;

    @Bean
    public NetworkParserImpl getNetworkParserImpl() {
        return new NetworkParserImpl();
    }

    @Bean
    public RouteFinderImpl getRouteFinderImpl() {
        return new RouteFinderImpl(filename);
    }

    @Bean
    public FileHelperImpl getFileHelperImpl() {
        return new FileHelperImpl();
    }
}
