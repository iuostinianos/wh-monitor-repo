package ellas.wh.alertservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DefaultAlertingServiceTest {
    @InjectMocks
    private DefaultAlertingService service;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void alertReturnsAlertForTemperature() {
        var output = """
                ################################################
                # Attention! Temperature is over 50.0℃: 51.0℃ #
                ################################################""";

        service.alert(51d, 50d, SensorTypes.TEMPERATURE);

        assertTrue(outputStreamCaptor.toString()
                .trim().contains(output));
    }

    @Test
    void alertReturnsAlertForHumidity() {
        var output = """
                ############################################
                # Attention! Humidity is over 50.0%: 51.0% #
                ############################################""";

        service.alert(51d, 50d, SensorTypes.HUMIDITY);

        assertTrue(outputStreamCaptor.toString()
                .trim().contains(output));
    }
}