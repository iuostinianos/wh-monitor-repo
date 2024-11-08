package ellas.wh.alertservice;

public interface AlertingService {
    void alert(double value, double threshold, SensorTypes sensor);
}
