package com.example.user.kgend.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.user.kgend.R;
import com.example.user.kgend.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/15.
 */

public class MineFragment extends Fragment {
    private static MineFragment fragment = null;
    private ExpandableListView mExpandableListView;
    private List<String> Group;
    private List<List<String>> Child;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_record, container, false);
        Group = new ArrayList<String>();
        Group.add("");
        mExpandableListView = (ExpandableListView)view.findViewById(R.id.all_list);
        RecordAdapter adapter = new RecordAdapter();
        mExpandableListView.setAdapter(adapter);
        return view;
    }
}

