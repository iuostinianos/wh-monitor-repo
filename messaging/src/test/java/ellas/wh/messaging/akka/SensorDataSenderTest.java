package ellas.wh.messaging.akka;

import ellas.wh.common.models.SensorData;
import ellas.wh.common.models.TemperatureData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorDataSenderTest {
    @InjectMocks
    private SensorDataSender sender;
    @Mock
    private ActorSystemWrapper<SensorData> actorSystem;

    @Test
    void sendWithCallingActor() {
        sender.send(mock(TemperatureData.class));

        verify(actorSystem).tell(any());
    }
}