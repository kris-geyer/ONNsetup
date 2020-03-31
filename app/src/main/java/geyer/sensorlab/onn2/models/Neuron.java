package geyer.sensorlab.onn2.models;


import geyer.sensorlab.onn2.constants.NeuronConstants;

public abstract class Neuron implements NeuronBehaviour {

    public static int neuronNum = 0;
    public final int id;
    public int resources = 20;
    public final String type;
    private final int costOfBroadcast;

    Neuron (String neuronType){
        id = neuronNum;
        this.type = neuronType;
        neuronNum++;

        if(neuronType.equals(NeuronConstants.unipolar_neuron)){
            costOfBroadcast = NeuronConstants.unipolar_efficiency;
        }else {
            costOfBroadcast = NeuronConstants.bipolar_efficiency;
        }
    }

    boolean haveSufficientResources(){
        return (resources-costOfBroadcast) > 0;
    }

    void expendResources(){
        resources-=costOfBroadcast;
    }



}
