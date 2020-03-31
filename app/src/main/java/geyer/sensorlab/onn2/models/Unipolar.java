package geyer.sensorlab.onn2.models;

import android.util.Log;

import geyer.sensorlab.onn2.constants.NeuronConstants;

public class Unipolar extends Neuron {

    private static String TAG = "Unipolar";

    public Unipolar() {
        super(NeuronConstants.unipolar_neuron);
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
        Log.i(TAG, "Signal sent");
    }
}
