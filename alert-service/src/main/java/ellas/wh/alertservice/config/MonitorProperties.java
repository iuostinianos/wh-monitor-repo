package ellas.wh.alertservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "alert-service.monitor")
public record MonitorProperties(Temperature temperature, Humidity humidity) {
    public record Temperature(double threshold) {
    }

    public record Humidity(double threshold) {
    }
}
