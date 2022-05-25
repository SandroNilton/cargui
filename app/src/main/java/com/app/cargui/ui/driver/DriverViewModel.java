package com.app.cargui.ui.driver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DriverViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final MutableLiveData<String> mText;

    public DriverViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Driver fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}