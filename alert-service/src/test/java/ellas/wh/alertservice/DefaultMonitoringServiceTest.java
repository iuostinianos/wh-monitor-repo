package ellas.wh.alertservice;

import ellas.wh.alertservice.config.MonitorProperties;
import ellas.wh.common.models.HumidityData;
import ellas.wh.common.models.TemperatureData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultMonitoringServiceTest {

    @InjectMocks
    private DefaultMonitoringService service;
    @Mock
    private DefaultAlertingService alertingService;
    @Mock
    private MonitorProperties monitorProperties;

    @Test
    void monitorsAndCallsAlertService() {
        when(monitorProperties.temperature()).thenReturn(new MonitorProperties.Temperature(35d));

        service.monitor(new TemperatureData(35.1));

        verify(monitorProperties).temperature();
        verify(alertingService).alert(35.1, 35d, SensorTypes.TEMPERATURE);
    }

    @Test
    void monitorsAndWithNoCallingAlertService() {
        when(monitorProperties.humidity()).thenReturn(new MonitorProperties.Humidity(50d));

        service.monitor(new HumidityData(50d));

        verify(monitorProperties).humidity();
        verifyNoInteractions(alertingService);
    }
}