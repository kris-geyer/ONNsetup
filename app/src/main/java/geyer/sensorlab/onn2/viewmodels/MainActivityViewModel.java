package geyer.sensorlab.onn2.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;


import geyer.sensorlab.onn2.models.bitCoin.BitCoinResponseListener;
import geyer.sensorlab.onn2.models.neurons.TrainingResult;
import geyer.sensorlab.onn2.models.neurons.Neuron;
import geyer.sensorlab.onn2.models.neurons.Training;
import geyer.sensorlab.onn2.repositories.NeuronRepository;

public class MainActivityViewModel extends ViewModel implements TrainingResult, BitCoinResponseListener {

    private MutableLiveData<List<Neuron>> neurons;
    private NeuronRepository neuronRepository;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private MutableLiveData<String> bitCoinPrice = new MutableLiveData<>();
    private static String TAG = "MainActivityVM";
    private static boolean requestPrice = true;

    public void init() {
        requestPrice = true;
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

    public LiveData<String> getPrice() {
        return bitCoinPrice;
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

    public void retrieveBitCoinPrice() {
        neuronRepository.retrievePrice(this);
    }


    @Override
    public void onPriceRetrieved(String price) {
        Log.i(TAG, "OnPriceRetrieved - Price: " + price);
        bitCoinPrice.postValue(price);
        if(requestPrice){
            retrieveBitCoinPrice();
        }
    }

    @Override
    public void onFailureToRetrievePrice() {
        Log.i(TAG, "onFailureToRetrievePrice");
    }


    public void stopDetectingPrice() {
        requestPrice=false;
        neuronRepository.stopPriceQuery();
    }
}
