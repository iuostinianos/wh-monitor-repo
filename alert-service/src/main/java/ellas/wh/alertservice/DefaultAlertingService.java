package ellas.wh.alertservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultAlertingService implements AlertingService {

    @Override
    public void alert(double value, double threshold, SensorTypes sensor) {
        var measureUnit = getMeasureUnit(sensor);
        var alertData = String.format("# Attention! %s is over %s%s: %s%s #",
                sensor.getSensorDomain(), threshold, measureUnit, value, measureUnit);
        var hashNumber = sensor == SensorTypes.HUMIDITY ? alertData.length() : alertData.length() + 1;
        var infoMsg = new StringBuilder("\n")
                .append("#".repeat(hashNumber))
                .append("\n")
                .append(alertData)
                .append("\n")
                .append("#".repeat(hashNumber)).toString();
        log.info(infoMsg);
    }

    private String getMeasureUnit(SensorTypes type) {
        return switch (type) {
            case TEMPERATURE -> "\u2103";
            case HUMIDITY -> "%";
        };
    }
}
