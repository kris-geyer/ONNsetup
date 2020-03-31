package geyer.sensorlab.onn2.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import geyer.sensorlab.onn2.models.Bipolar;
import geyer.sensorlab.onn2.models.Neuron;
import geyer.sensorlab.onn2.models.Unipolar;

public class NeuronRepository {

    private static String TAG = "NeuronRepository";
    private static NeuronRepository instance;
    private ArrayList<Neuron> dataSet = new ArrayList<>();

    public static NeuronRepository getInstance(){
        if(instance == null){
            instance = new NeuronRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Neuron>> getNeurons(){
        initializeNeurons();
        MutableLiveData<List<Neuron>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void initializeNeurons() {
        dataSet.add(new Unipolar());
        dataSet.add(new Bipolar());
        dataSet.add(new Unipolar());
        dataSet.add(new Unipolar());
    }
}
