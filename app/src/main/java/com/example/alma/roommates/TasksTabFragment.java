package com.example.alma.roommates;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TasksTabFragment extends android.support.v4.app.Fragment {

    static int ITEMS = 2 ;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate tab_layout and setup Views.
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        //Set an Apater for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        //Return fragment with respect to Position
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new FirstFragment();
                case 1 : return new FirstFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        //This method returns the title of the tab according to the position.
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "MY TASKS";
                case 1 :
                    return "ALL TASKS";
            }
            return null;
        }
    }

}