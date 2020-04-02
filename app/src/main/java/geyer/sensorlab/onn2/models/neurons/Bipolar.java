package geyer.sensorlab.onn2.models.neurons;

import android.util.Log;

import geyer.sensorlab.onn2.constants.NeuronConstants;

public class Bipolar extends Neuron {
    private static String TAG = "Bipolar";
    private final int target;

    public Bipolar(int target) {
        super(NeuronConstants.bipolar_neuron, target);
        this.target = target;
    }

    @Override
    public int receiveSignal() {

        if(sendOutput()){
            return target;
        }else{
            return 0;
        }
    }

    @Override
    public void cognize() {

    }

    @Override
    public boolean sendOutput() {
        if(!super.haveSufficientResources()){
            Log.i(TAG, "insufficient resources");
            return false;
        }
        super.expendResources();
        //here send the signal onwards
        Log.i(TAG, "signal sent");
        return true;
    }


}