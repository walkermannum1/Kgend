package com.example.user.kgend;

import android.support.v4.app.Fragment;

/**
 * Created by user on 2016/10/31.
 */
public class SafeFragment extends Fragment {
    private static SafeFragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            synchronized (SafeFragment.class) {
                if (fragment == null) {
                    fragment = new SafeFragment();
                }
            }
        }
        return fragment;
    }
}
