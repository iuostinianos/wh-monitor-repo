package ellas.wh.datacollector.config;

import ellas.wh.datacollector.server.UDPServer;
import ellas.wh.messaging.akka.DataSender;
import ellas.wh.messaging.akka.SensorDataSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan(basePackageClasses = SensorDataSender.class)
@EnableConfigurationProperties({UDPTemperatureProperties.class, UDPHumidityProperties.class})
public class ServerConfig {

    @Bean(destroyMethod = "close")
    @Qualifier("temperatureServer")
    public UDPServer temperatureServer(UDPTemperatureProperties properties, DataSender dataSender) throws IOException {
        return new UDPServer(properties, dataSender);
    }

    @Bean(destroyMethod = "close")
    @Qualifier("humidityServer")
    public UDPServer humidityServer(UDPHumidityProperties properties, DataSender dataSender) throws IOException {
        return new UDPServer(properties, dataSender);
    }

    @Bean
    CommandLineRunner temperatureServerRunner(@Qualifier("temperatureServer") UDPServer server) {
        return args -> server.start();
    }

    @Bean
    CommandLineRunner humidityServerRunner(@Qualifier("humidityServer") UDPServer server) {
        return args -> server.start();
    }
}
