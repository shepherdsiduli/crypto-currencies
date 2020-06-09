package com.shepherd.data.respository.datasource;

import androidx.lifecycle.LiveData;

public interface DataSource<T>{
    LiveData<T> getDataStream();
    LiveData<String> getErrorStream();
}
