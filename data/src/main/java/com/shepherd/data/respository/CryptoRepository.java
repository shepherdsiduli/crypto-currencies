package com.shepherd.data.respository;

import androidx.lifecycle.LiveData;

import com.shepherd.data.models.CoinModel;

import java.util.List;

public interface CryptoRepository {
    LiveData<List<CoinModel>> getCryptoCoinsData();
    LiveData<String> getErrorStream();
    LiveData<Double> getTotalMarketCapStream();
    void fetchData();
}
