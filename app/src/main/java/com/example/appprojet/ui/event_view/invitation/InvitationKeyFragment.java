package com.example.appprojet.ui.event_view.invitation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appprojet.R;
import com.example.appprojet.utils.FormFragment;

public class InvitationKeyFragment extends FormFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_fragment, container, false);

        return view;
    }
}
