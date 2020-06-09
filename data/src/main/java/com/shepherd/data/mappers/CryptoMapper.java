package com.shepherd.data.mappers;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepherd.data.entities.CryptoCoinEntity;
import com.shepherd.data.models.CoinModel;

import java.util.ArrayList;
import java.util.List;

public class CryptoMapper extends ObjectMapper {
    public final String CRYPTO_URL_PATH = "https://s2.coinmarketcap.com/static/img/coins/128x128/%s.png";
    public final String ENDPOINT_FETCH_CRYPTO_DATA = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=100";
    public final String API_KEY = "c5edc459-1455-4291-88ff-89fadab8c08a";

    /*private final String CRYPTO_URL_PATH =
            "https://files.coinmarketcap.com/static/img/coins/128x128/%s.png"; */
    public ArrayList<CryptoCoinEntity> mapJSONToEntity(String jsonStr) {
        ArrayList<CryptoCoinEntity> data = null;
        try {
            data = readValue(jsonStr, new TypeReference<ArrayList<CryptoCoinEntity>>() {
            });
        } catch (Exception e) {
        }
        return data;
    }
    @NonNull
    public List<CoinModel> mapEntityToModel(List<CryptoCoinEntity> data) {
        final ArrayList<CoinModel> listData = new ArrayList<>();
        CryptoCoinEntity entity;
        for (int i = 0; i < data.size(); i++) {
            entity = data.get(i);
            listData.add(new CoinModel(entity.getName(), entity.getSymbol(),
                    String.format(CRYPTO_URL_PATH, entity.getId()),entity.getPriceUsd(),
                    entity.get24hVolumeUsd(), Double.valueOf(entity.getMarketCapUsd())));
        }

        return listData;
    }

    public String mapEntitiesToString(List<CryptoCoinEntity> data) throws JsonProcessingException {
        return writeValueAsString(data);
    }
}
