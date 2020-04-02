package geyer.sensorlab.onn2.models.neurons;

interface NeuronBehaviour {

    int receiveSignal();
    void cognize();
    boolean sendOutput();

    static int dicks = 0;
}
