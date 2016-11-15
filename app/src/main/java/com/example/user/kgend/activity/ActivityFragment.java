package com.example.user.kgend.activity;

import android.support.v4.app.Fragment;


/**
 * Created by user on 2016/10/31.
 */
public class ActivityFragment extends Fragment {
    private static ActivityFragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            synchronized (ActivityFragment.class) {
                if (fragment == null) {
                    fragment = new ActivityFragment();
                }
            }
        }
        return fragment;
    }
}