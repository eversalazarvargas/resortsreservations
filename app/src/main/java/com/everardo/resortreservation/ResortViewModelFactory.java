package com.everardo.resortreservation;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

/**
 * @author everardo.salazar on 1/30/19
 */
public class ResortViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ResortViewModelFactory(Application context) {
        this.application = context;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ResortViewModel.class)) {
            return (T) new ResortViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}