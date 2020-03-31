package geyer.sensorlab.onn2.models;

import android.util.Log;

import geyer.sensorlab.onn2.constants.NeuronConstants;

public class Bipolar extends Neuron {
    private static String TAG = "Bipolar";

    public Bipolar() {
        super(NeuronConstants.bipolar_neuron);
    }

    @Override
    public void receiveSignal() {
        sendOutput();
    }

    @Override
    public void cognize() {

    }

    @Override
    public void sendOutput() {
        if(!super.haveSufficientResources()){
            Log.i(TAG, "insufficient resources");
            return;
        }
        super.expendResources();
        //here send the signal onwards
        Log.i(TAG, "signal sent");
    }
}
