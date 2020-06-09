package com.shepherd.data.respository.datasource;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shepherd.data.db.RoomDb;
import com.shepherd.data.entities.CryptoCoinEntity;

import java.util.List;

public class LocalDataSource implements DataSource<List<CryptoCoinEntity>>{
    private final RoomDb mDb;
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    public LocalDataSource(Context mAppContext) {
        mDb= RoomDb.getDatabase(mAppContext);
    }
    @Override
    public LiveData<List<CryptoCoinEntity>> getDataStream() {
        return mDb.coinDao().getAllCoinsLive();
    }
    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void writeData(List<CryptoCoinEntity> coins) {
        try {
            mDb.coinDao().insertCoins(coins);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }

    public List<CryptoCoinEntity> getALlCoins() {
        return mDb.coinDao().getAllCoins();
    }

    @VisibleForTesting
    public void deleteAllCoins()
    {
        mDb.coinDao().deleteAllCoins();
    }
}

