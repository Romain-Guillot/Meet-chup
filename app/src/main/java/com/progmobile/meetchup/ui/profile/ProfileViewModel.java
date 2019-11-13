package com.progmobile.meetchup.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.repositories.FirebaseAuthenticationRepository;
import com.progmobile.meetchup.repositories.IAuthenticationRepository;


/**
 * View model to handle the profile business logic, very basic, there are :
 *  - the user live data to get the logged user
 *  - the editMode live data to know if we currently in edition mode
 *
 * The user is retrieve when the viewmodel is built.
 */
public class ProfileViewModel extends ViewModel {

    private final IAuthenticationRepository authRepo;

    final LiveData<User> user;
    final MutableLiveData<Boolean> editMode = new MutableLiveData<>(false);

    public ProfileViewModel() {
        authRepo = FirebaseAuthenticationRepository.getInstance();
        user = authRepo.getObservableUser();
    }


    void signOut() {
        authRepo.signOut();
    }

}
