package ellas.wh.alertservice;

import ellas.wh.common.models.SensorData;

public interface MonitoringService {
    void monitor(SensorData data);
}
