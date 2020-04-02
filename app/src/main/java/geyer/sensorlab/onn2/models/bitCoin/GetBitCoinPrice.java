package geyer.sensorlab.onn2.models.bitCoin;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBitCoinPrice extends AsyncTask<Void, Void, String> {

    private static String
            TAG = "GetBitCoinPrice",
            bitCoin_endpoint = "https://api.coindesk.com/v1/bpi/currentprice.json";

    private OkHttpClient okHttpClient;
    private String price = "";
    private final BitCoinResponseListener bitCoinResponseListener;

    public GetBitCoinPrice(BitCoinResponseListener bitCoinResponseListener){
        this.bitCoinResponseListener = bitCoinResponseListener;
    }

    @Override
    protected String doInBackground(Void... voids) {

        okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(bitCoin_endpoint).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "Could not get price");
                Log.e(TAG, "Exception: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                StringBuilder result = new StringBuilder();

                try {
                    JSONObject json = new JSONObject(body);
                    JSONObject time = json.getJSONObject("time");
                    result
                            .append("time of update: ")
                            .append(time.getString("updated"))
                            .append("\n");

                    JSONObject bpi = json.getJSONObject("bpi");
                    JSONObject pound = bpi.getJSONObject("GBP");

                    result
                            .append("bitcoin price: ")
                            .append(pound.getString("rate"));
                    price = pound.getString("rate");
                    //

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            Thread.sleep(15*1000);
        } catch (InterruptedException e) {
            Log.e(TAG, "Error: " + e.getLocalizedMessage());
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(price.equals("")){
            bitCoinResponseListener.onFailureToRetrievePrice();
        }else{
            bitCoinResponseListener.onPriceRetrieved(price);
        }
    }
}
