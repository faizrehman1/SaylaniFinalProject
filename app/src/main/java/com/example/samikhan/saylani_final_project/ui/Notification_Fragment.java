package com.example.samikhan.saylani_final_project.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samikhan.saylani_final_project.R;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Notification_Fragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.noti_view,null);

        super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }
}
