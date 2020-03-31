package geyer.sensorlab.onn2.models;

import java.util.List;

public interface AsyncResult {
    void processFinished(List<Neuron> neurons);
}
