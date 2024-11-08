package ellas.wh.messaging.akka;


import ellas.wh.common.models.SensorData;

public interface DataSender {
    void send(SensorData sensorData);
}
