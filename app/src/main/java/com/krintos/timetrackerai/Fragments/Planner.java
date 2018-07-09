package com.krintos.timetrackerai.Fragments;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.krintos.timetrackerai.Fragments.Helper.add_to_planner;
import com.krintos.timetrackerai.R;
import java.util.ArrayList;
import java.util.List;
public class Planner extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public Planner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_planner, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }
    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(getString(R.string.mon));
        adapter.addFragment(getString(R.string.tue));
        adapter.addFragment(getString(R.string.wed));
        adapter.addFragment(getString(R.string.thu));
        adapter.addFragment(getString(R.string.fri));
        adapter.addFragment(getString(R.string.sat));
        adapter.addFragment(getString(R.string.sun));

        viewPager.setAdapter(adapter);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        private FloatingActionButton add;
        private ListView listView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.planner_helper, container, false);
            final int  position = getArguments().getInt(ARG_SECTION_NUMBER);
            add = rootView.findViewById(R.id.add);
            listView = rootView.findViewById(R.id.listview);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment add;
                    add = new add_to_planner();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, add,"add_to_planner");
                    Bundle args = new Bundle();
                    args.putInt(ARG_SECTION_NUMBER, position);
                    add.setArguments(args);
                    ft.addToBackStack("add_to_planner");
                    ft.commit();
                }
            });
            return rootView;
        }
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mTitle = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(String title){
            mTitle.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
        @Override
        public int getCount() {
            return mTitle.size();
        }
    }
}
