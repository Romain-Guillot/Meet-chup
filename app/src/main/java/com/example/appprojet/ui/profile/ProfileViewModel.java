package com.example.appprojet.ui.profile;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appprojet.models.User;
import com.example.appprojet.repositories.FirebaseAuthenticationRepository;
import com.example.appprojet.repositories.IAuthenticationRepository;

public class ProfileViewModel extends ViewModel {

    private IAuthenticationRepository authRepo;
    User user;

    MutableLiveData<Boolean> editMode = new MutableLiveData<>(false);

    public ProfileViewModel() {
        authRepo = FirebaseAuthenticationRepository.getInstance();
        user = authRepo.getUser();
    }


    void signOut() {
        authRepo.signOut();
    }

}
