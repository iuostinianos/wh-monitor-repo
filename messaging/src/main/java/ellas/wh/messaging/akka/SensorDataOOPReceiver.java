package ellas.wh.messaging.akka;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import ellas.wh.alertservice.MonitoringService;
import ellas.wh.common.models.SensorData;


public class SensorDataOOPReceiver extends AbstractBehavior<SensorData> {
    private final MonitoringService service;

    private SensorDataOOPReceiver(ActorContext<SensorData> context, MonitoringService service) {
        super(context);
        this.service = service;
    }

    @Override
    public Receive<SensorData> createReceive() {
        return newReceiveBuilder()
                .onMessage(SensorData.class, this::onArrival)
                .build();
    }

    public static Behavior<SensorData> create(MonitoringService service) {
        return Behaviors.setup(cxt -> new SensorDataOOPReceiver(cxt, service));
    }

    private Behavior<SensorData> onArrival(SensorData sensorData) {
        getContext().getLog().info("Arrived: {}", sensorData);
        Thread.ofVirtual().name("").start(() -> service.monitor(sensorData));
        return Behaviors.same();
    }
}
