package com.shepherd.data.respository.datasource;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shepherd.data.entities.CryptoCoinEntity;
import com.shepherd.data.mappers.CryptoMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteDataSource implements DataSource<List<CryptoCoinEntity>> {
    private static final String TAG = RemoteDataSource.class.getSimpleName();
    // public final String ENDPOINT_FETCH_CRYPTO_DATA = "https://api.coinmarketcap.com/v1/ticker/?limit=100";
    public final String CRYPTO_URL_PATH = "https://s2.coinmarketcap.com/static/img/coins/128x128/%s.png";
    public final String ENDPOINT_FETCH_CRYPTO_DATA = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=100";
    public final String API_KEY = "c5edc459-1455-4291-88ff-89fadab8c08a";
    private final RequestQueue mQueue;
    private final CryptoMapper mObjMapper;
    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<List<CryptoCoinEntity>> mDataApi = new MutableLiveData<>();

    public RemoteDataSource(Context appContext, CryptoMapper objMapper) {
        mQueue = Volley.newRequestQueue(appContext);
        mObjMapper = objMapper;
    }

    @Override
    public LiveData<List<CryptoCoinEntity>> getDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void fetch() {
        final JsonArrayRequest jsonObjReq =
                new JsonArrayRequest(ENDPOINT_FETCH_CRYPTO_DATA,
                        response -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot some network response");
                            final ArrayList<CryptoCoinEntity> data = mObjMapper.mapJSONToEntity(response.toString());
                            mDataApi.setValue(data);
                        },
                        error -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot network error");
                            mError.setValue(error.toString());
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("X-CMC_PRO_API_KEY", API_KEY);
                        return headers;
                    }
                };
        mQueue.add(jsonObjReq);
    }
}

