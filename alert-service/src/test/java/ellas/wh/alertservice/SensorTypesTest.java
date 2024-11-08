package ellas.wh.alertservice;

import ellas.wh.common.models.HumidityData;
import ellas.wh.common.models.SensorData;
import ellas.wh.common.models.TemperatureData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTypesTest {

    private static Object[][] values() {
        return new Object[][]{
                {TemperatureData.class, SensorTypes.TEMPERATURE},
                {HumidityData.class, SensorTypes.HUMIDITY}
        };
    }

    @ParameterizedTest
    @MethodSource("values")
    void getByDataType(Class<? extends SensorData> dataType, SensorTypes sensorType) {
        assertEquals(sensorType, SensorTypes.getByDataType(dataType));
    }
}