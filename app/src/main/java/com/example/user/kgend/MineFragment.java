package com.example.user.kgend;

import android.support.v4.app.Fragment;

/**
 * Created by user on 2016/10/31.
 */
public class MineFragment extends Fragment {
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
