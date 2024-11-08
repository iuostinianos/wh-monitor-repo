package ellas.wh.datacollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "data-collector.udp.temperature")
public record UDPTemperatureProperties(int serverPort, String serverName, String sensorIdPrefix)
        implements UDPServerProperties {
}
