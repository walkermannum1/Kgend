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

    /**
     * Created by user on 2016/10/31.
     */
    public static class MineFragment extends Fragment {
        private static MineFragment fragment = null;

        public static Fragment newInstance() {
            if (fragment == null) {
                synchronized (MineFragment.class) {
                    if (fragment == null) {
                        fragment = new MineFragment();
                    }
                }
            }
            return fragment;
        }

    }
}
