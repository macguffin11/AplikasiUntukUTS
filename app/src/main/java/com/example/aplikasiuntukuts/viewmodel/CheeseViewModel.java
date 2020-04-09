package com.example.aplikasiuntukuts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aplikasiuntukuts.data.Cheese;

import java.util.List;

public class CheeseViewModel extends AndroidViewModel {
    private CheeseRepository mRepository;
    private LiveData<List<Cheese>> mAllCheese;

    public CheeseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CheeseRepository(application);
        mAllCheese = mRepository.selectAll();
    }

    public void populateInitialData(int count) {
        mRepository.populateInitialData(count);
    }

    public LiveData<Integer> count(){
        return mRepository.count();
    }

    public LiveData<List<Cheese>> selectAll() {
        return mAllCheese;
    }

    public LiveData<List<Cheese>> selectById(long id){
        return mRepository.selectById(id);
    }

    public void insert(Cheese cheese) {
        mRepository.insert(cheese);
    }

    public void insertAll(Cheese[] cheeses){
        mRepository.insertAll(cheeses);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteById(long id){
        mRepository.deleteById(id);
    }

    public void update(Cheese cheese) {
        mRepository.update(cheese);
    }
}
