package com.shepherd.cryptocurrencies.screens;

import com.shepherd.data.models.CoinModel;

import java.util.List;

public interface MainScreen {
    void updateData(List<CoinModel> data);
    void setError(String msg);
}
