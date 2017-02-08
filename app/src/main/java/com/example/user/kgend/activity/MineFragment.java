package com.example.user.kgend.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.user.kgend.data.PathRecord;
import com.example.user.kgend.R;
import com.example.user.kgend.adapter.DbAdapter;
import com.example.user.kgend.adapter.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/15.
 */

public class MineFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static MineFragment fragment = null;
    private RecordAdapter mAdapter;
    private ListView mAllRecordListView;
    private DbAdapter mDbhelper;
    private List<PathRecord> mAllRecord = new ArrayList<PathRecord>();
    private ExpandableListView mExpandableListView;
    public static final String RECORD_ID = "record_id";

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
        mAllRecordListView = (ListView)view.findViewById(R.id.all_list);
        mDbhelper = new DbAdapter(getActivity());
        mDbhelper.open();
        searchAllRecordfromDB();
        mAdapter = new RecordAdapter(getActivity(), mAllRecord);
        mAllRecordListView.setAdapter(mAdapter);
        mAllRecordListView.setOnClickListener((View.OnClickListener) this);
        return view;
    }

    private void searchAllRecordfromDB() {
        mAllRecord = mDbhelper.queryRecordAll();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PathRecord recordItem = (PathRecord) parent.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity().getApplicationContext(), ShowRecord.class);
        intent.putExtra(RECORD_ID, recordItem.getId());
        startActivity(intent);
        getActivity().finish();
    }
}

