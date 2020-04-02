package geyer.sensorlab.onn2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import geyer.sensorlab.onn2.models.neurons.Neuron;
import geyer.sensorlab.onn2.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvData, tvResult, tvPrice;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        initializeViewModel();
        retrievePrice();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initializeUI() {
        tvData = findViewById(R.id.tvData);
        tvResult = findViewById(R.id.tvOutput);
        tvPrice = findViewById(R.id.tvPrice);
        findViewById(R.id.btnStart).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                startTraining();
                break;
        }
    }

    private void startTraining() {
        mainActivityViewModel.startTraining();
    }

    private void initializeViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();

        mainActivityViewModel.getNeurons().observe(this, new Observer<List<Neuron>>() {
            @Override
            public void onChanged(List<Neuron> neurons) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Neurons:").append("\n");
                for (Neuron neuron: neurons){
                    stringBuilder.append("Id: ").append(neuron.id).append(" ; Resources: ").append(neuron.resources)
                            .append(" ; Type: ").append(neuron.type).append("\n");
                }
                tvResult.setText(stringBuilder);
            }
        });

        mainActivityViewModel.getState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    tvData.setText(R.string.training_started);
                }else{
                    tvData.setText(R.string.training_not_underway);
                }
            }
        });

        mainActivityViewModel.getPrice().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvPrice.setText("Price: £" + s);
            }
        });
    }

    private void retrievePrice() {
        mainActivityViewModel.retrieveBitCoinPrice();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mainActivityViewModel.stopDetectingPrice();
    }
}
