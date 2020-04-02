package geyer.sensorlab.onn2.models.neurons;

import java.util.List;

public interface TrainingResult {
    void processFinished(List<Neuron> neurons);
}
