package ellas.wh.messaging.akka;


import ellas.wh.common.models.SensorData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataSender implements DataSender {
    private final ActorSystemWrapper<SensorData> closableActorSystem;

    @Override
    public void send(SensorData sensorData) {
        closableActorSystem.tell(sensorData);
    }
}
