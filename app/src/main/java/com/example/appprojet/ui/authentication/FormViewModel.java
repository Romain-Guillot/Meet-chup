package com.example.appprojet.ui.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;


/**
 *
 */
public abstract class FormViewModel extends ViewModel {

    protected final IAuthenticationRepository authenticationRepository;

    protected final MutableLiveData<Boolean> isLoadingLive = new MutableLiveData<>(false);
    protected final MutableLiveData<String> errorLive = new MutableLiveData<>();


    protected FormViewModel() {
        authenticationRepository = FirebaseAuthenticationRepository.getInstance();
    }

    protected abstract void submitForm();

    protected abstract boolean validate();

}
