package geyer.sensorlab.onn2.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import geyer.sensorlab.onn2.models.AsyncResult;
import geyer.sensorlab.onn2.models.Neuron;
import geyer.sensorlab.onn2.models.Training;
import geyer.sensorlab.onn2.repositories.NeuronRepository;

public class MainActivityViewModel extends ViewModel implements AsyncResult {

    private MutableLiveData<List<Neuron>> neurons;
    private NeuronRepository neuronRepository;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private static String TAG = "MainActivityVM";

    public void init() {
        if(neurons != null){
            return;
        }
        isUpdating.setValue(false);

        neuronRepository = NeuronRepository.getInstance();
        neurons = neuronRepository.getNeurons();
    }


    public LiveData<List<Neuron>> getNeurons(){
        return neurons;
    }

    public void startTraining() {
        if(!isUpdating.getValue()){
            new Training(this).execute(neurons.getValue());
        }
        isUpdating.setValue(true);

    }

    public LiveData<Boolean> getState() {
        return isUpdating;
    }

    @Override
    public void processFinished(List<Neuron> neurons) {

        isUpdating.postValue(false);

        for(Neuron neuron: neurons){
            Log.i(TAG, "Neuron: " + neuron.id + " resources: " + neuron.resources);
        }

        this.neurons.postValue(neurons);
        Log.i(TAG, "async finished");
    }
}
