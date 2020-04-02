package geyer.sensorlab.onn2.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import geyer.sensorlab.onn2.models.neurons.Bipolar;
import geyer.sensorlab.onn2.models.bitCoin.BitCoinResponseListener;
import geyer.sensorlab.onn2.models.bitCoin.GetBitCoinPrice;
import geyer.sensorlab.onn2.models.neurons.Neuron;
import geyer.sensorlab.onn2.models.neurons.Unipolar;

public class NeuronRepository {

    private static boolean asyncFunctioning;
    private GetBitCoinPrice getBitCoinPrice;

    private static NeuronRepository instance;
    private ArrayList<Neuron> dataSet = new ArrayList<>();

    public static NeuronRepository getInstance(){
        asyncFunctioning = false;
        if(instance == null){
            instance = new NeuronRepository();
        }
        return instance;
    }

    public void retrievePrice(BitCoinResponseListener bitCoinResponseListener){
        getBitCoinPrice = new GetBitCoinPrice(bitCoinResponseListener);
        getBitCoinPrice.execute();
        asyncFunctioning = true;
    }

    public MutableLiveData<List<Neuron>> getNeurons(){
        initializeNeurons();
        MutableLiveData<List<Neuron>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void initializeNeurons() {
        dataSet.add(new Unipolar(2));
        dataSet.add(new Unipolar(1));
        dataSet.add(new Bipolar(2));
        dataSet.add(new Bipolar(1));
    }

    public void stopPriceQuery() {
        if(asyncFunctioning){
            getBitCoinPrice.cancel(true);
        }
    }
}
