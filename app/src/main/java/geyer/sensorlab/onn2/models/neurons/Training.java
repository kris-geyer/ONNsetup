package geyer.sensorlab.onn2.models.neurons;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import geyer.sensorlab.onn2.viewmodels.MainActivityViewModel;

public class Training extends AsyncTask<Object, Integer, Object> {

    private static String TAG = "Training";
    private final TrainingResult delegate;
    List<Neuron> neurons;

    public Training(MainActivityViewModel delegate){
        this.delegate = delegate;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        int resources = 200;
        neurons = (List<Neuron>) objects[0];
        while(resources > 0){
            int preTrialExpenditure = resources;
            int trialExpenditure = 0;
            int nothingHappened = 0;

            ArrayList<Integer> accurateNeurons = new ArrayList<>();

            for(Neuron neuron: neurons){
                int result =neuron.receiveSignal();
                switch (result){
                    case 0:
                        //ensure that the neurons simply don't run out of resources and nothing happens
                        nothingHappened++;
                        break;
                    case 1:
                        nothingHappened = 0;
                        resources -= neuron.costOfBroadcast;
                        accurateNeurons.add(neuron.id);
                        break;
                    case 2:
                        nothingHappened = 0;
                        resources -= neuron.costOfBroadcast *2;
                        break;
                }
            }


            int resourcesToReward = (preTrialExpenditure - resources);
            Log.i(TAG, "resources to reward: " + resourcesToReward);
            Log.i(TAG, "resources: " + resources);




            for(Neuron neuron: neurons){
                for(Integer id: accurateNeurons){
                    if(neuron.id == id){
                        neuron.resources+= resourcesToReward/accurateNeurons.size();
                        Log.i(TAG, "Neuron ID: " + neuron.id + "resources:" + neuron.resources);
                    }
                }
            }


            if(nothingHappened > 20){
                Log.i(TAG, "Nothing is happening");
                break;
            }

        }






        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinished(neurons);
    }
}
