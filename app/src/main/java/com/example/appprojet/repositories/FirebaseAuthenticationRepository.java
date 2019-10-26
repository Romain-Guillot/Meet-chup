package com.example.appprojet.repositories;


public class FirebaseAuthenticationRepository implements IAuthenticationRepository {

    private static FirebaseAuthenticationRepository instance = null;

    private FirebaseAuthenticationRepository() {

    }


    public static FirebaseAuthenticationRepository getInstance() {
        synchronized (FirebaseAuthenticationRepository.class) {
            if (instance == null)
                instance = new FirebaseAuthenticationRepository();
            return instance;
        }
    }


    @Override
    public void getUser() {

    }

    @Override
    public void classicSignIn() {

    }

    @Override
    public void classicSignUp() {

    }

    @Override
    public void signOut() {

    }
}
