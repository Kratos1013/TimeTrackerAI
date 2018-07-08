package com.krintos.timetrackerai.MainMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.krintos.timetrackerai.Fragments.Planner;
import com.krintos.timetrackerai.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment implements View.OnClickListener {
    private Button plans;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        plans = rootView.findViewById(R.id.plans);
        plans.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.plans:
                Fragment planner;
                planner = new Planner();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, planner,"planner0");
                ft.commit();
                break;
        }
    }
}
