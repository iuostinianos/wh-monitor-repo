package ellas.wh.common.models;

import java.io.Serial;
import java.io.Serializable;

public record TemperatureData(double value) implements SensorData, Serializable {

    @Serial
    private static final long serialVersionUID = 100000000000000008L;
}
