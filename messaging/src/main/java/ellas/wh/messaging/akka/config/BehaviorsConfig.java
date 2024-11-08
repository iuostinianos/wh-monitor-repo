package ellas.wh.messaging.akka.config;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import ellas.wh.alertservice.DefaultMonitoringService;
import ellas.wh.alertservice.MonitoringService;
import ellas.wh.common.models.SensorData;
import ellas.wh.messaging.akka.ActorSystemWrapper;
import ellas.wh.messaging.akka.SensorDataOOPReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

;

@Configuration
@ComponentScan(basePackageClasses = DefaultMonitoringService.class)
public class BehaviorsConfig {

    @Bean
    public Behavior<SensorData> sensorDataReceiver(MonitoringService service) {
        return SensorDataOOPReceiver.create(service);
    }

    @Bean(destroyMethod = "close")
    public ActorSystemWrapper<SensorData> actorSystem(Behavior<SensorData> behavior) {
        return new ActorSystemWrapper<>(ActorSystem.create(behavior, "guardian"));
    }
}
