package geyer.sensorlab.onn2.models.bitCoin;

public interface BitCoinResponseListener  {
    public void onPriceRetrieved(String price);
    public void onFailureToRetrievePrice();
}
