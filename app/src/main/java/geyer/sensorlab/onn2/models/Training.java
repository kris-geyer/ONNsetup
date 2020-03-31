package geyer.sensorlab.onn2.models;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import geyer.sensorlab.onn2.viewmodels.MainActivityViewModel;

public class Training extends AsyncTask<Object, Integer, Object> {

    private static String TAG = "Training";
    private final AsyncResult delegate;
    List<Neuron> neurons;

    public Training(MainActivityViewModel delegate){
        this.delegate = delegate;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        neurons = (List<Neuron>) objects[0];
        for(Neuron neuron: neurons){
            neuron.receiveSignal();
            Log.i(TAG, "ID: " + neuron.id);
        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinished(neurons);
    }
}
