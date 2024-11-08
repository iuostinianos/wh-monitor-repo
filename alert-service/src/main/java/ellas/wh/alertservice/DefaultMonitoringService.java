package ellas.wh.alertservice;

import ellas.wh.alertservice.config.MonitorProperties;
import ellas.wh.common.models.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMonitoringService implements MonitoringService {
    private final AlertingService alertingService;
    private final MonitorProperties monitorProperties;


    @Override
    public void monitor(SensorData data) {
        var sensorType = SensorTypes.getByDataType(data.getClass());
        var threshold = getThreshold(sensorType);
        if (Double.compare(data.value(), threshold) == 1) {
            alertingService.alert(data.value(), threshold, sensorType);
        }
    }

    private double getThreshold(SensorTypes sensorType) {
        return switch (sensorType) {
            case TEMPERATURE -> monitorProperties.temperature().threshold();
            case HUMIDITY -> monitorProperties.humidity().threshold();
        };
    }
}
