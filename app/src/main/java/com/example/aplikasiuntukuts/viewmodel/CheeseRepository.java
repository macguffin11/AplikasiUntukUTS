package com.example.aplikasiuntukuts.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.aplikasiuntukuts.data.Cheese;
import com.example.aplikasiuntukuts.data.CheeseDao;
import com.example.aplikasiuntukuts.data.SampleDatabase;

import java.util.List;

public class CheeseRepository {
    private CheeseDao mCheeseDao;
    private LiveData<List<Cheese>> mAllCheese;
    SampleDatabase db;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CheeseRepository(Application application) {
        db = SampleDatabase.getInstance(application);
        mCheeseDao = db.cheese();
        mAllCheese = mCheeseDao.selectAll();
    }

    void populateInitialData(int count) {
        db.populateInitialData(count);
    }

    LiveData<Integer> count(){
        return mCheeseDao.count();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Cheese>> selectAll() {
        return mAllCheese;
    }

    LiveData<List<Cheese>> selectById(long id){
        return mCheeseDao.selectById(id);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Cheese cheese) {
        SampleDatabase.databaseWriteExecutor.execute(() -> {
            mCheeseDao.insert(cheese);
        });
    }

    void insertAll(Cheese[] cheeses){
        SampleDatabase.databaseWriteExecutor.execute(() ->{
            mCheeseDao.insertAll(cheeses);
        });
    }

    void deleteAll(){
        SampleDatabase.databaseWriteExecutor.execute(() ->{
            mCheeseDao.deleteAll();
        });
    }

    void deleteById(long id){
        SampleDatabase.databaseWriteExecutor.execute(() ->{
            mCheeseDao.deleteById(id);
        });
    }

    void update(Cheese cheese) {
        SampleDatabase.databaseWriteExecutor.execute(() -> {
            mCheeseDao.update(cheese);
        });
    }
}
