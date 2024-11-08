package ellas.wh.alertservice;

import ellas.wh.common.models.HumidityData;
import ellas.wh.common.models.SensorData;
import ellas.wh.common.models.TemperatureData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


@RequiredArgsConstructor
public enum SensorTypes {
    TEMPERATURE("Temperature", TemperatureData.class),
    HUMIDITY("Humidity", HumidityData.class);

    @Getter
    private final String sensorDomain;
    private final Class<? extends SensorData> dataType;

    public static SensorTypes getByDataType(Class<? extends SensorData> dataType) {
        return Arrays.stream(values())
                .filter(type -> type.dataType.equals(dataType))
                .findFirst()
                .orElseThrow();
    }
}
