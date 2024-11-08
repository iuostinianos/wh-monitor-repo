package ellas.wh.messaging.akka;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import ellas.wh.common.models.SensorData;


public class SensorDataReceiver {

    public static Behavior<SensorData> create() {
        return Behaviors.setup(SensorDataReceiver::listening);
    }

    private static Behavior<SensorData> listening(ActorContext<SensorData> cxt) {
        return Behaviors.receive(SensorData.class)
                .onMessage(SensorData.class, data -> onArrival(data, cxt))
                .build();
    }

    private static Behavior<SensorData> onArrival(SensorData data, ActorContext<SensorData> cxt) {
        cxt.getLog().info("Arrived: {}", data);
        return Behaviors.same();
    }
}
