package com.app.cargui.ui.imei;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImeiViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final MutableLiveData<String> mText;

    public ImeiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("REINICIAR IMEI");
    }

    public LiveData<String> getText() {
        return mText;
    }
}