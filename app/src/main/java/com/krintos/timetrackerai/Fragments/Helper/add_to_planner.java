package com.krintos.timetrackerai.Fragments.Helper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krintos.timetrackerai.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class add_to_planner extends Fragment {


    public add_to_planner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_planner, container, false);
    }

}
